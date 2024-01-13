# Elearning project

This project was developed to complete the Docker task and some tasks from AWS course.

## Structure of the project

The project includes the following modules:
* **MainMicroservice** - the microservice has Post entity, that can be created or viewed. This microservice sends a message to Report microservice so the latter could process an action.
* **Report** - the microservice has Report entity, that can be created or viewed. This microservice receives a message from MainMicroservice and stores action and timestamp.
* **acmq** - dependency for both previous microservices. This module is used to avoid code duplication in JMS configuration.

## How to run the project
This project includes `docker-compose.yml` file, that can be used to run the project.

## Explanation of the Dockerfiles complexity

As was mentioned before, `acmq` is the dependency of both microservices.
So, for maven to see this dependency, it should be installed in the local repository.
Thus, `mvn clean install` is performed before `mvn clean package`.