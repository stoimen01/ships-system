# ships-system

This repository contains Spring Boot project containing simple server  
which goal is to provide ships information from static JSON file (located in the resources folder).

## build and run

Prerequisites:
- Java 8
- Gradle 4.*

In order to build and run the server one must execute the following command from this directory: 

'./gradlew bootRun'

This command will run the server on port 8080 of the local machine.

The default port could be configured from /src/main/resources/application.properties file.

## API 

The server exposes REST API with the following endpoints :  

- /ships returns all available ships as JSON array  

Sample request : http://localhost:8080/ships

- /ships/{id} returns JSON object containing information about  
              the ship with the specified id or BAD_REQUEST if the id is not valid  

Sample request : http://localhost:8080/ships/9

- /ships/owner/{owner name} returns all available ships owned by the specified owner as JSON array

Sample request : http://localhost:8080/ships/owner/Mitsui%20O.S.K.%20Lines