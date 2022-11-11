FROM openjdk:18.0.2.1-jdk
MAINTAINER Jahnavi <jahnavi.manne.1@slu.edu>
ARG JAR_FILE=/target/electronic-voting-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
