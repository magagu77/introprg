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
from collections import namedtuple
from pathlib import Path
from argparse import ArgumentParser
import git
import yaml

# Encapsulation of the out,err and return code of a subprocess execution
SubprocessResult = namedtuple('SubprocessResult', ['stdout', 'stderr', 'returncode'])

class Prgtest:
    """ this class encapsulates the prgtest functionality """
    PROGRAM_NAME = "prgtest"
    PROGRAM_DESCRIPTION = ("Aquest programa et permet avaluar els teus exercicis"
                           " de programació abans de lliurar-los")
    PROGRAM_VERSION = "2021-22"

    DEFAULT_TIMEOUT = 10 # max time (seconds) allowed for a sigle test

    INTROPRGDIR_KEY = "INTROPRGDIR"
    INTROPRGSPECDIR_KEY = "INTROPRGSPECDIR"
    INTROPRGSUPPORTDIR_KEY = "INTROPRGSUPPORTDIR"

    DEFAULT_WORKING_DIR_NAME = "introprg"
    DEFAULT_SUPPORT_DIR_NAME = ".introprg"
    DEFAULT_SPEC_DIR_NAME = "exercises"
    FLAG_RELATIVE_PATH =  'tmp/last.yaml'
    TEST_RELATIVE_PATH =  'tmp/test'
    SPECS_FILENAME = "specs.yaml"
    JUNIT_CLASSNAME = 'TestExercise'
    JUNIT_FILENAME = f'{JUNIT_CLASSNAME}.java'

    PRGTEST_AUTHOR_NAME = 'prgtest'
    MSG_ERR_INTROPRGDIR_OPT_NOT_FOUND = ("No trobo el directori %s especificat "
                                         "al paràmetre --introprgdir")
    MSG_ERR_INTROPRGDIR_ENV_NOT_FOUND = ("No trobo el directori %s especificat "
                                         "a la variable d'entorn INTROPRGDIR")
    MSG_ERR_NO_JUNIT = "Aquest exercici no disposa de proves unitàries"
    MSG_ERR_JUNIT_OVERWRITE = f"Ja existeix un fitxer anomenat {JUNIT_FILENAME}"

    MSG_AUTOCOMMIT = 'prgtest autocommit #%s %s: %s'

    MSG_MISSING_COMMIT = "Cal registrar els canvis a git"
    MSG_MISSING_COMMIT_TIP = ("Considera:\n"
                              '$ git commit -am "»descripció dels canvis realitzats»')
    MSG_ALL_TEST_PASSED = "El teu exercici passa totes les proves"
    MSG_EXERCISE_WITHOUT_TEST = "Aquest exercici no disposa de proves automàtiques"
    MSG_EXERCISE_WITH_MORE_LINES_THAN_EXPECTED = "No s'esperava la línia:"
    MSG_EXERCISE_WITH_LESS_LINES_THAN_EXPECTED = "A la sortida del programa li falta:"
    MSG_EXERCISE_WITH_DIFFERENT_LINES = "Les següents línies difereixen"
    MSG_EXERCISE_NEVER_ENDS = "El teu exercici tarda massa en finalitzar"
    MSG_EXERCISE_NEVER_ENDS_TIP = "Executa'l manualment amb les entrades especificades"
    MSG_COMPILATION_ERROR = "S'ha trobat errors compilant %s"
    MSG_COMPILATION_JUNIT_ERROR = "S'ha trobat errors compilant les proves de JUnit"
    MSG_EXERCISE_BREAKS = "El teu exercici finalitza inesperadament"
    MSG_EXERCISE_BREAKS_TIP = ("Executa l'exercici fora de prgtest amb l'entrada indicada i "
                               "revisa les línies remarcades a la sortida d'error")
    MSG_JUNIT_COPIED = f"Trobaràs les proves de JUnit al fitxer {JUNIT_FILENAME}"

    MSG_TEXT_PASSED_TEST = "PASSA"
    MSG_TEXT_FAILED_TEST = "FALLA"
    MSG_TITLE_PROGRAM_EXECUTION = "Execució del programa"
    MSG_TITLE_STANDARD_INPUT = "Entrada estàndard"
    MSG_TITLE_EXPECTED_OUTPUT = "Sortida esperada"
    MSG_TITLE_STANDARD_OUTPUT = "Sortida trobada"
    MSG_TITLE_STANDARD_ERROR = "Sortida d'error trobada"
    MSG_TITLE_DISCREPANCY = "Discrepància"
    MSG_TITLE_JUNIT_ERROR = "Error de JUnit"

    def __init__(self):
        self.params = Prgtest.process_params()
        self.working_dir = None             # path to the dir containing the student's repo
        self.target_path = None             # path to the dir containing the target exercise
        self.specs = dict()                 # specs fot testing the target exercise
        self.test_path = None               # temporary path for testing
        self.env = Prgtest.compute_env()    # environment including CLASSPATH


    @property
    def target_exercise(self):
        """ returns the name of the target exercise """
        return self.target_path.name


    @property
    def target_exercise_id(self):
        """ returns the id of the target exercise """
        return "_".join(self.target_exercise.split('_', maxsplit=2)[:2])


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
            if self.params.get('specs') is not None:
                return Path(self.params.get('specs')).parent
            return Path.cwd()
        specs_dir = os.environ.get(Prgtest.INTROPRGSPECDIR_KEY)
        if specs_dir is None:
            specs_dir = self.support_dir / Prgtest.DEFAULT_SPEC_DIR_NAME
        else:
            specs_dir = Path(specs_dir)
        if not specs_dir.is_dir():
            print_error_and_exit(f"No es troba el directori d'especificacions {specs_dir}")
        return specs_dir


    @staticmethod
    def compute_env():
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


    def run(self):
        """ runs the prgtest functionality from the given params """

        # check --version
        Prgtest.check_option(self.params, 'version', Prgtest.show_version)


        # define the working directory
        self.working_dir = Prgtest.find_prgtestdir(self.params)

        # check --show-introprgdir
        Prgtest.check_option(self.params, 'show_introprgdir', self.show_introprgdir)

        # Once here the program has to deal with a concrete exercise
        self.find_target_path()     # obtain the target path

        # Obtain exercise specifications
        self.obtain_specs()

        # check --show-target-path
        Prgtest.check_option(self.params, 'show_target_path', self.show_target_path)

        # check non testable exercise
        self.check_nontestable()

        # check --copy-junit
        Prgtest.check_option(self.params, 'copy_junit', self.copy_junit_to_target)

        # Reaching here means the code is properly managed by git
        self.check_compiled()   # let's see if the code compilation is updated

        # Reaching here means that the target should be tested
        self.check_git()    # let's see whether the exercise is properly managed by git

        # Prepare test environment
        self.prepare_test()

        # Perform IO tests
        self.perform_io_tests()

        # Perform JUnit tests
        self.perform_junit_tests()

        print(Prgtest.MSG_ALL_TEST_PASSED)


    def prepare_test(self):
        """ prepares the test environment.
            The test env is a temporary folder that will hold all the rellevant
            files for the test.  These files include: all the target's contents
            and the junit file if present.
            The test_path is place in a gitignored folder and will be removed
            on each prgtest execution. For these reasons, it is not necessary
            to worry about cleaning it up after prgtest """
        if Prgtest.protected():
            self.test_path = self.support_dir / Prgtest.TEST_RELATIVE_PATH
            if self.test_path.exists():
                shutil.rmtree(self.test_path)
            shutil.copytree(self.target_path, self.test_path)
        else:
            self.test_path = self.target_path


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
        parser.add_argument("-j", "--copy-junit",
                            action='store_true',
                            help="Copia el fitxer amb els tests de JUnit a la carpeta de l'exercici",)

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
                print_error_and_exit(Prgtest.MSG_ERR_INTROPRGDIR_OPT_NOT_FOUND % path)
            return path

        # check if defined in environment
        path = os.environ.get(Prgtest.INTROPRGDIR_KEY)
        if path is not None:
            path = Path(path)
            if not path.is_dir():
                print_error_and_exit(Prgtest.MSG_ERR_INTROPRGDIR_ENV_NOT_FOUND % path)
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
                                            "https://moiatjda.github.io/jda.dev.m03/holagit.html"))
        return path


    def find_target_path(self):
        """ it finds the path of the target exercise. i.e. the exercise to be evaluated
            Currently, the only accepted way to define the target exercise is by running
            the program in the folder of the exercise """
        current = Path.cwd()
        if Prgtest.protected():
            # check current is within working directory
            if self.working_dir != current .parent:
                print_error_and_exit("El directori actual no es troba dins del directori de treball")
        self.target_path = current


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
            if self.autocommittable():
                self.autocommit(repo)
            else:
                print_error_and_exit(Prgtest.MSG_MISSING_COMMIT,
                                     tip=Prgtest.MSG_MISSING_COMMIT_TIP)
        else:
            self.reset_autocommit_flag(repo)


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
                print_error_and_exit(f"El fitxer {srcfile.relative_to(self.target_path)} "
                                     "ha estat modificat després de compilar",
                                     tip=("Considera una de les següents possibilitats:\n"
                                          f"$ javac {srcfile.relative_to(self.target_path)}\n"
                                          "o\n"
                                          "$ javac *.java"))


    def get_timeout(self):
        """ returns the timeout for the execution of the target program """
        return self.specs.get('_timeout', Prgtest.DEFAULT_TIMEOUT)


    def perform_io_tests(self):
        """ performs the I/O tests specified on specs for target """
        for testid, testspecs in self.specs.items():
            if testid.startswith('_'):
                continue
            # normalize stdin and args
            if 'stdin' in testspecs:
                testspecs['stdin'] = Prgtest.normalize_entry_spec(testspecs.get('stdin'))
            if 'argsin' in testspecs:
                testspecs['argsin'] = Prgtest.normalize_entry_spec(testspecs.get('argsin'))
            result = self.run_io_tests(
                stdin=testspecs.get('stdin'),
                argsin=testspecs.get('argsin'))
            self.compare_io_result(testid, result)


    def perform_junit_tests(self):
        """ performs the JUnit tests """
        if not self.has_junit_test():
            return

        # copy junit test file
        if Prgtest.protected():
            dest_path = self.test_path / Prgtest.JUNIT_FILENAME
            shutil.copy(self.compose_junit_path(), dest_path)
        else:
            junit_path = self.specs_dir / Prgtest.JUNIT_FILENAME
            dest_path = Path.cwd() / Prgtest.JUNIT_FILENAME
            if junit_path != dest_path:
                shutil.copy(junit_path, dest_path)

        # perform the tests
        result = self.run_junit_tests()
        self.compare_junit_result(result)


    @staticmethod
    def normalize_entry_spec(entry):
        """ normalizes an entry spec (i.e. the value of stdin, argsin, etc.)
            The value of an entry spec can be a single string or a list of strings.
            After normalization, the result will be always a list. Even if empty.
            In case entry is None, it returns None """
        if entry is None or isinstance(entry, list):
            return entry
        return [entry]


    def run_io_tests(self, stdin=None, argsin=None):
        """ runs the target with the specified stdin and returns the SubprocessResult """

        # compile target with the given environment
        result = run_javac(program_name=self.get_main_program(),
                           folder=self.test_path, env=self.env)
        if result.returncode != 0:
            print_error_and_exit(Prgtest.MSG_COMPILATION_ERROR % self.get_main_program(),
                                 tip="\n".join(result.stderr))

        # run test with the given stdin, args, env and timeout.
        stdin = '' if stdin is None else '\n'.join(str(item) for item in stdin) + '\n'
        return run_java(class_name=self.get_main(),
                        folder=self.test_path,
                        stdin=stdin, argsin=argsin,
                        env=self.env, timeout=self.get_timeout())


    def run_junit_tests(self):
        """ runs the JUnit test and returns the SubprocessResult  """
        # compile junit tests
        result = run_javac(program_name=Prgtest.JUNIT_FILENAME,
                           folder=self.test_path,
                           env=self.env)
        if result.returncode != 0:
            print_error_and_exit(Prgtest.MSG_COMPILATION_JUNIT_ERROR,
                                 tip="\n".join(result.stderr))

        # run junit tests
        argsin = ['-c', Prgtest.JUNIT_CLASSNAME,
                  '--disable-banner', '--fail-if-no-tests',]
                  #'--disable-ansi-colors']
        return run_java(class_name='org.junit.platform.console.ConsoleLauncher',
                        folder=self.test_path,
                        env=self.env,
                        timeout=self.get_timeout(),
                        argsin=argsin,
                        general=False,
                        )


    def compare_io_result(self, testid, test_result):
        """ compares the test_result found when running target on testid with
            the expected one """

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
            for chsrc in translatespecs[0]:
                output = [line.replace(chsrc, translatespecs[1]) for line in output]
            return output

        def compare_output(stdout):
            """ compares stdout with expected output as defined in specs """
            ignore_blank_lines = self.specs.get('_ignore_blank_lines', True)
            expected = self.specs[testid].get('stdout')
            if expected is None:
                return None
            output = prepare_output(stdout)
            result = Prgtest.compare_lines(expected, output,
                                           ignore_blank_lines=ignore_blank_lines)
            return result

        if test_result.returncode == 0:
            self.normalize_expected_output(testid)
            result = compare_output(test_result.stdout)
            if result is None:
                Prgtest.show_test_passed(testid)
                return

        Prgtest.show_test_failed(testid)
        self.show_program_execution(testid)
        self.show_provided_stdin(testid)

        if test_result.returncode == 0:
            nr_expected, nr_found = result
            self.show_discrepancy(testid, nr_expected, nr_found, test_result.stdout)
        elif test_result.returncode == 124:    # timeout
            print_error_and_exit(Prgtest.MSG_EXERCISE_NEVER_ENDS,
                      tip=Prgtest.MSG_EXERCISE_NEVER_ENDS_TIP)
        else:
            Prgtest.show_found_stderr(test_result.stderr)
            print_error_and_exit(Prgtest.MSG_EXERCISE_BREAKS,
                      tip=Prgtest.MSG_EXERCISE_BREAKS_TIP)
        sys.exit(1)


    def compare_junit_result(self, test_result):
        """ compares the results of the junit tests """
        if test_result.returncode == 0:
            Prgtest.show_test_passed('JUnit')
            return
        Prgtest.show_test_failed('JUnit')
        Prgtest.show_found_junit_error(test_result.stdout)
        sys.exit(1)


    @staticmethod
    def show_test_passed(testid):
        """ shows the test has passed """
        text = colorize_string(f" {Prgtest.MSG_TEXT_PASSED_TEST} ",
                               forecolor=get_color("FG_PASS_TEST"),
                               backcolor=get_color("BG_PASS_TEST"))
        print(f"Test {testid}: {text}")


    @staticmethod
    def show_test_failed(testid):
        """ shows the test has failed """
        text = colorize_string(f" {Prgtest.MSG_TEXT_FAILED_TEST} ",
                               forecolor=get_color("FG_FAIL_TEST"),
                               backcolor=get_color("BG_FAIL_TEST"))
        print(f"Test {testid}: {text}")


    def show_discrepancy(self, testid, nr_expected, nr_found, stdout):
        """ shows discrepancy between expected and found """
        self.show_expected_output(testid, nr_expected)
        Prgtest.show_found_output(nr_found, stdout)
        self.show_difference(testid, nr_expected, nr_found, stdout)


    def show_program_execution(self, testid):
        """ shows the command line call to the target program including arguments """
        argsin = self.specs[testid].get('argsin', '')
        if argsin is not None:
            argsin = ' '.join(argsin)
        print_err(compose_title(Prgtest.MSG_TITLE_PROGRAM_EXECUTION))
        print_err("L'execució ha estat la següent:\n")
        colorized_command = colorize_string(f'java {self.get_main()}',
                                            forecolor=get_color('FG_JAVAC_CMD'),
                                            backcolor=get_color('BG_JAVAC_ARGS'))
        colorized_args = colorize_string(argsin,
                                         forecolor=get_color('FG_JAVAC_ARGS'),
                                         backcolor=get_color('BG_JAVAC_ARGS'))
        print_err(f"$ {colorized_command} {colorized_args}")


    def show_provided_stdin(self, testid):
        """ shows the contents entered by stdin to the target program (if any) """
        if 'stdin' in self.specs[testid]:
            Prgtest.show_output_on_stderr(
                title=Prgtest.MSG_TITLE_STANDARD_INPUT,
                msg="Se li ha passat el següent codi per entrada estàndard\n",
                output=self.specs[testid]['stdin'],
            )


    def show_expected_output(self, testid, nr_expected):
        """ shows the contents expected from the target program execution """
        if 'stdout' in self.specs[testid]:
            Prgtest.show_output_on_stderr(
                title=Prgtest.MSG_TITLE_EXPECTED_OUTPUT,
                msg="S'esperava la següent sortida del programa:\n",
                output=self.specs[testid]['stdout'],
                text_type=TextType.EXPECTED,
                highlight_line=nr_expected)


    @staticmethod
    def show_found_output(nr_found, stdout):
        """ shows the found contents from the target program execution """
        Prgtest.show_output_on_stderr(
            title=Prgtest.MSG_TITLE_STANDARD_OUTPUT,
            msg="La sortida que ha generat el programa ha estat:\n",
            output=stdout,
            text_type=TextType.FOUND,
            highlight_line=nr_found)


    @staticmethod
    def show_output_on_stderr(title: str, msg: str, output: str, text_type=None, highlight_line=-1):
        """ shows the given output on stderr """
        print_err(compose_title(title))
        print_err(msg)
        print_err(compose_enumerated_text(output, text_type=text_type, highlight_line=highlight_line))


    @staticmethod
    def show_found_stderr(stderr):
        """ shows the contents stderr from the target program execution on stderr """
        print_err(compose_title(Prgtest.MSG_TITLE_STANDARD_ERROR))
        print_err("La sortida d'error que ha generat el programa ha estat:")
        print_err(compose_enumerated_text(stderr,
                                          highlight_function=lambda line: not 'at java.' in line))

    @staticmethod
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
                    if f'JUnit Jupiter:{Prgtest.JUNIT_CLASSNAME}' in line:
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


        print_err(compose_title(Prgtest.MSG_TITLE_JUNIT_ERROR))
        print_err("JUnit ha generat la següent sortida d'error:")

        print_err("\t" + "\n\t".join(junit_output))

        print_err("Et proposo que consideris el següent error:")
        print_err("* " + "\n* ".join(digest_output(junit_output)))


    def show_difference(self, testid, nr_expected, nr_found, stdout):
        """ shows the difference between expected and stdout """
        print_err(compose_title(Prgtest.MSG_TITLE_DISCREPANCY))
        if nr_expected >= len(self.specs[testid].get('stdout', [])):
            print_err(Prgtest.MSG_EXERCISE_WITH_MORE_LINES_THAN_EXPECTED)
            print_err(compose_enumerated_line(nr_found,
                stdout[nr_found], text_type=TextType.FOUND))
        elif nr_found >= len(stdout):
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
            print_err(compose_enumerated_line(nr_found, stdout[nr_found],
                                              text_type=TextType.FOUND,
                                              delimited=True))


    def normalize_expected_output(self, testid):
        """ normalizes the expected output as defined in the specs to be
            compared to the actual output """
        testspecs = self.specs[testid]
        testspecs['stdout'] = Prgtest.normalize_entry_spec(testspecs.get('stdout'))


    def check_nontestable(self):
        """ checks whether target is non testable """
        if not self.specs:
            print(Prgtest.MSG_EXERCISE_WITHOUT_TEST)
            sys.exit(0)

    def autocommittable(self):
        """ returns True if an autocommit can be performed """
        flag_path = self.support_dir / Prgtest.FLAG_RELATIVE_PATH
        if not flag_path.is_file():
            return False
        old_contents = load_yaml(flag_path)
        if old_contents.get('exercise') != self.target_exercise:
            return False
        return True


    def autocommit(self, repo):
        """ sets the exercise flag considering last commit and if not the first one,
            performs the autocommit.
            It returns True when the autocommit has been performed """

        def do_autocommit(seq, message):
            """ performs the autocommit with the given seq nr and the student message """
            author = git.Actor(Prgtest.PRGTEST_AUTHOR_NAME, repo.head.commit.author.email)
            comment = Prgtest.MSG_AUTOCOMMIT % (seq, self.target_exercise_id, message)
            try:
                repo.git.add(all=True)
                repo.index.commit(message=comment, author=author)
            except git.GitError:
                return False
            return True

        flag_path = self.support_dir / Prgtest.FLAG_RELATIVE_PATH
        old_contents = load_yaml(flag_path) if flag_path.is_file() else {}
        splitted_last_comment = repo.head.commit.message.split(": ", maxsplit=2)
        same_exercise = old_contents.get('exercise') == self.target_exercise
        old_seq = old_contents.get('seq', -1)
        expected_msg = old_seq == 0 or (
            len(splitted_last_comment) == 2 and
            old_contents.get('msg') == splitted_last_comment[1]
        )
        if same_exercise and expected_msg:
            seq = old_seq + 1
            msg = old_contents['msg']
            do_autocommit(seq, msg)
            contents = {
                'msg': msg,
                'seq': seq,
                'exercise': self.target_exercise,
                'sha': repo.head.commit.hexsha,
            }
            save_yaml(flag_path, contents)
        else:
            self.reset_autocommit_flag(repo)


    def reset_autocommit_flag(self, repo):
        """ creates or resets the autocommit flag """
        flag_path = self.support_dir / Prgtest.FLAG_RELATIVE_PATH
        old_contents = load_yaml(flag_path) if flag_path.is_file() else {}
        current_commit = repo.head.commit
        current_sha = current_commit.hexsha
        current_msg = current_commit.message.strip()
        msg = old_contents.get('msg') if old_contents.get('sha') == current_sha else current_msg
        contents = {
            'msg': msg,
            'seq': 0,
            'exercise': self.target_exercise,
            'sha': current_sha,
        }
        if old_contents != contents:
            save_yaml(flag_path, contents)


    @staticmethod
    def check_option(params, option, method):
        """ checks option in params and if there, runs method and ends execution """
        if params.get(option):
            method()
            sys.exit(0)


    @staticmethod
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


    @staticmethod
    def show_version():
        """ shows the program version """
        print(f"{Prgtest.PROGRAM_NAME} versió {Prgtest.PROGRAM_VERSION}")


    def show_introprgdir(self):
        """ shows the working directory """
        print(self.working_dir)


    def compose_junit_path(self):
        """ returns the path to the junit test file """
        if not Prgtest.protected():
            return self.specs_dir / Prgtest.JUNIT_FILENAME
            #if self.params.get('specs') is None:
            #    path = Path.cwd() / Prgtest.JUNIT_FILENAME
            #else:
            #    path = Path(self.params.get('specs')).parent / Prgtest.JUNIT_FILENAME
            #if path.is_file():
            #    return path
            
        result = self.specs_dir / f"{self.target_exercise}.junit"
        return result


    def has_junit_test(self):
        """ returns True when there's a junit test for the target exercise """
        return self.compose_junit_path().is_file()


    def copy_junit_to_target(self):
        """ copies the junit file in the target directory.
            If the file is missing it halts with an error """
        if not self.has_junit_test():
            print_error_and_exit(Prgtest.MSG_ERR_NO_JUNIT)
        dest_path = self.target_path / Prgtest.JUNIT_FILENAME
        if dest_path.exists():
            print_error_and_exit(Prgtest.MSG_ERR_JUNIT_OVERWRITE)
        shutil.copy(self.compose_junit_path(), dest_path)
        print(Prgtest.MSG_JUNIT_COPIED)


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


