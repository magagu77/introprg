#! /usr/bin/env python3
"""
    prgtest: main program
"""
import os
import sys
import subprocess
import enum
import traceback
from pathlib import Path
from argparse import ArgumentParser
import git
import yaml
import datetime

class Prgtest:
    """ this class encapsulates the prgtest functionality """
    PROGRAM_NAME = "prgtest"
    PROGRAM_DESCRIPTION = ("Aquest programa et permet avaluar els teus exercicis"
                           " de programació abans de lliurar-los")
    PROGRAM_VERSION = "2021-22"

    DEFAULT_TIMEOUT = 10 # max time (seconds) expected to be run

    INTROPRGDIR_KEY = "INTROPRGDIR"
    INTROPRGSPECDIR_KEY = "INTROPRGSPECDIR"
    INTROPRGSUPPORTDIR_KEY = "INTROPRGSUPPORTDIR"

    DEFAULT_WORKING_DIR_NAME = "introprg"
    DEFAULT_SUPPORT_DIR_NAME = ".introprg"
    DEFAULT_SPEC_DIR_NAME = "exercises"
    SPECS_FILENAME = "specs.yaml"

    MSG_EXERCISE_WITHOUT_TEST = "Aquest exercici no disposa de proves automàtiques"
    MSG_EXERCISE_WITH_UNNEXPECTED_OUTPUT = f"S'ha trobat un error en executar la prova %s"
    MSG_EXERCISE_WITH_MORE_LINES_THAN_EXPECTED = "No s'esperava la línia:"
    MSG_EXERCISE_WITH_LESS_LINES_THAN_EXPECTED = "A la sortida del programa li falta:"
    MSG_EXERCISE_WITH_DIFFERENT_LINES = "Les següents línies difereixen"
    MSG_ALL_TEST_PASSED = "El teu exercici passa totes les proves"
    MSG_EXERCISE_NEVER_ENDS = "El teu exercici tarda massa en finalitzar"
    MSG_EXERCISE_NEVER_ENDS_TIP = "Executa'l manualment amb les entrades especificades"
    MSG_COMPILATION_ERROR = "S'ha trobat errors compilant %s"

    def __init__(self):
        self.params = Prgtest.process_params()
        self.working_dir = None     # path to the dir containing the student's repo
        self.target_path = None     # path to the dir containing the target exercise
        self.specs = dict()         # specs fot testing the target exercise
        self.timeout = False        # indicates if the execution did not finish

    @property
    def target_exercise(self):
        """ returns the name of the target exercise """
        return self.target_path.name

    @property
    def support_dir(self):
        """ returns the path to the dir containing the support files.
            In case environment var INTROPRG_SUPPORT_DIR is set, it uses it,
            otherwise, a default subfolder within working_dir will be used """
        if not Prgtest.protected():
            return Path.cwd()
        support_dir = os.environ.get(Prgtest.INTROPRGSUPPORTDIR_KEY)
        if support_dir is None:
            support_dir = self.working_dir / self.DEFAULT_SUPPORT_DIR_NAME
        else:
            support_dir = Path(support_dir)
        if not support_dir.is_dir():
            print_error_and_exit(f"No es troba el directori de suport {support_dir}")
        return support_dir


    @property
    def specs_dir(self):
        """ composes and returns the path to the dir containing the exercise specs.
            In case environment var INTROPRG_SPEC_DIR is set, it uses it,
            otherwise, a default subfolder within support dir will be used """
        if not Prgtest.protected():
            return Path.cwd()
        specs_dir = os.environ.get(Prgtest.INTROPRGSPECDIR_KEY)
        if specs_dir is None:
            specs_dir = self.support_dir / 'exercises'
        else:
            specs_dir = Path(specs_dir)
        if not specs_dir.is_dir():
            print_error_and_exit(f"No es troba el directori d'especificacions {specs_dir}")
        return specs_dir


    def run(self):
        """ runs the prgtest functionality from the given params """

        # check --version
        Prgtest.check_option(self.params, 'version', Prgtest.show_version)


        # define the working directori
        self.working_dir = Prgtest.find_prgtestdir(self.params)

        # check --show-introprgdir
        Prgtest.check_option(self.params, 'show_introprgdir', self.show_introprgdir)

        # Once here the program has to deal with a concrete exercise
        self.find_target_path()     # obtain the target path

        # Obtain exercise specifications
        #Prgtest.check_option(self.params, 'specs', self.set_specs_path)
        #self.obtain_specs()         # obtain exercise specifications
        self.obtain_specs()

        # check --show-target-path
        Prgtest.check_option(self.params, 'show_target_path', self.show_target_path)

        # check non testable exercise
        self.check_nontestable()

        # Reaching here means the code is properly managed by git
        self.check_compiled()   # let's see if the code compilation is updated

        # Reaching here means that the target should be tested
        self.check_git()    # let's see whether the exercise is properly managed by git

        # Run the program
        self.perform_tests()


        print(Prgtest.MSG_ALL_TEST_PASSED)


    @staticmethod
    def process_params():
        """ processes the commandline arguments """
        parser = ArgumentParser(prog=Prgtest.PROGRAM_NAME,
                                description=Prgtest.PROGRAM_DESCRIPTION)
        parser.add_argument("-v", "--version",
                            action='store_true',
                            help="Mostra la versió d'aquest programa")
        parser.add_argument("-d", "--introprgdir",
                            dest=Prgtest.INTROPRGDIR_KEY,
                            metavar="camí",
                            help="Defineix el directori de treball; és a dir, el camí al repositori")
        parser.add_argument("-p", "--show-introprgdir",
                            action='store_true',
                            help="Mostra el directori de treball")
        parser.add_argument("-t", "--show-target-path",
                            action='store_true',
                            help="Mostra el camí a l'exercici a avaluar")
        parser.add_argument("-s", "--specs",
                            help="Camí a les especificacions de l'exercici",
                            metavar="camí")

        args = parser.parse_args()
        params = {k:v for k,v in vars(args).items() if v}
        return params


    @staticmethod
    def find_prgtestdir(params) -> Path:
        """ checks the parameters obtained from command line to obtain the working directory.
            The working directory can be found either:
            1. from commandline option --introprgdir
            2. from environment variable INTROPRGDIR
            3. as fixed folder at ~/introprg

            When this method is call, the commandline option is expected to have been processed.

            In case INTROPRGDIR can't be determined or corresponds to a non existing folder,
            it halts with an error message

            On success it returns the path to the working directory """

        # check if provided by commandline
        path = params.get(Prgtest.INTROPRGDIR_KEY)
        if path is not None:
            path = Path(path)
            if not path.is_dir():
                print_error_and_exit("No trobo el directori %s especificat "
                                              "al paràmetre --introprgdir" % path)
            return path

        # check if defined in environment
        path = os.environ.get(Prgtest.INTROPRGDIR_KEY)
        if path is not None:
            path = Path(path)
            if not path.is_dir():
                print_error_and_exit("No trobo el directori %s especificat "
                                                  "a la variable d'entorn INTROPRGDIR" % path)
            return path

        # check if teacher test
        if not Prgtest.protected():
            return Path.cwd()

        # check fixed folder
        path = Path.home() / Prgtest.DEFAULT_WORKING_DIR_NAME
        if not path.is_dir():
            print_error_and_exit("No trobo el teu repositori amb els exercicis",
                                       tip=("Revisa que tens el teu sistema configurat tal i "
                                            "com s'especifica als apunts:\n"
                                            "https://moiatjda.github.io/jda.dev.m03/holagit.html"));

        return path


    def find_target_path(self):
        """ it finds the path of the target exercise. i.e. the exercise to be evaluated
            Currently, the only accepted way to define the target exercise is by running
            the program in the folder of the exercise """
        cwd = Path.cwd()
        if Prgtest.protected():
            # check cwd is within working directory
            if self.working_dir != cwd.parent:
                print_error_and_exit("El directori actual no es troba dins del directori de treball")
        self.target_path = cwd


    def obtain_specs(self):
        """ obtains the specifications of the target exercise """
        # check if provided by commandline
        path = self.params.get('specs')
        if path is not None:
            path = Path(path)
            if path.is_dir():
                path = path / Prgtest.SPECS_FILENAME
        else:
            folder = self.specs_dir
            if Prgtest.protected():
                path = folder / f"{self.target_exercise}.yaml"
            else:
                path = folder / Prgtest.SPECS_FILENAME
            if not path.is_file():
                print_error_and_exit("El directori actual no es correspon a cap exercici conegut",
                                              tip=("Si creus que sí hauria de ser un exercici, revisa que"
                                                   " el nom\n"
                                                   "'%s' sigui exactament l'especificat"
                                                   " a l'enunciat" % self.target_exercise))
        self.specs.update(load_yaml(path))


    def get_main(self):
        """ returns the name of the class containing the main() function in the exercise """
        main = self.specs.get('_mainclass')
        if main is None:
            print_error_and_exit("Les especificacions de la prova no són correctes",
                                 tip="Considera recuperar la versió correcta si les has modificat o parla amb el teu docent")
        return main


    def get_main_program(self):
        """ returns the name of the file containing the main() function """
        return f"{self.get_main()}.java"


    def check_git(self):
        """ checks whether the target exercise is properly managed by git.

            Current interpretation of "properly" consists on:
            - all the files in the given folder have been added to git
            - all the changes in files in the whole repository have been committed

            It is acceptable that some files in other folders are not staged .

            It is not acceptable that there are uncommitted files in other
            folders since they will get automatically committed when
            autocommit. Therefore, prgtest should complain and propose
            committing or reverting them """


        def unstaged_files_in_target_folder(repo):
            """ returns the list of unstaged files in the target folder """
            return [path.split('/', maxsplit=1)[1]
                    for path in repo.untracked_files
                    if path.startswith(self.target_exercise)]

        # in case of teacher test, these checkings are ommitted.
        if not Prgtest.protected():
            return

        # Check unstaged files on exercise folder first
        repo = git.Repo(self.working_dir)
        unstaged = unstaged_files_in_target_folder(repo)
        if unstaged:
            items = " ".join(unstaged)
            print_error_and_exit("Cal afegir fitxers a git",
                                          tip=("Considera una de les següents possibilitats:\n"
                                               f"$ git add {items}\n"
                                               "o\n"
                                               "$ git add --all"))
        # check uncommitted files
        if repo.is_dirty():
            print_error_and_exit("Cal registrar els canvis a git",
                                          tip=("Considera:\n"
                                               '$ git commit -am "»descripció dels canvis realitzats»'))


    def check_compiled(self):
        """ checks whether all the source java programs have been compiled
            after last change in sources. That is, there's a .class for each .java
            whose updated date is newer than the date of it's source file """
        if not Prgtest.protected():
            return
        for srcfile in self.target_path.rglob("*.java"):
            compiledfile = srcfile.parent / f"{srcfile.stem}.class"
            if not compiledfile.is_file():
                print_error_and_exit(f"El fitxer {srcfile.relative_to(self.target_path)} no està compilat",
                                              tip=("Considera una de les següents possibilitats:\n"
                                                   f"$ javac {srcfile.relative_to(self.target_path)}\n"
                                                   "o\n"
                                                   "$ javac *.java"))
            # check dates
            if srcfile.stat().st_mtime > compiledfile.stat().st_mtime:
                print_error_and_exit(f"El fitxer {srcfile.relative_to(self.target_path)} ha estat modificat després de compilar",
                                              tip=("Considera una de les següents possibilitats:\n"
                                                   f"$ javac {srcfile.relative_to(self.target_path)}\n"
                                                   "o\n"
                                                   "$ javac *.java"))

    def get_timeout(self):
        """ returns the timeout for the execution of the target program """
        return self.specs.get('_timeout', Prgtest.DEFAULT_TIMEOUT)

    def perform_tests(self):
        """ performs the tests specified on specs for target """
        for testid, testspecs in self.specs.items():
            if testid.startswith('_'):
                continue
            output = self.run_target(
                stdin=Prgtest.normalize_entry_spec(testspecs.get('stdin')),
                argsin=Prgtest.normalize_entry_spec(testspecs.get('argsin')))
            self.compare_results(testid, output)

    @staticmethod
    def normalize_entry_spec(entry):
        """ normalizes an entry spec (i.e. the value of stdin, argsin, etc.)
            The value of an entry spec can be a single string or a list of strings. 
            After normalization, the result will be always a list. Even if empty.
            In case entry is None, it returns None """
        if entry is None:
            return None
        if isinstance(entry, list):
            return [value if value else '\n' for value in entry]
        return [entry]


    def run_target(self, stdin=None, argsin=None):
        """ runs the target with the specified stdin and returns the stdout if no errors """
        def compile_target(env):
            """ try to compile targe on teacher test with the given environment """
            if Prgtest.protected():
                return
            cwd = Path.cwd()
            os.chdir(self.target_path)
            # remove existing compiled files
            for compiled_file in self.target_path.glob("*.class"):
                compiled_file.unlink()
            command_list =  ['javac', self.get_main_program()]
            # perform the compilation
            result = subprocess.run(command_list, capture_output=True, env=env, encoding='utf-8')
            os.chdir(cwd)
            if result.returncode != 0:
                print_error_and_exit(Prgtest.MSG_COMPILATION_ERROR % self.get_main_program(),
                                     tip=result.stderr)

        # Prepare CLASSPATH
        env = os.environ.copy()
        classpath = env.get('CLASSPATH', '')
        introprgdir_path = Path(__file__).parent
        if not classpath:
            env['CLASSPATH'] = f'.:{introprgdir_path}'
        elif introprgdir_path not in classpath.split(':'):
            env['CLASSPATH'] = f"{env['CLASSPATH']}:{introprgdir_path}"

        compile_target(env)

        cwd = Path.cwd()
        os.chdir(self.target_path)

        # Prepare stdin
        stdin = '' if stdin is None else '\n'.join(str(item) for item in stdin)

        # Prepare command
        command_list = ['java', self.get_main()]

        # Add args if any
        if argsin is not None:
            command_list.extend(argsin)

        # run the exercise
        try:
            result = subprocess.run(command_list,
                                    capture_output=True,
                                    timeout=self.get_timeout(),
                                    env=env,
                                    input=stdin, encoding='utf-8',)
        except subprocess.TimeoutExpired:
            self.timeout = True
            return '∞'
        os.chdir(cwd)
        if result.returncode != 0:
            print_error_and_exit(f"Errors executant {self.get_main_program()}",
                                 tip=result.stderr)
        return result.stdout.splitlines()


    def compare_results(self, testid, found):
        """ compares the output found when running target on testif with the expected one """

        def prepare_output(output):
            """ prepares the output: - it has translations applied if any """
            translatespecs = self.specs.get('_tr')
            if translatespecs is None:
                return output
            if len(translatespecs) != 2:    # illdefined specs
                raise AttributeError(f"illdefined _tr for exercise {self.target_path}")
            if not translatespecs[0]:
                raise AttributeError(f"illdefined _tr for exercise {self.target_path}")
            if len(translatespecs[0]) != len(translatespecs[1]):
                raise AttributeError(f"illdefined _tr for exercise {self.target_path}")
            for i, chsrc in enumerate(translatespecs[0]):
                chdst = translatespecs[1]
                new_lines = []
                for line in output:
                    new_lines.append(line.replace(chsrc, chdst))
                output = new_lines
            return output
            
        def compare_output(output):
            """ compares output with expected output as defined in specs """
            ignore_blank_lines = self.specs.get('_ignore_blank_lines', True)
            expected = self.specs[testid].get('stdout')
            if expected is None:
                return None
            output = prepare_output(output)
            result = Prgtest.compare_lines(expected, output,
                                           ignore_blank_lines = ignore_blank_lines)
            return None if result is None else (testid, result)

        # normalize expected output
        testspecs = self.specs[testid]
        testspecs['stdout'] = Prgtest.normalize_entry_spec(testspecs.get('stdout'))
        result = compare_output(found)
        if result is None:
            text = colorize_string(" PASSA ",
                                   forecolor=get_color("FG_PASS_TEST"),
                                   backcolor=get_color("BG_PASS_TEST"))
            print(f"Test {testid}: {text}")
            return

        print_err(f"Test {testid}: " + colorize_string(" FALLA ",
                                                       forecolor=get_color("FG_FAIL_TEST"),
                                                       backcolor=get_color("BG_FAIL_TEST")))
        # show discrepancy
        testid, (nr_expected, nr_found) = result
        argsin = self.specs[testid].get('argsin', '')
        if argsin is not None:
            argsin = ' '.join(argsin)
        print_err(Prgtest.MSG_EXERCISE_WITH_UNNEXPECTED_OUTPUT % testid)
        print_err(compose_title("Execució del programa"))
        print_err("L'execució ha estat la següent:\n")
        colorized_args = colorize_string(argsin,
                                         forecolor=get_color('FG_JAVAC_ARGS'),
                                         backcolor=get_color('BG_JAVAC_ARGS'))
        print_err(f"$ java {self.get_main()} {colorized_args}")
        if 'stdin' in self.specs[testid]:
            print_err(compose_title("Entrada estàndard"))
            print_err("Se li ha passat el següent codi per entrada estàndard\n")
            print_err(compose_enumerated_text(self.specs[testid]['stdin']))
            print_err()
        if 'stdout' in self.specs[testid]:
            print_err(compose_title("Sortida esperada"))
            print_err("S'esperava la següent sortida del programa:\n")
            print_err(compose_enumerated_text(self.specs[testid]['stdout'],
                                              TextType.EXPECTED, highlight_line=nr_expected))
        print_err(compose_title("Sortida trobada"))
        if self.timeout:
            print_error_and_exit(Prgtest.MSG_EXERCISE_NEVER_ENDS,
                                 tip=Prgtest.MSG_EXERCISE_NEVER_ENDS_TIP)

        print_err("La sortida que ha generat el programa ha estat:")
        print_err(compose_enumerated_text(found,
                                          text_type=TextType.FOUND, highlight_line=nr_found))

        print_err(compose_title("Discrepància"))
        if nr_expected >= len(self.specs[testid].get('stdout', [])):
            print_err(Prgtest.MSG_EXERCISE_WITH_MORE_LINES_THAN_EXPECTED)
            print_err(compose_enumerated_line(nr_found,
                found[nr_found], text_type=TextType.FOUND))
        elif nr_found >= len(found):
            print_err(Prgtest.MSG_EXERCISE_WITH_LESS_LINES_THAN_EXPECTED)
            print_err(compose_enumerated_line(nr_expected,
                                              self.specs[testid]['stdout'][nr_expected],
                                              text_type=TextType.EXPECTED))
        else:
            print_err(Prgtest.MSG_EXERCISE_WITH_DIFFERENT_LINES)
            print_err(compose_enumerated_line(nr_expected,
                                              self.specs[testid]['stdout'][nr_expected],
                                              text_type=TextType.EXPECTED,
                                              delimited=True))
            print_err(compose_enumerated_line(nr_found, found[nr_found],
                                              text_type=TextType.FOUND,
                                              delimited=True))
        sys.exit(1)


    def check_nontestable(self):
        """ checks whether target is non testable """
        if not self.specs:
            print(Prgtest.MSG_EXERCISE_WITHOUT_TEST)
            sys.exit(0)


    @staticmethod
    def check_option(params, option, method):
        """ checks option in params and if there, runs method and ends execution """
        if params.get(option):
            method()
            sys.exit(0)


    @staticmethod
    def compare_lines(expected, found,
                      ignore_blank_lines = True):
        """ compares two lists of lines and:
            - when both lists are considered equals, it returns None
            - otherwise it returns a pair of numbers corresponding to the lines
              where the first discrepancy is found (line_expected, line_found).
              Note: the two lines can be different because of ignore_blank_lines """
        nr_expected = nr_found = 0
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


    @staticmethod
    def show_version():
        """ shows the program version """
        print(f"{Prgtest.PROGRAM_NAME} versió {Prgtest.PROGRAM_VERSION}")

    def show_introprgdir(self):
        """ shows the working directory """
        print(self.working_dir)


    def show_target_path(self):
        """ shows the target path """
        print(self.target_path)

    @staticmethod
    def protected():
        """ True when the execution of this test is under protected mode """
        return not bool(os.environ.get('INTROPRG_UNPROTECTED'))


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
    if not colorschema in color_schemata:
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


