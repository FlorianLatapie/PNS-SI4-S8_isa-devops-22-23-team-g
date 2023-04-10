#!/bin/bash

echo -e "\n###################### base-config.sh ######################\n"

CURR_DIR="$(pwd)"
FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )

# go to file directory to follow relative path
cd "$FILE_DIR" || exit 1
cp ../config/.bashrc ~/.bashrc
source "$HOME/.bashrc"

# Install socat 

echo -e "\nInstall socat\n"

sudo apt install socat
sudo apt-get install jq -y
sudo apt-get install expect -y

# go back to execution directory
cd "$CURR_DIR" || exit 1
