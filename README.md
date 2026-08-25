# 🩺 Nurse Booking System

A full-stack **Nurse Booking System** designed to connect patients with nurses and simplify the process of finding, scheduling, and managing nursing appointments.

> 🚧 **Project Status:** In Progress

This project is being built to strengthen my understanding of **Java backend development, Spring Boot, REST APIs, database design, authentication, and full-stack application development**.

---

## 📌 Overview

The Nurse Booking System allows users to:

* 👤 Create and manage their accounts
* 🔎 Browse available nurses
* 🩺 View nurse profiles and services
* 📅 Check nurse availability
* 📋 Book nursing appointments
* 🔄 Manage/cancel bookings
* ⭐ Review nurses
* 🔐 Secure user authentication and authorization

The system is being developed with a focus on **clean architecture, RESTful APIs, database relationships, and real-world backend development practices**.

---

## 🛠️ Tech Stack

### Backend

* **Java**
* **Spring Boot**
* **Spring MVC**
* **Spring Data JPA / Hibernate**
* **REST APIs**
* **Maven**

### Database

* **MySQL**

### Frontend

* **HTML / CSS / JavaScript**
* **React** *(planned)*

### Tools

* **Git & GitHub**
* **IntelliJ IDEA**
* **Postman**
* **MySQL Workbench**

---

## 🏗️ Architecture

The application follows a layered backend architecture:

```text
Client
   │
   ▼
REST API
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
MySQL Database
```

### Main Layers

**Controller**

* Handles HTTP requests and responses
* Exposes REST endpoints

**Service**

* Contains business logic
* Handles booking and user-related operations

**Repository**

* Handles database operations using Spring Data JPA

**Entity**

* Represents database tables and relationships

---

## 📊 Core Entities

The initial database design includes entities such as:

```text
User
 │
 ├── Patient
 │
 └── Nurse
       │
       ├── Availability
       │
       └── Bookings

Booking
 │
 ├── Patient
 └── Nurse
```

The database structure will evolve as additional functionality is implemented.

---

## 🚀 Current Progress

### Completed

* [x] Initial project setup
* [x] Spring Boot configuration
* [x] Database connection
* [x] Initial entity/model design
* [x] Basic backend structure
* [x] Initial REST API development

### In Progress

* [ ] User registration & login
* [ ] Authentication & authorization
* [ ] Nurse management
* [ ] Nurse availability management
* [ ] Appointment booking
* [ ] Booking management
* [ ] API validation & exception handling
* [ ] Frontend development
* [ ] Integration between frontend and backend

### Planned

* [ ] Role-based access control
* [ ] Search and filtering
* [ ] Nurse ratings & reviews
* [ ] Email/SMS notifications
* [ ] Payment integration
* [ ] Docker support
* [ ] Deployment
* [ ] Automated testing
* [ ] CI/CD pipeline

---

## 🔐 Security

Security will be implemented using Spring Security with role-based access control.

Planned roles include:

```text
ADMIN
NURSE
PATIENT
```

Each role will have access to functionality relevant to its responsibilities.

---

## 🔌 Planned API Structure

Example REST endpoints:

```http
POST   /api/auth/register
POST   /api/auth/login

GET    /api/nurses
GET    /api/nurses/{id}

POST   /api/bookings
GET    /api/bookings/{id}
PUT    /api/bookings/{id}
DELETE /api/bookings/{id}

GET    /api/nurses/{id}/availability
POST   /api/nurses/{id}/availability
```

> API endpoints are subject to change as development progresses.

---

## 🧪 Testing

API development and testing is being performed using **Postman**.

Planned testing:

* Unit testing
* Integration testing
* Controller testing
* Repository testing
* API validation

---

## 💻 Running the Project

### Prerequisites

Make sure you have installed:

* Java 17+
* Maven
* MySQL
* Git

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/nurse-booking-system.git
cd nurse-booking-system
```

### Configure the database

Create a MySQL database:

```sql
CREATE DATABASE nurse_booking_system;
```

Update your application configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nurse_booking_system
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application directly from IntelliJ IDEA.

---

## 📁 Project Structure

```text
src
└── main
    ├── java
    │   └── com.example.nursebooking
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       ├── entity
    │       ├── dto
    │       ├── exception
    │       └── config
    │
    └── resources
        ├── application.properties
        └── static
```

The structure may evolve as the application grows.

---

## 🎯 Project Goals

The primary goal of this project is to build a realistic production-style application while gaining practical experience with:

* Java & Spring Boot
* REST API development
* Object-oriented design
* Relational database design
* JPA & Hibernate
* Authentication & authorization
* Exception handling
* API validation
* Git/GitHub workflows
* Testing
* Deployment

---

## 🗺️ Roadmap

```text
Project Setup
      ↓
Database Design
      ↓
REST APIs
      ↓
Authentication
      ↓
Nurse Management
      ↓
Booking System
      ↓
Frontend
      ↓
Testing
      ↓
Dockerization
      ↓
Deployment
```

---

## 🤝 Contributing

This is currently a personal learning project, but suggestions and feedback are welcome.

If you find an issue or have an improvement idea, feel free to open an issue or submit a pull request.

---

## 👨‍💻 Author

**Harsh Shukla**

This project is being developed as part of my journey toward becoming a **Java Backend / Full-Stack Developer**.

---

⭐ If you find this project interesting, consider giving the repository a star!
