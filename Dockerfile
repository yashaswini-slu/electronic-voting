FROM openjdk:18.0.2.1-jdk
ARG JAR_FILE=/home/circleci/project/target/electronic-voting-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
