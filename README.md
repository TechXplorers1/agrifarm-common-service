# Agri Farms Common Service

Spring Boot application for common services in Agri Farms.

## Requirements

1. **Java**: Version 17 or higher (Java 20 is fully tested and supported).
2. **Maven**: Properly configured in PATH.
3. **PostgreSQL**: Installed locally (the app will automatically create and run a private database instance on port `5435` so it doesn't conflict with your other services).

## Quick Start (Automated Script)

The easiest way to run the database and the backend together is by running the automated PowerShell script:

```powershell
.\run.ps1
```

This script will:
- Automatically find your local PostgreSQL installation.
- Spin up a private PostgreSQL server instance running on port `5435` using `pg_dev_data` folder (isolated from your other databases).
- Automatically create the `agrifarms` database inside this instance if it doesn't exist.
- Start the Spring Boot application, which will run database migrations via Liquibase and begin listening on port `8083`.

---

## Manual Startup

If you prefer to start elements individually:

### 1. Start the Database
Start a PostgreSQL server running on port `5435` and create a database named `agrifarms`.

### 2. Run the Backend Application
```bash
mvn clean install
mvn spring-boot:run
```
