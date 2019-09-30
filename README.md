# Simple Spring boot application configured to run with Azure Application Insights

This very basic application contains 3 modules under ticket-service:

# Prerequisites:
* Java 8
* maven

# Maven Modules:
* java-azure-common: has common code (utils, enums, dtos etc.)
* java-azure-main: has services, rest controllers and other main code

# Unit Testing:
*junit, and mockito to write unit tests and mock out dependencies.

# REST Service Endpoints:
* Created simple REST endpoints, and added dependency tracking

# Integration Testing:
* TODO

# Environment Specific Configuration:
* Used Spring and .properties file for a very basic environment specific configuration

# Running the application:

1. Clone/download the project from git
2. cd into the ```java-azure-appinsights``` directory
3. To build and run unit tests: ```mvn clean install```
4. cd into java-azure-main project
5. Start the application using: ```mvn spring-boot:run```
   This starts up the application on port "8080" by default (I have tested using 8080).

   If you don't have port 8080 available, then run using 
   ```mvn spring-boot:run -Dserver.port=<port_num>```
   (or)
   you could also update "server.port" property under src/main/resources/app-config.properties

# Enable Eclipse Remote Debugging:
 cd into java-azure-main project, and run: ```mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000```, and In eclipse, setup a debug configuration running on port ```8000```

