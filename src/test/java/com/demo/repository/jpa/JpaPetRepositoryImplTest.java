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
 * Unit tests for {@link JpaPetRepositoryImpl}.
 * Tests BR-OP-04: @PersistenceContext → @Inject migration
 * Tests BR-OP-07: @Transactional on data modification methods
 */
public class JpaPetRepositoryImplTest {

    @Test
    public void shouldUseInjectForEntityManager() {
        // BR-OP-04: Verify EntityManager is injected via @Inject instead of @PersistenceContext
        try {
            java.lang.reflect.Field emField = JpaPetRepositoryImpl.class.getDeclaredField("em");
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
            Class<?> repositoryClass = JpaPetRepositoryImpl.class;
            
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
            
        } catch (Exception e) {
            fail("Failed to verify jakarta persistence usage: " + e.getMessage());
        }
    }

    @Test
    public void shouldHaveTransactionalOnSaveMethod() {
        // BR-OP-07: Verify @Transactional is present on save method
        try {
            var saveMethod = JpaPetRepositoryImpl.class.getMethod("save", com.demo.model.Pet.class);
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
            var deleteMethod = JpaPetRepositoryImpl.class.getMethod("delete", com.demo.model.Pet.class);
            assertTrue(deleteMethod.isAnnotationPresent(jakarta.transaction.Transactional.class),
                "delete method should be annotated with @Transactional");
        } catch (NoSuchMethodException e) {
            fail("delete method should exist: " + e.getMessage());
        }
    }

    @Test
    public void shouldBeAnnotatedWithApplicationScoped() {
        // Verify repository is a proper CDI bean
        assertTrue(JpaPetRepositoryImpl.class.isAnnotationPresent(jakarta.enterprise.context.ApplicationScoped.class),
            "Repository should be annotated with @ApplicationScoped for CDI");
    }

    @Test
    public void shouldImplementPetRepository() {
        // Verify the class implements the correct interface
        assertTrue(com.demo.repository.PetRepository.class.isAssignableFrom(JpaPetRepositoryImpl.class),
            "JpaPetRepositoryImpl should implement PetRepository");
    }

    @Test
    public void shouldUseCorrectPersistenceImports() {
        // BR-OP-01 & BR-OP-04: Verify jakarta.* imports are used
        try {
            Class<?> clazz = JpaPetRepositoryImpl.class;
            
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

    @Test
    public void shouldFindPetTypes() {
        // Test findPetTypes method functionality
        // This is a structural test to ensure the method exists
        try {
            var findPetTypesMethod = JpaPetRepositoryImpl.class.getMethod("findPetTypes");
            assertNotNull(findPetTypesMethod, "findPetTypes method should exist");
        } catch (NoSuchMethodException e) {
            fail("findPetTypes method should exist: " + e.getMessage());
        }
    }

    @Test
    public void shouldFindPetById() {
        // Test findById method functionality
        // This is a structural test to ensure the method exists
        try {
            var findByIdMethod = JpaPetRepositoryImpl.class.getMethod("findById", int.class);
            assertNotNull(findByIdMethod, "findById method should exist");
        } catch (NoSuchMethodException e) {
            fail("findById method should exist: " + e.getMessage());
        }
    }
}
