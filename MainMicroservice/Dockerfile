FROM maven:3.8-openjdk-17 AS builder

WORKDIR /app
COPY ./ ./
RUN mvn clean install

WORKDIR MainMicroservice
RUN mvn clean package

FROM eclipse-temurin:17.0.9_9-jre-jammy as runner

WORKDIR /app
COPY --from=builder /app/MainMicroservice/target/MainMicroservice-1.0-SNAPSHOT.jar .

CMD ["java", "-jar", "MainMicroservice-1.0-SNAPSHOT.jar"]

