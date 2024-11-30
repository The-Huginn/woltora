#!/bin/bash

# Person details
firstName="John"
lastName="Doe"
email="johndoe@example.com"
type="CUSTOMER"

echo "Creating new person with attributes:"
echo "firstName: $firstName"
echo "lastName: $lastName"
echo "email: $email"
echo "type: $type"
echo ""

echo "{\"firstName\":\"$firstName\",\"lastName\":\"$lastName\",\"email\":\"$email\",\"type\":\"$type\"}" | kcat -P -b localhost:9092 -t createPerson

# Wait for the message to be processed
sleep 2

echo "Retrieving the created person details:"
curl -s "localhost:8084/api/person?lastName=$lastName"
echo ""

