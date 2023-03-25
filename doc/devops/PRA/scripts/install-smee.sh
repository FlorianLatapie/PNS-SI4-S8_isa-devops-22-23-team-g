#!/bin/bash
 
# Install node
cd ~
curl -sL https://deb.nodesource.com/setup_18.x -o /tmp/nodesource_setup.sh
echo "Vérifier le script ..."
read -p "Appuyer sur une touche pour continuer"
vi /tmp/nodesource_setup.sh
sudo bash /tmp/nodesource_setup.sh
sudo apt-get install -y nodejs

# Install Smee client
npm install --global smee-client