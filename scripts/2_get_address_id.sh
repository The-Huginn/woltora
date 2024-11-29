#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Usage: $0 <userId>"
    exit 1
fi

USER_ID="$1"

curl -s localhost:8084/api/address?userId=$USER_ID | jq '.[] | {id}'
