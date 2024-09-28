# Aapli Mahiti Backend REST API

**Efficient Data Management at Your Fingertips - Backend API for the Aapli Mahiti Dashboard Application**

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Technologies Used](#technologies-used)
- [Folder Structure](#folder-structure)
- [Contributing](#contributing)

## Introduction
**Aapli Mahiti** is a comprehensive backend REST API developed using **Spring Boot** and **MySQL** as the database. This API supports the frontend **Dashboard** for efficient data management, enabling users to interact with data through endpoints for creating, reading, updating, and deleting data entries.

This backend is designed to be scalable and secure, ensuring seamless interaction between the frontend and the underlying data.

## Features
- **User Authentication**: Secure JWT-based authentication for authorized access.
- **Data Management**: Perform CRUD (Create, Read, Update, Delete) operations on people's data such as names, phone numbers, and emails.
- **Search and Filtering**: Advanced search and filtering options to find specific data entries quickly.
- **Error Handling**: Consistent and informative error responses for failed API requests.
- **Security**: Authentication and role-based access control (RBAC) for protecting data.
  
## Installation

### Prerequisites
Before running the application, ensure you have the following installed:
- [Java JDK 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/)

## Technologies Used
- **Spring Boot**: Java framework for building RESTful web services.
- **MySQL**: Relational database for storing data.
- **JWT (JSON Web Tokens)**: Secure authentication and authorization.
- **Maven**: Dependency management and build automation tool.

## Folder Structure

The project is structured as follows:

```
/src
  /main
    /java
      /com/api/
        /controller        # REST API controllers
        /config            # Centralized configuration classes
        /service           # Service layer for business logic
        /repository        # Data repository interfaces
        /dto               # Data Transfer Objects for request/response
        /model             # Entity models
        /exceptions        # Custom exception handling classes
        /utils             # Utility classes (e.g., validation, formatting)
        /security          # JWT and security configurations
    /resources
      /application.properties # Configuration file for environment variables and database settings
  /test                    # Unit and integration tests
pom.xml                    # Maven build file
```

## Contributing
Contributions are welcome! To contribute to the project:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit them (`git commit -am 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Create a new Pull Request.

---
