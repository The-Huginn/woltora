echo '{"firstName":"John","lastName":"Doe","email":"johndoe@example.com","type":"CUSTOMER"}' | kcat -P -b localhost:9092 -t createPerson