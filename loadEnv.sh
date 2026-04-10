#!/bin/sh

# Load environment variables from .env file
export $(grep -v '^#' .env | xargs)


echo "Environment variables loaded:"
echo DATASOURCE_USER=$DATASOURCE_USER
echo DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD
echo DATASOURCE_URL=$DATASOURCE_URL
echo JWT_SECRETKEY=$JWT_SECRETKEY