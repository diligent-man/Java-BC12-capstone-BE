$PROJECT_ROOT = "../../.."
$DOCKER_ROOT = "docker"

$CONTEXT_PATH = "$PROJECT_ROOT/$DOCKER_ROOT/mysql"

$MYSQL_VERSION = if ($env:MYSQL_VERSION) { $env:MYSQL_VERSION } else { "9.5.0" }

docker build `
    --tag java_bc12_capstone/mysql:1.0.0 `
    --platform linux/amd64 `
    --build-arg "MYSQL_VERSION=$MYSQL_VERSION" `
    --file "$CONTEXT_PATH/Dockerfile" `
    $CONTEXT_PATH
