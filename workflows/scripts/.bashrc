#!/bin/bash

set -o vi
bind '"kj":vi-movement-mode'
set show-mode-in-prompt on
set vi-cmd-mode-string "\1\e[2 q\2"
set vi-ins-mode-string "\1\e[6 q\2"
parse_git_branch() {
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/(\1)/'
}
export PS1="\u@\h \[\e[32m\]\w \[\e[91m\]\$(parse_git_branch)\[\e[00m\]$ "

function jup() {
    cd ~/isa-devops-22-23-team-g-23/workflows/
    sudo docker-compose up
}

function jdown() {
    cd ~/isa-devops-22-23-team-g-23/workflows/
    sudo docker-compose down
}
function docksh(){
    sudo docker exec -it $1 /bin/bash
}


function jreboot(){
    cd ~/isa-devops-22-23-team-g-23/workflows/
    sudo docker-compose down
    yes|sudo docker container prune
    yes|sudo docker image prune -a
    sudo docker-compose up
}

function jrebootfull(){
    cd ~/isa-devops-22-23-team-g-23/workflows/
    sudo docker-compose down
    yes|sudo docker container prune
    yes|sudo docker image prune -a
    yes|sudo docker volume prune
    sudo docker-compose up
}