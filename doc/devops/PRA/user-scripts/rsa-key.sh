#!/bin/bash

if [[ ! -f ~/.ssh/id_rsa.pub ]]
then
    echo "Generating RSA key"
    ssh-keygen -b 4096
fi
ssh-copy-id -i ~/.ssh/id_rsa.pub teamg@vmpx07.polytech.unice.fr