class TextType(enum.Enum):
    GENERAL = enum.auto()
    EXPECTED = enum.auto()
    FOUND = enum.auto()


def compose_enumerated_text(lines,
                            text_type=TextType.GENERAL,
                            highlight_line=-1,
                            highlight_function=None):
    """ composes the lines enumerated """
    width = len(str(len(lines) + 1))
    return "\n".join(compose_enumerated_line(n, line, width=width,
                                             text_type=text_type,
                                             highlighted=(n==highlight_line),
                                             highlight_function=highlight_function)
                     for n, line in enumerate(lines))


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
             stdin = None, argsin = None, general= True):
    """ runs java class_name,
        on folder,
        passing argsin as command line arguments,
        passing stdin as standard input,
        considering env as environment,
        giving as much as timeout time to finish execution.
        considering general usage
        It returns the SubprocessResult """

    # Prepare command
    if general and 'INTROPRG_JAVAPOLICYFILE' in os.environ:
        command_list = ['java',
                        '-Djava.security.manager',
                        f"-Djava.security.policy={os.environ['INTROPRG_JAVAPOLICYFILE']}",
                        class_name]
    else:
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
# Main
##################################################

if __name__ == '__main__':
    initial_directory = Path.cwd()
    try:
        prgtest = Prgtest()
        prgtest.run()
    except SystemExit as exception:
        raise exception
    except Exception as exception:
        if Prgtest.protected():
            dump_path = initial_directory / '__prgtest_dump.dat'
            with open(dump_path, 'a') as dest:
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
