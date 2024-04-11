FROM adoptopenjdk/openjdk17:jre-17.0.2_8-alpine
ARG JAR_FILE=./target/expensemanagement-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /usr/app/expensemanagement.jar
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","expensemanagement.jar"]