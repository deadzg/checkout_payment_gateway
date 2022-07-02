# Solution Overview
Payment Gateway Checkout

Component and Techstack Used:
- `Java` : Programming language used to code solution
- `SpringBoot` : Framework used to build the REST API for Payment Gateway
- `H2 Inmemory Data` : Database to store payment information 
- `ActiveMQ Inmemory Queue` : Messaging Queue to enable the async behaviour between Payment Gateway and Acquiring Bank 
- `Gradle`: Build tool for the java application


## Assumptions
- Card Details validation logic follows the below rules:
  - A credit card number must have between 13 and 16 digits. It must start with:
  - 4 for Visa cards
  - 5 for Master cards
  - 3 for American Express cards
  - 6 for Discover cards
- CVV validation logic assumes the below rules:
  - CVV Should have 3 or 4 digits
  - It should have only digits between 0-9
- Bank Simulator will send a success event back only for the below card numbers
  - `4999999999999109`, `5431111111111111`, `5123455806308521`
- Communication between Payment Gateway and Bank Simulator is asynchronous and achieved using In-Memory ActiveMQ component with the below queues:
  - `bank.request.queue` : Payment Gateway sends an event with payment information to Bank Simulator
  - `bank.response.queue` : Bank Simulator send an event with payment status `SUCCESS` or `FAILED` for a given paymentId
- As the communication is async the Payment Gateway marks the initial status of Payment as `PENDING` when the request is received using `POST /v1/payments`
- IDs generated for payment is of type `UUID`

## Deliverables Summary
1. Build an API that allows a merchant:
   a. To process a payment through your payment gateway. - `Delivered using POST /v1/payments`
   b. To retrieve details of a previously made payment. - `Delivered using GET /v1/payments/{paymentId}`
2. Build a bank simulator to test your payment gateway API. - `Deliver using MockBankService`

## Pre-requisite to run the solution
- Java11
- Gradle 7.2
- Prior to running the application on a machine make sure it has port `8080` unoccupied

## Steps to build the jar
- Make sure you are on the root of the project
- Run : `./gradlew clean build`
- This will compile, run the test cases and build the executable jar
- Navigate to `build/libs`
- Execute jar from commandline: `java -jar checkout_payment_gateway-1.0-SNAPSHOT.jar`

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