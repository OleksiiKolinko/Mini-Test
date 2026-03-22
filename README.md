# Mini Test: Two Spring Boot Apps with Postgres and Docker

## ⚙️ Configuration

Create `.env` file:

```bash
cp .env.example .env
```

Example:

```env
POSTGRES_DB=auth
POSTGRES_USER=postgres
POSTGRES_PASSWORD=1111

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/auth
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=1111

JWT_SECRET=your_secret
JWT_EXPIRATION=3600000

INTERNAL_TOKEN=super-secret-token

SERVICE_B_URL=http://data-api:8081

AUTH_API_PORT=8080
DATA_API_PORT=8081
```

---

## 🛠 Requirements

- Java 21
- Maven 3.9+
- Docker & Docker Compose

---

## 🚀 Run

### 1. Build

```bash
mvn -f auth-api/pom.xml clean package -DskipTests
mvn -f data-api/pom.xml clean package -DskipTests
```

### 2. Start services

```bash
docker compose up -d --build
```

---

## 📡 API

### Register

```bash
POST http://localhost:8080/api/auth/register
```

### Login

```bash
POST http://localhost:8080/api/auth/login
```

### Process

```bash
POST http://localhost:8080/api/process
Authorization: Bearer <token>
```

---

## 🔒 Security

- Passwords are hashed using **BCrypt**
- JWT is used for authentication
- Service B validates requests via `X-Internal-Token`
- Sensitive data is stored in `.env` (not in repository)

---

## 🗄 Data

PostgreSQL stores:

- `users` (id, email, password_hash)
- `processing_log` (id, user_id, input_text, output_text, created_at)

---

## 🐳 Docker

- `auth-api` communicates with `data-api` via internal network:
  ```
  http://data-api:8081
  ```
- Services are fully containerized using Docker Compose

---

## 🧪 Done

- [x] User registration and login
- [x] JWT authentication
- [x] Protected `/process` endpoint
- [x] Service-to-service communication
- [x] PostgreSQL persistence
- [x] Dockerized environment  