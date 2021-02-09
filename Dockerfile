FROM maven:3.6.3-jdk-11
COPY pom.xml .
WORKDIR /src
COPY src .
WORKDIR /..
ENTRYPOINT [ "mvn", "tomcat7:run" ]