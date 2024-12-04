#!/bin/bash

echo '{"restaurantId": "10a3edc2-0c83-45f3-9345-f27f828ed01a", "name": "Zradlo", "description": "Description", "price": 123.45}' | kcat -P -b localhost:9092 -t createFood

MONGO_DOCKER=$(docker ps --format 'table {{ .Names }}\t{{ .Image }}' | grep mongo | awk '{print $1}')

docker exec $MONGO_DOCKER mongosh food --eval 'db.Food.find()' | grep '_id' | tail -n 1 | awk -F \' '{print $2}'

