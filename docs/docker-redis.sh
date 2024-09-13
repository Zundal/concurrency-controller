#!/bin/bash

CONTAINER_NAME="redis-concurrency"

# 컨테이너가 이미 존재하는지 확인
if [ "$(docker ps -a -q -f name=$CONTAINER_NAME)" ]; then
    # 컨테이너가 존재하면 시작
    echo "Starting existing Redis container..."
    docker start $CONTAINER_NAME
else
    # 컨테이너가 존재하지 않으면 새로 실행
    echo "Redis container not found. Creating and starting a new one..."
    docker run -d --name $CONTAINER_NAME -p 6379:6379 redis
fi
