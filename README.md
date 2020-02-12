# Initiate Payment Service
An initiate payment service

## Running
`mvn compile exec:java -Dexec.mainClass=com.revolut.payment.app.InitiatePaymentApp`

## Stack
- Java 11 (LTS)
- Spark
- Guice
- JUnit
- REST Assured

## TODO
1. Document flows with Plant UML
2. Complete tests for all scenarios
3. Persistent initiate payment service with jOOQ
4. Integrate payments with a payment API ( e.g. with Revolut API )
5. Authenticate with credentials and hand out signed bearer token