#! /usr/bin/env python3
"""
    prgtest: main program
"""
import os
import sys
import enum
import datetime
import shutil
import traceback
import subprocess
from typing import List
from collections import namedtuple
from pathlib import Path
from argparse import ArgumentParser
import git
import yaml

###################################################
# helping type definitions and global constants
###################################################

# Encapsulation of the out,err and return code of a subprocess execution
SubprocessResult = namedtuple('SubprocessResult', ['stdout', 'stderr', 'returncode'])

class TextType(enum.Enum):
    """ Type of text to be shown. It gives a hint about how to show it """
    GENERAL = enum.auto()   # undifferentiated text
    EXPECTED = enum.auto()  # it corresponds to the expected text
    FOUND = enum.auto()     # it corresponds to the text found after testing

# max time (seconds) allowed for a sigle test
DEFAULT_TIMEOUT = 10

# General naming and contexts
PROGRAM_DESCRIPTOR = "prgtest"
PROGRAM_VERSION = "2021-22"
INTROPRGDIR_KEY = "INTROPRGDIR"
INTROPRGSUPPORTDIR_KEY = "INTROPRGSUPPORTDIR"
DEFAULT_WORKING_DIR_NAME = "introprg"
SUPPORT_FOLDER_NAME = ".introprg"
SPECS_FOLDER_NAME = ".introprg/exercises"
FLAG_RELATIVE_PATH = "tmp/last.yaml"
TEST_RELATIVE_PATH = "tmp/test"
SPECS_FILENAME = "specs.yaml"
JUNIT_CLASSNAME = "TestExercise"
JUNIT_FILENAME = "TestExercise.java"
PRGTEST_AUTHOR_NAME = "prgtest"
PRGTESTCFG_PATH = Path(__file__).resolve().parent / 'prgtestcfg.yaml'


###################################################
# messages utilities
###################################################

# a dict containing the literals to be presented for messages
READABLE_MESSAGES = None
def getmsg(key: str) -> str:
    """ tries to get the human readable message corresponding to the given key.
        In case it is not present, it returns the key itself """
    global READABLE_MESSAGES
    if READABLE_MESSAGES is None:
        READABLE_MESSAGES = load_yaml(PRGTESTCFG_PATH, allow_non_existing=True)
    return READABLE_MESSAGES.get(key, key)


##################################################
# Colors
# Functionality to deal with terminal colors
##################################################

class Foreground():
    BLACK     = '\033[30m'
    RED       = '\033[31m'
    GREEN     = '\033[32m'
    YELLOW    = '\033[33m'
    BLUE      = '\033[34m'
    MAGENTA   = '\033[35m'
    CYAN      = '\033[36m'
    WHITE     = '\033[37m'
    ORANGE    = '\033[33m'
    RESET     = '\033[39m'


class Background():
    BLACK     = '\033[40m'
    RED       = '\033[41m'
    GREEN     = '\033[42m'
    YELLOW    = '\033[43m'
    BLUE      = '\033[44m'
    MAGENTA   = '\033[45m'
    CYAN      = '\033[46m'
    WHITE     = '\033[47m'
    DARKGRAY  = '\033[100m'
    LIGHTGREEN = '\033[102m'
    LIGHTBLUE  = '\033[104m'
    RESET      = '\033[49m'

# color schemas
color_schemata = {
    "clear": {
        "FG_GENERAL_CODE": Foreground.WHITE,
        "BG_GENERAL_CODE": Background.RESET,
        "FG_EXPECTED_CODE": Foreground.GREEN,
        "BG_EXPECTED_CODE": Background.RESET,
        "FG_FOUND_CODE": Foreground.BLUE,
        "BG_FOUND_CODE": Background.RESET,
        "FG_DIFF_MARK": Foreground.RED,
        "BG_DIFF_MARK": Background.RESET,
        "FG_LINE_NUMBER": Foreground.MAGENTA,
        "BG_LINE_NUMBER": Background.RESET,
        "FG_PASS_TEST": Foreground.GREEN,
        "BG_PASS_TEST": Background.RESET,
        "FG_FAIL_TEST": Foreground.BLACK,
        "BG_FAIL_TEST": Background.RED,
        "FG_JAVAC_ARGS": Foreground.CYAN,
        "BG_JAVAC_ARGS": Background.RESET,
        "FG_JAVAC_CMD": Foreground.MAGENTA,
        "BG_JAVAC_CMD": Background.RESET,
        "FG_WARNING": Foreground.RED,
        "BG_WARNING": Background.RESET,
        "FG_ERROR": Foreground.RED,
        "BG_ERROR": Background.RESET,
    },
    "dark": {
        "FG_GENERAL_CODE": Foreground.WHITE,
        "BG_GENERAL_CODE": Background.RESET,
        "FG_EXPECTED_CODE": Foreground.GREEN,
        "BG_EXPECTED_CODE": Background.DARKGRAY,
        "FG_FOUND_CODE": Foreground.BLUE,
        "BG_FOUND_CODE": Background.DARKGRAY,
        "FG_DIFF_MARK": Foreground.WHITE,
        "BG_DIFF_MARK": Background.RESET,
        "FG_LINE_NUMBER": Foreground.ORANGE,
        "BG_LINE_NUMBER": Background.RESET,
        "FG_PASS_TEST": Foreground.GREEN,
        "BG_PASS_TEST": Background.RESET,
        "FG_FAIL_TEST": Foreground.BLACK,
        "BG_FAIL_TEST": Background.RED,
        "FG_JAVAC_ARGS": Foreground.YELLOW,
        "BG_JAVAC_ARGS": Background.RESET,
        "FG_JAVAC_CMD": Foreground.WHITE,
        "BG_JAVAC_CMD": Background.RESET,
        "FG_WARNING": Foreground.YELLOW,
        "BG_WARNING": Background.RESET,
        "FG_ERROR": Foreground.RED,
        "BG_ERROR": Background.RESET,
    }
}

