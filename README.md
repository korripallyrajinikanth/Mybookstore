# Bookstore CRUD Operations API

A comprehensive Spring Boot REST API for managing books in a bookstore with full CRUD (Create, Read, Update, Delete) operations.

## 🚀 Features

- **Complete CRUD Operations** for book management
- **Input Validation** with detailed error messages
- **Pagination and Sorting** support
- **Search Functionality** by book name or author
- **Global Exception Handling** with consistent error responses
- **API Documentation** with Swagger/OpenAPI 3
- **Logging** with SLF4J
- **Data Transfer Objects (DTOs)** for clean API design
- **PostgreSQL Database** integration

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **SpringDoc OpenAPI 3**
- **Maven**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone <your-repo-url>
cd bookstore
```

### 2. Database Setup
Create a PostgreSQL database and update the connection details in `src/main/resources/application.properties`:

```properties
server.port=1001
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run
```bash
# Build the project
./mvnw clean compile

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:1001`

## 📚 API Documentation

### Base URL
```
http://localhost:1001/api/v1/books
```

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:1001/swagger-ui.html
```

## 🔗 API Endpoints

### 1. Create a Book
**POST** `/api/v1/books`

**Request Body:**
```json
{
  "name": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "price": 12.99
}
```

**Response:**
```json
{
  "success": true,
  "message": "Book created successfully",
  "data": {
    "id": 1,
    "name": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "price": 12.99
  }
}
```

### 2. Create Multiple Books
**POST** `/api/v1/books/batch`

**Request Body:**
```json
[
  {
    "name": "1984",
    "author": "George Orwell",
    "price": 13.99
  },
  {
    "name": "To Kill a Mockingbird",
    "author": "Harper Lee",
    "price": 14.99
  }
]
```

### 3. Get All Books
**GET** `/api/v1/books`

**Response:**
```json
{
  "success": true,
  "message": "Books retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "The Great Gatsby",
      "author": "F. Scott Fitzgerald",
      "price": 12.99
    }
  ]
}
```

### 4. Get Books with Pagination
**GET** `/api/v1/books/paginated?page=0&size=5&sortBy=name&sortDir=asc`

**Query Parameters:**
- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 10)
- `sortBy`: Sort field (default: id)
- `sortDir`: Sort direction - asc/desc (default: asc)

### 5. Get Book by ID
**GET** `/api/v1/books/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Book retrieved successfully",
  "data": {
    "id": 1,
    "name": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "price": 12.99
  }
}
```

### 6. Update Book
**PUT** `/api/v1/books/{id}`

**Request Body:**
```json
{
  "name": "The Great Gatsby - Updated",
  "author": "F. Scott Fitzgerald",
  "price": 15.99
}
```

### 7. Delete Book
**DELETE** `/api/v1/books/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Book deleted successfully",
  "data": null
}
```

### 8. Delete All Books
**DELETE** `/api/v1/books`

### 9. Search Books
**GET** `/api/v1/books/search?keyword=gatsby`

**Query Parameters:**
- `keyword`: Search term for book name or author

### 10. Check if Book Exists
**GET** `/api/v1/books/{id}/exists`

### 11. Health Check
**GET** `/api/v1/books/health`

## 🔍 Search Functionality

The search endpoint allows you to find books by:
- Book name (case-insensitive, partial match)
- Author name (case-insensitive, partial match)

Example:
```bash
GET /api/v1/books/search?keyword=gatsby
GET /api/v1/books/search?keyword=scott
```

## ✅ Input Validation

All endpoints validate input data:

- **Book Name**: Required, cannot be blank
- **Author**: Required, cannot be blank  
- **Price**: Required, must be greater than 0

**Validation Error Response:**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Book name is required",
    "price": "Price must be greater than 0"
  }
}
```

## 🚨 Error Handling

The API provides consistent error responses:

### 404 Not Found
```json
{
  "success": false,
  "message": "Book not found with ID: 999",
  "data": null
}
```

### 400 Bad Request
```json
{
  "success": false,
  "message": "Book with same name and author already exists",
  "data": null
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "An unexpected error occurred: Database connection failed",
  "data": null
}
```

## 🧪 Testing the API

### Using cURL

**Create a book:**
```bash
curl -X POST http://localhost:1001/api/v1/books \
  -H "Content-Type: application/json" \
  -d '{
    "name": "The Great Gatsby",
    "author": "F. Scott Fitzgerald", 
    "price": 12.99
  }'
```

**Get all books:**
```bash
curl http://localhost:1001/api/v1/books
```

**Get book by ID:**
```bash
curl http://localhost:1001/api/v1/books/1
```

**Search books:**
```bash
curl "http://localhost:1001/api/v1/books/search?keyword=gatsby"
```

### Using Postman

1. Import the API endpoints into Postman
2. Set the base URL to `http://localhost:1001`
3. Test each endpoint with sample data

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/bookstore/
│   │   ├── controller/          # REST Controllers
│   │   │   └── BookController.java
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── BookDTO.java
│   │   │   └── ApiResponse.java
│   │   ├── entity/              # JPA Entities
│   │   │   └── Book.java
│   │   ├── exception/           # Custom Exceptions & Handlers
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── mapper/              # Entity-DTO Mappers
│   │   │   └── BookMapper.java
│   │   ├── repository/          # JPA Repositories
│   │   │   └── BookRepository.java
│   │   ├── service/             # Business Logic
│   │   │   ├── BookService.java
│   │   │   └── BookServiceImpl.java
│   │   └── BookstoreApplication.java
│   └── resources/
│       └── application.properties
└── test/                        # Test Classes
```

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=1001

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Logging Configuration
logging.level.com.bookstore=INFO
logging.level.org.springframework.web=DEBUG
```

## 🐛 Common Issues & Solutions

### 1. Database Connection Failed
- Ensure PostgreSQL is running
- Verify database credentials in `application.properties`
- Check if the database exists

### 2. Port Already in Use
- Change the port in `application.properties`: `server.port=8080`
- Kill the process using the port: `sudo lsof -ti:1001 | xargs kill -9`

### 3. Compilation Errors
- Ensure Java 17 is installed: `java -version`
- Clean and rebuild: `./mvnw clean compile`

## 🚀 Future Enhancements

- [ ] Add authentication and authorization
- [ ] Implement book categories
- [ ] Add inventory management
- [ ] Create user reviews and ratings
- [ ] Add file upload for book covers
- [ ] Implement caching with Redis
- [ ] Add monitoring with Actuator
- [ ] Create integration tests

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -m 'Add new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Contact: [your-email@example.com]

---

**Happy Coding! 🎉**