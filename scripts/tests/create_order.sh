#!/bin/bash

echo "Retrieving person id with last name 'Kozel' ..."

PERSON_ID=$(bash ../1_get_person_id.sh)

echo "The retrieved person ID is: $PERSON_ID"

echo -e "\nRetrieving the id of the first user's address ..."

ADDRESS_ID=$(bash ../2_get_address_id.sh $PERSON_ID | jq -r '.id')

echo "The retrieved address ID is: $ADDRESS_ID"

echo -e "\nCreating new food ..."

FOOD_ID=$(bash ../8_mongo.sh)

echo "The new food ID is: $FOOD_ID"

echo -e "\nCreating new order ..."

bash ../4_create_order.sh $PERSON_ID $ADDRESS_ID $FOOD_ID

echo -e "\nRetrieving user's order list ..."

sleep 2

bash ../5_get_orders.sh $PERSON_ID
