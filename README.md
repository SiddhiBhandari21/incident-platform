# Security Incident Response Platform

A Spring Boot REST API for managing the lifecycle of security incidents: intake from detection tools, triage, assignment, collaboration, and closure. Every change is recorded in an audit trail, and the people involved are notified.

It's modelled on how a SOC team works. Incidents can come from **CrowdStrike**, **ServiceNow**, or be logged manually. Each one moves through a controlled status workflow, and every action is traceable.

---

## Features

- **Incident management:** create, view, list and paginate incidents, with severity, source, affected asset, detection link and external ticket ID.
- **Status workflow with guardrails:** incidents move through `OPEN → IN_PROGRESS → ON_HOLD → RESOLVED → CLOSED`, and invalid transitions are rejected with a clear `400` error.
- **Assignment:** assign incidents to analysts and list everything assigned to a given user.
- **Comments:** threaded investigation notes on each incident.
- **Audit trail:** every create, assign, status change and comment is logged with old and new values, who did it, and when.
- **Notifications:** users are notified about incidents relevant to them, and can fetch all or unread notifications and mark them as read.
- **Dashboard summary:** live counts of incidents by status and by severity.
- **Filtering:** query incidents by status, severity or assigned user.
- **Validation and error handling:** Bean Validation on requests, plus a global exception handler that returns a consistent JSON error shape.
- **Unit tested:** service layer covered with JUnit 5 and Mockito.

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4 (Web MVC, Data JPA, Validation, Security) |
| Database | MySQL |
| ORM | Hibernate / JPA |
| Boilerplate | Lombok |
| Testing | JUnit 5, Mockito |
| Build | Maven (wrapper included) |

## Project structure

```
src/main/java/com/siddhi/incident_platform
├── cofig/          # Spring Security configuration
├── controller/     # REST endpoints (incidents, dashboard, notifications, health)
├── dto/            # Request / response objects with validation rules
├── entity/         # JPA entities: Incident, User, IncidentComment, IncidentAuditLog, Notification
├── enums/          # Severity, IncidentStatus, SourceType, UserRole, AuditAction, NotificationType
├── exception/      # Custom exceptions + GlobalExceptionHandler
├── mapper/         # Entity ↔ DTO mappers
├── repository/     # Spring Data JPA repositories
└── service/        # Business logic (interfaces + impl)
```

The code follows a layered **Controller → Service → Repository** design. DTOs keep the API contract separate from the database entities.

## Domain model

- **User:** name, email, role (`ADMIN`, `MANAGER`, `ANALYST`), active flag
- **Incident:** title, description, severity (`CRITICAL`, `HIGH`, `MEDIUM`, `LOW`), status, source (`CROWDSTRIKE`, `SERVICENOW`, `MANUAL`), external ID, affected asset, detection link, created-by / assigned-to user, and lifecycle timestamps (created, updated, resolved, closed)
- **IncidentComment:** comment text, author, timestamp
- **IncidentAuditLog:** action, old value → new value, performed by, timestamp
- **Notification:** type, message, related incident, recipient, read status

## API endpoints

Base URL: `http://localhost:8080`

### Incidents

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/incidents` | Create an incident |
| `GET` | `/api/incidents` | List all incidents |
| `GET` | `/api/incidents/page?page=0&size=5` | Paginated list |
| `GET` | `/api/incidents/{id}` | Get incident by ID |
| `PATCH` | `/api/incidents/{id}/status` | Update status (validated transitions) |
| `PATCH` | `/api/incidents/{id}/assign` | Assign to a user |
| `POST` | `/api/incidents/{id}/comments` | Add a comment |
| `GET` | `/api/incidents/{id}/comments` | List comments |
| `GET` | `/api/incidents/{id}/audit-logs` | View audit trail |
| `GET` | `/api/incidents/status/{status}` | Filter by status |
| `GET` | `/api/incidents/severity/{severity}` | Filter by severity |
| `GET` | `/api/incidents/assigned/{userId}` | Incidents assigned to a user |

### Notifications

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/users/{userId}/notifications` | All notifications for a user |
| `GET` | `/api/users/{userId}/notifications/unread` | Unread notifications |
| `PATCH` | `/api/notifications/{id}/read` | Mark as read |

### Dashboard and health

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/dashboard/summary` | Counts by status and severity |
| `GET` | `/api/health` | Health check |

### Example: create an incident

```http
POST /api/incidents
Content-Type: application/json

{
  "title": "Suspicious PowerShell execution on finance laptop",
  "description": "CrowdStrike flagged encoded PowerShell command spawning from Outlook.",
  "severity": "HIGH",
  "sourceType": "CROWDSTRIKE",
  "externalId": "CS-DET-48213",
  "affectedAsset": "FIN-LT-0231",
  "detectionLink": "https://falcon.crowdstrike.com/...",
  "createdByUserId": 1
}
```

### Example: error response

```json
{
  "status": 400,
  "message": "Severity is required, Title is required",
  "timestamp": "2026-10-05T14:32:10"
}
```

## Getting started

### Prerequisites

- Java 17+
- MySQL 8+
- Maven (or use the included `mvnw` wrapper)

### 1. Create the database

```sql
CREATE DATABASE security_incident_db;
```

### 2. Configure the connection

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_incident_db
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.jpa.hibernate.ddl-auto=update
```

Tables are created automatically on first run.

### 3. Run the application

```bash
# Windows
mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`. Check `GET /api/health` to confirm it's up.

> **Note:** incidents reference users by ID, so add at least one row to the `users` table before creating incidents.

### 4. Run the tests

```bash
mvnw.cmd test
```

## Roadmap

- [ ] JWT-based authentication and role-based access (`ADMIN` / `MANAGER` / `ANALYST`)
- [ ] User management endpoints
- [ ] SLA tracking with warning and breach notifications
- [ ] Automated ingestion of CrowdStrike / ServiceNow alerts via Kafka
- [ ] Swagger / OpenAPI documentation
- [ ] Docker Compose setup for app + MySQL

## Author

**Siddhi Bhandari:** Backend & Security Engineer
GitHub: [@SiddhiBhandari21](https://github.com/SiddhiBhandari21)
