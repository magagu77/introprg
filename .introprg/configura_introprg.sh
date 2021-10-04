#! /bin/bash
echo "Programa d'ajuda a la configuració de l'entorn introprg"

echo -n "Comprovant versió de Python: "
pyver=$(python3 -V | cut -d . -f 2  2> /dev/null) 
if [[ "$pyver" -lt 7 ]];
then
    echo
    echo "PROBLEMA: la versió de Python3 no és adequada. Ha de ser com a mínim 3.7.x"
    exit 1
fi
echo "Fet"

echo -n "Comprovant si PATH inclou introprg: "
scriptdir=$(dirname "$0")
folder=$(realpath "$scriptdir")
line='export PATH=$PATH:'"$folder"
grep "$line" ~/.bashrc &> /dev/null || echo "$line" >> ~/.bashrc
echo "Fet"

echo -n "Comprovant si CLASSPATH inclou introprg: "
line='export CLASSPATH=${CLASSPATH:-.}:'"$folder"
grep "$line" ~/.bashrc &> /dev/null || echo "$line" >> ~/.bashrc
echo "Fet"

echo -n "Comprovant la definició de la variable INTROPRGDIR: "
introprgdir=$(dirname $folder)
line="export INTROPRGDIR=$introprgdir"
grep "$line" ~/.bashrc &> /dev/null || echo "$line" >> ~/.bashrc
echo "Fet"

echo -n "Comprovant si la utilitat pip es troba instal·lada: "
if pip3 -V > /dev/null;
then
    echo "Fet"
else
    echo "Instaŀlant"
    sudo apt install python3-pip -y
fi

echo -n "Actualitzant biblioteques requerides per prgtest: "
pip3 install -Ur "$folder/requirements.txt" > /dev/null
echo "Fet"

echo
echo "Recorda escriure la següent comanda si vols fer servir els canvis en aquest terminal"
echo -e "\tsource ~/.bashrc"
