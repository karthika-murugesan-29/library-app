
---
# Library Management System – Spring Boot

## Project Overview

This project is a simple **Library Management System** built using **Java Spring Boot**.
It demonstrates basic CRUD operations, layered architecture, unit testing, and integration testing.

The application allows users to:

* Add books
* Borrow books
* View all books

The focus of this project is **clean architecture, test coverage, and best practices**, not complex UI.

---

## Tech Stack

* Java 17
* Spring Boot
* Maven
* Spring Web
* Spring Data JPA
* H2 In-Memory Database
* JUnit 5
* Mockito
* SonarLint / SonarQube
* Jenkins (for CI)

---

## Project Structure

```
library-app
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 ├── resources
 └── test
```

---

## Features

1. **Add Book**
2. **Borrow Book**
3. **List All Books**

---

## How to Run the Application

### Using VS Code / IntelliJ

1. Open the project folder.
2. Locate:

   ```
   src/main/java/com/example/library/LibraryAppApplication.java
   ```
3. Click **Run**.

### Using Terminal

From project root (where `pom.xml` exists):

```
mvn spring-boot:run
```

---

## API Endpoints

### Add Book

**POST** `/books`

```json
{
  "title": "Clean Code",
  "author": "Robert Martin"
}
```

### Borrow Book

**PUT** `/books/borrow/{id}`

### List Books

**GET** `/books`

---

## H2 Database Console

URL:

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:mem:testdb
```

---

## Testing

### Unit Tests

* Service layer tested using Mockito.

### Integration Tests

* Controller endpoints tested using SpringBootTest and MockMvc.

Target coverage: **70–80%**

---

## Jenkins Pipeline (Basic Idea)

Steps:

1. Pull code from GitHub
2. Run:

   ```
   mvn clean test
   ```
3. Generate reports

---

## Code Quality

* Java naming conventions followed
* SonarLint used for static analysis
* SonarQube report used for coverage and quality checks

---

## Demo Functions for Presentation

1. Add a new book
2. Borrow an existing book

---

## Goal of the Project

* Demonstrate Spring Boot fundamentals
* Show layered architecture
* Achieve test coverage requirements
* Practice CI/CD and code quality tools

---
