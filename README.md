# CRUD_Java

A simple CRUD (Create, Read, Update, Delete) application built using Java and Spring Boot.

## 🚀 Features
- Create, Read, Update, and Delete operations for managing data.
- Uses Spring Boot for backend development.
- RESTful API endpoints.
- Integrated with a relational database (e.g., MySQL, PostgreSQL).
- Uses Hibernate for ORM (Object-Relational Mapping).
- Includes logging using Lombok's `@Slf4j`.
- Includes testing

## 🛠 Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- Maven
- MySQL (Database)

## 📂 Project Structure
```
CRUD_Java/
│-- src/main/java/com/example/crud
│   │-- controller/  # Contains REST controllers
│   │-- service/     # Business logic layer
│   │-- repository/  # Database access layer
│   │-- entities/       # Entity classes
│   └-- CrudApplication.java # Main Spring Boot application
│-- src/main/resources/
│   └-- application.properties  # Configuration file
│-- pom.xml  # Maven dependencies
```

## ⚙️ Setup and Installation
### Prerequisites
- Java 17+
- Maven
- MySQL

### Steps to Run the Project
1. **Clone the repository:**
   ```sh
   git clone https://github.com/Rammy12/CRUD_Java.git
   cd CRUD_Java
   ```

2. **Configure Database:**
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/crud_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build and Run the Project:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access API Endpoints:**
   - Create Employee: `POST /employees`
   - Get All Employees: `GET /employees`
   - Get Employee by ID: `GET /employees/{id}`
   - Update Employee: `PUT /employees/{id}`
   - Delete Employee: `DELETE /employees/{id}`


## 🤝 Contributing
Contributions are welcome! Feel free to open issues or submit pull requests.

## 📧 Contact
For any questions, contact **[Ramesh kumar]** at `rameshkumar455555@gmail.com`.

