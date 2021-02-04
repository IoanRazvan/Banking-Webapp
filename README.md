# Banking-Webapp

`Banking-Webapp` is a web banking application developed for the *Advanced Elements of Programming* course which uses older Java EE technologies such as Java Server Pages, Servlets and Expression Language to manage and track money of registered users.

## How to build and run

### Prerequisites

- Java Development Kit [Oracle Website](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html)
- Maven [Apache Maven Website](https://maven.apache.org/download.cgi#)
- MySQL Community [MySQL Website](https://dev.mysql.com/downloads/windows/installer/8.0.html)

### How to run

Clone this repository using:

```
$ git clone https://github.com/IoanRazvan/RGBanking
```

After cloning the repository you need to log into MySQL Workbench and create the `dbAdmin` user and `banking` database by running the script `database/create.sql` as the root user. Once the user and database are created, you can start a Tomcat server running the application by executing:

```
$ cd RGBanking
$ mvn tomcat7:run
```

Note that the above commands assume that you have set the environment variable `JAVA_HOME` to point the jdk path and added maven's bin directory to the PATH variable.

## Authors

[@grigoruta-cosmin](https://github.com/grigoruta-cosmin) - most of the frontend and the part of the backend that was heavily intertwined with the frontend.

[@IoanRazvan](https://github.com/IoanRazvan) - fair amount of backend and a bit of frontend.
