#!/bin/bash

PROJECT_ROOT=../../..
DOCKER_ROOT="docker"
CONTEXT_PATH="$PROJECT_ROOT/${DOCKER_ROOT}/mysql"

docker build \
    --tag java_bc12_capstone/mysql:1.0.0 \
    --platform linux/amd64 \
    --build-arg MYSQL_VERSION="${MYSQL_VERSION:-9.5.0}" \
    --file "$CONTEXT_PATH/Dockerfile" \
    $CONTEXT_PATH
