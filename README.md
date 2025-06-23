# Exchange Currency API
This is a Spring Boot application that fetches and exposes current exchange rate data from the Bank of Latvia ECB XML (https://www.bank.lv/vk/ecb.xml).

## Features
- Fetches real-time or historical currency exchange data from `https://www.bank.lv/vk/ecb.xml`
- Parses and returns data in JSON format
- Provides RESTful endpoint(s) to access exchange rates
- Handles errors gracefully

## Technologies Used
- Java 17
- Maven
- Spring Boot (3.5.3)
- Spring Web (REST)
- Jsoup for XML parsing

## Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle

### Run Locally

```bash
git clone https://github.com/Rolands-Bidzans/Currency-Exchange-API.git
cd Currency-Exchange-API
mvn clean package
java -jar target/currencycalculator-0.0.1-SNAPSHOT.jar
```

## API Testing with Postman
You can import the included Postman collection: *Currency Exchange API.postman_collection.json*
into Postman to quickly test all API endpoints and their requests.
