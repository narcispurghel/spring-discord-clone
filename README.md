# Spring Boot + Angular Discord Clone Application

This project consists of a Spring Boot backend API and an Angular frontend user interface. This document explains how to set up the development environment and run the application locally.

## Table of Contents

* [Prerequisites](#prerequisites)
* [Project Structure](#project-structure)
* [Setup](#setup)
* [Configuration](#configuration)
* [Running the Application](#running-the-application)
    * [Backend (Spring Boot)](#backend-spring-boot)
    * [Frontend (Angular)](#frontend-angular)

## Prerequisites

Before you begin, ensure you have the following installed on your system:

* **Java Development Kit (JDK):** Version 23 or higher. [Download JDK](https://www.oracle.com/java/technologies/downloads/)
* **Maven or Gradle:** The build tool used by the Spring Boot backend.
    * Maven: Version 4.0.0+ [Download Maven](https://maven.apache.org/download.cgi)
* **Node.js and npm:** Required for the Angular frontend.
    * Node.js: Version 22.x or higher. [Download Node.js](https://nodejs.org/)
    * npm: Usually comes with Node.js (Version 10.x or higher recommended).
* **Angular CLI:** The command-line interface for Angular. Install globally:
    ```bash
    npm install -g @angular/cli
    ```
* **Git:** For cloning the repository. [Download Git](https://git-scm.com/downloads)
* **Database:** Ensure that PostgreSQL it's installed and running. Configure connection details in the backend's application.properties file.
* **(Optional) IDE:** An Integrated Development Environment like IntelliJ IDEA, VS Code, or Eclipse.

## Project Structure

The project is organized into two main directories:
[project-root]/  
├── backend/  
│   ├── src/  
│   ├── pom.xml         
│   └── ...  
├── frontend/          
│   ├── src/  
│   ├── angular.json  
│   ├── package.json  
│   └── ...  
├── .gitignore  
└── README.md  

## Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/narcispurghel/spring-discord-clone.git
    cd spring-discord-clone
    ```

2.  **Set up the Backend:**
    * Navigate to the backend directory:
        ```bash
        cd backend
        ```
    * Install dependencies using Maven or Gradle:
        ```bash
        mvn clean install
        ```
    * Configure database connection details in `src/main/resources/application.properties`.

3.  **Set up the Frontend:**
    * Navigate to the frontend directory:
        ```bash
        cd ../frontend
        ```
    * Install npm dependencies:
        ```bash
        npm install (--force in case it doesn't work)
        ```
## Configuration

* **Backend:** Key configurations (database connection, server port, security settings, etc.) are managed in `backend/src/main/resources/application.properties`.
* **Frontend:** Environment-specific settings are managed in `frontend/src/environments/`.
    * `environment.ts` (for development)
    * `environment.prod.ts` (for production)

## Running the Application

You need to run both the backend and frontend servers simultaneously.

### Backend (Spring Boot)

1.  Ensure you are in the `backend` directory.
2.  Run the application using Maven:

    ```bash
    mvn spring-boot:run
    ```

3.  The backend server will start on `http://localhost:8080`. Check the console output for the exact port and context path.

### Frontend (Angular)

1.  Ensure you are in the `frontend` directory.
2.  Run the Angular development server:

    ```bash
    ng serve -o
    # or
    npm start
    ```
    The `-o` flag automatically opens your default browser.

3.  The frontend application will typically be available at `http://localhost:4200`.

You should now be able to access the application through your browser at the frontend URL (usually `http://localhost:4200`).
