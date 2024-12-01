#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Usage: $0 <orderId>"
    exit 1
fi

ORDER_ID="$1"

curl -s localhost:8082/api/order-location?orderId=$ORDER_ID | jq '{location}'
