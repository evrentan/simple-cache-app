# Use a base JDK image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy Java files
COPY src/*.java ./src/

# Compile Java files
RUN javac -d out src/*.java

# Set default command with optional TTL argument
ENTRYPOINT ["java", "-cp", "out", "SimpleCacheApp"]
