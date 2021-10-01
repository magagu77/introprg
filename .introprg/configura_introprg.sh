#! /bin/bash
echo "Programa d'ajuda a la configuració de l'entorn introprg"

echo "Comprovant si PATH inclou introprg"
scriptdir=$(dirname "$0")
folder=$(realpath "$scriptdir")
line='export PATH=$PATH:'"$folder"
grep "$line" ~/.bashrc || echo "$line" >> ~/.bashrc

echo "Comprovant si CLASSPATH inclou introprg"
line='export CLASSPATH=${CLASSPATH:-.}:'"$folder"
grep "$line" ~/.bashrc || echo "$line" >> ~/.bashrc

echo "Comprovant si la utilitat pip es troba instal·lada"
pip3 -V || sudo apt install python3-pip -y

echo "Actualitzant biblioteques requerides per prgtest"
pip3 install -Ur "$folder/requirements.txt"
