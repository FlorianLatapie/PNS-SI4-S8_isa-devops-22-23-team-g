#!/bin/bash
source End2End/utils.sh

while getopts "d:f:" opt; do
  case $opt in
    d) DEMO=true        ;;
    f) file="$OPTARG"
       DEMO=true   ;;
  esac
done

if ! [[ -z "${JENKINS_URL}" ]]; then
    if ! command -v socat &> /dev/null; then
        echo "Installing socat"
        sudo apt-get install socat
    fi 
    if ! command -v jq &> /dev/null; then
        echo "Installing jq ..."
        sudo apt-get install jq
    fi 
fi

if [ "$ENV" == "prod" ] ; then
    echo "Running in production mode ..."
elif [ "$DEMO" == "true" ] ; then
    echo "Running in demo mode ..."
else 
    echo "Running in staging mode ..."
fi

if [ "$ENV" == "prod" ] || [ "$DEMO" == "true" ]; then
    COMPOSE_CMD="docker-compose -f docker-compose.prod.yml -p mfc-prod"
else
    COMPOSE_CMD="docker-compose"
fi

testSuiteResults=()

if [[ -z "${JENKINS_URL}" ]]; then
    eval "$COMPOSE_CMD down" && eval "$COMPOSE_CMD up -d"
    else 
    eval "$COMPOSE_CMD up -d"
fi

if [ "$ENV" == "prod" ] || [ "$DEMO" == "true" ]; then
    CONTAINER_NAME="mfc-cli-prod"
fi

waitForCLIStart

if [ -z "$file" ]; then
    testRunner "./End2End/creditCard.json"
    testSuiteResults+=($?) 

    testRunner "./End2End/loyaltyCard.json"
    testSuiteResults+=($?) 

    testRunner "./End2End/statsGlobal.json"
    testSuiteResults+=($?) 

    testRunner "./End2End/VFP.json"
    testSuiteResults+=($?) 

    testRunner "./End2End/advantages.json"
    testSuiteResults+=($?) 
    else 
    testRunner "$file"
    testSuiteResults+=($?) 
fi

FAILED_TESTS_SUITES=0
for testSuite in "${testSuiteResults[@]}" 
do
    if [ "$testSuite" -ne 0 ];then
        FAILED_TESTS_SUITES=$(($FAILED_TESTS_SUITES + 1))
    fi
done

printf '\n'

if ! [[ -z "${JENKINS_URL}" ]]; then
    docker-compose down
    docker container rm cli -f
    docker container rm server -f
    docker container rm db -f
    docker container rm bank -f
fi

if [ "$FAILED_TESTS_SUITES" -eq 0 ];then
    printf "${GREEN}All test suites have passed${NC}\n"
    exit 0;
else 
    if [ "$FAILED_TESTS_SUITES" -eq 1 ];then
        printf "${RED}$FAILED_TESTS_SUITES Test suite has failed${NC}\n"
    else
        printf "${RED}$FAILED_TESTS_SUITES Test suites have failed${NC}\n"
    fi
    printf '\n'
    exit 1;
fi