def get_color(key):
    """ returns the color for the given key.
    If not present, it returns general reset value """
    colorschema = os.environ.get('INTROPRG_COLORSCHEMA', 'clear')
    if colorschema not in color_schemata:
        print_warning_and_continue(
            f"No disponible l'esquema de colors {colorschema}",
            tip="Revisa el valor de la variable INTROPRG_COLORSCHEMA",
            plain_color=True)
    return color_schemata.get(
        os.environ.get('INTROPRG_COLORSCHEMA', 'clear'),
        color_schemata.get('clear')
    ).get(key, "\033[0m")


##################################################
# IO utilities
##################################################

def colorize_string(text, forecolor=Foreground.RESET, backcolor=Background.RESET):
    """ returns a colorized version of this text """
    return f"\033[0m{forecolor}{backcolor}{text}\033[0m"


def compose_enumerated_text(lines: List[str],          # list of lines to be shown
                            text_type=TextType.GENERAL,
                            highlight_line=-1,
                            highlight_function=None):
    """ composes the lines enumerated """
    width = len(str(len(lines) + 1))
    result = "\n".join(compose_enumerated_line(n, line, width=width,
                                               text_type=text_type,
                                               highlighted=(n==highlight_line),
                                               highlight_function=highlight_function)
                       for n, line in enumerate(lines))
    return result


def compose_enumerated_line(linenr, line, width=0,
                            text_type=TextType.GENERAL,
                            delimited=False,
                            highlighted=False,
                            highlight_function=None):
    """ composes the line prepending the linenr """
    if text_type == TextType.EXPECTED:
        forecolor = get_color("FG_EXPECTED_CODE")
        backcolor = get_color("BG_EXPECTED_CODE")
    elif text_type == TextType.FOUND:
        forecolor = get_color("FG_FOUND_CODE")
        backcolor = get_color("BG_FOUND_CODE")
    else:
        forecolor = get_color("FG_GENERAL_CODE")
        backcolor = get_color("BG_GENERAL_CODE")
    if highlighted or (highlight_function is not None and highlight_function(line)):
        spaces = colorize_string('!',
                                 forecolor=get_color("FG_DIFF_MARK"),
                                 backcolor=get_color("BG_DIFF_MARK")) + '  '
    else:
        spaces = '   '
    if delimited:
        delimiter = colorize_string('|',
                                    forecolor=get_color("FG_DIFF_MARK"),
                                    backcolor=get_color("BG_DIFF_MARK"))
    else:
        delimiter = ''
    num = colorize_string(str(linenr + 1).rjust(width),
                          forecolor=get_color("FG_LINE_NUMBER"),
                          backcolor=get_color("BG_LINE_NUMBER"))
    return f"{num}{spaces}{delimiter}{colorize_string(line, forecolor=forecolor, backcolor=backcolor)}{delimiter}"


def compose_title(title, underline='='):
    """ composes the given title underlined with the specified character """
    return f"\n{title}\n{underline * len(title)}\n"


def print_err(text=''):
    """ prints the given text on stderr """
    print(text, file=sys.stderr)


def print_error_and_exit(msg: str, tip: str = None,
                         plain_color = False):
    """ prints the message and stops execution with error code
        if tip is set, it shows it in a different line.
        if plain_color, it does not try to colorize"""
    error_mark = 'ERROR:'
    tab=" " * (len(error_mark) + 1)
    if not plain_color:
        error_mark = colorize_string(error_mark,
                                     forecolor=get_color('FG_ERROR'),
                                     backcolor=get_color('BG_ERROR'))

    print_err()
    print_err(f"{error_mark} {msg}")
    if tip is not None:
        print_err()
        for line in tip.splitlines():
            print_err(tab + line)
    print_err()
    sys.exit(1)


def print_warning_and_continue(msg: str, tip: str = None,
                               plain_color = False):
    """ prints the message to the stderr and continues with execution.
    if tip is set, it shows it in a different line.
        if plain_color, it does not try to colorize"""
    warning_mark = 'WARNING:'
    tab=" " * (len(warning_mark) + 1)
    if not plain_color:
        warning_mark = colorize_string(warning_mark,
                                       forecolor=get_color('FG_WARNING'),
                                       backcolor=get_color('BG_WARNING'))
    print_err(f"{warning_mark} {msg}")
    if tip is not None:
        print_err()
        for line in tip.splitlines():
            print_err(tab + line)
    print_err()


