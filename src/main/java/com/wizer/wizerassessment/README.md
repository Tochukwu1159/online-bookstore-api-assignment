# Online Bookstore API

## Overview
This project implements a RESTful API for managing an online bookstore. It is built using Java with the Spring Boot framework, utilizes a relational database for data storage, and includes features for viewing, adding, updating, and deleting books from the store.

## Table of Contents
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Business Logic](#business-logic)
- [Testing](#testing)
- [Documentation](#documentation)
- [Error Handling](#error-handling)
- [Bonus Features](#bonus-features)
- [Contributing](#contributing)
- [License](#license)

## Setup Instructions
To run the application locally, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/online-bookstore-api.git
Save to grepper
Navigate to the Project Directory:

bash
Copy code
cd online-bookstore-api
Build and Run the Application:

bash
Copy code
./mvnw spring-boot:run
The application will be accessible at http://localhost:8080.

Access MySQL Database Console:
The MySQL database console is available at http://localhost:8080/phpmyadmin. Use the JDBC URL jdbc:mysql://localhost:3306/your-database-name to connect. Update the values in application.properties accordingly.

API Endpoints

Admin Registration
Endpoint: POST /api/v1/users/admin
Description: Registers the admin who is responsible for managing the book.

Buyer Registration
Endpoint: POST /api/v1/users/buyer
Description: Registers the buyer who orders/buys the book.

Login
Endpoint: POST /api/v1/users/login
Description: Login either the admin or buyer.
Get All Books:

Endpoint: GET /api/v1/bookstore/books
Description: Retrieve a list of all available books.
Add a New Book:

Endpoint: POST /api/v1/bookstore/addBook
Description: Add a new book to the store.
Update Book Details:

Endpoint: PUT /api/v1/bookstore/updateBookQuantity
Description: Update details of a specific book.
Delete a Book:

Endpoint: DELETE api/v1/bookstore/deleteBook/{id}
Description: Delete a specific book from the store.
Database
The application uses a MySQL database for data storage. JPA (Java Persistence API) is employed for database interactions. The provided application.properties file includes database configuration.

Business Logic
The API includes business logic to manage the availability status of books. This allows for efficient tracking of book availability.

Testing
Unit tests are implemented to ensure the correctness of the application. These tests cover various scenarios, including CRUD operations and business logic.

To run tests, execute the following command:

bash
Copy code
./mvnw test
Save to grepper
Documentation
API documentation is crucial for developers and users to understand how to interact with the application. Documentation for this project can be found in the docs directory. Link to API Documentation

Error Handling
Proper error handling is implemented throughout the API. The application returns appropriate HTTP status codes and error messages to indicate the result of each request.

Bonus Features
This project includes optional bonus features:

Authentication: (Optional) Protect API endpoints with authentication.
Front-End Client: (Optional) Implement a simple front-end client to interact with the API.
Contributing
Contributions are welcome! Feel free to open issues and pull requests.

License
This project is licensed under the MIT License.