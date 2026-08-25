# User Service

A user management microservice built using **Spring Boot** for handling user-related operations within a microservices-based system.

## Student Information

| **Information**    | **Details**                                              |
| ------------------ | -------------------------------------------------------- |
| **Student Name**   | `Dineth Osanka Nakandala`                                |
| **Student Number** | `241711046`                                              |
| **Slack Handle**   | `https://ijse-eca-hdse-71-72.slack.com/team/U0BERT7M7PH` |
| **GCP Project ID** | `impactful-study-477106-j6`                              |

## Project Description

The **User Service** is a backend microservice responsible for managing user-related functionality within the CAMS microservices platform.

It uses **PostgreSQL** for persistent data storage and **Spring Data JPA** for database operations.

The service integrates with **Netflix Eureka** for service discovery and **Spring Cloud Config** for centralized configuration management.

It also provides security and authentication capabilities using **Spring Security** and **JWT**, while **Google Cloud Storage** is used for cloud-based file and object storage.

## Technology Stack

* **Java:** 23
* **Spring Boot:** 4.1.0
* **Spring Cloud:** 2025.1.2
* **Spring Data JPA**
* **PostgreSQL**
* **Netflix Eureka Client**
* **Spring Cloud Config**
* **Spring Boot Actuator**
* **Google Cloud Storage**
* **MapStruct:** 1.6.3
* **ModelMapper:** 3.0.0
* **Lombok:** 1.18.42
* **Spring Boot Validation**
* **Spring Boot AOP**
* **Maven**
* **Git & GitHub**
* **Google Cloud Platform (GCP)**

## Project Structure

```text
user-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .gitignore
├── pom.xml
└── README.md
```

## Prerequisites

* Java JDK 23
* Apache Maven
* Git
* PostgreSQL
* An IDE such as IntelliJ IDEA or Eclipse
* Running Config Server
* Running Service Registry

Verify the installed versions:

```bash
java -version
mvn -version
git --version
```

## Setup / Getting Started

### 1. Clone the Repository

```bash
git clone <GITHUB_REPOSITORY_URL>
cd user-service
```

### 2. Database Configuration

The service uses **PostgreSQL** as its database.

The application is configured to connect to:

```text
Database: eca
Host: localhost
Port: 12500
```

Make sure PostgreSQL is running and the required database is available before starting the service.

### 3. Start Required Services

Before starting the User Service, make sure the following services are available:

* **Config Server**
* **Service Registry / Eureka Server**
* **PostgreSQL**

The service uses the Config Server for centralized configuration and Eureka for service discovery.

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The service is configured with a dynamic port, so the actual port is assigned when the application starts.


## Database

The User Service uses **PostgreSQL** with **Spring Data JPA**.

Hibernate is configured to automatically update the database schema when the application runs.

```text
PostgreSQL
    │
    ▼
Spring Data JPA
    │
    ▼
User Service
```

## Google Cloud Storage

The service integrates with **Google Cloud Storage** for cloud-based file storage.

**Storage Bucket:**

```text
cams-project-cloud-storage
```

## File Upload

The service supports multipart file uploads with the following configured limits:

```text
Maximum file size: 5 MB
Maximum request size: 10 MB
```

## Building for Deployment

To create the application JAR file:

```bash
mvn clean package
```

The generated JAR file will be available inside:

```text
target/
```

Run the generated application using:

```bash
java -jar target/user-service-0.0.1-SNAPSHOT.jar
```

## Repository Information

**Repository Name:** `user-service`

**Repository Description / About:**

> User management microservice for the CAMS platform using Spring Boot, PostgreSQL, Spring Security, Eureka, and Google Cloud Storage.

## License

This project is developed for academic purposes.
