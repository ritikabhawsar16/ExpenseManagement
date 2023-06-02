FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=./target/expensemanagement-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /usr/app/expensemanagement.jar
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","expensemanagement.jar"]