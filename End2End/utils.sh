#!/bin/bash

CONTAINER_NAME="mfc-cli-staging"
if [[ -z "${JENKINS_URL}" ]]; then
        RED='\033[0;31m'
        NC='\033[0m'
        GREEN='\033[0;32m'
        BOLD='\033[1m'
    else 
        RED=''
        NC=''
        GREEN=''
        BOLD=''
fi


function runCommand() {
    echo "$1" | socat - exec:"docker attach $CONTAINER_NAME",pty
    sleep 1
    docker logs "$CONTAINER_NAME" --tail=3
}

function waitForCLIStart(){
   printf '\n'
   waitFor="Started CliApplication in"
   while ! docker logs "$CONTAINER_NAME" --tail=1 | grep -q "$waitFor"; do
        echo "Waiting for CLI to start ..."
        sleep 5
    done 
    echo "CLI has started!"
    printf '\n'
}

function waitEnterKey(){
    read -p "Press [ENTER] to continue" -s -r -n 1 key  

    if [[ $key != "" ]]; then 
        waitEnterKey 
    fi
}

function assertEquals(){
    echo "$2" |grep -Eq "$1"
    if [ $? -eq 0 ]; then
        printf "${GREEN}Test passed${NC}\n"
        return 0
    else
        printf "${RED}Test failed${NC}\n"
        return 1
    fi
}

function resetTable(){
    docker exec db sh -c "echo 'TRUNCATE $1 RESTART IDENTITY CASCADE;' | psql -d mfc-db-staging -U postgresuser" 
}

function testRunner(){
    globalResult=()
    failedTests=()
    failedTestsExpected=()
    failedTestsResults=()
    echo "Running test file: $1"
    for e2eTest in $(jq -r '.[] | @base64' "$1"); do
      _jq() {
        echo "${e2eTest}" | base64 --decode | jq -r "${1}"
      }
      COMMAND=$(_jq '.command')
      EXPECTED=$(_jq '.expected')
      NAME=$(_jq '.name')
      printf "\n%s\n" "$NAME"
      printf "Running command: %s\n" "$COMMAND"
      seedCommands="$(_jq '.seedCommands')"
      if [ "$seedCommands" != "null"  ]; then
        items="$(echo "$seedCommands" | jq -c -r '.[]')"
        for item in "${items[@]}"; do
            VOID=$(runCommand "$item")
        done
      fi
      RESULT=$(runCommand "$COMMAND")
      assertEquals "$EXPECTED" "$RESULT"
      globalResult+=($?) 
      lastElement="${globalResult[$((${#globalResult[@]} - 1))]}" 
      if(($lastElement != 0));then
        failedTests+=("$NAME")
        failedTestsExpected+=("$EXPECTED")
        failedTestsResults+=("$RESULT")
      fi
      if [ "$DEMO" == "true" ]; then
        waitEnterKey
      fi
    done
    FAILED_TESTS=0
    for localResult in "${globalResult[@]}" 
    do
        if [ "$localResult" -ne 0 ];then
            FAILED_TESTS=$(($FAILED_TESTS + 1))
        fi
    done
    
    printf '\n'
    if [ "$FAILED_TESTS" -eq 0 ];then
        printf "${GREEN}All tests passed${NC}\n"
        return 0;
    else 
        printf "${RED}$FAILED_TESTS tests have failed${NC}\n"
        printf '\n'
        for i in "${!failedTests[@]}"; do
            printf "${BOLD} ${failedTests[$i]} failed\n ${NC}"
            printf "Expected: \n\t ${failedTestsExpected[$i]}\n"
            printf "Result: \n\t ${failedTestsResults[$i]}\n"
            printf '\n'
        done
        return 1;
    fi
}