def load_yaml(path, allow_non_existing=False):
    """ loads and returns a dict with the values kept in a yaml file
    In case allow_non_existing, it returns an empty dict when the file
        doesn't exist
        Otherwise, on non existing file it breaks execution """
    if not path.exists():
        if allow_non_existing:
            return dict()
        print_error_and_exit(f"File not found {path}")
    with open(path, 'r') as fd:
        data = yaml.load(fd, Loader=yaml.SafeLoader)
    if data is None:
        return dict()
    return data


def save_yaml(path, values):
    """ saves the values as yaml file """
    path.parent.mkdir(parents = True, exist_ok = True)
    with open(path, 'w') as fd:
        yaml.safe_dump(values, fd, allow_unicode=True)

##################################################
# running system commands
##################################################

def run_command(command_list, folder, env=None, stdin=None, timeout=None):
    """ runs command in command_list with the given environment
    and returns the corresponding SubprocessResult """
    previous_dir = Path.cwd()
    os.chdir(folder)
    result = subprocess.run(command_list, env=env,
                            input=stdin, timeout=timeout,
                            capture_output=True, encoding='utf-8')
    os.chdir(previous_dir)
    return SubprocessResult(stdout=result.stdout.splitlines(),
                            stderr=result.stderr.splitlines(),
                            returncode=result.returncode)


def run_javac(program_name, env, folder, full_compilation=True):
    """ compile program_name in folder with the given environment.
    if full_compilation it will remove any .class prior to compile.
        It returns the SubprocessResult """
    if full_compilation:
        for compiled_file in folder.glob("*.class"):
            compiled_file.unlink()
    command_list = ['javac', program_name]
    result = run_command(command_list, folder, env=env)
    return result


def run_java(class_name, folder, env, timeout,
             stdin=None, argsin=None):
    """ runs java class_name,
    on folder,
        passing argsin as command line arguments,
        passing stdin as standard input,
        considering env as environment,
        giving as much as timeout time to finish execution.
        It returns the SubprocessResult """

    # Prepare command
    command_list = ['java', class_name]

    # Add args if any
    if argsin is not None:
        command_list.extend(argsin)

    # run the target having into account timeout
    try:
        return run_command(command_list, folder, env=env, stdin=stdin, timeout=timeout)
    except subprocess.TimeoutExpired:
        return SubprocessResult(stdout=[], stderr=[], returncode=124)


##################################################
# git utilities
##################################################

def reset_autocommit_flag(working_dir, target_exercise, repo):
    """ creates or resets the autocommit flag """
    flag_path = get_flag_path(working_dir)
    old_contents = load_yaml(flag_path) if flag_path.is_file() else {}
    current_commit = repo.head.commit
    current_sha = current_commit.hexsha
    current_msg = current_commit.message.strip()
    msg = old_contents.get('msg') if old_contents.get('sha') == current_sha else current_msg
    contents = {
        'msg': msg,
        'seq': 0,
        'exercise': target_exercise,
        'sha': current_sha,
    }
    if old_contents != contents:
        save_yaml(flag_path, contents)


def get_git_repo(folder):
    """ checks folder is a git repository
    If so, it builds a git.Repo instance. Otherwise it halts execution """
    try:
        return git.Repo(folder, search_parent_directories=True)
    except git.InvalidGitRepositoryError:
        print_error_and_exit(getmsg('MSG_ERR_NON_GIT') % folder)


def try_commit(repo, exercise_id, seq, message):
    """ performs the autocommit on repo with the given seq nr and the student
    message """
    author = git.Actor(PRGTEST_AUTHOR_NAME, repo.head.commit.author.email)
    comment = getmsg('MSG_AUTOCOMMIT') % (seq, exercise_id, message)
    try:
        repo.git.add(all=True)
        repo.index.commit(message=comment, author=author)
    except git.GitError:
        return False
    return True


def do_autocommit(working_dir, target_exercise, repo):
    """ sets the exercise flag considering last commit and if not the first one,
    performs the autocommit.
        It returns True when the autocommit has been performed """

    flag_path = get_flag_path(working_dir)
    old_contents = load_yaml(flag_path) if flag_path.is_file() else {}
    splitted_last_comment = repo.head.commit.message.split(": ", maxsplit=2)
    same_exercise = old_contents.get('exercise') == target_exercise
    old_seq = old_contents.get('seq', -1)
    expected_msg = old_seq == 0 or (
        len(splitted_last_comment) == 2 and
        old_contents.get('msg') == splitted_last_comment[1]
    )
    if same_exercise and expected_msg:
        seq = old_seq + 1
        msg = old_contents['msg']
        exercise_id = extract_exercise_id(target_exercise)
        try_commit(repo, exercise_id, seq, msg)
        contents = {
            'msg': msg,
            'seq': seq,
            'exercise': target_exercise,
            'sha': repo.head.commit.hexsha,
        }
        save_yaml(flag_path, contents)
    else:
        reset_autocommit_flag(working_dir, target_exercise, repo)


