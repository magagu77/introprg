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

# output file result on comparison:
# - equals: the file contains the expected
# - nr_expected, nr_found: lines where the discrepancy is found (-1 when none)
OutputComparison = namedtuple('OutputComparison', ['exists', 'equals', 'nr_expected', 'nr_found'])

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
SUPPORT_FOLDER_NAME = ".introprg"
SPECS_FOLDER_NAME = ".introprg/exercises"
FLAG_RELATIVE_PATH = "tmp/last.yaml"
TEST_RELATIVE_PATH = "tmp/test"
JUNIT_CLASSNAME = "TestExercise"
JUNIT_FILENAME = f"{JUNIT_CLASSNAME}.java"
PRGTEST_AUTHOR_NAME = "prgtest"

###################################################
# Literal messages
###################################################

MESSAGES = {
    # Error messages
    "MSG_ERR_SUPPORT_FILES_ISSUES": "S'ha trobat problemes amb els fitxers de suport",
    "MSG_ERR_SUPPORT_FILES_ISSUES_TIP": "Considera tornar a instal·lar o consulta al teu docent",
    "MSG_ERR_INTROPRGDIR_ENV_NOT_FOUND": "No està definida la variable INTROPRGDIR",
    "MSG_ERR_INTROPRGDIR_ENV_NOT_FOUND_TIP": "Revisa la instal·lació o consulta el teu docent",
    "MSG_ERR_INTROPRGDIR_NOT_FOUND": "No trobo el directori %s especificat a la variable d'entorn INTROPRGDIR",
    "MSG_ERR_INTROPRGDIR_NOT_FOUND_TIP": "Revisa la instal·lació o consulta el teu docent",
    "MSG_ERR_NON_GIT": "El directori %s no és un repositori git vàlid",

    "MSG_ERR_OUT_OF_WORKINGDIR": "Aquest programa no funciona en el directori actual",
    "MSG_ERR_OUT_OF_WORKINGDIR_TIP": "Cal executar-lo a la carpeta d'algun dels exercicis.",

    "MSG_ERR_NOT_AN_EXERCISE": "El directori actual no es correspon amb un exercici conegut",
    "MSG_ERR_NOT_AN_EXERCISE_TIP": "Revisa que el nom de la carpeta sigui exàctament el requerit a l'enunciat",

    "MSG_ERR_EXERCISE_WITHOUT_MAIN_FILE": "No s'ha trobat el fitxer %s",
    "MSG_ERR_NON_COMPILED_SOURCE_FILE": "No es troba el fitxer %s",
    "MSG_ERR_NON_COMPILED_SOURCE_FILE_TIP": "Considera una de les següents possibilitats:\n$ javac %s\no\n$ javac *.java",
    "MSG_ERR_OUTDATED_COMPILED_SOURCE_FILE": "El fitxer %s ha estat modificat després de compilar",
    "MSG_ERR_OUTDATED_COMPILED_SOURCE_FILE_TIP": "Considera una de les següents possibilitats:\n$ javac %s\no\n$ javac *.java",


    "MSG_ERR_NO_JUNIT": "Aquest exercici no disposa de proves unitàries",
    "MSG_ERR_JUNIT_OVERWRITE": "Ja existeix un fitxer anomenat TestExercise.java",
    "MSG_ERR_JUNIT_OVERWRITE_TIP": "Si el vols actualitzar, elimina'l primer.",
    "MSG_ERR_JUNIT_NOTICE": "JUnit ha generat la següent sortida d'error:",
    "MSG_ERR_JUNIT_TIP": "Et proposo que consideris el següent error:",

    "MSG_ERR_NON_STAGED": "Hi ha fitxers no afegits a git",
    "MSG_ERR_NON_STAGED_TIP": "Considera una de les següents possibilitats:\n$ git add %s\no\n$ git add --all",

    "MSG_ERR_MISSING_COMMIT": "Cal registrar els canvis a git",
    "MSG_ERR_MISSING_COMMIT_TIP": 'Considera:\n$ git commit -am "»descripció dels canvis realitzats»"',

    "MSG_ERR_TEST_INPUT_CONTENTS": "Se li ha passat el següent contingut per entrada estàndard:",

    "MSG_ERR_MORE_LINES_THAN_EXPECTED": "Li sobra la línia:",
    "MSG_ERR_LESS_LINES_THAN_EXPECTED": "Li falta la línia:",
    "MSG_ERR_LINE_DISCREPANCY": "Les següents línies difereixen",
    "MSG_ERR_EXERCISE_NEVER_ENDS": "El teu programa tarda massa en finalitzar",
    "MSG_ERR_EXERCISE_NEVER_ENDS_TIP": "Executa'l manualment amb les entrades especificades",
    "MSG_ERR_COMPILATION": "S'ha trobat errors compilant %s",
    "MSG_ERR_COMPILATION_JUNIT": "S'ha trobat errors compilant les proves de JUnit",
    "MSG_ERR_EXERCISE_BREAKS": "El teu programa finalitza inesperadament",
    "MSG_ERR_EXERCISE_BREAKS_TIP": "Executa el programa fora de prgtest amb l'entrada indicada i revisa les línies remarcades a la sortida d'error",

    "MSG_ERR_EXPECTED_OUTPUT": "S'esperava el següent contingut:",
    "MSG_ERR_FOUND_OUTPUT": "S'ha trobat, però:",
    "MSG_ERR_FOUND_EMPTY": "S'ha trobat, però el fitxer buit",
    "MSG_ERR_EXPECTED_FILEOUT": "S'esperava el fitxer %s amb el següent contingut:",
    "MSG_ERR_FOUND_FILEOUT": "S'ha trobat, però, el següent contingut:",
    "MSG_ERR_MISSING_EXPECTED_FILE": "No s'ha trobat el fitxer %s",
    "MSG_ERR_NO_STDOUT_EXPECTED": "No s'esperava cap contingut per sortida estàndard",
    "MSG_ERR_NO_STDOUT_FOUND": "No s'ha trobat, però, cap contingut per sortida estàndard",
    "MSG_ERR_NO_OUTPUT_EXPECTED": "S'esperava el fitxer buit %s",
    "MSG_ERR_NO_OUTPUT_FOUND": "S'ha trobat el fitxer buit",

    "MSG_CREATED_FILEIN": "S'ha creat el fitxer %s amb el contingut",
    "MSG_CREATED_EMPTY_FILE": "S'ha creat el fitxer buit %s",
    "MSG_CREATED_EMPTY_FOLDER": "S'ha creat el directori buit %s",

    # Information messages
    "PROGRAM_DESCRIPTION": "Aquest programa et permet avaluar els teus exercicis de programació abans de lliurar-los",
    "MSG_HELP_VERSION": "Mostra la versió d'aquest programa",
    "MSG_HELP_COPY_JUNIT": "Copia els tests JUnit a la carpeta de l'exercici",

    "MSG_EXERCISE_WITHOUT_TEST": "Aquest exercici no disposa de proves automàtiques.",
    "MSG_JUNIT_COPIED": "Trobaràs les proves de JUnit al fitxer TestExercise.java",
    "MSG_ALL_TEST_PASSED": "El teu exercici passa totes les proves",
    "MSG_AUTOCOMMIT": "prgtest autocommit #%s %s: %s",

    "MSG_TEXT_PASSED_TEST": "PASSA",
    "MSG_TEXT_FAILED_TEST": "FALLA",
    "MSG_TITLE_PROGRAM_EXECUTION": "Execució del programa",
    "MSG_TITLE_STANDARD_INPUT": "Entrada estàndard",
    "MSG_TITLE_FILEIN": "Fitxers d'entrada",
    "MSG_TITLE_STANDARD_OUTPUT": "Sortida estàndard",
    "MSG_TITLE_STANDARD_ERROR": "Sortida d'error",
    "MSG_TITLE_JUNIT_ERROR": "Error de JUnit",
    "MSG_TITLE_EXPECTED_FILEOUT": "Fitxer de sortida esperat",
}

