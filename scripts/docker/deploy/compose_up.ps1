#!/usr/bin/env bash
$ProjectRoot = Resolve-Path -Path (Join-Path (Get-Location) "..\..\..")

docker compose `
    --env-file (Join-Path $ProjectRoot ".env") `
    -f (Join-Path $ProjectRoot "compose.yml") `
    up `
    --remove-orphans `
    --force-recreate `
    --build `
    --detach