def check_git(working_dir, target_exercise):
    """ checks whether the target exercise is properly managed by git.

        Current interpretation of "properly" consists on:
        - all the files in the given folder have been added to git
        - all the changes in files in the whole repository have been committed

        It is acceptable that some files in other folders are not staged .

        It is not acceptable that there are uncommitted files in other
        folders since they will get automatically committed when
        autocommit. Therefore, prgtest should complain and propose
        committing or reverting them """

    def unstaged_files_in_target_folder(target_exercise, repo):
        """ returns the list of unstaged files in the target folder """
        return [path.split('/', maxsplit=1)[1]
                for path in repo.untracked_files
                if path.startswith(target_exercise)]

    def same_exercise_as_flagged():
        """ returns True if the flagged exercise matches the target """
        flag_path = get_flag_path(working_dir)
        flag_contents = load_yaml(flag_path) if flag_path.is_file() else {}
        return flag_contents.get('exercise') == target_exercise

    repo = get_git_repo(working_dir)
    # Check unstaged files on exercise folder first
    unstaged = unstaged_files_in_target_folder(target_exercise, repo)
    if unstaged:
        print_error_and_exit(
            getmsg('MSG_ERR_NON_STAGED'),
            tip=getmsg('MSG_ERR_NON_STAGED_TIP') % " ".join(unstaged))

    # check uncommitted files
    if repo.is_dirty():
        #if is_autocommittable(working_dir, target_exercise):
        if same_exercise_as_flagged():
            do_autocommit(working_dir, target_exercise, repo)
        else:
            print_error_and_exit(getmsg('MSG_ERR_MISSING_COMMIT'),
                                 tip=getmsg('MSG_ERR_MISSING_COMMIT_TIP'))
    elif not same_exercise_as_flagged():
        reset_autocommit_flag(working_dir, target_exercise, repo)


##################################################
# context utilities
##################################################

def get_working_dir():
    """ tries to get the working directory from the environment.
    The working directory must be defined by the environment variable
        INTROPRGDIR, otherwise it halts execution.
        On success it returns the path to the working directory """
    path = os.environ.get(INTROPRGDIR_KEY)
    if path is None:
        print_error_and_exit(getmsg('MSG_ERR_INTROPRGDIR_ENV_NOT_FOUND'),
                             tip=getmsg('MSG_ERR_INTROPRGDIR_ENV_NOT_FOUND_TIP'))
    path = Path(path)
    if not path.is_dir():
        print_error_and_exit(getmsg('MSG_ERR_INTROPRGDIR_NOT_FOUND') % path,
                             tip=getmsg('MSG_ERR_INTROPRGDIR_NOT_FOUND_TIP'))
    return path


def get_support_dir(working_dir):
    """ returns the path to the dir containing the support files. """
    support_dir = working_dir / SUPPORT_FOLDER_NAME
    if not support_dir.is_dir():
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    return support_dir


def get_flag_path(working_dir):
    """ returns the path to the flag file used for autocommit """
    return get_support_dir(working_dir) / FLAG_RELATIVE_PATH


def get_target_exercise(working_dir):
    """ it gets the name of the target exercise from the current folder
    as long as it is a direct subfolder of the working directory.
        Otherwise it halts execution.  """
    current = Path.cwd()
    if working_dir != current.parent:
        print_error_and_exit(getmsg('MSG_ERR_OUT_OF_WORKINGDIR'),
                             tip=getmsg('MSG_ERR_OUT_OF_WORKINGDIR_TIP') % working_dir)
    return current.name


def gets_specs_folder(working_dir):
    """ composes and returns the path to the folder containing the exercise specs. """
    specs_dir = working_dir / SPECS_FOLDER_NAME
    if not specs_dir.is_dir():
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    return specs_dir


def get_specs(specs_folder, target_exercise):
    """ obtains the test specifications in specs_folder.
        specs should be placed as a yaml file named as target_exercise in the
        support folder """
    path = specs_folder / f'{target_exercise}.yaml'
    if not path.is_file():
        print_error_and_exit(getmsg('MSG_ERR_NOT_AN_EXERCISE'),
                             tip=getmsg('MSG_ERR_NOT_AN_EXERCISE_TIP'))
    specs = load_yaml(path)
    if specs and '_mainclass' not in specs:
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    normalize_specs(specs)
    return specs


def get_test_path(working_dir):
    """ returns the path where to perform the test """
    return  get_support_dir(working_dir) / TEST_RELATIVE_PATH


def get_junit_path(specs_folder, target):
    """ obtains the path to the junit test file for the target exercise
    if any. Otherwise it returns None """
    path = specs_folder / f'{target}.junit'
    return path if path.is_file() else None


def has_junit_test(working_dir, target):
    """ returns True when there's a junit test for the target exercise """
    path = get_junit_path(working_dir, target)
    return path is not None and path.is_file()


def check_testable(specs, junit):
    """ checks whether the target exercise has tests to perform.
    An exercise is considered not to have tests if the corresponding
        IO specs do not specify a main class and there's no junit.
        In case there's no test, it halts execution but it is not considered an
        error """
    if '_mainclass' not in specs and junit is None:
        print(getmsg('MSG_EXERCISE_WITHOUT_TEST'))
        sys.exit(0)