###################################################
# messages utilities
###################################################

# a dict containing the literals to be presented for messages
READABLE_MESSAGES = None
def getmsg(key: str) -> str:
    """ gets the human readable message corresponding to the given key.
        In case it is not present, it halts execution. """
    return MESSAGES[key]


##################################################
# Colors
# Functionality to deal with terminal colors
##################################################

class Foreground():
    """ definition of foreground colors """
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
    """ definition of background colors """
    BLACK      = '\033[40m'
    RED        = '\033[41m'
    GREEN      = '\033[42m'
    YELLOW     = '\033[43m'
    BLUE       = '\033[44m'
    MAGENTA    = '\033[45m'
    CYAN       = '\033[46m'
    WHITE      = '\033[47m'
    DARKGRAY   = '\033[100m'
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
    colorized_line = colorize_string(line, forecolor=forecolor, backcolor=backcolor)
    return f"{num}{spaces}{delimiter}{colorized_line}{delimiter}"


def compose_title(title, underline='='):
    """ composes the given title underlined with the specified character """
    return f"\n{title}\n{underline * len(title)}\n"


def print_err(text=''):
    """ prints the given text on stderr """
    print(text, file=sys.stderr)


def print_error_and_exit(msg: str, tip: str=None,
                         plain_color=False, returncode=1):
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
    sys.exit(returncode)


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
            return {}
        print_error_and_exit(f"File not found {path}")
    with open(path, 'r', encoding='utf8') as the_file:
        data = yaml.load(the_file, Loader=yaml.SafeLoader)
    if data is None:
        return {}
    return data


