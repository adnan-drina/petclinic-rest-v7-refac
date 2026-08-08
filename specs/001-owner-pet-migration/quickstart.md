# Quickstart Guide: Owner/Pet Migration Validation

**Date**: August 8, 2026

## Prerequisites

1. **Java 21** installed and configured (JAVA_HOME set)
2. **Maven 3.8+** installed  
3. **Docker** installed (for PostgreSQL test database)
4. **Git** for source control
5. **PostgreSQL 14+** running locally or via Docker

## Setup Commands

### 1. Clone and Prepare Repository

```bash
cd /projects/modernized
git status
```

### 2. Configure Database

**Option A: Local PostgreSQL**
```bash
# Create database
createdb petclinic

# Set environment variable
export QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:5432/petclinic
```

**Option B: Docker Container**
```bash
docker run --name petclinic-db -e POSTGRES_PASSWORD=petclinic \
  -e POSTGRES_DB=petclinic -p 5432:5432 -d postgres:14
export QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:5432/petclinic
```

### 3. Set Quarkus Profile

```bash
export QUARKUS_PROFILE=dev
```

## Build and Test Commands

### 1. Compile Application

```bash
cd /projects/modernized
export JAVA_HOME="${JAVA_HOME_21}"
export PATH="${JAVA_HOME}/bin:${PATH}"
mvn clean compile
```

**Expected Outcome**: Compilation succeeds with no errors related to javax-to-jakarta migration, persistence context, or Spring DI annotations.

### 2. Run Unit Tests

```bash
mvn test
```

**Expected Outcome**: All tests pass, validating that migrated code maintains the same behavior as legacy system.

### 3. Start Application in Dev Mode

```bash
mvn quarkus:dev
```

**Expected Outcome**: Application starts successfully, showing Owner/Pet endpoints available at `/q/health` (health check) and `/api/owners` (REST endpoints).

**Wait for**: "Application started in X.XXXs" message

## Validation Scenarios

### Scenario 1: Owner CRUD Operations

**Test Command**:
```bash
# Create owner
curl -X POST http://localhost:8080/api/owners \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe", 
    "address": "123 Main St",
    "city": "Springfield",
    "telephone": "5551234567"
  }'

# Retrieve owner
curl http://localhost:8080/api/owners/1

# List all owners  
curl http://localhost:8080/api/owners
```

**Expected Result**: 
- POST returns 201 Created with owner JSON
- GET /api/owners/1 returns 200 OK with owner details
- GET /api/owners returns 200 OK with array of owners

### Scenario 2: Pet CRUD Operations

**Test Command**:
```bash
# First ensure owner exists (from Scenario 1)
# Create pet for owner
curl -X POST http://localhost:8080/api/owners/1/pets \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Buddy",
    "birthDate": "2020-01-15", 
    "type": {"id": 1}
  }'

# Retrieve pet
curl http://localhost:8080/api/owners/1/pets/1
```

**Expected Result**:
- POST returns 201 Created with pet JSON including owner association
- GET returns 200 OK with complete pet information including type and owner details

### Scenario 3: Validation Rules

**Test Command**:
```bash
# Try to create owner with missing required fields
curl -X POST http://localhost:8080/api/owners \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John"
  }'

# Try to create owner with invalid phone number
curl -X POST http://localhost:8080/api/owners \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "telephone": "123"  # Too short
  }'
```

**Expected Result**: Both requests return 400 Bad Request with validation error details matching legacy system behavior.

### Scenario 4: Error Handling

**Test Command**:
```bash
# Try to retrieve non-existent owner
curl http://localhost:8080/api/owners/999

# Try to delete non-existent owner
curl -X DELETE http://localhost:8080/api/owners/999
```

**Expected Result**: 
- GET returns 404 Not Found with appropriate error message
- DELETE returns 204 No Content or 404 Not Found (consistent with legacy)

## Success Criteria Validation

### ✅ Build Success
- `mvn clean compile` completes without errors
- No compilation warnings related to migrated annotations

### ✅ Test Success  
- All unit tests pass
- Integration tests validate Owner/Pet operations work correctly

### ✅ REST API Success
- All CRUD operations return correct HTTP status codes
- JSON responses match legacy system format
- Validation errors are communicated properly

### ✅ Data Integrity Success
- Owner-Pet relationships are preserved
- No data corruption during database operations
- Validation rules function identically to legacy system

## Performance Baseline

Run basic performance check:
```bash
# Start time measurement
time curl -s http://localhost:8080/api/owners > /dev/null

# Should complete within reasonable time (same as legacy system)
```

**Expected**: Response times match legacy system within 10% variance.

## Cleanup

```bash
# Stop application (Ctrl+C in terminal running mvn quarkus:dev)

# Clean up Docker container if used
docker stop petclinic-db
docker rm petclinic-db

# Clean Maven artifacts
mvn clean
```

## Troubleshooting

**Issue**: Compilation errors related to javax imports
**Solution**: Verify jakarta.persistence and jakarta.validation imports in model classes

**Issue**: Database connection errors
**Solution**: Check QUARKUS_DATASOURCE_JDBC_URL and ensure PostgreSQL is running

**Issue**: Tests failing due to validation differences
**Solution**: Verify migration preserved all validation rules from legacy system

**Issue**: REST endpoints returning 404
**Solution**: Check that application started successfully and endpoints are at `/api/*` path