def check_main_file(specs):
    """ ensures the main class specified in specs is present in cwd. 
        Otherwise halts execution """
    if '_mainclass' not in specs:
        return
    main_filename = f"{specs['_mainclass']}.java"
    path = Path.cwd() / main_filename
    if not path.is_file():
        print_error_and_exit(getmsg('MSG_ERR_EXERCISE_WITHOUT_MAIN_FILE') %
                             main_filename)


def check_compiled():
    """ checks whether all the source java programs in cwd have been compiled
        after last change in sources. That is, there's a .class for each .java
        whose updated date is newer than the date of it's source file """
    cwd = Path.cwd()
    for srcfile in cwd.rglob("**/*.java"):
        compiledfile_name = f"{srcfile.stem}.class"
        compiledfile = srcfile.parent / compiledfile_name
        missing_compile = srcfile.relative_to(cwd)
        if not compiledfile.is_file():
            print_error_and_exit(
                getmsg('MSG_ERR_NON_COMPILED_SOURCE_FILE') % compiledfile_name,
                tip=getmsg('MSG_ERR_NON_COMPILED_SOURCE_FILE_TIP') % missing_compile)
        # check dates
        if srcfile.stat().st_mtime > compiledfile.stat().st_mtime:
            print_error_and_exit(
                getmsg('MSG_ERR_OUTDATED_COMPILED_SOURCE_FILE') % missing_compile,
                tip=getmsg('MSG_ERR_OUTDATED_COMPILED_SOURCE_FILE_TIP') %
                missing_compile)


def extract_exercise_id(exercise_name):
    """ extracts the exercise id (BB_NN) from exercise name """
    return "_".join(exercise_name.split('_', maxsplit=2)[:2])


def prepare_test(working_dir):
    """ prepares the test environment.
        The test env is a temporary folder that will hold all the rellevant
        files n cwd for the test.  These files include: all the target's
        contents and the junit file if present.
        The test_path is place in a gitignored folder and will be removed
        on each prgtest execution. For these reasons, it is not necessary
        to worry about cleaning it up after prgtest """
    test_path = get_test_path(working_dir)
    if test_path.exists():
        shutil.rmtree(test_path)
    shutil.copytree(Path.cwd(), test_path)


def compute_environment_variables():
    """ computes the environment where the tests will be performed.
        It includes specially the CLASSPATH var """
    env = os.environ.copy()
    classpath = env.get('CLASSPATH', '')
    introprgdir_path = Path(__file__).parent
    if not classpath:
        env['CLASSPATH'] = f'.:{introprgdir_path}'
    elif introprgdir_path not in classpath.split(':'):
        env['CLASSPATH'] = f"{env['CLASSPATH']}:{introprgdir_path}"
    return env


##################################################
# Specs utilities
##################################################

def get_main_class(specs):
    """ returns the name of the class containing the main() function in the exercise """
    main_class = specs.get('_mainclass')
    if main_class is None:
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    return main_class


def get_main_program(specs):
    """ returns the name of the file containing the main() function """
    return f"{get_main_class(specs)}.java"


def normalize_specs(specs):
    """ normalizes the given IO specs """
    for testid, testspecs in specs.items():
        if testid.startswith('_'):
            continue
        if testspecs.get('stdin') is not None:
            testspecs['stdin'] = normalize_entry_spec(testspecs.get('stdin'))
        if testspecs.get('argsin') is not None:
            testspecs['argsin'] = normalize_entry_spec(testspecs.get('argsin'))
        if testspecs.get('stdout') is not None:
            testspecs['stdout'] = normalize_entry_spec(testspecs.get('stdout'))


def normalize_entry_spec(entry):
    """ normalizes an entry spec (i.e. the value of stdin, argsin, etc.)
    The value of an entry spec can be a single string or a list of strings.
        After normalization, the result will be always a list. Even if empty.
        In case entry is None, it returns None """
    if entry is None or isinstance(entry, list):
        return entry
    return [entry]


##################################################
# test utilities
##################################################

def perform_io_tests(test_path, target_exercise, specs, env):
    """ performs the i/o tests specified on specs for target """
    for testid, testspecs in specs.items():
        if testid.startswith('_'):
            continue
        stdin=testspecs.get('stdin')
        argsin=testspecs.get('argsin')
        result = run_io_tests(test_path, specs, env, stdin, argsin)
        compare_io_result(target_exercise, specs, testid, result)


def run_io_tests(test_path, specs, env, stdin=None, argsin=None):
    """ runs the target with the specified stdin and returns the subprocessresult """

    # compile target with the given environment
    result = run_javac(program_name=get_main_program(specs),
                       folder=test_path, env=env)
    if result.returncode != 0:
        print_error_and_exit(getmsg('MSG_ERR_COMPILATION') % get_main_program(specs),
                             tip="\n".join(result.stderr))

    # run test with the given stdin, args, env and timeout.
    stdin = '' if stdin is None else '\n'.join(str(item) for item in stdin) + '\n'
    return run_java(class_name=get_main_class(specs),
                    folder=test_path,
                    stdin=stdin, argsin=argsin,
                    env=env, timeout=get_timeout(specs))


