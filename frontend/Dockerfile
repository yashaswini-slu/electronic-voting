# setting up base image, which is nodeJS as we are building nodeJS application
FROM node:19.0-alpine

# specifying the user who maintain this Dockerfile
MAINTAINER Jahnavi <jahnavi.manne.1@slu.edu>

# creating a directory
RUN mkdir /app

# setting up a working directory
WORKDIR /app

# copying the code into container after installing dependencies and building the code
COPY . /app

# exposing container port 
EXPOSE 4200

# starting node application, required for container start-up
ENTRYPOINT ["npm", "start"]
