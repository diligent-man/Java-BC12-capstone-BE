#!/bin/bash
PROJECT_ROOT="$(pwd)/../../.."
APP_VERSION="$(cd "$PROJECT_ROOT" && ./mvnw -q -DforceStdout help:evaluate -Dexpression=project.version)"
export APP_VERSION

docker compose \
    --env-file "${PROJECT_ROOT}/.env.prod" \
    --file "${PROJECT_ROOT}/compose.yml" \
    up \
    --remove-orphans \
    --force-recreate \
    --build \
    --detach
