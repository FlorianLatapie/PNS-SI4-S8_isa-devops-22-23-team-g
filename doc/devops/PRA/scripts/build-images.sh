#!/bin/bash

PROJECT_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd -P )
cd "$PROJECT_DIR"
cd ../../../../workflows/jenkins_conf
docker build -t ci/pipeline -f ci.Dockerfile .
cd "$PROJECT_DIR"