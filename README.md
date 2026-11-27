# Employee Management System

A RESTful Employee Management System developed using Java 17 and Spring Boot 3. It provides APIs to create, retrieve, update, search, and delete employee records.

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- JUnit 5
- Mockito
- Swagger / OpenAPI

## Features

- Create new employees
- Retrieve employee details by ID
- Retrieve all employees
- Search employees by partial name
- Update employee information
- Delete employees
- Validate employee details
- Handle exceptions globally
- Pagination and sorting
- API documentation using Swagger

## Employee Fields

- ID
- Name
- Email
- Department
- Salary
- Mobile Number

## Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE employee_management;
```

Update your database credentials in `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

## Running the Project

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure MySQL credentials.
4. Run `ManagementSystemApplication.java`.

The application runs at:

`http://localhost:8080`

## API Documentation

Swagger UI:

`http://localhost:8080/swagger-ui/index.html`

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/employees | Create employee |
| GET | /api/employees | Get all employees |
| GET | /api/employees/{id} | Get employee by ID |
| GET | /api/employees/search?name=Rahul | Search employees |
| PUT | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |
| GET | /api/employees/page | Pagination and sorting |

## Testing

Unit tests are written using JUnit 5 and Mockito.

The tests cover employee creation, duplicate email handling, retrieval, updating, and deletion.

## Author

Developed as an Employee Management System backend project.