def compare_io_result(target_exercise, specs, testid, test_result):
    """ compares the test_result found when running target on testid with
    the expected one """

    def prepare_output(target_exercise, specs, output):
        """ prepares the output: - it has translations applied if any """
        translatespecs = specs.get('_tr')
        if translatespecs is None:
            return output
        if len(translatespecs) != 2:    # illdefined specs
            raise AttributeError(f"illdefined _tr for exercise {target_exercise}")
        if not translatespecs[0]:
            raise AttributeError(f"illdefined _tr for exercise {target_exercise}")
        if len(translatespecs[0]) != len(translatespecs[1]):
            raise AttributeError(f"illdefined _tr for exercise {target_exercise}")
        for chsrc in translatespecs[0]:
            output = [line.replace(chsrc, translatespecs[1]) for line in output]
        return output

    def compare_output(target_exercise, specs, stdout):
        """ compares stdout with expected output as defined in specs """
        ignore_blank_lines = specs.get('_ignore_blank_lines', True)
        expected = specs[testid].get('stdout')
        if expected is None:
            return None
        output = prepare_output(target_exercise, specs, stdout)
        result = compare_lines(expected, output,
                               ignore_blank_lines=ignore_blank_lines)
        return result

    if test_result.returncode == 0:
        result = compare_output(target_exercise, specs, test_result.stdout)
        if result is None:
            show_test_passed(testid)
            return

    show_test_failed(testid)
    show_program_execution(provided_argsin=specs[testid].get('argsin'),
                           main_class=get_main_class(specs))

    show_provided_stdin(specs[testid].get('stdin'))

    if test_result.returncode == 0:
        nr_expected, nr_found = result
        show_discrepancy(specs, testid,
                         nr_expected, nr_found,
                         test_result.stdout)
    elif test_result.returncode == 124:    # timeout
        print_error_and_exit(getmsg('MSG_ERR_EXERCISE_NEVER_ENDS'),
                             tip=getmsg('MSG_ERR_EXERCISE_NEVER_ENDS_TIP'))
    else:
        show_found_stderr(test_result.stderr)
        print_error_and_exit(getmsg('MSG_ERR_EXERCISE_BREAKS'),
                             tip=getmsg('MSG_ERR_EXERCISE_BREAKS_TIP'))
    sys.exit(1)


def perform_junit_tests(test_path, junit_path, env, timeout):
    """ performs the JUnit tests """
    if junit_path is None:
        return

    # copy junit test file
    copy_junit(junit_path, dest_path=test_path)
    #dest_path = test_path / JUNIT_FILENAME
    #shutil.copy(junit_path, dest_path)

    # perform the tests
    result = run_junit_tests(test_path, env, timeout)
    compare_junit_result(result)


def run_junit_tests(test_path, env, timeout):
    """ runs the JUnit test and returns the SubprocessResult  """
    # compile junit tests
    result = run_javac(program_name=JUNIT_FILENAME,
                       folder=test_path,
                       env=env)
    if result.returncode != 0:
        print_error_and_exit(getmsg('MSG_ERR_COMPILATION_JUNIT'),
                             tip="\n".join(result.stderr))

    # run junit tests
    argsin = ['-c', JUNIT_CLASSNAME,
              '--disable-banner', '--fail-if-no-tests',]
    return run_java(class_name='org.junit.platform.console.ConsoleLauncher',
                    folder=test_path,
                    env=env,
                    timeout=timeout,
                    argsin=argsin,)


def show_test_failed(testid):
    """ shows the test has failed """
    text = colorize_string(f" {getmsg('MSG_TEXT_FAILED_TEST')} ",
                           forecolor=get_color("FG_FAIL_TEST"),
                           backcolor=get_color("BG_FAIL_TEST"))
    print(f"Test {testid}: {text}")


def show_found_output(nr_found, stdout):
    """ shows the found contents from the target program execution """
    show_output_on_stderr(
        title=getmsg('MSG_TITLE_STANDARD_OUTPUT'),
        msg="La sortida que ha generat el programa ha estat:\n",
        output=stdout,
        text_type=TextType.FOUND,
        highlight_line=nr_found)


def show_found_stderr(stderr):
    """ shows the contents stderr from the target program execution on stderr """
    print_err(compose_title(getmsg('MSG_TITLE_STANDARD_ERROR')))
    print_err("La sortida d'error que ha generat el programa ha estat:")
    print_err(compose_enumerated_text(stderr,
                                      highlight_function=lambda line: not 'at java.' in line))


def show_found_junit_error(junit_output):
    """ shows the junit output found when passing the failing tests """
    def digest_output(junit_output):
        """ returns a filtered version of the output that includes just one failure """
        test_name = ''
        test_descr = ''
        fail_descr = ''
        found_first = False
        for line in junit_output:
            if not found_first:
                if f'JUnit Jupiter:{JUNIT_CLASSNAME}' in line:
                    found_first = True
                    splitted = line.split(":")
                    test_descr = splitted[2] if len(splitted) >2 else line
                continue
            if 'MethodSource' in line:
                splitted = line.split("'")
                test_name = splitted[3] if len(splitted) > 3 else line
                continue
            if '==>' in line:
                splitted = line.split(': ')
                fail_descr = splitted[1].split("==>")[0] if len(splitted) > 1 else line
                break
        return [
            f'Nom del test: {test_name}',
            f'Descripció  : {test_descr}',
            f'Problema    : {fail_descr}',
        ]

    print_err(compose_title(getmsg('MSG_TITLE_JUNIT_ERROR')))
    print_err(getmsg('MSG_ERR_JUNIT_NOTICE'))

    print_err("\t" + "\n\t".join(junit_output))

    print_err(getmsg('MSG_ERR_JUNIT_TIP'))
    print_err("* " + "\n* ".join(digest_output(junit_output)))


