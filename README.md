# Initiate Payment Service
An initiate payment service

## Running
`mvn clean install` \
`java -jar target/initiate-payment-1.0-SNAPSHOT.jar`

## Stack
- Java 13
- Spark
- Guice
- JUnit
- REST Assured

## TODO
1. Document flows with Plant UML
2. Complete tests for all scenarios
3. Persistent initiate payment service with jOOQ
4. Integrate payments with core banking
5. Authenticate with credentials and hand out signed bearer token