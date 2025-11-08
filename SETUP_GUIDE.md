# HCL Hackathon - Complete Setup Guide

This guide provides all the remaining files you need to add to complete the Spring Boot 3.5.7 template.

## Files Already Added
- pom.xml
- docker-compose.yml
- .gitignore
- src/main/java/com/hclhackathon/HclHackathonApplication.java
- src/main/resources/application.properties

## Remaining Files to Create

Follow the instructions below to add the remaining files to complete your template.

### 1. Entity Class: User.java
Path: `src/main/java/com/hclhackathon/entity/User.java`

### 2. Repository Interface: UserRepository.java
Path: `src/main/java/com/hclhackathon/repository/UserRepository.java`

### 3. Controller Class: UserController.java
Path: `src/main/java/com/hclhackathon/controller/UserController.java`

### 4. Test Class: HclHackathonApplicationTests.java
Path: `src/test/java/com/hclhackathon/HclHackathonApplicationTests.java`

## Quick Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/vegitosaiyan/hclhackathon.git
   cd hclhackathon
   ```

2. Create your working branch:
   ```bash
   git checkout -b yourname
   ```

3. Add the remaining Java files (see detailed code below)

4. Start PostgreSQL:
   ```bash
   docker-compose up -d
   ```

5. Build the project:
   ```bash
   mvn clean install
   ```

6. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Database Configuration

- Host: localhost
- Port: 5432
- Database: hcldb
- Username: postgres
- Password: postgres

## API Base URL
- Base URL: http://localhost:8080/api

## Complete Code for Remaining Files

See the individual files in the repository for the full implementation.
