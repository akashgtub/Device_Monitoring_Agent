# Device Monitoring Agent

A standalone Java application that runs on the user's local machine to collect hardware and software telemetry, securely communicating with the central backend.

## Overview

- **Language**: Java 21
- **Framework**: Spring Boot (Core, Scheduling, WebFlux)
- **Telemetry Engine**: OSHI (Operating System & Hardware Information)

## Features

- **Hardware Telemetry**: CPU usage/frequency, Memory availability, Disk usage, and Battery information.
- **Software Telemetry**: Top consuming OS processes.
- **Resource Protection**: Uses a `ResourceGuard` to monitor its own CPU and memory usage to prevent causing the very slowdowns it attempts to monitor.
- **Permissions**: Guided by `PermissionConfig` to restrict what it collects.

## How to Build and Run

Navigate to `device-monitoring-agent`:

```bash
# Build the JAR
./mvnw clean package

# Run the agent
java -jar target/agent-0.0.1-SNAPSHOT.jar
```
*Note on Windows: Use `mvnw.cmd`.*

The agent requires the backend to be running at `http://localhost:8080`.
