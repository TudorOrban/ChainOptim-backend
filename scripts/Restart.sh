#!/bin/bash

# Navigate to the root directory where the pom.xml and docker-compose.prod.yml are located
cd ..

echo "Stopping and removing current Docker containers..."
docker-compose -f docker-compose.yml down

echo "Building the Maven project (skipping tests)..."
mvn clean package -DskipTests

echo "Building and starting Docker containers..."
docker-compose -f docker-compose.yml up --build
