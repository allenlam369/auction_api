# syntax=docker/dockerfile:1

# official image base
FROM openjdk:11

# default working directory
WORKDIR /app

# copy some files and dir into working dir
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# add source code to image
# copy src is a MUST
COPY src ./src

# a Dockerfile must be in the same path of the main Application
COPY Dockerfile src/main/java/allen/

# use mvn to install dependencies
# RUN ./mvnw dependency:go-offline

# tell Docker what command we want to run when our image is exe inside a container
CMD ["./mvnw", "spring-boot:run"]



# git clone https://github.com/allenlam369/spring_docker
# cd spring_docker

# mvn -N io.takari:maven:wrapper
# docker build -t allenlam/rest .
# docker run --rm --name rest -p 9999:9090 allenlam/rest
# docker stop employee
# docker rm employee
# docker push allenlam/employee
# docker container ls
# docker ps -a
# docker network ls
# docker network inspect [name]
# docker-compose --version
# docker-compose up
# docker-compose down

# -a is all, -q is only id, -f is filter
# docker rm $(docker ps -a -q -f status=exited)

# How to commit an exec file to github from Windows?
# git update-index --chmod=+x foo.sh
# git ls-files --stage
# git commit -m"Executable!"
# git push

# Find what is using a port
# sudo lsof -i :8080

 


