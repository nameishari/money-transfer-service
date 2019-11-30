[![Build Status](https://travis-ci.org/nameishari/money-transfer-service.svg?branch=master)](https://travis-ci.org/nameishari/money-transfer-service/) 

## Overview
Simple RESTful API for transfering money between two accounts.

## Frameworks/Libraries used:

<ul>
  <li>Spark Java</li>
  <li>JOOQ</li>
  <li>H2 as in-memory database</li>
  <li>Junit and Rest Assured</li>
<ul>

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