class TextType(enum.Enum):
    GENERAL = enum.auto()
    EXPECTED = enum.auto()
    FOUND = enum.auto()


def compose_enumerated_text(lines, text_type=TextType.GENERAL, highlight_line=-1):
    """ composes the lines enumerated """
    width = len(str(len(lines) + 1))
    return "\n".join(compose_enumerated_line(n, line,
                                             text_type = text_type,
                                             highlighted=(n==highlight_line))
                     for n, line in enumerate(lines))


def compose_enumerated_line(linenr, line, width=0, text_type=TextType.GENERAL, highlighted=False, delimited=False):
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
    if highlighted:
        spaces = colorize_string('!', forecolor=get_color("FG_DIFF_MARK"), backcolor=get_color("BG_DIFF_MARK")) + '  '
    else:
        spaces = '   '
    if delimited:
        delimiter = colorize_string('|', forecolor=get_color("FG_DIFF_MARK"), backcolor=get_color("BG_DIFF_MARK"))
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
    error_mark = 'ERROR: '
    if not plain_color:
        error_mark = colorize_string(error_mark,
                                     forecolor=get_color('FG_ERROR'),
                                     backcolor=get_color('BG_ERROR'))

    print_err(f"{error_mark} {msg}")
    if tip is not None:
        print_err()
        for line in tip.splitlines():
            print_err("       " + line)
    print_err()
    sys.exit(1)


