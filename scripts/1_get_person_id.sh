#!/bin/bash
curl -s localhost:8084/api/person?lastName=Kozel | jq -r '.[0].id'
