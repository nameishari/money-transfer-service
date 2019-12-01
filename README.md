[![Build Status](https://travis-ci.org/nameishari/money-transfer-service.svg?branch=master)](https://travis-ci.org/nameishari/money-transfer-service/) 

## Overview
Simple RESTful API for transfering money between two accounts.

## Frameworks/Libraries used:

<ul>
  <li>Spark Java</li>
  <li>JOOQ</li>
  <li>H2 as in-memory database</li>
  <li>Junit and Rest Assured</li>
</ul>

## Endpoints
* `POST` /account - Creates an acccount
  - example request body
  ```json
    {
    	"balance": 100.01
    }
  ```
* `GET` /account/&lt;accountId&gt; - returns an account with balance
* `POST` /account/&lt;accountId&gt;/transfer - transfers money from one account to another
  - example request body
  ```json
    {
    		"destinationAccountId" : "5fd25de2-3051-4e50-ade1-f9bfe4e6506e",
	      "sourceAccountId": "11fa45c3-2ff1-46db-8073-2c1431441161",
	      "amount": 200
    }
  ```
* `GET` /account/&lt;accountId&gt;/transfer  - returns all account made by an account

## Running
This service is using maven wrapper, it is not necessary to have maven in the execution environment.

```./mvnw clean verify``` - to run tests

```./mvnw clean package``` - creates executable jar

```java -jar ./target/money-transfer-service-1.0-SNAPSHOT.jar``` - Running the executable jar on default port(8090)

```java -Dserver.port=8080 -jar ./target/money-transfer-service-1.0-SNAPSHOT.jar``` - If default port, 8090 already in use then it can be changed with server.port
