FROM maven:3.8-openjdk-17 AS builder

# Installing dependencies to maven's local repository
WORKDIR /app
COPY ./ ./
RUN mvn clean install

# Packaging microserice
WORKDIR Report
RUN mvn clean package

# Running microserice
FROM eclipse-temurin:17.0.9_9-jre-jammy as runner

WORKDIR /app
COPY --from=builder /app/Report/target/Report-1.0-SNAPSHOT.jar .

CMD ["java", "-jar", "Report-1.0-SNAPSHOT.jar"]

