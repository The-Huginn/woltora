#!/bin/bash

if [ $# -ne 3 ]; then
  echo "Usage: $0 <userId> <addressId> <foodId>"
  exit 1
fi

USER_ID="$1"
ADDRESS_ID="$2"
ITEM_UUID="$3"

echo "{\"userId\": \"$USER_ID\", \"addressId\": \"$ADDRESS_ID\", \"items\": [\"$ITEM_UUID\"], \"restaurantId\": \"10a3edc2-0c83-45f3-9345-f27f828ed01a\"}" | kcat -P -b localhost:9092 -t createOrder