def show_output_on_stderr(title: str,           # title of the error section
                          msg: str,             # subtitle of the error section
                          output: str,          # output to be shown
                          text_type:TextType=None,
                          highlight_line=-1):   # line to be hightligte (-1 none)
    """ shows the given output on stderr """
    print_err(compose_title(title))
    print_err(msg)
    print_err(compose_enumerated_text(output, text_type=text_type, highlight_line=highlight_line))


def show_discrepancy(specs, testid, nr_expected, nr_found, stdout):
    """ shows discrepancy between expected and found """
    expected_output = specs[testid].get('stdout', [])
    #expected_output = expected_output_str.split('\n') if expected_output_str else []
    show_expected_output(expected_output, nr_expected)
    show_found_output(nr_found, stdout)
    show_difference(expected_output, nr_expected, nr_found, stdout)


def show_expected_output(expected_output, nr_expected):
    """ shows the contents expected from the target program execution """
    if expected_output:
        show_output_on_stderr(
            title=getmsg('MSG_TITLE_EXPECTED_OUTPUT'),
            msg="S'esperava la següent sortida del programa:\n",
            output=expected_output,
            text_type=TextType.EXPECTED,
            highlight_line=nr_expected)


def show_difference(expected_output, nr_expected, nr_found, stdout):
    """ shows the difference between expected and stdout """
    print_err(compose_title(getmsg('MSG_TITLE_DISCREPANCY')))
    if nr_expected >= len(expected_output):
        print_err(getmsg('MSG_ERR_EXERCISE_WITH_MORE_LINES_THAN_EXPECTED'))
        print_err(compose_enumerated_line(nr_found,
                                          stdout[nr_found], text_type=TextType.FOUND))
    elif nr_found >= len(stdout):
        print_err(getmsg('MSG_ERR_EXERCISE_WITH_LESS_LINES_THAN_EXPECTED'))
        print_err(compose_enumerated_line(nr_expected,
                                          expected_output[nr_expected],
                                          text_type=TextType.EXPECTED))
    else:
        print_err(getmsg('MSG_ERR_EXERCISE_WITH_DIFFERENT_LINES'))
        print_err(compose_enumerated_line(nr_expected,
                                          expected_output[nr_expected],
                                          text_type=TextType.EXPECTED,
                                          delimited=True))
        print_err(compose_enumerated_line(nr_found, stdout[nr_found],
                                          text_type=TextType.FOUND,
                                          delimited=True))


def compare_lines(expected, found, ignore_blank_lines = True):
    """ compares two lists of lines and:
    - when both lists are considered equals, it returns None
        - otherwise it returns a pair of numbers corresponding to the lines
          where the first discrepancy is found (line_expected, line_found).
          Note: the two lines can be different because of ignore_blank_lines """
    nr_expected = nr_found = 0
    if found is None:
        found = ''
    while nr_expected < len(expected) and nr_found < len(found):
        line_expected = expected[nr_expected]
        line_found = found[nr_found]

        if line_expected == '' and ignore_blank_lines:
            nr_expected += 1
            continue
        if line_found == '' and ignore_blank_lines:
            nr_found += 1
            continue

        if line_expected != line_found:
            return (nr_expected, nr_found)
        nr_expected += 1
        nr_found += 1

    while nr_found < len(found):
        line_found = found[nr_found]
        if line_found == '' and ignore_blank_lines:
            nr_found += 1
            continue
        return (nr_found, nr_found)

    while nr_expected < len(expected):
        line_expected = expected[nr_expected]
        if line_expected == '' and ignore_blank_lines:
            nr_expected += 1
            continue
        return (nr_expected, nr_expected)

    return None


def compare_junit_result(test_result):
    """ compares the results of the junit tests """
    if test_result.returncode == 0:
        show_test_passed('JUnit')
        return
    show_test_failed('JUnit')
    show_found_junit_error(test_result.stdout)
    sys.exit(1)


def show_test_passed(testid):
    """ shows the test has passed """
    text = colorize_string(f" {getmsg('MSG_TEXT_PASSED_TEST')} ",
                           forecolor=get_color("FG_PASS_TEST"),
                           backcolor=get_color("BG_PASS_TEST"))
    print(f"Test {testid}: {text}")


def get_timeout(specs):
    """ returns the timeout for the execution of the target program """
    return specs.get('_timeout', DEFAULT_TIMEOUT)


