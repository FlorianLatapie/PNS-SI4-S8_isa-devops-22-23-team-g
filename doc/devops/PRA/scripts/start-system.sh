#!/bin/bash

echo -e "\n###################### start-system.sh ######################\n"

CURR_DIR="$(pwd)"
FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
SSH_DIR="$HOME/.ssh"
WORKFLOWS_DIR="$FILE_DIR/../../../../workflows"

# create rsa key if it does not exist
if ! [[ -f "$SSH_DIR/id_rsa" ]]; then
  mkdir -p "$SSH_DIR"
  chmod 700 "$SSH_DIR"
  ssh-keygen -b 4096 -t rsa -f "$SSH_DIR/id_rsa" -N "" -C "$USER@$HOSTNAME"
fi

if ! [[ -f "$WORKFLOWS_DIR/env/.secrets.env" ]]; then
  source "$FILE_DIR/initialize-secrets.sh"
fi

# go to file directory to follow relative path
cd "$FILE_DIR" || exit 1

cd ../../../../workflows || exit 1
sudo docker-compose up --build -d

# go back to execution directory
cd "$CURR_DIR" || exit 1

sleep 10
firefox localhost:8000 &
