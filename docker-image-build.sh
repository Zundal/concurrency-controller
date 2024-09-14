#!/bin/bash

# Gradle 빌드 수행
echo "Running Gradle build..."
./gradlew clean build --no-daemon

# Docker 이미지 빌드
echo "Building docker image..."
docker build -t concurrency .
