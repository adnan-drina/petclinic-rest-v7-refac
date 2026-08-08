# Owner/Pet migration brief (plan #1c — §17.2 one-bridge)

**Brief id:** `B-OWNER-PET-1`
**Slice:** PetClinic REST Owner/Pet (+ transitive closure)
**Authority:** `EXECUTION-LIVE-VALIDATION-PLAN.md` #1c; AD-S; SOLUTION-ARCHITECTURE §17.2
**Linkage:** reuse §17.2 citation triple only — **no second scheme**.
**OpenAPI:** typed descope (Architect `E-20260808T095454Z` layer A) — not in AC.
**Findings:** `measurements/live-1a-mta/mta-findings.json` (#1a live MTA)
**Harvest referent:** `/projects/.derived/legacy-at-3` (descope sha `08c8d6a5…` — see live-1b NOTES)

## Non-Goals

- Visit / Vet / Specialty groups (later slices)
- OpenAPI/Swagger UI parity (descoped; G-4 OpenAPI N/A)
- Coolstore specimen
- `/speckit.implement` (AD-S never)

## Requirements

Each requirement carries the plan #1c citation shape: **brief/AC + legacy locus + finding id**,
which maps onto §17.2 (task id · brief/story id · legacy locus) without inventing a second linkage.

### BR-OP-01
- **AC:** Close or harvest finding `javax-to-jakarta-import-00001` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `javax-to-jakarta-import-00001`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/model/Owner.java:22`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/model/Owner.java:22`
- **§17.2 triple (for IMPLEMENT):** task `M3-T01` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/model/Owner.java:22`
- **MTA message (trunc):** Replace the `javax.persistence` import statement with `jakarta.persistence`

### BR-OP-02
- **AC:** Close or harvest finding `javax-to-jakarta-import-00001` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `javax-to-jakarta-import-00001`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/model/Owner.java:23`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/model/Owner.java:23`
- **§17.2 triple (for IMPLEMENT):** task `M3-T02` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/model/Owner.java:23`
- **MTA message (trunc):** Replace the `javax.validation` import statement with `jakarta.validation`

### BR-OP-03
- **AC:** Close or harvest finding `persistence-to-quarkus-00010` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `persistence-to-quarkus-00010`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaOwnerRepositoryImpl.java:44`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaOwnerRepositoryImpl.java:44`
- **§17.2 triple (for IMPLEMENT):** task `M3-T03` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaOwnerRepositoryImpl.java:44`
- **MTA message (trunc):** Replace `@PersistenceContext` with `@Inject` for EntityManager injection in Quarkus.

In Java EE/Jakarta EE, `@PersistenceContext` was used to inject EntityMana

### BR-OP-04
- **AC:** Close or harvest finding `persistence-to-quarkus-00010` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `persistence-to-quarkus-00010`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaPetRepositoryImpl.java:44`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaPetRepositoryImpl.java:44`
- **§17.2 triple (for IMPLEMENT):** task `M3-T04` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaPetRepositoryImpl.java:44`
- **MTA message (trunc):** Replace `@PersistenceContext` with `@Inject` for EntityManager injection in Quarkus.

In Java EE/Jakarta EE, `@PersistenceContext` was used to inject EntityMana

### BR-OP-05
- **AC:** Close or harvest finding `springboot-di-to-quarkus-00003` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `springboot-di-to-quarkus-00003`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/repository/jdbc/JdbcOwnerRepositoryImpl.java:54`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jdbc/JdbcOwnerRepositoryImpl.java:54`
- **§17.2 triple (for IMPLEMENT):** task `M3-T05` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jdbc/JdbcOwnerRepositoryImpl.java:54`
- **MTA message (trunc):** Spring DI annotation usage detected. Verify each annotation against Quarkus Spring DI compatibility and CDI equivalents.

Common conversions:
- `@Autowired` -> 

### BR-OP-06
- **AC:** Close or harvest finding `springboot-di-to-quarkus-00003` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `springboot-di-to-quarkus-00003`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/repository/jdbc/JdbcOwnerRepositoryImpl.java:62`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jdbc/JdbcOwnerRepositoryImpl.java:62`
- **§17.2 triple (for IMPLEMENT):** task `M3-T06` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jdbc/JdbcOwnerRepositoryImpl.java:62`
- **MTA message (trunc):** Spring DI annotation usage detected. Verify each annotation against Quarkus Spring DI compatibility and CDI equivalents.

Common conversions:
- `@Autowired` -> 

### BR-OP-07
- **AC:** Close or harvest finding `transaction-to-quarkus-00003` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `transaction-to-quarkus-00003`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaPetRepositoryImpl.java:80`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaPetRepositoryImpl.java:80`
- **§17.2 triple (for IMPLEMENT):** task `M3-T07` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/jpa/JpaPetRepositoryImpl.java:80`
- **MTA message (trunc):** Methods that modify data using EntityManager operations (persist, merge, remove) must be annotated with `@Transactional` in Quarkus.

Ensure that methods contai

### BR-OP-08
- **AC:** Close or harvest finding `transaction-to-quarkus-00003` for Owner/Pet without inventing APIs; destination Quarkus native.
- **Brief/story:** `B-OWNER-PET-1`
- **Finding id:** `transaction-to-quarkus-00003`
- **Legacy locus (@2.x evidence):** `/projects/legacy/src/main/java/org/springframework/samples/petclinic/repository/springdatajpa/SpringDataPetRepositoryImpl.java:42`
- **Legacy locus (harvest_referent @3.x):** `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/springdatajpa/SpringDataPetRepositoryImpl.java:42`
- **§17.2 triple (for IMPLEMENT):** task `M3-T08` · brief `B-OWNER-PET-1` · locus `/projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/repository/springdatajpa/SpringDataPetRepositoryImpl.java:42`
- **MTA message (trunc):** Methods that modify data using EntityManager operations (persist, merge, remove) must be annotated with `@Transactional` in Quarkus.

Ensure that methods contai

## Plan-advance gate

| Q-* | Status |
|-----|--------|
| Q-springfox | **CLOSED** — typed OpenAPI descope; boot+serve Owner API HTTP 200 evidenced |

No open `Q-*` remains on Owner/Pet AC for this brief.

