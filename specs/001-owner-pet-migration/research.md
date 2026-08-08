# Research: Owner/Pet Migration

**Date**: August 8, 2026

## Phase 0: Research Summary

No clarifications were required. All technical decisions were made based on:

1. **Java 21 Selection**: Required by project specification and provides latest LTS features
2. **Quarkus 3.x Platform**: Specified in project context (Red Hat BOM 3.27.3.SP1)  
3. **Migration Pattern**: Standard Spring Boot to Quarkus migration following established patterns for:
   - javax → jakarta package migration
   - @PersistenceContext → @Inject conversion
   - Spring @Autowired → CDI @Inject conversion
   - Transaction management with @Transactional

## Key Decisions

| Decision | Rationale | Alternatives Considered |
|----------|-----------|------------------------|
| Java 21 | Project specification requires Java 21, provides latest features | Java 17 (rejected - doesn't meet spec) |
| Quarkus 3.x | Red Hat BOM specified, native compilation support | Spring Boot 3.x (rejected - target is Quarkus) |
| Maintain existing structure | Minimize changes, preserve business logic | Complete refactoring (rejected - violates migration constraints) |
| REST API preservation | Must maintain client compatibility | API redesign (rejected - breaks compatibility) |

## MTA Findings Resolution Strategy

1. **javax-to-jakarta-import-00001**: Replace imports in model classes (Owner.java, Pet.java)
2. **persistence-to-quarkus-00010**: Replace @PersistenceContext with @Inject in JPA repositories
3. **springboot-di-to-quarkus-00003**: Replace @Autowired with @Inject in JDBC repositories
4. **transaction-to-quarkus-00003**: Add @Transactional to data modification methods

All findings map directly to well-established migration patterns with minimal risk.
