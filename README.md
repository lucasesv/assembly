# Assembly API

Assembly is a simple REST API to manage the proccess of voting em Assembly Sessions. It is an example application for practicing Java stack only.

*It was not meant to use in real life.

## Running the Application

The application was deployed in Heroku, but can also be run locally.

In the next days, the application will be configured to run with docker-compose so that it will be possible to run the whole environment (application + database + RabbitMQ) locally using containers.

When the application runs for the first time, it will run SQL scripts to create the database structure (tables, sequences, etc) and insert some date in order to have everything set to start to consume the REST API's. Actually, the scripts are run every time the application is started, but they are smart enough to modify the database only if the structure and data was is missing. It could be done via database migrations such as Flyway.

### Heroku

The application is currently deployed in Heroku and can be accessed [here](https://lucas-assembly.herokuapp.com/). The REST API responds on port 8080.

### Localhost

There is no need for additional configurations in order to run the application locally. It will use the database and AMQP service that are running in Heroku.

Prerequisites:
* Java 11;
* Maven >=3.6.3.

In the root of the project, run the following command.

```
mvn spring-boot:run
```

## Endpoints

The API is fully documented with Swagger and can be found [here](https://lucas-assembly.herokuapp.com/swagger-ui.html) or by accessing the `/swagger-ui.html` of the REST API.

## Configuration

No need for additional configurations, it's all set to go.

## Architecture

The application design is based in Clean Architecture, although it does not fully match its concepts.

The main concern was isolating the core business from the infrastructure layers such as persistence, web and messaging.

### Core

The core was designed with 3 main packages:

 - **entitiy**: the domain entities;
 - **usecase**: the logic that will process all core business of the application;
 - **adapters**: a set of interfaces to isolate the core components from external "world".

The dependency direction is: `external -> adapters -> use cases -> entities`

### Infra Layers

#### Persistence

Persistence was implemented with JPA to connecto to PostgreeSQL database.

This layer doesn't use the core entities to persist into database, but it uses its own entities instead. This way there was no need to use annotations all over the core entities. Although it keeps the core isolated,  it creates the  needs to have a mapper to convert core entities into JPA entities and vice versa.

The following components were implemented in this Layer:

 - Persistence Adapter implementations;
 - Entity mappers;
 - JPA entities;
 - JPA repositories.

#### Web

The application is exposed to web via REST API wich was implemented with Spring Web.

The following steps were taken in order to mainting the core decoupled:

 - Creation of DTO's preventing entities from being exposed over web;
 - Creation of a Configuration class to configure beans for use cases so that the use cases could be autowired into the controllers, keeping the use cases classes free of framework annotations.
 
A few more steps took when creating the web layer were: 
 
 - Creation of Swagger documentation accessible in 
 - Validation of DTO's with `javax.validation`;
 - Configuration of custom messages (even though there was no need for custo messages so far);
 - Internationalization;
 - Custom REST error messages with a central heandler of exceptions;
 - Use of Feign Client to accomplish the **Bonus Task 1**;
 - Use of try resilience mechanism in the **Bonus Task 1**. It may considered a bonus of bonus :). It could be used other mechanisms of resilience such as circuit breaker, fallbacks, limiters and so on.
 - Creation of an endpoint which publishes a message to RabbitMQ to accomplish the **Bonus Task 2**. It was created an endpoint instead of scheduling for sake of simplicity.

#### Messaging

Messaging was configured with RabbitMQ which is a simple but powerful message broker. Besides AMQP integreates smoothly with Spring Boot.

In this application, messaging was configure exclusively to meet the requirements of **Bonus Task 2**.

Since the objective of **Bonus Task2** was to test messaging in the application, it was created a REST endpoint wich will publish a message to RabbitMQ whenever it receives a GET request in the following endpoint:

```
(GET) /amqp/sessions/{sessionId}/result
```

## Bonus Tasks

### Bonus Task 1

It was used Feign Client to consume the external API [https://user-info.herokuapp.com/users/{cpf}](https://user-info.herokuapp.com/users/{cpf}).

It was created the REST endpoint that will use Feign to consume the external API and forward its response to the  user who made the request. The endpoint which was created is:
 
```
(GET) /members/{cpf}/member-status
```

The endpoint breaks the RESTFull principles, but it was keep it like that in order to make it easier to test the AMQP communication.

### Bonus Task 2

The Bonus Task 2 is explained in secttion `Architecture > Infra Layers > Messaging`.

### Bonus Task 3

Not accomplished. It will be made soon though.

### Bonus Task 4

It was asked how to make API versioning.

There are a couple of strategies for versioning the API, but the chosen one for this appliation is the URI Versioning. This kind of strategy prefixes all endpoints with its version, example:

```
(GET) api/v1/members/{cpf}/member-status
```

Notice the `v1` in the URI righ before the endpoint path.

Using URI Versioning strategy has some benefits:

 - **Caching** is easier when the version is in URI rather then in headers. Because of the scenario stated in **Bonus Task 2**, caching its very important to improve performance and resilience of the application;
 - The URI becames a little poluted but not as poluted as the Request Parameter Versioning strategy would be;
 - API Documentation becames much more straight forward than in strategies that use headers for versioning;
 - When the version is more evident when in URI;
 - It's easyer for the clients to make the requests.

## Improvements

- The endpoint for registering a vote `(POST) /subjects/{subjectId}/sessions/{sessionId}/votes` should return HTTP Status 201 with the vote information in the response body because registering a vote means that the vote will be created.

- The Persistence Layer created its own entity mapper, but there are a plenty of libs that already do this such as ModelMapper, MapStruct, etc.

- The validations of use cases were created inside the use case classes, but it would be better to make an abstraction for this validations and maybe some Design Pattern such as Template Method. It needs a bit more of analysys to find the patter that best fits.

- The AMQP usage should definitely be removed form the MemberController and a schedulling should be created in propper place.

- Error messages are spread in some classes and this is wrong for many reasons. It is way better to create an `enum` to encapsulate the code of errors and then create `properties` file to bind the code errors with the messages. This way the internationalization feature could be used for theses errors.

- A API `(POST) /subjects/{subjectId}/sessions/{sessionId}/votes` (método computeVote) deveria retornar status 201 (Created) e o dto do voto (com id do voto), pois o que a API de computar voto faz é criar um voto, que acaba sendo um resource desse ponto de vista. Creio que é o mais correto semanticamente.

- The REST API should implement HATEOAS so that it will be easier for the consumers to navigate throughout the application.

- The application still doesn't do database versioning. It would be nice if to have some migration mechanism such as Flyway.

- It needs to create acceptance and load/performance tests.

## Known Bugs

 1. Subject Id Without Validation

The endpoints listed bellow don't validates the id of the subject in the URI. The user may set any value for subject id and the application will process the request anyway. It needs to do the subject id validation.
```
(POST) /subjects/55/sessions/{{sessionId}}/votes
(GET)  /subjects/50/sessions/{{sessionId}}/result
(GET)  /subjects/9/sessions/{{sessionId}}/result-amqp
```

## Authors

* **Lucas Vasconcelos** - *eng_cadu@hotmail.com*
