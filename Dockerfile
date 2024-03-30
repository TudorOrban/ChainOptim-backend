FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# Use wait-for-it.sh to wait for redis to be ready
COPY scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY target/ChainOptimizer-0.0.1-SNAPSHOT.jar /app/ChainOptimizer.jar

ENTRYPOINT ["/wait-for-it.sh", "redis:6379", "--", "java", "-jar", "ChainOptimizer.jar"]

EXPOSE 8080