#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <UUID-for-items-array>"
  exit 1
fi

ITEM_UUID="$1"

echo "{\"userId\": \"0111b250-1c15-44ae-8149-6eef0867ed84\", \"items\": [\"$ITEM_UUID\"], \"restaurantId\": \"10a3edc2-0c83-45f3-9345-f27f828ed01a\"}" | kcat -P -b localhost:9092 -t createOrder
