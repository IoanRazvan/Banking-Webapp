<!-- omit in toc -->
# Banking-Webapp

`Banking-Webapp` is a web banking application developed for the *Advanced Elements of Programming* course which uses older Java EE technologies such as Java Server Pages, Servlets and Expression Language to manage and track money of registered users.

<!-- omit in toc -->
## How to build and run

- [Normal Build](#normal-build)
- [Build using containers](#build-using-containers)

### Normal Build
<!-- omit in toc -->
#### Prerequisites

- [Java Development Kit](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html)
- [Maven](https://maven.apache.org/download.cgi#)
- [MySQL Community](https://dev.mysql.com/downloads/windows/installer/8.0.html)

<!-- omit in toc -->
#### How to run

Clone this repository using:

```bash
$ git clone https://github.com/IoanRazvan/Banking-Webapp.git
```

After cloning, edit the file `src/main/resources/META-INF/persistence.xml` by commenting the property used when testing with containers and uncommenting the one used for testing without containers. After modifying the persistence unit log into MySQL Workbench and create the `dbAdmin` user and `banking` database by running the script `database/create.sql` as the root user. Once the user and database are created, you can start a Tomcat server running the application by executing:

```bash
$ cd Banking-Webapp
$ mvn tomcat7:run
```

Note that the commands above assume you set the environment variable `JAVA_HOME` to point the jdk path and added maven's bin directory to the PATH variable. After seeing the following output produced by the last command:

```
[INFO] Running war on http://localhost:8080/BankingApplication
[INFO] Creating Tomcat server configuration at /target/tomcat
[INFO] create webapp with contextPath: /BankingApplication
```

meaning that maven finished downloading the application's dependencies, you can start testing the app by visiting http://localhost:8080/BankingApplication.

### Build using containers

<!-- omit in toc -->
#### Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop)

<!-- omit in toc -->
#### How to run

Clone this repository using:

```bash
$ git clone https://github.com/IoanRazvan/Banking-Webapp.git
```

Build the images for the database and web application.

Database image:

```bash
$ cd Banking-Webapp/database
$ docker build -t banking-webapp-database .
```

Web application image:

```bash
$ cd Banking-Webapp
$ docker build -t banking-webapp .
```

If for some reason you don't want to build the images yourself you can pull them from Docker Hub using:

```bash
$ docker pull ioanrusu/banking-webapp-database
$ docker tag ioanrusu/banking-webapp-database banking-webapp-database # tag the image so that the commands below apply uniformly regardless of the method used to obtain the images
$ docker pull ioanrusu/banking-webapp
$ docker tag ioanrusu/banking-webapp banking-webapp
```

This also removes the need to clone the repository. After building or pulling the images you can start testing the application by running the following commands:

```bash
$ docker network create my-network # create bridge network so the containers can reference each other using a hostname
$ docker run -d -p 3306:3306 --network my-network --name mysqlserver -e MYSQL_ROOT_PASSWORD=rootPass banking-webapp-database # create a container for the database image
$ docker run -p 8080:8080 --network my-network --name webapp banking-webapp # note that this runs the container in foreground in order to see maven's logs without the need to open Docker Desktop.
```

After running the commands you should wait for the following output from the `web-application` container:

```
[INFO] Running war on http://localhost:8080/BankingApplication
[INFO] Creating Tomcat server configuration at /target/tomcat
[INFO] create webapp with contextPath: /BankingApplication
```

This indicates that maven finished downloading the application's dependencies and started running it on http://localhost:8080/BankingApplication.
<!-- omit in toc -->
## Authors

[@grigoruta-cosmin](https://github.com/grigoruta-cosmin) - most of the frontend and the part of the backend that was heavily intertwined with the frontend.

[@IoanRazvan](https://github.com/IoanRazvan) - fair amount of backend and a bit of frontend.