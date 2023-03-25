#!/bin/bash

# inspired by https://github.com/FlorianLatapie/Config/blob/main/linux/ubuntu/ubuntu_install.sh

sudo apt update
sudo apt upgrade -y

# affiche le nom de la distro
echo -e "\nInstall neofetch\n"
sudo apt install neofetch -y

# github cli 
echo -e "\nInstall GitHub cli\n"
type -p curl >/dev/null || sudo apt install curl -y
curl -fsSL https://cli.github.com/packages/githubcli-archive-keyring.gpg | sudo dd of=/usr/share/keyrings/githubcli-archive-keyring.gpg \
&& sudo chmod go+r /usr/share/keyrings/githubcli-archive-keyring.gpg \
&& echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/githubcli-archive-keyring.gpg] https://cli.github.com/packages stable main" | sudo tee /etc/apt/sources.list.d/github-cli.list > /dev/null \
&& sudo apt update \
&& sudo apt install gh -y
