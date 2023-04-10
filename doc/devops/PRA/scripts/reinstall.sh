#!/bin/bash

CURR_DIR="$(pwd)"

printf "\n###################### reinstall.sh ######################\n"

printf "\n##### ACTIONS WILL BE REQUIRED #######\n"

cd "$CURR_DIR" || exit 1
source ./initialize-secrets.sh

cd "$CURR_DIR" || exit 1
source ./install-smee.sh

printf "\n##### ACTIONS WILL NO LONGER BE REQUIRED #######\n"
sleep 3

cd "$CURR_DIR" || exit 1
source ./base-config.sh
cd "$CURR_DIR" || exit 1
source ./install-docker.sh
cd "$CURR_DIR" || exit 1
source ./install-artifactory.sh
cd "$CURR_DIR" || exit 1
source ./start-smee.sh
cd "$CURR_DIR" || exit 1
source ./build-images.sh
cd "$CURR_DIR" || exit 1
source ./start-system.sh
cd "$CURR_DIR" || exit 1
source ./initialize-sonarQube.sh
cd "$CURR_DIR" || exit 1
source ./initialize-artifactory.sh
cd "$CURR_DIR" || exit 1
