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

globalResult=()

docker-compose down && docker-compose up -d
sleep 30


testRunner "./End2End/creditCard.json"
globalResult+=($?) 

testRunner "./End2End/loyaltyCard.json"
globalResult+=($?) 

testRunner "./End2End/statsGlobal.json"
globalResult+=($?) 

FAILED_TESTS=0
for localResult in "${globalResult[@]}" 
do
    if [ "$localResult" -ne 0 ];then
        FAILED_TESTS=$(($FAILED_TESTS + 1))
    fi
done

printf '\n'
if [ "$FAILED_TESTS" -eq 0 ];then
    printf "${GREEN}All test suites have passed${NC}\n"
    exit 0;
else 
    if [ "$FAILED_TESTS" -eq 1 ];then
        printf "${RED}$FAILED_TESTS test suite has failed${NC}\n"
    else
        printf "${RED}$FAILED_TESTS test suites have failed${NC}\n"
    fi
    printf '\n'
    exit 1;
fi