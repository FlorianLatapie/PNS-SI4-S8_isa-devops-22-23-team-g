#!/bin/bash
source End2End/utils.sh

# resetTable "answers"
# resetTable "advantage_items"
# resetTable "customers"
# resetTable "euro_transactions"
# resetTable "point_transactions"
# resetTable "questions"
# resetTable "shop_keeper_accounts"
# resetTable "shops"
# resetTable "surveys"
# resetTable "surveys_questions"

testSuiteResults=()

if [[ -z "${JENKINS_URL}" ]]; then
    docker-compose down && docker-compose up -d
    sleep 30
    else 
    docker-compose up -d
    sleep 60
fi


testRunner "./End2End/creditCard.json"
testSuiteResults+=($?) 

testRunner "./End2End/loyaltyCard.json"
testSuiteResults+=($?) 

testRunner "./End2End/statsGlobal.json"
testSuiteResults+=($?) 

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
