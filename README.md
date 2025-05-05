# Simple Cache App

This is a simple cache app that is run from the command line. It allows you to store, retrieve, and delete key-value pairs in a cache. The cache is stored in a WeakHaspMap, which means that the keys are weakly referenced and can be garbage collected if there are no strong references to them. This is useful for caching data that is expensive to compute or retrieve, as it allows the cache to automatically free up memory when it is no longer needed.

It is designed to be simple and easy to use, with a command line interface that allows you to quickly interact with the cache. The app is written in Java 21 and dockerized for easy deployment.

## Features

- Store key-value pairs in a cache
- Retrieve values from the cache
- Delete values from the cache
- Print cache contents
- Print the size of the cache
- Automatic garbage collection of unused keys
- Command line interface for easy use
- Docker support for easy deployment

## Running the App

### Prerequisites

- Java 21
- Docker (You can install Docker with the help of [run-simple-cache-app.sh][run-simple-cache-app.sh] script automatically)

### Running the App

1. Clone the repository from [SimpleCacheApp GitHub Repo][simple-cache-app-repo-url] or with the command below:

   ```bash
   git clone git@github.com:evrentan/simple-cache-app.git
    ```
   
2. Change to the directory:

   ```bash
   cd simple-cache-app
   ```
   
3. Give execution permission to the script:

   ```bash
   chmod +x run-simple-cache-app.sh
   ```
   
4. Run the script:

   ```bash
    ./run-simple-cache-app.sh
    ```

5. Follow the instructions in the command line interface to interact with the cache.

Or you can run the app directly from my [simple-cache-app Docker Hub][simple-cache-app-docker-hub-url] repository in my [Docker Hub][evren-docker-hub-url] with the command below:

```bash
docker run -it --rm evrentan/simple-cache-app:latest
```

## Script Help

The script `run-simple-cache-app.sh` is a simple script that runs the Simple Cache App in a Docker container. It builds the Docker image and runs the container, allowing you to interact with the app from the command line.

You can check the help of the script with the command below:

```bash
./run-simple-cache-app.sh --help
```

Script has the following arguments:

- `--help`: Show help message and exit.
- `--build`: Force to rebuild the Docker image.
- `--ttl <ms>`: Set the time-to-live (TTL) for the cache in milliseconds. The default is 60000 ms (60 seconds).
- `--nogc`: Disable System.gc() to prevent explicit garbage collection within the default/specified TTL.
- `--nodebug`: Disable debug mode. By default, debug mode is enabled.

## How to Contribute

For the contributor covenant to this project, please check the Code of Conduct file.

[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)


[run-simple-cache-app.sh]: run-simple-cache-app.sh
[simple-cache-app-repo-url]: https://github.com/evrentan/simple-cache-app
[simple-cache-app-docker-hub-url]: https://hub.docker.com/r/evrentan/simple-cache-app
[evren-docker-hub-url]: https://hub.docker.com/u/evrentan
