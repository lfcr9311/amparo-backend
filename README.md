# amparo-backend

The backend api for amparo web app

## Requirements:
- Java 17 JDK
- Docker

# FAQ:
will be shown on the bottom of this page.

## Useful links:

- [x] [Spring Tutorial](https://spring.io/guides/gs/spring-boot/)
- [x] [Docker](https://docs.docker.com/)
  - [x] [Post Install](https://docs.docker.com/engine/install/linux-postinstall/)
- [x] [Spock Testing](https://spockframework.org/spock/docs/2.3/index.html)
  - [x] [Primer (the basic commands)](https://spockframework.org/spock/docs/2.3/spock_primer.html)
  - [x] [Data Drive test](https://spockframework.org/spock/docs/2.3/data_driven_testing.html)
  - [x] [Mocking/Stubbing](https://spockframework.org/spock/docs/2.3/interaction_based_testing.html)
- [ ] Swagger documentation
  - [x] [Springdocs](https://springdoc.org/)
  - [x] [Local](http://localhost:8080/swagger/docs.html)
    - Necessário estar rodando a aplicação.
  - [ ] [Cloud](#useful-links)


### How to run the application:
With all following versions, always do some of the commands (if you are with the source-code)
```bash
git pull origin master
```
or
```bash
git pull origin dev
```

#### I'm just a front-end developer and just want to run the application to make what really matters to the stakeholders. 
 To begin with, you can access the cloud web swagger and use it from there, but since the AGES III does not upload the
 application, it's needed to run the application locally, so here we go:
 
- #### Via Docker-compose.
then execute the following commands:
```bash
docker compose build
```
after this, execute:
```bash
docker compose up 
```
And your application should be running.

- #### If you want to run locally to debug the application.  
I'm sorry to say that, but you are beginning to following a no way back towards to becoming
a full-stack developer or at least know something of the backstage of development.
Só just follow the back-end tutorial.

#### I'm the back-end developer that need to make this application evolve.

For those how choose these dark path, I shall show you the way to ease you from these hurtful way

- #### To begin with all we need to generate the build executable file.
So to do this, we are using the gradle as our dependencies' manager.
to generate and install everything that we are going to need, we use these command.
```bash
./gradlew clean build
```
Ps: If the test are failing put the flag ```-x test``` at the end of the command.
- #### docker-compose.

run the command:
```bash
docker compose -f docker-compose-db.yml up -d
```
This will start only the database of the application.

now to run application properly saying, you can do two ways.

running the command:
```bash
./gradlew bootrun
```
Or running the application via your most favorite IDE.



### FAQ.
- #### THE DATABASE IT'S NOT RUNNING... WHAT CAN I DO.
  - Probably you have another application running at the port 5432 (most likely to be another postgresql)
  so you can, or stop the application on port 5432, or change the following files:
    - docker-compose.yml:
      - service > amparo-db > ports > 5432:5432 change to 5433:5432
      - service > amparo-back > environment > SPRING_DATASOURCE_URL=jdbc:postgresql://amparo-db:5432/amparo-db?user=amparo&password=amparo change the 5432 to 5433
    - docker-compose-db.yml:
      - service > amparo-db > ports > 5432:5432 change to 5433:5432
    - application.properties:
      - Change spring.datasource.url=jdbc:postgresql://localhost:5432/amparo-db?user=amparo&password=amparo to spring.datasource.url=jdbc:postgresql://localhost:5433/amparo-db?user=amparo&password=amparo
- #### What is the path to the swagger?
  - localhost:8080/swagger/docs.html
- #### I have to add something on the base, a new table for example
  - Go to src > resources > db.migration and add a new file like V2__operation.sql
    - Look the last version and +1 so with the last file is V1__init.sql, the next will be V2__operation_that_you_will_be.sql
- #### The application is not running even after the FAQ what can i do.
  - Ask for help to your other AGES