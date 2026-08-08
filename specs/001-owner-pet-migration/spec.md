# Feature Specification: Owner/Pet Migration

**Feature Branch**: `001-owner-pet-migration`

**Created**: August 8, 2026

**Status**: Draft

**Input**: Migrate PetClinic Owner/Pet domain from Spring Boot 2.x to Quarkus 3.x with Java 21 - addressing 8 MTA findings for javax-to-jakarta import migration, persistence context replacement, Spring DI to CDI conversion, and transaction management updates

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Owner Data Management (Priority: P1)

Veterinarian staff need to manage pet owner information in the migrated Quarkus application while maintaining the same data integrity and business rules as the legacy Spring Boot system.

**Why this priority**: This is the core domain functionality that all other PetClinic features depend on. Without functional Owner management, the entire application is non-functional.

**Independent Test**: Deploy the Quarkus application and verify Owner CRUD operations work identically to the legacy system, including validation rules, data persistence, and error handling.

**Acceptance Scenarios**:

1. **Given** a valid Owner with name, address, city, and telephone, **When** the Owner is created through the REST API, **Then** the Owner is persisted in the database and can be retrieved with all fields intact.
2. **Given** an existing Owner record, **When** the Owner information is updated via REST API, **Then** changes are saved atomically and reflected in subsequent queries.
3. **Given** Owner data in the legacy database, **When** the Quarkus application starts, **Then** all Owner records are accessible and functional without data loss or corruption.

---

### User Story 2 - Pet Data Management (Priority: P1)

Veterinarian staff need to manage pet information (name, birth date, type, owner association) in the migrated Quarkus application with the same validation and business rules as the legacy system.

**Why this priority**: Pet management is the second core domain entity that depends on Owner management. This functionality is essential for basic clinic operations.

**Independent Test**: Verify Pet CRUD operations work correctly, including the owner-pet relationship, type validation, and date handling.

**Acceptance Scenarios**:

1. **Given** a valid Pet with name, birth date, type, and associated Owner, **When** the Pet is created through the REST API, **Then** the Pet is persisted with proper owner association and can be retrieved with complete information.
2. **Given** an existing Pet record, **When** the Pet information is updated via REST API, **Then** changes are saved and the owner association remains intact.
3. **Given** Pet data in the legacy database, **When** the Quarkus application starts, **Then** all Pet records are accessible with correct owner relationships and no data corruption.

---

### Edge Cases

- What happens when trying to create a Pet without a valid Owner?
- How does the system handle invalid date formats for pet birth dates?
- What occurs when updating an Owner that has associated Pets?
- How are validation errors communicated to API clients?
- What happens when deleting an Owner that has associated Pets?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST migrate all javax.persistence imports to jakarta.persistence in Owner.java and Pet.java model classes without changing functionality
- **FR-002**: System MUST migrate all javax.validation imports to jakarta.validation in model classes while preserving all validation rules
- **FR-003**: System MUST replace @PersistenceContext annotations with @Inject for EntityManager in JPA repositories (JpaOwnerRepositoryImpl, JpaPetRepositoryImpl)
- **FR-004**: System MUST convert Spring @Autowired annotations to CDI @Inject in JDBC repository implementations (JdbcOwnerRepositoryImpl)
- **FR-005**: System MUST add @Transactional annotations to data modification methods in JPA and Spring Data JPA repositories
- **FR-006**: System MUST maintain all existing validation rules, business logic, and data integrity constraints from the legacy system
- **FR-007**: System MUST preserve the REST API contract and data format compatibility with existing PetClinic clients
- **FR-008**: System MUST handle all 8 specific MTA findings identified in the analysis without introducing regressions

### Key Entities

- **Owner**: Pet owner with id, first name, last name, address, city, telephone fields and associated pets
- **Pet**: Pet information with id, name, birth date, type, and bidirectional relationship to Owner
- **PetType**: Pet type definitions (Dog, Cat, etc.) used by Pet entities
- **Repository Layer**: Data access components for Owner and Pet entities using Quarkus-compatible persistence

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: All 8 MTA findings are resolved: 2 javax-to-jakarta import issues, 2 persistence context replacements, 2 Spring DI to CDI conversions, and 2 transaction management additions
- **SC-002**: Application builds successfully with Quarkus 3.x and Java 21 without compilation errors or warnings related to the migrated code
- **SC-003**: Owner and Pet REST endpoints respond with HTTP 200 for all CRUD operations, matching legacy system behavior
- **SC-004**: Database queries execute with same performance characteristics as legacy system (within 10% variance)
- **SC-005**: All validation rules from legacy system (required fields, format validation, business rules) function identically in migrated system
- **SC-006**: Zero data integrity issues when migrating from legacy database to Quarkus application (all owner-pet relationships preserved)

## Non-Goals *(mandatory — AD-S)*

- **NG-001**: Visit, Vet, and Specialty clinic groups are explicitly excluded from this migration (deferred to later slices)
- **NG-002**: OpenAPI/Swagger UI parity is descoped - G-4 OpenAPI validation is not required for this feature
- **NG-003**: Coolstore specimen is explicitly excluded from this migration scope
- **NG-004**: /speckit.implement phase is never executed for this feature per AD-S requirements
- **NG-005**: No new features or enhancements beyond resolving the 8 specified MTA findings

## Assumptions

- The existing Owner and Pet domain model structure and relationships are preserved
- Legacy database schema and data remain compatible with the migrated Quarkus application
- Existing REST API clients will continue to work without modifications
- The Quarkus 3.x platform with Red Hat BOM 3.27.3.SP1 provides necessary compatibility
- Java 21 runtime environment is available and configured correctly
- All validation rules and business logic from the legacy Spring Boot 2.x system must be preserved
