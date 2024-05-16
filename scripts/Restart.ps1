# Navigate to the root directory where the pom.xml and docker-compose.yml are located
Push-Location -Path ".."

Clear-Host
Write-Host "Stopping and removing current Docker containers..."
docker-compose -f docker-compose.dev.yml down

Write-Host "Building the Maven project (skipping tests)..."
mvn clean package -DskipTests

Write-Host "Building and starting Docker containers..."
docker-compose -f docker-compose.dev.yml up --build
