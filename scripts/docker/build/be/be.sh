#!/bin/bash

PROJECT_ROOT=../../../..
CONTEXT_PATH="$PROJECT_ROOT/"
APP_VERSION="$(cd "$PROJECT_ROOT" && ./mvnw -q -DforceStdout help:evaluate -Dexpression=project.version)"

docker build \
    --tag java_bc12_capstone/be:${APP_VERSION} \
    --platform linux/amd64 \
    --file "$CONTEXT_PATH/Dockerfile" \
    $CONTEXT_PATH
