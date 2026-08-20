# Device Monitoring Backend

The central API for the Device Monitoring project. Built with Java and Spring Boot.

## Architecture

- **Java 21**, **Spring Boot 3.4.0**
- **Data**: PostgreSQL with Hibernate JPA and Flyway Migrations
- **Web**: Spring Web (REST API), Spring WebSocket (Real-time dashboard)
- **Security**: Spring Security (Placeholder structure for development)

## Installation & Setup

1. **Install Java 21+** (e.g., using SDKMAN or downloading an OpenJDK distribution).
2. **Install PostgreSQL**:
   - Ensure a local PostgreSQL server is running on port 5432.
   - Create a database: `CREATE DATABASE devicemonitoring;`
   - Default credentials configured in `application.properties`: `postgres` / `postgres`.

3. **Run the Application**:
   Navigate to the `backend` directory and run:
   ```bash
   ./mvnw spring-boot:run
   ```
   *Note on Windows: Use `mvnw.cmd spring-boot:run`*

## API Endpoints

- `POST /api/telemetry/system`: Ingest hardware telemetry from agents.
- `POST /api/telemetry/processes`: Ingest process telemetry from agents.
- `GET /ws`: WebSocket endpoint for real-time frontend integration.

*(Other device and incident CRUD APIs are scaffolded in the repository structure for future integration)*.
