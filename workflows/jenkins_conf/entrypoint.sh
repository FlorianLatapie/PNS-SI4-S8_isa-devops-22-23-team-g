#!/bin/bash

#usermod -g root jenkins
chown jenkins /var/run/docker.sock
# chown jenkins /usr/bin/docker
# chown jenkins /usr/bin/docker-init
# chown jenkins /usr/bin/docker-proxy
# chown jenkins /usr/bin/dockerd
# chown jenkins /usr/bin/dockerd-rootless-setuptool.sh
# chown jenkins /usr/bin/dockerd-rootless.sh
# chown jenkins /usr/bin/rootlesskit-docker-proxy

setup-sshd