# user-service
In the interest of time, This application demonstrates the basic implementation of all the features requested in the problem statement. 
It is implemented with following features: 
* It exposes two endpoints as getUser and Update User using Spring Boot Rest APIs. 
* It is built using Java 13. 
* It uses in memory H2 DB. 
* Spring Security Framework is used for basic authentication and Authorisation. 
* Aspect is used to log method start and end loggers. 
* It supports a circuit breaker implementation (using Hystrix) in case there is any error while communicating with Database. 
* It has Unit Testing, Integration Testing and Pact testing to cover all the test scenarios.  
* Method level role security is implemented in the application using @PreAuthorize annotation so that specific endpoints can be accessed by users having specific roles. 
 


## Software & Libraries

* Spring Boot, Java 13,mapstruct, lombok, In memory H2 Database, Git, Maven, Intellij(Eclipse can be used as well). 

    
    
## Plugins

* maven compiler plugin to generate implementation code for mapstruct interface.  

Building and Deploying Application

* Checkout the project from this location : https://github.com/brijeshparashar/user_services
* Application can be built by using an IDE or by using maven command : mvn clean install.
* Run the application (using the UserApplication class) as a spring boot application from the IDE.
* The application on startup will create the DB tables and populate the tables with predefiend scripts using  /resources/schema.sql and /resources/data.sql.   
* Once the application is up it can be tested using the postman collection scripts.
* There are three roles defined in the database as given below :
** ADMIN - This user role can perform any operation.
** READ_ONLY - This user role can perform only GET operation. 
** READ_UPDATE_USER - This user role can perform GET as well as UPDATE operation. 
* Application uses these roles to validate the access using the method level role security. 
* The application will require  user and password to be passed in the request. This is already provided in the postman scripts. 


## Testing the application -
* Get User 
    * URL - http://localhost:8080/users/1000001
    * Header - Basic Auth :  User - 1000001 , Password- test123
    * Method - GET
    * Request Body : blank
    * Response - 
        `{
             "userId": 1000001,
             "title": "Mr",
             "firstName": "Alex",
             "lastName": "Smith",
             "gender": "male",
             "address": {
                 "street": "11 Pyrmont Street",
                 "city": "Sydney",
                 "state": "NSW",
                 "postcode": "2000"
             }
         }`
 
* Update User 
    * Endpoint - http://localhost:8080/users/1001
    * Header - Basic Auth with username - 1000001, password- test123
    * Method - PUT
    * Request Body -  
        `{
             "userId": "1000001",
             "title": "Mr",
             "firstName": "firstname",
             "lastName": "lastName",
             "gender": "female",
             "address": {
                 "street": "11 AB STREET",
                 "city": "Sydney",
                 "state": "NSW",
                 "postcode": "2000"
             }
         }`
    * Response - 
        `{
             "userId": "1000001",
             "title": "Mr",
             "firstName": "firstname",
             "lastName": "lastName",
             "gender": "female",
             "address": {
                 "street": "11 AB STREET",
                 "city": "Sydney",
                 "state": "NSW",
                 "postcode": "2000"
             }
         }
         
         * Error response (if the user doesnot have authority to access the endpoint)
          {
              "errorCode": "ER003",
              "errorMessage": "403 FORBIDDEN:Access is denied",
              "errors": null
          }`

## Postman Link for testing.

