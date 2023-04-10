#!/bin/bash

echo -e "\n###################### install-smee.sh ######################\n"

CURR_DIR="$(pwd)"

# Install node

echo -e "\nInstall node\n"

cd "$HOME" || exit 1 

sudo apt-get install -y \
     curl \

curl -sL https://deb.nodesource.com/setup_18.x -o /tmp/nodesource_setup.sh
echo -e "\n\nVérifiez le script ...\nun éditeur s'ouvre (:q pour quitter vi)\n\n"
read -r -p "Appuyer sur une touche pour continuer"
${EDITOR:-${VISUAL:-vi}} /tmp/nodesource_setup.sh
sudo bash /tmp/nodesource_setup.sh
sudo apt-get install -y nodejs

# Install screen

echo -e "\nInstall screen\n"

sudo apt install screen -y

# Install Smee client

echo -e "\nInstall Smee client\n"

sudo npm install --global smee-client

cd "$CURR_DIR"