def show_provided_stdin(provided_stdin):
    """ shows the contents entered by stdin to the target program (if any) """
    if provided_stdin is not None:
        show_output_on_stderr(
            title=getmsg('MSG_TITLE_STANDARD_INPUT'),
            msg="Se li ha passat el següent codi per entrada estàndard\n",
            output=provided_stdin,
        )


def show_program_execution(provided_argsin, main_class):
    """ shows the command line call to the target program including arguments """
    argsin = '' if provided_argsin is None else provided_argsin
    if argsin is not None:
        argsin = ' '.join(argsin)
    print_err(compose_title(getmsg('MSG_TITLE_PROGRAM_EXECUTION')))
    print_err("L'execució ha estat la següent:\n")
    colorized_command = colorize_string(f'java {main_class}',
                                        forecolor=get_color('FG_JAVAC_CMD'),
                                        backcolor=get_color('BG_JAVAC_ARGS'))
    colorized_args = colorize_string(argsin,
                                     forecolor=get_color('FG_JAVAC_ARGS'),
                                     backcolor=get_color('BG_JAVAC_ARGS'))
    print_err(f"$ {colorized_command} {colorized_args}")


##################################################
# Command line arguments
##################################################

def process_params():
    """ processes the commandline arguments """
    parser = ArgumentParser(prog=PROGRAM_DESCRIPTOR,
                            description=getmsg('PROGRAM_DESCRIPTION'))
    parser.add_argument("-v", "--version",
                        action='store_true',
                        help=getmsg('MSG_HELP_VERSION'))
    parser.add_argument("-j", "--copy-junit",
                        action='store_true',
                        help=getmsg('MSG_HELP_COPY_JUNIT'),)
    args = parser.parse_args()
    params = {k:v for k,v in vars(args).items() if v}
    return params


def check_commandline_option_version(params):
    """ checks option --version in commandline params.
        If present, it shows the version and ends execution """
    if 'version' in params:
        print(f"{PROGRAM_DESCRIPTOR} versió {PROGRAM_VERSION}")
        sys.exit(0)

def check_commandline_option_copy_junit(junit_path, params):
    """ checks option --copy-junit in commandline params
        If present, it tries to copy the junit test file in cwd and ends execution """
    if 'copy_junit' in params:
        if junit_path is None:
            print_error_and_exit(getmsg('MSG_ERR_NO_JUNIT'))
        copy_junit(junit_path, exist_ok=False)
        print(getmsg('MSG_JUNIT_COPIED'))
        sys.exit(0)

def copy_junit(junit_path, dest_path=None, exist_ok=True):
    """ copies junit test file to cwd """
    dest = (Path.cwd() if dest_path is None else dest_path) / JUNIT_FILENAME 
    if not exist_ok and dest.exists():
        print_error_and_exit(getmsg('MSG_ERR_JUNIT_OVERWRITE'),
                             tip=getmsg('MSG_ERR_JUNIT_OVERWRITE_TIP'))
    shutil.copy(junit_path, dest)

##################################################
# Main
##################################################

def run():
    """ runs the prgtest functionality from the given params """

    # process params
    params = process_params()

    # check --version
    check_commandline_option_version(params)

    # get the working directory: the root of the repository
    working_dir = get_working_dir()

    # get the target exercise: the name of the exercise to be tested
    target_exercise = get_target_exercise(working_dir)

    # get the tests specifications
    specs_folder = gets_specs_folder(working_dir)
    specs = get_specs(specs_folder, target_exercise)

    # check --copy-junit
    junit_path = get_junit_path(specs_folder, target_exercise)
    check_commandline_option_copy_junit(junit_path, params)

    # check whether this is a non testable exercise
    check_testable(specs, junit_path)

    # the exercise can be tested
    check_main_file(specs)

    # Check all source files are compiled
    check_compiled()

    # Check whether the exercise is properly managed by git
    check_git(working_dir, target_exercise)

    # Prepare test environment
    prepare_test(working_dir)

    # Perform IO tests
    env = compute_environment_variables()
    test_path = get_test_path(working_dir)
    perform_io_tests(test_path, target_exercise, specs, env)

    # Perform JUnit tests
    timeout = get_timeout(specs)
    perform_junit_tests(test_path, junit_path, env, timeout)

    print(getmsg('MSG_ALL_TEST_PASSED'))


def dump(initial_directory: Path):
    """ generates context information to help evaluating a problem """
    dump_path = initial_directory / '__prgtest_dump.dat'
    with open(dump_path, 'a') as dest:
        dest.write("\n\n" + "=" * 100 + "\n\n")
        dest.write(f"{datetime.datetime.now()}\n")
        issue = Path('/etc/issue')
        dest.write(issue.read_text() if issue.is_file() else 'NO ISSUE\n')
        dest.write(f"{sys.version}\n")
        dest.write(str(os.environ))
        traceback.print_exc(file=dest)
    print_error_and_exit("S'ha produït un error intern de prgtest. "
                         "Comenta-li al teu docent")

def main():
    """ wraps prgtest with a try/except so any problem get's a dump to be reviewed """
    initial_directory = Path.cwd()
    try:
        run()
    except SystemExit as exception:
        raise exception
    except Exception as exception:
        dump(initial_directory)

if __name__ == '__main__':
    main()
