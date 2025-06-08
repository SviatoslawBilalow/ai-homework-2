# JSONPlaceholder API Clone

This is a Java-based backend API that replicates the behavior and structure of https://jsonplaceholder.typicode.com, with extended support for full REST operations, JWT-based authentication, and structured user data storage.

## Features

- Full CRUD operations for users
- JWT-based authentication
- PostgreSQL database
- Docker containerization
- RESTful API endpoints
- Data validation
- Secure password hashing

## Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose
- PostgreSQL (if running locally)

## Getting Started

1. Clone the repository
2. Navigate to the backend directory
3. Build and run using Docker Compose:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update a user
- `DELETE /api/users/{id}` - Delete a user

## Data Model

The API follows the JSONPlaceholder user model structure:

```typescript
interface Geo {
  lat: string;
  lng: string;
}

interface Address {
  street: string;
  suite: string;
  city: string;
  zipcode: string;
  geo: Geo;
}

interface Company {
  name: string;
  catchPhrase: string;
  bs: string;
}

interface User {
  id: number;
  name: string;
  username: string;
  email: string;
  address: Address;
  phone: string;
  website: string;
  company: Company;
}
```

## Security

- JWT-based authentication
- Password hashing using BCrypt
- Protected endpoints requiring authentication
- CORS configuration
- Input validation

## Development

To run the application locally without Docker:

1. Start PostgreSQL
2. Configure application.yml with your database settings
3. Run the application:

```bash
mvn spring-boot:run
```

## Testing

Run the tests using Maven:

```bash
mvn test
```

## License

This project is licensed under the MIT License. 