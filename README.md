# checkout_payment_gateway
Payment Gateway Checkout

## Pre-requisite
- Java11
- Gradle 7.2

## Steps to run:
- Run task to get the 7.4 gradle version  : `gradle wrapper --gradle-version 7.2`
- Build Application : `./gradlew clean build`
  - This will compile, run the test cases and build the executable jar
- Run application without build : `./gradlew bootRun`
- Access H2 DB Console: `http://localhost:8080/h2db-console/`
- H2 Database JDBC URL: `jdbc:h2:mem:payment-gateway-db`
- H2 UserName: `payment-gateway-user1`
- H2 Password: `payment-gateway-pass1`
- Get All Payments Query: `select * from payments;`
- Access Open API Spec UI : `http://localhost:8080/swagger-ui.html`
- Access Open API Spec JSON : `http://localhost:8080/v3/api-docs`
- Health Endpoint : `http://localhost:8080/actuator/health`