FROM openjdk:17-alpine

MAINTAINER Darmokhval

RUN apk add bash

RUN mkdir /app
WORKDIR /app


COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

#VOLUME /tmp
#ARG JAR_FILE=./target/Backend_part-0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080