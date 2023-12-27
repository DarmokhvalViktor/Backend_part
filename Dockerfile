FROM openjdk:17-jdk-slim

VOLUME /tmp
ARG JAR_FILE=./target/Backend_part-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080