def save_yaml(path, values):
    """ saves the values as yaml file """
    path.parent.mkdir(parents = True, exist_ok = True)
    with open(path, 'w', encoding='utf8') as the_file:
        yaml.safe_dump(values, the_file, allow_unicode=True)

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
             stdin=None, argsin=None, precommand=None):
    """ runs java class_name, on folder,
        passing argsin as command line arguments,
        passing stdin as standard input,
        considering env as environment,
        giving as much as timeout time to finish execution.
        It returns the SubprocessResult """

    # Prepare command
    if precommand is None:
        precommand = []
    command_list = precommand + ['java', class_name]

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
        result = git.Repo(folder, search_parent_directories=True)
    except git.InvalidGitRepositoryError:
        print_error_and_exit(getmsg('MSG_ERR_NON_GIT') % folder)
    return result


def try_commit(repo, exercise_id, seq, message):
    """ performs the autocommit on repo with the given seq nr and the student message """
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

def get_working_dir(cwd=None):
    """ tries to get the working directory from cwd
        If cwd is not a subfolder of a proper prgtest installation,
        i.e. the folder is not a direct subfolder of a folder
        containing SUPPORT_FOLDER_NAME, it halts with an error """
    if cwd is None:
        cwd = Path.cwd()
    if (cwd / SUPPORT_FOLDER_NAME).is_dir():
        return cwd
    if cwd == Path(cwd.root):
        print_error_and_exit(getmsg('MSG_ERR_OUT_OF_WORKINGDIR'),
                             tip=getmsg('MSG_ERR_OUT_OF_WORKINGDIR_TIP'))
    return get_working_dir(cwd.parent)


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
                             tip=getmsg('MSG_ERR_OUT_OF_WORKINGDIR_TIP'))
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
    return specs


def get_test_path(working_dir):
    """ returns the path where to perform the test """
    return  get_support_dir(working_dir) / TEST_RELATIVE_PATH


def get_junit_path(specs_folder, target):
    """ obtains the path to the junit test file for the target exercise if any.
        Otherwise it returns None """
    path = specs_folder / f'{target}.junit'
    return path if path.is_file() else None


