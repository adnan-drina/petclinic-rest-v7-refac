# Tasks: Owner/Pet Migration

**Input**: Design documents from `/specs/001-owner-pet-migration/`

**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Test tasks included to ensure validation of migrated functionality matches legacy behavior

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2)
- Include exact file paths in descriptions

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and basic structure per AD-S §S.6 ordering

- [ ] T001 Create project structure per implementation plan (src/main/java/com/demo/ structure)
- [ ] T002 Initialize Quarkus 3.x project with Red Hat BOM 3.27.3.SP1 dependencies
- [ ] T003 [P] Configure Maven build plugins and Java 21 target

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Core infrastructure that MUST be complete before ANY user story can be implemented

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

**AD-S §S.6 Ordering**: build → security → data/schema → API/SPI → test infra → feature/port → surfaces last

- [ ] T004 Setup database schema and persistence configuration for PostgreSQL
- [ ] T005 [P] Configure Quarkus CDI and transaction management
- [ ] T006 [P] Setup REST API routing structure under /api/* endpoints
- [ ] T007 Create base model classes with Jakarta Persistence annotations
- [ ] T008 Configure validation framework with Jakarta Validation
- [ ] T009 Setup error handling and logging infrastructure
- [ ] T010 [P] Configure Quarkus test framework with TestContainers

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - Owner Data Management (Priority: P1) 🎯 MVP

**Goal**: Complete Owner entity migration with all 8 MTA findings addressed for Owner-related code

**Independent Test**: Deploy and verify Owner CRUD operations match legacy system behavior exactly

**Citations**: Each task carries §17.2 triple (task id · brief B-OWNER-PET-1 · legacy locus)

### Tests for User Story 1

> **NOTE: Write these tests FIRST, ensure they FAIL before implementation**

- [ ] T011 [P] [US1] Contract test for Owner REST endpoints in src/test/java/com/demo/rest/OwnerResourceTest.java
- [ ] T012 [P] [US1] Integration test for Owner persistence in src/test/java/com/demo/repository/OwnerRepositoryTest.java
- [ ] T013 [P] [US1] Unit test for Owner validation in src/test/java/com/demo/model/OwnerTest.java

### Implementation for User Story 1

**BR-OP-01 & BR-OP-02: javax-to-jakarta import migration in Owner.java**

- [ ] T014 [P] [US1] Migrate javax.persistence import to jakarta.persistence in src/main/java/com/demo/model/Owner.java:22
- [ ] T015 [P] [US1] Migrate javax.validation import to jakarta.validation in src/main/java/com/demo/model/Owner.java:23

**BR-OP-03: persistence-to-quarkus @PersistenceContext → @Inject migration**

- [ ] T016 [US1] Replace @PersistenceContext with @Inject for EntityManager in src/main/java/com/demo/repository/jpa/JpaOwnerRepositoryImpl.java:44

**BR-OP-05 & BR-OP-06: springboot-di-to-quarkus @Autowired → @Inject migration**

- [ ] T017 [P] [US1] Replace @Autowired with @Inject for JdbcOwnerRepositoryImpl dependencies in src/main/java/com/demo/repository/jdbc/JdbcOwnerRepositoryImpl.java:54,62

**Core Owner functionality**

- [ ] T018 [P] [US1] Create OwnerService with business logic in src/main/java/com/demo/service/OwnerService.java
- [ ] T019 [US1] Implement OwnerResource REST endpoints in src/main/java/com/demo/rest/OwnerResource.java (depends on T014-T018)
- [ ] T020 [US1] Add validation and error handling for Owner operations
- [ ] T021 [US1] Add logging for Owner management operations

**Checkpoint**: At this point, User Story 1 should be fully functional and testable independently

---

## Phase 4: User Story 2 - Pet Data Management (Priority: P1)

**Goal**: Complete Pet entity migration with remaining MTA findings for Pet-related code

**Independent Test**: Verify Pet CRUD operations work correctly with owner associations

**Citations**: Each task carries §17.2 triple (task id · brief B-OWNER-PET-1 · legacy locus)

### Tests for User Story 2

- [ ] T022 [P] [US2] Contract test for Pet REST endpoints in src/test/java/com/demo/rest/PetResourceTest.java
- [ ] T023 [P] [US2] Integration test for Pet persistence and owner relationships in src/test/java/com/demo/repository/PetRepositoryTest.java
- [ ] T024 [P] [US2] Unit test for Pet validation in src/test/java/com/demo/model/PetTest.java

### Implementation for User Story 2

**Pet entity migration (javax-to-jakarta)**

- [ ] T025 [P] [US2] Migrate javax.persistence import to jakarta.persistence in src/main/java/com/demo/model/Pet.java
- [ ] T026 [P] [US2] Migrate javax.validation import to jakarta.validation in src/main/java/com/demo/model/Pet.java

**BR-OP-04: persistence-to-quarkus @PersistenceContext → @Inject migration**

- [ ] T027 [US2] Replace @PersistenceContext with @Inject for EntityManager in src/main/java/com/demo/repository/jpa/JpaPetRepositoryImpl.java:44

**BR-OP-07 & BR-OP-08: transaction-to-quarkus @Transactional additions**

- [ ] T028 [P] [US2] Add @Transactional to data modification methods in src/main/java/com/demo/repository/jpa/JpaPetRepositoryImpl.java:80
- [ ] T029 [P] [US2] Add @Transactional to data modification methods in src/main/java/com/demo/repository/springdatajpa/SpringDataPetRepositoryImpl.java:42

**Core Pet functionality**

- [ ] T030 [P] [US2] Create PetService with business logic in src/main/java/com/demo/service/PetService.java
- [ ] T031 [US2] Implement PetResource REST endpoints in src/main/java/com/demo/rest/PetResource.java (depends on T025-T030)
- [ ] T032 [US2] Add validation and error handling for Pet operations including owner associations
- [ ] T033 [US2] Add logging for Pet management operations

**Checkpoint**: At this point, User Stories 1 AND 2 should both work independently

---

## Phase 5: Polish & Cross-Cutting Concerns

**Purpose**: Validation and improvements that ensure migration quality

- [ ] T034 [P] Run comprehensive integration tests covering all Owner/Pet operations
- [ ] T035 [P] Validate REST API contract matches legacy system exactly
- [ ] T036 Performance testing to ensure migrated system matches legacy performance within 10%
- [ ] T037 [P] Update quickstart.md validation scenarios and run full validation
- [ ] T038 Security review to ensure no new vulnerabilities introduced
- [ ] T039 Documentation updates reflecting Quarkus-specific configurations

**Checkpoint**: All 8 MTA findings resolved, system validated against legacy behavior

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: All depend on Foundational phase completion
  - User stories can then proceed in parallel (if staffed)
  - Both User Story 1 and 2 are P1 priority but can be implemented in parallel
- **Polish (Final Phase)**: Depends on all user stories being complete

### User Story Dependencies

- **User Story 1 (Owner)**: Can start after Foundational (Phase 2) - No dependencies on other stories
- **User Story 2 (Pet)**: Can start after Foundational (Phase 2) - Should integrate with US1 but be independently testable

### Within Each User Story

- Tests (if included) MUST be written and FAIL before implementation
- Model migration tasks (T014-T015, T025-T026) can run in parallel
- Repository migration tasks should run before service implementation
- Story complete before moving to polish phase

### Parallel Opportunities

- All Setup tasks marked [P] can run in parallel
- All Foundational tasks marked [P] can run in parallel (within Phase 2)
- User Story 1 tasks T014-T017 can run in parallel (different files)
- User Story 2 tasks T025-T029 can run in parallel (different files)
- Model migrations for both stories can run in parallel

---

## Implementation Strategy

### MVP First (Both User Stories)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational (CRITICAL - blocks all stories)
3. Complete Phase 3: User Story 1 (Owner Management)
4. Complete Phase 4: User Story 2 (Pet Management)
5. **STOP and VALIDATE**: Test both stories together to ensure integration works
6. Complete Phase 5: Polish & validation

### MTA Findings Resolution Order

1. **Phase 3**: Address BR-OP-01, BR-OP-02, BR-OP-03, BR-OP-05, BR-OP-06 (Owner-related)
2. **Phase 4**: Address BR-OP-04, BR-OP-07, BR-OP-08 (Pet-related)
3. **Phase 5**: Validate all 8 findings are resolved

### Citation Requirements (§17.2)

Each implementation task in Phases 3-4 must include:
- Task ID (e.g., T014)
- Brief reference: B-OWNER-PET-1
- Legacy locus from brief (e.g., `/projects/.derived/legacy-at-3/src/main/java/...`)

---

## Notes

- **[P] tasks** = different files, no dependencies
- **[Story] label** maps task to specific user story for traceability
- Each user story should be independently completable and testable
- Verify tests fail before implementing
- All 8 MTA findings must be addressed with proper citations
- Commit after each task or logical group
- Stop at any checkpoint to validate story independently
- Avoid: vague tasks, same file conflicts, cross-story dependencies that break independence
