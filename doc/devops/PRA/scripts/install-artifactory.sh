#!/bin/bash

VERSION="7.49.6"
CURR_DIR="$(pwd)"
FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
ARTIFACTORY_CONFIG_DIR="$FILE_DIR/../../../../workflows/artifactory_conf"
ARTIFACTORY_INSTALL_DIR="/opt/jfrog/artifactory"

chmod +x "$ARTIFACTORY_CONFIG_DIR/artifactory-installer.sh"

mkdir -p "$HOME/artifactory"
cd "$HOME/artifactory" || exit 1

curl -L https://releases.jfrog.io/artifactory/artifactory-pro/org/artifactory/pro/docker/jfrog-artifactory-pro/$VERSION/jfrog-artifactory-pro-$VERSION-compose.tar.gz > "jfrog-artifactory-pro-$VERSION-compose.tar.gz"
tar -xvf jfrog-artifactory-pro-$VERSION-compose.tar.gz
cd artifactory-pro-$VERSION || exit 1
cp "$ARTIFACTORY_CONFIG_DIR/artifactory-installer.sh" "$HOME/artifactory/artifactory-pro-$VERSION"
sudo ./artifactory-installer.sh

cd "$CURR_DIR" || exit 1
