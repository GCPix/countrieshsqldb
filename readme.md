# MicroProfile generated Application

## Introduction

MicroProfile Starter has generated this MicroProfile application for you.

The generation of the executable jar file can be performed by issuing the following command

    mvn clean package

This will create an executable jar file **countriesAPI-microbundle.jar** within the _target_ maven folder. This can be started by executing the following command

    java -jar target/countriesAPI-microbundle.jar




    http://localhost:8080/openAPI will show the openAPI documentation in full (or it will if I can finish it).  List of paths available are below:

All paths have been tested to some extent through Postman, I have added the script for this to the project if you want to import it and update it for your own tests.

All paths are http://localhost:8080/'endpoint' from list below:
   'ROOT' REST Endpoints:
GET     /application.wadl
GET     /countries/summary
POST    /country
DELETE  /country/{id}
GET     /country/{id}
PUT     /country/{id}
GET     /currencies
POST    /currency
DELETE  /currency/{id}
GET     /currency/{id}
PUT     /currency/{id}
POST    /language
DELETE  /language/{languageid}
GET     /language/{languageid}
PUT     /language/{languageid}
GET     /languages
GET     /openapi/
GET     /openapi/application.wadl
POST    /regionalblock
DELETE  /regionalblock/{id}
GET     /regionalblock/{id}
PUT     /regionalblock/{id}
GET     /regionalblocks

## Specification examples

By default, there is always the creation of a JAX-RS application class to define the path on which the JAX-RS endpoints are available.



More information on MicroProfile can be found [here](https://microprofile.io/)












### Open API

Exposes the information about your endpoints in the format of the OpenAPI v3 specification. Specification [here](https://microprofile.io/project/eclipse/microprofile-open-api)

The index page contains a link to the OpenAPI information of your endpoints.





