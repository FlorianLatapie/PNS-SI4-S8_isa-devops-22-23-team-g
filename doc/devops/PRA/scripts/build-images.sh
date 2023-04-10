#!/bin/bash

echo -e "\n###################### build-images.sh ######################\n"

CURR_DIR="$(pwd)"
FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )

# go to file directory to follow relative path
cd "$FILE_DIR" || exit 1

cd ../../../../workflows/jenkins_conf || exit 1
sudo docker build -t ci/pipeline -f ci.Dockerfile .
cd "$PROJECT_DIR" || exit 1

# go back to execution directory
cd "$CURR_DIR" || exit 1
