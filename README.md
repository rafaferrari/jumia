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
- Guava
- Database: MySQL
- Database Source Control: Liquibase
- API for Parsing Command Line: Apache Commons CLI
- Code Coverageand Tests Reports: Jacoco 

# How to Use

- Clone this repository
- Install [Gradle](https://docs.gradle.org/current/userguide/installation.html)
- Install [MySQL](https://www.mysql.com/downloads/)
- Configure MySQL(URL, USER and PASS) in the Project at "src/main/resources/application.yml"
- To build project, run below code in root:
```bash
gradle clean build
```
- To run tests and generate reports with Jacoco:
```bash
gradle clean test jacocoTestReport
```

# About

Developed by Rafael Lopes Ferrari