def print_warning_and_continue(msg: str, tip: str = None,
                               plain_color = False):
    """ prints the message to the stderr and continues with execution.
        if tip is set, it shows it in a different line.
        if plain_color, it does not try to colorize"""
    warning_mark = 'WARNING: '
    if not plain_color:
        warning_mark = colorize_string(warning_mark,
                                         forecolor=get_color('FG_WARNING'),
                                         backcolor=get_color('BG_WARNING'))
    print_err(f"{warning_mark} {msg}")
    if tip is not None:
        print_err()
        for line in tip.splitlines():
            print_err("       " + line)
    print_err()


def load_yaml(path, allow_non_existing=False):
    """ loads and returns a dict with the values kept in a yaml file
        In case allow_non_existing, it returns an empty dict when the file
        doesn't exist
        Otherwise, on non existing file it breaks execution """
    if not path.exists():
        if allow_non_existing:
            return dict()
        else:
            print_error_and_exit(f"File not found {path}")
    with open(path, 'r') as f:
        data = yaml.load(f, Loader=yaml.SafeLoader)
    if data is None:
        return dict()
    return data


##################################################
# Main
##################################################
if __name__ == '__main__':
    try:
        prgtest = Prgtest()
        prgtest.run()
    except SystemExit as exception:
        raise exception
    except Exception as exception:
        if Prgtest.protected():
            with open('__prgtest_dump.dat', 'a') as dest:
                dest.write("\n\n" + "=" * 100 + "\n\n")
                dest.write(f"{datetime.datetime.now()}\n")
                issue = Path('/etc/issue')
                dest.write(issue.read_text() if issue.is_file() else 'NO ISSUE\n')
                dest.write(f"{sys.version}\n")
                dest.write(str(os.environ))
                traceback.print_exc(file=dest)
            print_error_and_exit("S'ha produït un error intern de prgtest. Comenta-li al teu docent")
        else:
            traceback.print_exc()
            print_error_and_exit("Internal error")
