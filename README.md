# Jumia Project

Service responsible for receives an interval and filters all orders placed during that interval. 
The result should be a list of intervals (in months) that groups the orders based on the product age (creation date field in the product entity).

# Project Technologies

- Spring Stack
- Java 8
- JPA/Hibernate
- Tests: Mockito, Junit
- Log Manager: Log4J
- Version Control: Git
- Build Tool: Gradle
- Google Guava
- Database: MySQL
- Database Source Control: Liquibase
- API for Parsing Command Line: Apache Commons CLI
- Code Coverage and Tests Reports: Jacoco 

# How to Use

- Clone this repository
- Install [Gradle](https://docs.gradle.org/current/userguide/installation.html)
- Install [MySQL](https://www.mysql.com/downloads/)
- Configure MySQL(URL, USER and PASS) in the Project at "src/main/resources/application.yml"
- To run tests and generate reports with Jacoco:
```bash
gradle clean test jacocoTestReport
```
- To build project, run below code in root:
```bash
gradle clean build
```
- After project build, gradle will generate the "orders-1.0.0.jar" at "build/libs/"
- The first time that you build project, liquibase will generate all database structure and create some inital data(ORDERS, ITEMS AND PRODUCTS) to see when run the project.
- To run project, run the code below:
```bash
java -jar orders-1.0.0.jar -initialDate "2016-01-01 00:00:00" -finalDate "2017-01-01 00:00:00" -monthSort "1-3, 4-6, 7-12"
```
- You can change all parameters, as follows bellow:
```bash
java -jar orders-1.0.0.jar -initialDate "2015-01-01 00:00:00" -finalDate "2016-01-01 00:00:00" -monthSort "1-2, 3-4, 5-6, 7-12"
```
```bash
java -jar orders-1.0.0.jar -initialDate "2017-01-01 00:00:00" -finalDate "2018-01-01 00:00:00" -monthSort "1-6, 7-12"
```

# About

Developed by Rafael Lopes Ferrari
