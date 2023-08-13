# Client Manager

This is a easy API for testing concepts about Quarkus, the Supersonic Subatomic Java Framework. 

The `application.properties` file have every configuration property with a default value, so you just needed to follow the instructions and that's it.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Docket instance for MySQL Database
Run next command to create a docker container with a MySQL database.

 ```shell script
docker run --name client_manager_db -e MYSQL_DATABASE=client_manager -e MYSQL_USER=userClientDb -e MYSQL_PASSWORD=P@ssword! -e MYSQL_RANDOM_ROOT_PASSWORD=yes -p 3306:3306 -d  mysql:latest
 ```

## Postman Collection
At the root of this project, you can see a Postman Collection `Client Manager.postman_collection.json` which have all the endpoints and request examples.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/client-manager-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Reactive MySQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the MySQL database using the reactive pattern
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- REST Client Reactive ([guide]https://es.quarkus.io/guides/rest-client-reactive): REST Client Reactive in order to interact with REST APIs

 