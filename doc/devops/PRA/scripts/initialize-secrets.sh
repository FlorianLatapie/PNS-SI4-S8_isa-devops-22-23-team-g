#!/bin/bash

echo -e "\n###################### initialize-secrets.sh ######################\n"

FILE_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
SECRETS_FILE="$FILE_DIR/../../../../workflows/env/.secrets.env"
SSH_DIR="$HOME/.ssh"

# create rsa key if it does not exist
if ! [[ -f "$SSH_DIR/jenkins_agent" ]]; then
  mkdir -p "$SSH_DIR"
  chmod 700 "$SSH_DIR"
  ssh-keygen -b 4096 -t rsa -f "$SSH_DIR/jenkins_agent" -N "" -C "$USER@$HOSTNAME"
fi

if ! [[ -f "$SECRETS_FILE" ]]; then
  touch "$SECRETS_FILE"
fi

echo "Your input will be writting in $SECRETS_FILE where you can always update it"
printf "\n"

read -p "Enter your desired Jenkins admin username: " -r JENKINS_USERNAME  
echo "JENKINS_ADMIN_ID=$JENKINS_USERNAME" >> "$SECRETS_FILE"
printf "\n"

read -p "Enter your desired Jenkins admin password: " -s -r JENKINS_PASSWORD  
echo "JENKINS_ADMIN_PASSWORD=$JENKINS_PASSWORD" >> "$SECRETS_FILE"
printf "\n"

echo "SONARQUBE_JDBC_USERNAME=admin" >> "$SECRETS_FILE"

read -p "Enter your desired SonarQube admin password: " -s -r SONARQUBE_PASSWORD  
echo "SONARQUBE_JDBC_PASSWORD=$SONARQUBE_PASSWORD" >> "$SECRETS_FILE"
printf "\n"

read -p "Enter your Github Token: " -s -r GITHUB_TOKEN  
echo "GITHUB_TOKEN=$GITHUB_TOKEN" >> "$SECRETS_FILE"
printf "\n"

AGENT_KEY="$(cat "$SSH_DIR/jenkins_agent")"
echo "AGENT_KEY=\"$AGENT_KEY\"" >> "$SECRETS_FILE"

JENKINS_AGENT_SSH_PUBKEY="$(cat "$SSH_DIR/jenkins_agent.pub")"
echo "JENKINS_AGENT_SSH_PUBKEY=$JENKINS_AGENT_SSH_PUBKEY" >> "$SECRETS_FILE"
