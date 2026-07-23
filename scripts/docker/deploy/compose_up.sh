#!/bin/bash
PROJECT_ROOT="$(pwd)/../../.."

docker compose \
    --env-file "${PROJECT_ROOT}/.env" \
    --file "${PROJECT_ROOT}/compose.yml" \
    up \
    --remove-orphans \
    --force-recreate \
    --build \
    --detach
