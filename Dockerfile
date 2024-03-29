FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY target/ChainOptimizer-0.0.1-SNAPSHOT.jar /app/ChainOptimizer.jar

ENTRYPOINT ["java", "-jar", "ChainOptimizer.jar"]

EXPOSE 8080