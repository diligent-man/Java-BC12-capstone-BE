#!/bin/bash

PROJECT_ROOT=../../..
DOCKER_ROOT="docker"
CONTEXT_PATH="$PROJECT_ROOT/${DOCKER_ROOT}/be"

docker build \
    --tag java_bc12_capstone/be:1.0.0 \
    --platform linux/amd64 \
    --file "$CONTEXT_PATH/Dockerfile" \
    $CONTEXT_PATH
