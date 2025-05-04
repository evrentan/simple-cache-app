#!/bin/bash

IMAGE_NAME="simple-cache-app"
CONTAINER_NAME="simple-cache-app"

# Function to print help
print_help() {
  echo "Usage: ./run-simple-cache-app.sh [--ttl <ms>] [--nodebug] [--nogc] [--build] [--help]"
  echo ""
  echo "Arguments:"
  echo "  --ttl <ms>   Set cache TTL in milliseconds"
  echo "  --nodebug    Disable debug logs"
  echo "  --nogc       Disable System.gc() to prevent explicit garbage collection within the default/specified TTL"
  echo "  --build      Force rebuild the Docker image"
  echo "  --help       Show this help message"
}

# Default values
EXTRA_ARGS=()
FORCE_BUILD=false

# Parse arguments
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --ttl)
            EXTRA_ARGS+=("--ttl" "$2")
            shift
            ;;
        --nodebug|--nogc)
            EXTRA_ARGS+=("$1")
            ;;
        --build)
            FORCE_BUILD=true
            ;;
        --help)
            print_help
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            print_help
            exit 1
            ;;
    esac
    shift
done

# Install Docker if not installed
if ! command -v docker &> /dev/null; then
    echo "[INFO] Docker not found. Installing..."
    curl -fsSL https://get.docker.com -o get-docker.sh
    sh get-docker.sh
fi

# Build Docker image if needed
if [[ "$FORCE_BUILD" == true || -z "$(docker images -q $IMAGE_NAME)" ]]; then
    echo "[INFO] Building Docker image: $IMAGE_NAME"
    docker build -t $IMAGE_NAME .
fi

# Stop and remove any running container with the same name
if docker ps -q -f name=$CONTAINER_NAME | grep -q .; then
    echo "[INFO] Stopping running container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME
fi

# Remove exited container if exists
if docker ps -a -q -f name=$CONTAINER_NAME | grep -q .; then
    echo "[INFO] Removing existing container: $CONTAINER_NAME"
    docker rm $CONTAINER_NAME
fi

# Run the container
echo "[INFO] Running container with args: ${EXTRA_ARGS[*]}"
docker run --name $CONTAINER_NAME -it --rm $IMAGE_NAME "${EXTRA_ARGS[@]}"
