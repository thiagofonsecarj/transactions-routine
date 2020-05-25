# Transactions' routine API
This api manages creation of accounts and transactions.

## Getting Started
Some advertisements before seeing things working... 

### Prerequisites
In order to run this API, you will need all them installed first:
```
* Java 11
* Maven 3.6.0
* Docker 19.03
* Docker Compose
```

### Technologies
Here, there is a list of used technologies in this project. 
```
* Spring Boot 2.3.0
* Postgresql
* H2
* Liquibase
* Lombok
* ModelMapper
* Swagger
```

### Detailed Structure
This is a Restful API that was implemented using Intellij on Ubuntu 18.04.
In case you are windows user, please, note path's volume for the database located at docker file.  

#### Database
A postgres database and all its properties was configured in a docker image. 

#### Unit Tests
All unit tests were made using Junit and H2 for its own database.
These tests can be executed automatically, via maven, or manually.

## Scope of functionalities
The implemented functionalities can be viewed and used through Swagger.
[Transactions' routine API - Swagger](http://localhost:9000/swagger-ui.html)

## How to run
There are some commands to be executed in this specific order, in order to run this API: 
* Clone this project
```
git clone
```
* Prepare this artifact to deploy
```
mvn clean install
```
* Start database
```
docker-compose up
```
* Run the application
```
mvn spring boot
```

## Other information
Thank you!

