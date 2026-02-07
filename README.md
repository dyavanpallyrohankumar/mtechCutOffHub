
<br />
<div align="center">
  <a href="https://github.com/your-username/gate-pgcet-cutoff-service">
    <img src="https://cdn-icons-png.flaticon.com/512/2995/2995461.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Gate–PGCET M.Tech Cutoff Management Service</h3>

  <p align="center">
    A secure, scalable backend system for managing and publishing GATE & PGCET M.Tech admission cutoffs.
    <br />
    <br />
    <a href="https://github.com/your-username/gate-pgcet-cutoff-service/issues">Report Bug</a>
    ·
    <a href="https://github.com/your-username/gate-pgcet-cutoff-service/issues">Request Feature</a>
  </p>
</div>

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/build-passing-brightgreen?style=flat-square" alt="Build Status">
  <img src="https://img.shields.io/badge/license-MIT-orange?style=flat-square" alt="License">
</div>

---

<details>
  <summary><strong>📖 Table of Contents</strong> (Click to Expand)</summary>
  <ol>
    <li><a href="#-overview">Overview</a></li>
    <li><a href="#-tech-stack">Tech Stack</a></li>
    <li><a href="#-prerequisites--requirements">Prerequisites</a></li>
    <li><a href="#-installation--setup">Installation & Setup</a></li>
    <li><a href="#-running-the-project">Running the Application</a></li>
    <li><a href="#-api-endpoints-overview">API Documentation</a></li>
    <li><a href="#-running-tests">Testing</a></li>
    <li><a href="#-project-structure">Project Structure</a></li>
    <li><a href="#-contribution-guidelines">Contribution</a></li>
    <li><a href="#-license">License</a></li>
    <li><a href="#-authors--acknowledgements">Authors</a></li>
  </ol>
</details>

---

## 📌 Overview

The **Gate–PGCET M.Tech Cutoff Management Service** is a robust backend application built to **store, compute, and expose postgraduate engineering admission cutoffs** for colleges and branches based on **GATE** and **PGCET** examinations.

The platform automates cutoff generation from student allotment data and organizes results across multiple dimensions such as:

- 🎯 Exam type (GATE / PGCET)
- 🏷 Category
- 🚻 Gender
- 🌀 Phase
- 📅 Academic year
- 🏫 College & Branch

### 🔐 Role-Based Access Model

| Role | Access Level | Description |
| :--- | :--- | :--- |
| **Admin APIs** | 🔴 Secure | Secure endpoints for administrators to manage colleges, branches, student allotments, and trigger cutoff generation. |
| **Public APIs** | 🟢 Open | Read-only endpoints that allow students and users to fetch verified cutoff data **without authentication**. |

### ⚙️ Key Design Goals

- **Stateless Authentication:** Using **JWT**.
- **Clear Separation:** Distinct Admin and Public APIs.
- **Data Consistency:** Through normalized relational models.
- **Scalability:** Suitable for multi-college, multi-year datasets.

This service is intended to act as a **single source of truth** for M.Tech admission cutoffs and can be easily integrated with web or mobile frontends.

---

## 🚀 Tech Stack

<div align="center">
  <strong>A modern, secure, and scalable backend architecture built with industry-grade technologies</strong>
</div>
<br />

<div align="center">

