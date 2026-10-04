#!/bin/bash
PROJECT_ROOT="$(pwd)/../../.."

docker compose down \
    --remove-orphans \
    --force-recreate \
    --build \
    --detach
