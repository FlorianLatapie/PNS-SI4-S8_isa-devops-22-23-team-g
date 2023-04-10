#!/usr/bin/expect -f

set timeout -1

spawn sudo ./config.sh

expect "Installation Directory (Default: /root/.jfrog/artifactory)"

send -- "/opt/jfrog/artifactory\r"

expect "Please specify the IP address of this machine"

send -- "\r"

expect "Are you adding an additional node to an existing product cluster?"

send -- "N\r"

expect "Do you want to install PostgreSQL?"

send -- "n\r"

expect "Enter database type,"

send -- "derby\r"

expect eof