def check_testable(specs, junit):
    """ checks whether the target exercise has tests to perform.
        An exercise is considered not to have tests if the corresponding
        IO specs do not specify a main class and there's no junit.
        In case there's no test, it halts execution but it is not considered an error """
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
        to worry about cleaning it up after prgtest.
        It returns the test path """
    test_path = get_test_path(working_dir)
    if test_path.exists():
        shutil.rmtree(test_path)
    shutil.copytree(Path.cwd(), test_path)
    return test_path


def compute_environment_variables(working_dir):
    """ computes the environment where the tests will be performed.
        It includes specially the CLASSPATH var """
    env = os.environ.copy()
    introprgdir_path = working_dir / SUPPORT_FOLDER_NAME
    classpath = env.get('CLASSPATH', '')
    if not classpath:
        env['CLASSPATH'] = f'.:{introprgdir_path}'
    elif introprgdir_path not in classpath.split(':'):
        env['CLASSPATH'] = f"{env['CLASSPATH']}:{introprgdir_path}"
    return env


def compose_filein(test_path, filein):
    """ composes the input files """
    if filein is None:
        return
    for entry in filein:
        if entry.get('filename') is None:
            continue
        path = test_path / entry['filename']
        if entry['filename'].endswith('/'):
            path.mkdir(parents=True, exist_ok=True)
            continue
        lines = entry.get('lines')
        if lines is None:
            contents = ''
        elif isinstance(lines, str):
            contents = lines
        else:
            contents = "\n".join(lines)
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(contents)


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


def normalize_iotest(iotest):
    """ given a spec for a iotest, it normalizes it's contents so they can be used
        as if all the possible contents was present in the specs. """
    for entry_name in ('stdin', 'argsin', 'stdout', 'filein', 'fileout'):
        iotest[entry_name] = normalize_entry_spec(iotest.get(entry_name))
    for entry_name in ('filein', 'fileout'):
        entry = iotest.get(entry_name)
        if not entry:
            continue
        for file_entry in entry:
            if file_entry.get('filename') is None:
                print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                                     tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
            lines = file_entry.get('lines')
            if lines is None:
                file_entry['lines'] = []
            elif isinstance(lines, str):
                file_entry['lines'] = [lines]


def normalize_entry_spec(entry):
    """ normalizes an entry spec (i.e. the value of stdin, argsin, etc.)
        The value of an entry spec can be a single string or a list of strings.
        After normalization, the result will be always a list. Even if empty.
        In case entry is None, it returns None """
    if entry is None:
        return []
    return entry if isinstance(entry, list) else [entry]


def translate_output(contents, translate_specs):
    """ processes the specs option _tr that allows to specify the translation of some
        characters into others. It returns the resulting text or contents when no translation
        is specified """
    if translate_specs is None:
        return contents
    if len(translate_specs) != 2:    # illdefined specs
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    if not translate_specs[0]:
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    if len(translate_specs[0]) != len(translate_specs[1]):
        print_error_and_exit(getmsg('MSG_ERR_SUPPORT_FILES_ISSUES'),
                             tip=getmsg('MSG_ERR_SUPPORT_FILES_ISSUES_TIP'))
    for chsrc in translate_specs[0]:
        contents = [line.replace(chsrc, translate_specs[1]) for line in contents]
    return contents

##################################################
# test utilities
##################################################

def perform_io_tests(working_dir, specs, env, precommand=None):
    """ performs the i/o tests specified on specs:
        - cwd contains the target exercise
        - specs potentially contains:
          - _tr: translation specs
          - [^_]\w*: io test specs
    """
    def remove_fileout(test_path, iotest):
        """ removes existing fileout from the test_path """
        for filespec in iotest.get('fileout'):
            path = test_path / filespec.get('filename')
            if path.is_file():
                path.unlink()

    main_program = get_main_program(specs)
    main_class = get_main_class(specs)
    timeout = get_timeout(specs)
    translation = specs.get('_tr')
    ignore_blank_lines = specs.get('_ignore_blank_lines', True)
    for testid, iotest in specs.items():
        if testid.startswith('_'):
            continue
        normalize_iotest(iotest)
        test_path = prepare_test(working_dir)
        remove_fileout(test_path, iotest)
        result = run_io_tests(test_path, iotest, env,
                              main_class, main_program,
                              timeout, precommand=precommand)
        if result.returncode != 0:
            show_failure_context(testid, iotest, main_class)
            show_failure_execution_and_end(result)
        compare_stdout(testid, iotest, result, main_class, translation, ignore_blank_lines)
        compare_fileout(testid, iotest, test_path, main_class, ignore_blank_lines)
        show_test_passed(testid)


def run_io_tests(test_path, iotest, env, main_class, main_program, timeout,
                 precommand=None):
    """ runs the target with iotest specs and returns the subprocessresult """

    def prepare_stdin(rawstdin):
        """ given the specified stdin, it prepares it to be redirectable """
        return '' if rawstdin is None else '\n'.join(str(item) for item in rawstdin) + '\n'

    compose_filein(test_path, iotest.get('filein'))
    result = run_javac(program_name=main_program, folder=test_path, env=env)
    if result.returncode != 0:
        print_error_and_exit(getmsg('MSG_ERR_COMPILATION') % main_program,
                             tip="\n".join(result.stderr))
    stdin = prepare_stdin(iotest.get('stdin'))
    return run_java(class_name=main_class,
                    folder=test_path,
                    stdin=stdin, argsin=iotest.get('argsin'),
                    env=env, timeout=timeout, precommand=precommand)


def compare_stdout(testid, iotest, test_result, main_class,
                   translation, ignore_blank_lines):
    """ compares the expected stdout with the resulting one.
        If applies translation if needed.
        Ends execution on discrepancy """
    expected_stdout = iotest.get('stdout')
    found_stdout = translate_output(test_result.stdout, translation)
    comparison = compare_lines(expected_stdout, found_stdout, ignore_blank_lines)
    if comparison is None:
        return
    show_failure_context(testid, iotest, main_class)
    nr_expected, nr_found = comparison
    show_discrepancy(expected_stdout, found_stdout, nr_expected, nr_found)
    sys.exit(1)


def compare_fileout(testid, iotest, test_path, main_class, ignore_blank_lines):
    """ compares the expected files generated by testing target with the actually found ones.
        Ends execution on discrepancy """
    for filespec in iotest.get('fileout'):
        path = test_path / filespec.get('filename')
        comparison = check_output_file(path, filespec, ignore_blank_lines)
        if comparison.exists and comparison.equals:
            continue
        show_failure_context(testid, iotest, main_class)

        filename = colorize_string(text=filespec.get('filename'),
                                   forecolor=get_color('FG_GENERAL_CODE'),
                                   backcolor = get_color("BG_GENERAL_CODE"))
        if not comparison.exists:
            print_err(compose_title(getmsg('MSG_TITLE_EXPECTED_FILEOUT')))
            print_err(getmsg('MSG_ERR_MISSING_EXPECTED_FILE') % filename)
            print_err()
        else:
            found_contents = path.read_text().split('\n')[:-1] # do not include
                                                               # last extra empty line
            show_discrepancy(expected=filespec.get('lines'), found=found_contents,
                             nr_expected=comparison.nr_expected,
                             nr_found=comparison.nr_found,
                             filename=filename)
        sys.exit(1)


def check_output_file(path, filespec, ignore_blank_lines) -> OutputComparison:
    """ checks whether the path is present and contains the expected contents. """
    if not path.is_file():
        return OutputComparison(exists=False, equals=False, nr_expected=-1, nr_found=-1)
    expected = filespec.get('lines')
    found = path.read_text().split('\n')[:-1] # do not include last extra empty line
    comparison = compare_lines(expected, found, ignore_blank_lines)
    if comparison is None:
        return OutputComparison(exists=True, equals=True, nr_expected=-1, nr_found=-1)
    nr_expected, nr_found = comparison
    return OutputComparison(exists=True, equals=False, nr_expected=nr_expected, nr_found=nr_found)


def perform_junit_tests(working_dir, junit_path, env, timeout, precommand=None):
    """ performs the JUnit tests """
    if junit_path is None:
        return
    test_path = prepare_test(working_dir)
    copy_junit(junit_path, dest_path=test_path)
    result = run_junit_tests(test_path, env, timeout, precommand=precommand)
    compare_junit_result(result)


def run_junit_tests(test_path, env, timeout, precommand=None):
    """ runs the JUnit test and returns the SubprocessResult  """
    # compile junit tests
    result = run_javac(program_name=JUNIT_FILENAME,
                       folder=test_path, env=env)
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
                    argsin=argsin,
                    precommand=precommand)


def show_test_failed(testid):
    """ shows the test has failed """
    text = colorize_string(f"{getmsg('MSG_TEXT_FAILED_TEST')} ",
                           forecolor=get_color("FG_FAIL_TEST"),
                           backcolor=get_color("BG_FAIL_TEST"))
    print(f"Test {testid}: {text}")


def show_found_stderr(stderr):
    """ shows the contents stderr from the target program execution on stderr """
    print_err(compose_title(getmsg('MSG_TITLE_STANDARD_ERROR')))
    print_err("La sortida d'error que ha generat el programa ha estat:")
    print_err(compose_enumerated_text(
        stderr,
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

    print_err(getmsg('MSG_ERR_JUNIT_NOTICE'))

    print_err("\t" + "\n\t".join(junit_output))

    print_err(getmsg('MSG_ERR_JUNIT_TIP'))
    print_err("* " + "\n* ".join(digest_output(junit_output)))


def show_output_on_stderr(title: str=None,          # title of the error section
                          msg: str=None,            # subtitle of the error section
                          output: str=None,         # output to be shown
                          text_type:TextType=None,
                          highlight_line=-1):       # line to be hightligte (-1 none)
    """ shows the given output on stderr """
    if title is not None:
        print_err(compose_title(title))
    if msg is not None:
        print_err(msg)
    if output is not None:
        print_err()
        print_err(compose_enumerated_text(output,
                                          text_type=text_type,
                                          highlight_line=highlight_line))
        print_err()


def show_failure_context(testid, iotest, main_class):
    """ shows the context of a failed test """
    show_test_failed(testid)
    show_program_execution(provided_argsin=iotest.get('argsin'),
                           main_class=main_class)
    show_provided_stdin(iotest.get('stdin'))
    show_provided_filein(iotest.get('filein'))


def show_failure_execution_and_end(test_result):
    """ notifies the target program broke when being tested """
    if test_result.returncode == 124:    # timeout
        print_error_and_exit(getmsg('MSG_ERR_EXERCISE_NEVER_ENDS'),
                             tip=getmsg('MSG_ERR_EXERCISE_NEVER_ENDS_TIP'),
                             returncode=test_result.returncode)
    else:
        show_found_stderr(test_result.stderr)
        print_error_and_exit(getmsg('MSG_ERR_EXERCISE_BREAKS'),
                             tip=getmsg('MSG_ERR_EXERCISE_BREAKS_TIP'))


def show_discrepancy(expected, found, nr_expected, nr_found, filename=None):
    """ shows discrepancy between expected and found
        In case filename, it assumes the contents are about the file and the
        file exists. """
    # Show title
    if filename is None:
        title = getmsg('MSG_TITLE_STANDARD_OUTPUT')
    else:
        title = getmsg('MSG_TITLE_EXPECTED_FILEOUT')
    print_err(compose_title(title))

    # let's consider the expected side
    one_is_empty = False
    if filename is None:
        if expected:
            msg_expected = getmsg('MSG_ERR_EXPECTED_OUTPUT')
            contents_expected = expected
        else:
            msg_expected = getmsg('MSG_ERR_NO_STDOUT_EXPECTED')
            contents_expected = None
            one_is_empty = True
    else:
        filename = colorize_string(text=filename,
                                   forecolor=get_color('FG_GENERAL_CODE'),
                                   backcolor = get_color("BG_GENERAL_CODE"))
        if expected:
            msg_expected = getmsg('MSG_ERR_EXPECTED_FILEOUT') % filename
            contents_expected = expected
        else:
            msg_expected = getmsg('MSG_ERR_NO_OUTPUT_EXPECTED') % filename
            contents_expected = None
            one_is_empty = True

    # let's consider the found side
    if filename is None:
        if found:
            msg_found = getmsg('MSG_ERR_FOUND_OUTPUT')
            contents_found = found
        else:
            msg_found = getmsg('MSG_ERR_NO_STDOUT_FOUND')
            contents_found = None
            one_is_empty = True
    else:
        if found:
            msg_found = getmsg('MSG_ERR_FOUND_FILEOUT')
            contents_found = found
        else:
            msg_found = getmsg('MSG_ERR_FOUND_EMPTY')
            contents_found = None
            one_is_empty = True

    # let's show the results
    nr_expected = None if one_is_empty else nr_expected
    show_output_on_stderr(msg=msg_expected, output=contents_expected,
                          text_type=TextType.EXPECTED, highlight_line=nr_expected)
    if contents_expected is None:
        print_err()
    nr_found = None if one_is_empty else nr_found
    show_output_on_stderr(msg=msg_found, output=contents_found,
                          text_type=TextType.FOUND, highlight_line=nr_found)

    if contents_found is None:
        print_err()

    if not one_is_empty:
        show_difference(expected, nr_expected, nr_found, found)


def show_difference(expected_output, nr_expected, nr_found, stdout):
    """ shows the difference between expected and stdout """
    if nr_expected < 0:
        print_err(getmsg('MSG_ERR_MORE_LINES_THAN_EXPECTED'))
        print_err()
        print_err(compose_enumerated_line(nr_found,
                                          stdout[nr_found], text_type=TextType.FOUND))
    elif nr_found < 0:
        print_err(getmsg('MSG_ERR_LESS_LINES_THAN_EXPECTED'))
        print_err()
        print_err(compose_enumerated_line(nr_expected,
                                          expected_output[nr_expected],
                                          text_type=TextType.EXPECTED))
    else:
        print_err(getmsg('MSG_ERR_LINE_DISCREPANCY'))
        print_err()
        print_err(compose_enumerated_line(nr_expected,
                                          expected_output[nr_expected],
                                          text_type=TextType.EXPECTED,
                                          delimited=True))
        print_err(compose_enumerated_line(nr_found, stdout[nr_found],
                                          text_type=TextType.FOUND,
                                          delimited=True))
    print_err()


def compare_lines(expected, found, ignore_blank_lines=True):
    """ compares two lists of lines and:
        - when both lists are considered equals, it returns None
        - otherwise it returns a pair of numbers corresponding to the lines
          where the first discrepancy is found (line_expected, line_found).
        Discrepancy cases:
            expected        found           result          description
            --------        -----           ------          -----------

            a               a               (1, -1)         more lines expected
            b

            a               a               (-1, 1)         more lines found
                            b

            a               a               (1, 1)          discrepancy in contents
            b               c

        Notes:
        - the two lines can be different because of ignore_blank_lines
        - it returns the first available discrepancy. i.e.
            expected        found           result          description
            --------        -----           ------          -----------

            a               c               (0, 0)          first discrepancy
            b               d                               ignored others
            f
    """
    nr_expected = nr_found = 0
    last_expected = len(expected) - 1
    last_found = len(found) -1
    while True:
        if last_expected < nr_expected and last_found < nr_found:
            return None                     # reviewed all  of them
        if ignore_blank_lines:
            if last_expected >= nr_expected and not expected[nr_expected]:
                nr_expected += 1            # ignore blank on expected
                continue
            if last_found >= nr_found and not found[nr_found]:
                nr_found += 1               # ignore blank on found
                continue
        if last_expected < nr_expected:
            return (-1, nr_found)           # more expected than found
        if last_found < nr_found:
            return (nr_expected, -1)        # more found than expected
        if expected[nr_expected] != found[nr_found]:
            return (nr_expected, nr_found)  # contents discrepancy
        nr_expected += 1
        nr_found += 1


def compare_junit_result(test_result):
    """ compares the results of the junit tests """
    if test_result.returncode == 0:
        show_test_passed('JUnit')
        return
    show_test_failed('JUnit')
    print_err(compose_title(getmsg('MSG_TITLE_JUNIT_ERROR')))
    if test_result.returncode == 124:    # timeout
        print_error_and_exit(getmsg('MSG_ERR_EXERCISE_NEVER_ENDS'),
                             tip=getmsg('MSG_ERR_EXERCISE_NEVER_ENDS_TIP'),
                             returncode=test_result.returncode)
        return
    show_found_junit_error(test_result.stdout)
    sys.exit(1)


def show_test_passed(testid):
    """ shows the test has passed """
    text = colorize_string(f"{getmsg('MSG_TEXT_PASSED_TEST')} ",
                           forecolor=get_color("FG_PASS_TEST"),
                           backcolor=get_color("BG_PASS_TEST"))
    print(f"Test {testid}: {text}")


def get_timeout(specs):
    """ returns the timeout for the execution of the target program """
    return specs.get('_timeout', DEFAULT_TIMEOUT)


def show_provided_stdin(provided_stdin):
    """ shows the contents entered by stdin to the target program (if any) """
    if provided_stdin:
        show_output_on_stderr(
            title=getmsg('MSG_TITLE_STANDARD_INPUT'),
            msg=getmsg('MSG_ERR_TEST_INPUT_CONTENTS'),
            output=provided_stdin,
        )


def show_provided_filein(provided_filein):
    """ shows the contents of the files used as input (if any) """
    if not provided_filein:     # nothing to see here
        return
    show_output_on_stderr(title=getmsg('MSG_TITLE_FILEIN'))
    for filein in provided_filein:
        filename = filein.get('filename')
        if filename is None:
            continue
        filename = colorize_string(text=filename,
                                   forecolor=get_color('FG_GENERAL_CODE'),
                                   backcolor = get_color("BG_GENERAL_CODE"))
        contents = filein.get('lines')
        if contents:
            show_output_on_stderr(msg=getmsg('MSG_CREATED_FILEIN') % filename,
                                  output=contents)
        elif filein.get('filename').endswith('/'):
            show_output_on_stderr(msg=getmsg('MSG_CREATED_EMPTY_FOLDER') % filename,)
            print_err()
        else:
            show_output_on_stderr(msg=getmsg('MSG_CREATED_EMPTY_FILE') % filename,)
            print_err()


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


def check_commandline_option_version(params,
                                     program_descriptor=PROGRAM_DESCRIPTOR):
    """ checks option --version in commandline params.
        If present, it shows the version and ends execution """
    if 'version' in params:
        print(f"{program_descriptor} versió {PROGRAM_VERSION}")
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
    """ copies junit test file to dest_path or cwd if not specified """
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

    # Perform IO tests
    env = compute_environment_variables(working_dir)
    perform_io_tests(working_dir, specs, env)

    # Perform JUnit tests
    timeout = get_timeout(specs)
    perform_junit_tests(working_dir, junit_path, env, timeout)

    print(getmsg('MSG_ALL_TEST_PASSED'))


def dump(initial_directory: Path):
    """ generates context information to help evaluating a problem """
    dump_path = initial_directory / '__prgtest_dump.dat'
    with open(dump_path, 'a', encoding='utf8') as dest:
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
    except Exception:
        dump(initial_directory)

if __name__ == '__main__':
    main()
