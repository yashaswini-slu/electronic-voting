# setting up base image, which is openjdk as we are building java application
FROM openjdk:18.0.2.1-jdk

# specifying the user who maintain this Dockerfile
MAINTAINER Jahnavi <jahnavi.manne.1@slu.edu>

# setting up a variable that maps to jar file path
ARG JAR_FILE=/target/electronic-voting-0.0.1-SNAPSHOT.jar

# copying jar file
COPY ${JAR_FILE} app.jar

# setiing up jar executable - necessary for container start-up
ENTRYPOINT ["java","-jar","/app.jar"]