| Core Backend | Security & Auth | Database |
| :---: | :---: | :---: |
| ![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) <br> **Java 21 (LTS)** | ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) <br> **JWT & OTP** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Relational-316192?style=for-the-badge&logo=postgresql&logoColor=white) <br> **PostgreSQL 15+** |
| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) <br> **Spring Boot 3** | ![JWT](https://img.shields.io/badge/JWT-Token%20Based-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white) <br> **Stateless Auth** | ![Hibernate](https://img.shields.io/badge/Spring%20Data%20JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white) <br> **Spring Data JPA** |

| Architecture | Developer Tooling | API & Testing |
| :---: | :---: | :---: |
| ![Architecture](https://img.shields.io/badge/Architecture-Layered-blueviolet?style=for-the-badge) <br> **Layered Design** | ![Maven](https://img.shields.io/badge/Maven-Build%20Tool-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) <br> **Apache Maven** | ![REST](https://img.shields.io/badge/API-RESTful-0096D6?style=for-the-badge) <br> **RESTful JSON** |
| ![DTO](https://img.shields.io/badge/API-DTO%20Driven-green?style=for-the-badge) <br> **DTO Driven** | ![Lombok](https://img.shields.io/badge/Lombok-Boilerplate-red?style=for-the-badge&logo=lombok&logoColor=white) <br> **Project Lombok** | ![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white) <br> **JUnit 5 & Mockito** |

</div>

### ✅ Highlights
- ✔️ Production-ready backend
- ✔️ Secure admin-only write APIs
- ✔️ Public read-only data access
- ✔️ Scalable cutoff computation engine

---

## 🧩 Prerequisites & Requirements

Ensure the following tools and services are installed and properly configured before running the project.

### 🖥️ System Requirements
- Any modern OS (Linux / macOS / Windows)
- Minimum **8 GB RAM** recommended
- Internet access (for dependencies & email)

### ☕ Java Runtime
- **Java 21 (LTS)** is mandatory.
- Earlier versions are **not supported** due to Spring Boot 3 requirements.
```bash
java -version

```

### 🧱 Build Tool

* **Apache Maven 3.9+**

```bash
mvn -v

```

### 🗄️ Database

* **PostgreSQL 15 or higher**
* Required database: `mtech_admissions`

```sql
CREATE DATABASE mtech_admissions;

```

### 📧 Email Service (OTP Verification)

* SMTP access required for admin OTP authentication.
* Gmail / Outlook / custom SMTP providers supported.
* **Note:** App-specific password recommended for Gmail.

### 🔐 Environment Configuration

The following environment variables **must be configured**:

```env
# Database
DB_URL=jdbc:postgresql://localhost:5432/mtech_admissions
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your_super_secure_256bit_secret
JWT_EXPIRATION=86400000

# Email (OTP)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=app_password

```

> ⚠️ **Never commit `.env` or secrets to version control.**

---

## 🚀 Installation & Setup

Get the Gate & PGCET Cutoff Service running locally in minutes.

### 📥 Step 1: Clone the Repository

```bash
git clone [https://github.com/your-username/gate-pgcet-cutoff-service.git](https://github.com/your-username/gate-pgcet-cutoff-service.git)
cd gate-pgcet-cutoff-service

```

*(Replace `your-username` with the actual GitHub organization or user.)*

### 🗄️ Step 2: Database Setup

Ensure PostgreSQL is running.

```sql
CREATE DATABASE mtech_admissions;

```

*Optional (recommended for production):*

```sql
CREATE USER mtech_admin WITH PASSWORD 'strong_password';
GRANT ALL PRIVILEGES ON DATABASE mtech_admissions TO mtech_admin;

```

### 🌱 Step 3: Environment Configuration

Create a `.env` file in the root directory:

```env
DB_URL=jdbc:postgresql://localhost:5432/mtech_admissions
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=ReplaceWithVeryLongSecureKey_AtLeast32Chars
JWT_EXPIRATION=86400000
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

```

### 📦 Step 4: Build the Project

```bash
mvn clean install

```

### ▶️ Step 5: Start the Application

```bash
mvn spring-boot:run

```

*Or using the built JAR:*

```bash
java -jar target/gate-pgcet-cutoff-service-0.0.1-SNAPSHOT.jar

```

### ✅ Step 6: Verify Health

```http
GET http://localhost:8080/test

```

**Expected Response:** `Backend is Working 2026-01-26T10:30:45`

---

## ▶️ Running the Project

### Runtime Modes

| Mode | Command | Features |
| --- | --- | --- |
| **Development** | `mvn spring-boot:run` | Hot reload, Detailed logs, Fast iteration |
| **Production** | `java -jar target/app.jar` | Optimized bytecode, Stateless JWT, Secure |

### 🌐 Active Ports

| Component | Default Port |
| --- | --- |
| Backend Server | `8080` |
| PostgreSQL | `5432` |
| SMTP | `587` |

To change the server port: `SERVER_PORT=9090`

### 🧭 Runtime Profiles

* **Dev:** `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
* **Prod:** `java -jar app.jar --spring.profiles.active=prod`

### ⛔ Graceful Shutdown

Stop the application safely using `CTRL + C`. Spring Boot ensures resources are released cleanly.

---

## 🔌 API Endpoints Overview

RESTful APIs for authentication, colleges, branches, cutoffs, and student allotments.

### 🔐 Authentication APIs (Admin)

*Base Path:* `/api/admin/auth`

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| `POST` | `/login` | Initiates admin login & sends OTP | Public |
| `POST` | `/verify-otp` | Verifies OTP & returns JWT | Public |
| `POST` | `/register` | Registers a new admin | Public |

**Sample: Verify OTP**

```json
POST /api/admin/auth/verify-otp
{
  "email": "admin@example.com",
  "otp": "123456"
}

```

### 🌍 Public APIs (No Auth)

*Base Path:* `/api/public`

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/colleges` | Get all colleges |
| `GET` | `/colleges/{code}` | Get college details |
| `GET` | `/colleges/{code}/branches` | Get branches of a college |
| `GET` | `/cutoffs/college/{code}` | All cutoffs for a college |
| `GET` | `/cutoffs/college/{code}/branch/{branch}` | Cutoffs for a specific branch |

### 🛠️ Admin APIs (JWT Required)

*Header:* `Authorization: Bearer <JWT_TOKEN>`

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/admin/colleges` | Create a college |
| `PUT` | `/api/admin/colleges` | Update college |
| `DELETE` | `/api/admin/colleges/{code}` | Delete college |
| `POST` | `/api/admin/colleges/{code}/branches` | Add branches to college |
| `POST` | `/api/admin/allotments` | Allot a student |
| `GET` | `/api/admin/allotments/college/{code}` | Students allotted to college |

---

## 🧪 Running Tests

This project follows a **layered testing approach** (Controller, Service, Repository) using **JUnit 5** and **Mockito**.

### ▶️ Run All Tests

```bash
mvn test

```

This will compile, spin up a test context, execute tests, and generate a report in `target/surefire-reports/`.

### 🎯 Run Specific Tests

* **Specific Class:** `mvn -Dtest=CutoffServiceTest test`
* **Specific Method:** `mvn -Dtest=CutoffServiceTest#testGetCutoffsByCollegeAndBranch test`

---

## 🗂️ Project Structure

The project follows a clean **layered backend architecture**.

<details>
<summary><strong>Click to expand the Directory Tree</strong></summary>

```text
├── src
│   ├── main
│   │   ├── java/com/mtech/addmissions
│   │   │   ├── config          # LoggingAspect.java
│   │   │   ├── controller      # Admin & Public Controllers
│   │   │   ├── dto             # Request/Response DTOs
│   │   │   ├── enums           # Exam, Gender, Region, Role
│   │   │   ├── exception       # GlobalExceptionHandler
│   │   │   ├── model           # JPA Entities (College, Branch, Cutoff)
│   │   │   ├── repository      # Spring Data Repositories
│   │   │   ├── security        # JwtUtil, SecurityConfig
│   │   │   ├── service         # Business Logic Implementations
│   │   │   └── util            # Bootstrapping & ErrorResponse
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql
│   └── test                    # Unit & Integration Tests
├── pom.xml                     # Maven Dependencies
└── README.md                   # Documentation

```

</details>

### Layer Responsibilities

* **Controller:** Exposes REST APIs, handles validation.
* **Service:** Core business logic, orchestration.
* **Repository:** Database access via JPA/Hibernate.
* **DTO:** Custom response shaping (Public vs Admin).
* **Security:** Stateless JWT filter chain.

---

## 🤝 Contribution Guidelines

Contributions are welcome — quality, clarity, and consistency matter.

### Development Workflow

1. **Fork** the repository.
2. **Create a feature branch:** `git checkout -b feature/your-feature-name`
3. **Commit changes:** `git commit -m "feat: add cutoff aggregation"`
4. **Push to fork:** `git push origin feature/your-feature-name`
5. **Open a Pull Request**.

### Commit Message Convention

* `feat(auth): add OTP-based admin login`
* `fix(cutoff): correct percentile range`
* `docs(readme): improve API documentation`

### Checklist

* [ ] Code compiles and runs locally.
* [ ] No sensitive data committed.
* [ ] APIs follow existing patterns.
* [ ] Naming conventions are consistent.

---

## 📜 License

This project is released under the **MIT License**.

You are free to:

* ✅ Use the project for personal or commercial purposes.
* ✅ Modify and distribute the source code.
* ✅ Integrate it into other systems.

*See the `LICENSE` file for full details.*

---

## 👤 Authors & Acknowledgements

**Rohankumar Dyavanpally**
<br />

*Backend Developer · Java · Spring Boot*

* Architecture design
* Cutoff computation engine
* Secure admin authentication (JWT + OTP)
* API design & role-based access control

### Acknowledgements

Special thanks to the **Spring Boot & Spring Security teams**, **Hibernate & JPA**, and the **PostgreSQL** community for the robust tooling that made this project possible.

---

<div align="center">
<strong>⭐ Support the Project</strong>
<br />
If you find this project useful, please give it a star!
</div>
