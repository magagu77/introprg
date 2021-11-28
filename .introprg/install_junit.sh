#! /bin/bash

# Instal·lació de JUnit al repositori de introprg

function show_ok() {
    echo -e "\\e[1;37;42mCORRECTE\\e[0m: $1"
}

function show_error() {
    echo -e "\\e[1;37;41mERROR\\e[0m: $1"
}

function show_warning() {
    echo -e "\\e[1;37;43mAVíS\\e[0m: $1"
}

function check_junit() {
    java org.junit.platform.console.ConsoleLauncher -h &> /dev/null
}
    
JUNITJAR=junit-platform-console-standalone.jar
JUNITDEST="$INTROPRGDIR/.introprg"
function download_junit() {
    if [ -f "$JUNITDEST/$JUNITJAR" ];
    then
        show_ok "JUnit ja es troba descarregat"
    else
        echo "#####################"
        echo "# Descarregant JUnit#"
        echo "#####################"
        mkdir -p "$JUNITDEST"
        cd "$JUNITDEST" || exit
        wget -O $JUNITJAR -c https://search.maven.org/remotecontent?filepath=org/junit/platform/junit-platform-console-standalone/1.8.1/junit-platform-console-standalone-1.8.1.jar
        if [ "$?" -ne 0 ];
        then
            show_error "S'ha produït un error mentre es descarregava el fitxer."
            echo "Intenta-ho més tard o parla amb el teu docent si el problema persisteix."
            echo
            rm -f $JUNITJAR > /dev/null
        fi
        cd - &> /dev/null || exit
    fi
}

echo "Instal·lació i configuració del framework junit"
echo

# Comprova que el directori de suport existeixi
if [ -z "$INTROPRGDIR" ] || [ ! -d "$INTROPRGDIR/.introprg" ];
then
    show_error "La teva instal·lació no sembla completa. Comenta-li al teu docent."
    exit 1
fi

show_ok "Variables d'entorn d'introprg trobades"

if ! check_junit;
then
    download_junit
    if [ "$?" -ne 0 ];
    then
        show_error "No s'ha pogut descarregar JUnit. Comprova accés a internet o consulta al teu docent"
        exit
    fi
fi

# comprova si ja es troba al CLASSPATH
if ! check_junit;
then
    CLASSPATHLINE='CLASSPATH=$CLASSPATH:'$JUNITDEST/$JUNITJAR
    if grep "$CLASSPATHLINE" ~/.bashrc &> /dev/null;
    then
        show_ok "CLASSPATH ja configurat"
    else
        grep "$CLASSPATHLINE" ~/.bashrc &> /dev/null || echo "$CLASSPATHLINE" >> ~/.bashrc
        echo "S'ha configurat el CLASSPATH a ~/.bashrc"
        export CLASSPATH=$CLASSPATH:$JUNITDEST/$JUNITJAR
    fi
fi

if check_junit;
then
    show_ok "JUnit es troba instal·lada correctament"
else
    show_error "Problemes per fer la configuració automàtica. Consulta al teu docent."
fi
