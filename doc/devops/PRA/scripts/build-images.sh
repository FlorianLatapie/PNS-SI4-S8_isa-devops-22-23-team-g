#!/bin/bash

PROJECT_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
cd "$PROJECT_DIR"
cd ../../../../workflows/jenkins_conf
docker build -t ci/maven.artifactory -f ci.maven.Dockerfile .
docker build -t ci/node.artifactory -f ci.node.Dockerfile .
cd "$PROJECT_DIR"