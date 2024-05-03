#!/bin/bash

# Navigate to the root directory where the pom.xml and docker-compose.yml are located
cd ..

echo "Stopping and removing current Docker containers..."
docker-compose down

echo "Building the Maven project (skipping tests)..."
mvn clean package -DskipTests

echo "Building and starting Docker containers..."
docker-compose up --build
