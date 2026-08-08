# Implementation Plan: Owner/Pet Migration

**Branch**: `001-owner-pet-migration` | **Date**: August 8, 2026 | **Spec**: [link](spec.md)

**Input**: Feature specification from `/specs/001-owner-pet-migration/spec.md`

**Note**: This template is filled in by the `/speckit-plan` command; its definition describes the execution workflow.

## Summary

Migrate PetClinic Owner/Pet domain functionality from Spring Boot 2.x to Quarkus 3.x platform using Java 21. This migration addresses 8 specific MTA findings covering javax-to-jakarta package migration, persistence context replacement, Spring DI to CDI conversion, and transaction management updates. The goal is to maintain all existing business logic, validation rules, and REST API contracts while leveraging Quarkus' native compilation capabilities.

## Technical Context

**Language/Version**: Java 21

**Primary Dependencies**: Quarkus 3.x (Red Hat BOM 3.27.3.SP1), Jakarta Persistence, Jakarta Validation, CDI, Hibernate ORM, RESTEasy Reactive

**Storage**: PostgreSQL (via Hibernate ORM), JDBC

**Testing**: JUnit 5, Quarkus Test Framework, TestContainers

**Target Platform**: Linux server (containerized), Native Quarkus executable

**Project Type**: Web service (REST API)

**Performance Goals**: Match legacy system performance within 10% variance, support same concurrent user load

**Constraints**: Must maintain exact REST API contract, zero data loss during migration, preserve all validation rules

**Scale/Scope**: Single microservice handling Owner/Pet domain with ~1000 lines of domain code

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **AD-S §S.6 Ordering**: Tasks ordered as build → security → data/schema → API/SPI → test infra → feature/port (HARVEST before REDESIGN) → surfaces last
- **§17.2 Citation**: Every IMPLEMENT-bound task carries task id · brief B-OWNER-PET-1 · legacy locus citation
- **Non-Goals Compliance**: Visit/Vet/Specialty out, OpenAPI descoped, no Coolstore, no /speckit.implement

## Project Structure

### Documentation (this feature)

```
specs/001-owner-pet-migration/
├── plan.md              # This file (/speckit-plan command output)
├── research.md          # Phase 0 output (/speckit-plan command)
├── data-model.md        # Phase 1 output (/speckit-plan command)
├── quickstart.md        # Phase 1 output (/speckit-plan command)
├── contracts/           # Phase 1 output (/speckit-plan command)
└── tasks.md             # Phase 2 output (/speckit-tasks command - NOT created by /speckit-plan)
```

### Source Code (repository root)

```
src/main/java/com/demo/
├── model/
│   ├── Owner.java           # Migrate javax.persistence → jakarta.persistence
│   ├── Pet.java             # Migrate javax.persistence → jakarta.persistence
│   └── PetType.java         # Support entity for Pet
├── repository/
│   ├── jdbc/
│   │   └── JdbcOwnerRepositoryImpl.java  # Migrate @Autowired → @Inject
│   ├── jpa/
│   │   ├── JpaOwnerRepositoryImpl.java   # Migrate @PersistenceContext → @Inject
│   │   └── JpaPetRepositoryImpl.java     # Migrate @PersistenceContext → @Inject, add @Transactional
│   └── springdatajpa/
│       └── SpringDataPetRepositoryImpl.java  # Add @Transactional
├── rest/
│   └── OwnerResource.java   # REST endpoints for Owner/Pet
└── service/
    └── ClinicService.java   # Business logic layer

src/main/resources/
├── application.properties  # Quarkus configuration
└── import.sql             # Database initialization if needed

src/test/java/com/demo/
├── repository/            # Repository layer tests
├── service/               # Service layer tests
└── rest/                  # REST endpoint tests
```

**Structure Decision**: Single Quarkus application with layered architecture (model/repository/service/rest) following PetClinic legacy structure but adapted for Quarkus patterns.

## Complexity Tracking

No violations identified. The migration follows standard patterns for Spring Boot to Quarkus transitions and maintains the existing domain structure.
