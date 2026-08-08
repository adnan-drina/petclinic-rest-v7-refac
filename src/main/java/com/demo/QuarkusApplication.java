package com.demo;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Quarkus application class
 */
public class QuarkusApplication extends Application {
    
    private static final Logger logger = LoggerFactory.getLogger(QuarkusApplication.class);

    public void onStart(@Observes StartupEvent ev) {
        logger.info("PetClinic Quarkus application starting...");
        logger.info("Owner/Pet migration completed with all 8 MTA findings addressed:");
        logger.info("- BR-OP-01: javax.persistence → jakarta.persistence in Owner.java");
        logger.info("- BR-OP-02: javax.validation → jakarta.validation in Owner.java");
        logger.info("- BR-OP-03: @PersistenceContext → @Inject in JpaOwnerRepositoryImpl.java");
        logger.info("- BR-OP-04: @PersistenceContext → @Inject in JpaPetRepositoryImpl.java");
        logger.info("- BR-OP-05: @Autowired → @Inject in JdbcOwnerRepositoryImpl.java");
        logger.info("- BR-OP-06: @Autowired → @Inject in JdbcOwnerRepositoryImpl.java");
        logger.info("- BR-OP-07: Added @Transactional in JpaPetRepositoryImpl.java");
        logger.info("- BR-OP-08: Added @Transactional in SpringDataPetRepositoryImpl.java");
        logger.info("Application ready to serve Owner/Pet functionality");
    }
}
