#!/bin/bash

echo -e "\n###################### initialize-artifactory.sh ######################\n"

FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
ARTIFACTORY_CONFIG_DIR="$FILE_DIR/../../../../workflows/artifactory_conf"
ARTIFACTORY_INSTALL_DIR="/opt/jfrog/artifactory"


ARTIFACTORY_USER=$(get_secret "ARTIFACTORY_USER")
ARTIFACTORY_PASSWORD=$(get_secret "ARTIFACTORY_PASSWORD")
ARTIFACTORY_URL="localhost"
ARTIFACTORY_PORT="8003"

while ! [[ -d "/opt/jfrog/artifactory/var/etc/artifactory" ]]; do
    sleep 5
done 

curl -X POST "http://$ARTIFACTORY_URL:$ARTIFACTORY_PORT/artifactory/api/system/configuration" -u "$ARTIFACTORY_USER:$ARTIFACTORY_PASSWORD" -H "Content-type: application/xml" -T artifactory.config.xml

curl -X POST "http://$ARTIFACTORY_URL:$ARTIFACTORY_PORT/artifactory/api/repositories/generic-releases-local" -u "$ARTIFACTORY_USER:$ARTIFACTORY_PASSWORD" -H "Content-Type: application/json" -T artifactory-repository.json


sudo cp "$ARTIFACTORY_CONFIG_DIR/artifactory.config.xml"  "$ARTIFACTORY_INSTALL_DIR/var/etc/artifactory/artifactory.config.latest.xml"
sudo chown 1030 "$ARTIFACTORY_INSTALL_DIR/var/etc/artifactory/artifactory.config.latest.xml"
sudo chgrp 1030 "$ARTIFACTORY_INSTALL_DIR/var/etc/artifactory/artifactory.config.latest.xml"
sudo cp "$ARTIFACTORY_CONFIG_DIR/artifactory-repository.json" "$ARTIFACTORY_INSTALL_DIR/var/etc/artifactory/artifactory.repository.config.latest.json"

sudo chown 1030 "$ARTIFACTORY_INSTALL_DIR/var/etc/artifactory/artifactory.repository.config.latest.json"
sudo chgrp 1030 "$ARTIFACTORY_INSTALL_DIR/var/etc/artifactory/artifactory.repository.config.latest.json"
