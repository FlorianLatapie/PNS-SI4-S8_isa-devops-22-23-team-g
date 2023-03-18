#!/bin/bash

ssh-keygen -b 4096
ssh-copy-id -i ~/.ssh/id_rsa.pub teamg@vmpx07.polytech.unice.fr