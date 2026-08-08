/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.demo.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link JpaOwnerRepositoryImpl}.
 * Tests BR-OP-03: @PersistenceContext → @Inject migration
 */
public class JpaOwnerRepositoryImplTest {

    @Test
    public void shouldUseInjectForEntityManager() {
        // BR-OP-03: Verify EntityManager is injected via @Inject instead of @PersistenceContext
        try {
            java.lang.reflect.Field emField = JpaOwnerRepositoryImpl.class.getDeclaredField("em");
            emField.setAccessible(true);
            jakarta.inject.Inject injectAnnotation = emField.getAnnotation(jakarta.inject.Inject.class);
            assertNotNull(injectAnnotation, "EntityManager field should have @Inject annotation");
        } catch (Exception e) {
            fail("Failed to verify @Inject annotation: " + e.getMessage());
        }
    }

    @Test
    public void shouldUseJakartaPersistenceApi() {
        // BR-OP-01: Verify use of jakarta.persistence.* imports
        try {
            Class<?> repositoryClass = JpaOwnerRepositoryImpl.class;
            
            // Check for jakarta.persistence.EntityManager import
            boolean hasJakartaPersistence = false;
            for (java.lang.reflect.Field field : repositoryClass.getDeclaredFields()) {
                if (field.getType().equals(EntityManager.class)) {
                    // Verify the field has jakarta.inject.Inject annotation
                    jakarta.inject.Inject injectAnnotation = field.getAnnotation(jakarta.inject.Inject.class);
                    assertNotNull(injectAnnotation, "EntityManager field should have @Inject annotation");
                    hasJakartaPersistence = true;
                    break;
                }
            }
            
            assertTrue(hasJakartaPersistence, "Repository should use jakarta.persistence.EntityManager");
            
            // Check for jakarta.transaction.Transactional
            boolean hasTransactional = repositoryClass.getMethod("save", com.demo.model.Owner.class)
                .isAnnotationPresent(jakarta.transaction.Transactional.class);
            assertTrue(hasTransactional, "save method should have @Transactional annotation");
            
        } catch (Exception e) {
            fail("Failed to verify jakarta persistence usage: " + e.getMessage());
        }
    }

    @Test
    public void shouldHaveTransactionalOnSaveMethod() {
        // BR-OP-07: Verify @Transactional is present on save method
        try {
            var saveMethod = JpaOwnerRepositoryImpl.class.getMethod("save", com.demo.model.Owner.class);
            assertTrue(saveMethod.isAnnotationPresent(jakarta.transaction.Transactional.class),
                "save method should be annotated with @Transactional");
        } catch (NoSuchMethodException e) {
            fail("save method should exist: " + e.getMessage());
        }
    }

    @Test
    public void shouldHaveTransactionalOnDeleteMethod() {
        // BR-OP-07: Verify @Transactional is present on delete method
        try {
            var deleteMethod = JpaOwnerRepositoryImpl.class.getMethod("delete", com.demo.model.Owner.class);
            assertTrue(deleteMethod.isAnnotationPresent(jakarta.transaction.Transactional.class),
                "delete method should be annotated with @Transactional");
        } catch (NoSuchMethodException e) {
            fail("delete method should exist: " + e.getMessage());
        }
    }

    @Test
    public void shouldBeAnnotatedWithApplicationScoped() {
        // Verify repository is a proper CDI bean
        assertTrue(JpaOwnerRepositoryImpl.class.isAnnotationPresent(jakarta.enterprise.context.ApplicationScoped.class),
            "Repository should be annotated with @ApplicationScoped for CDI");
    }

    @Test
    public void shouldImplementOwnerRepository() {
        // Verify the class implements the correct interface
        assertTrue(com.demo.repository.OwnerRepository.class.isAssignableFrom(JpaOwnerRepositoryImpl.class),
            "JpaOwnerRepositoryImpl should implement OwnerRepository");
    }

    @Test
    public void shouldUseCorrectPersistenceImports() {
        // BR-OP-01 & BR-OP-02: Verify jakarta.* imports are used
        try {
            Class<?> clazz = JpaOwnerRepositoryImpl.class;
            
            // Check for EntityManager from jakarta.persistence
            boolean hasEntityManager = false;
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                if (field.getType().equals(EntityManager.class)) {
                    hasEntityManager = true;
                    break;
                }
            }
            assertTrue(hasEntityManager, "Should use EntityManager from jakarta.persistence");
            
            // Check for jakarta.inject.Inject import
            jakarta.inject.Inject injectAnnotation = null;
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                injectAnnotation = field.getAnnotation(jakarta.inject.Inject.class);
                if (injectAnnotation != null) break;
            }
            assertNotNull(injectAnnotation, "Should use @Inject from jakarta.inject");
            
        } catch (Exception e) {
            fail("Failed to verify correct imports: " + e.getMessage());
        }
    }
}
