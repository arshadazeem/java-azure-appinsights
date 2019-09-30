# java-azure-appinsights
java on azure with Application Insights

# Simple Spring boot application configured to run with Azure Application Insights

This very basic application contains 3 modules under ticket-service:

* ticket-service-common: has common code (utils, enums, dtos etc.)
* ticket-service-main: has services, rest controllers and other main code
* ticket-service-integration-tests: REST service integration tests using Spring's Rest Template as the Rest client.

# Prerequisites:
* Java 8
* maven

# Unit Testing:
*junit, and mockito to write unit tests and mock out dependencies.

# REST Service Endpoints:
* Created simple REST endpoints, and added dependency tracking

# Integration Testing:
* TODO

# Environment Specific Configuration:
* Used Spring and .properties file for a very basic environment specific configuration

# Running the application:

1. Checkout/download the project from git
2. cd into the java-azure-appinsights directory
3. To build and run unit tests: ```mvn clean install```
4. cd into java-azure-main project
5. Start the application using: ```mvn spring-boot:run```
   This starts up the application on port "8080" by default (I have tested using 8080).

   If you don't have port 8080 available, then run using 
   mvn spring-boot:run -Dserver.port=<port_num>
   (or)
   you could also update "server.port" property under src/main/resources/ticketservice-config.properties

