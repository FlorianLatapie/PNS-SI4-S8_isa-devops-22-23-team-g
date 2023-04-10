#!/bin/bash

echo -e "\n###################### initialize-sonarQube.sh ######################\n"

FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
SECRETS_FILE="$FILE_DIR/../../../../workflows/env/.secrets.env"

INITIAL_SONARUSER="admin"
INITIAL_SONARPASS="admin"

function get_secret(){
  echo $(cat "$SECRETS_FILE"|grep "$1"|sed "s/$1=//")
}

SONARQUBE_USER=$(get_secret "SONARQUBE_JDBC_USERNAME")
SONARQUE_PASSWORD=$(get_secret "SONARQUBE_JDBC_PASSWORD")
SONARQUBE_URL="localhost"
SONARQUBE_PORT="8001"
TOKEN_NAME="JENKINS_TOKEN"

function wait_sonar_start(){
   printf '\n'
   waitFor='data-server-status="UP"'
   while ! curl "$SONARQUBE_URL:$SONARQUBE_PORT"| grep -q "$waitFor"; do
        echo "Waiting for SonarQube to start ..."
        sleep 5
    done 
    echo "SonarQube has started!"
    printf '\n'
}

wait_sonar_start

# Updating ids with requested ids
curl -u "$INITIAL_SONARUSER":"$INITIAL_SONARPASS" -X POST "http://$SONARQUBE_URL:$SONARQUBE_PORT/api/users/change_password?login=$INITIAL_SONARUSER&previousPassword=$INITIAL_SONARPASS&password=$SONARQUE_PASSWORD"

RES=$(curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "name=${TOKEN_NAME}" -u ${SONARQUBE_USER}:${SONARQUE_PASSWORD} ${SONARQUBE_URL}:${SONARQUBE_PORT}/api/user_tokens/generate)

SONARQUBE_TOKEN="$(echo "$RES" | jq -c -r '.token')"
echo "SONARQUBE_TOKEN=$SONARQUBE_TOKEN" >> "$SECRETS_FILE"

# Creating projects
curl -u "$SONARQUBE_USER":"$SONARQUE_PASSWORD" -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "mainBranch=develop" -d "name=mfc-cli" -d "project=mfc-cli" "http://$SONARQUBE_URL:$SONARQUBE_PORT/api/projects/create"
curl -u "$SONARQUBE_USER":"$SONARQUE_PASSWORD" -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "mainBranch=develop" -d "name=mfc-backend" -d "project=mfc-backend" "http://$SONARQUBE_URL:$SONARQUBE_PORT/api/projects/create"

