/*
 * Copyright 2002-2013 the original author or authors.
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
package com.demo.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the {@link Owner} class.
 * Tests BR-OP-01 & BR-OP-02: javax→jakarta migration validation
 */
public class OwnerTest {

    @Test
    public void shouldUseJakartaPersistenceAnnotations() {
        // BR-OP-01: Verify jakarta.persistence annotations are present
        try {
            Class<?> ownerClass = Owner.class;
            
            // Check for jakarta.persistence.Entity
            assertTrue(ownerClass.isAnnotationPresent(jakarta.persistence.Entity.class),
                "Owner should be annotated with @Entity from jakarta.persistence");
            
            // Check for jakarta.persistence.Table
            jakarta.persistence.Table tableAnnotation = ownerClass.getAnnotation(jakarta.persistence.Table.class);
            assertNotNull(tableAnnotation, "Owner should have @Table annotation");
            assertEquals("owners", tableAnnotation.name(), "Table name should be 'owners'");
            
        } catch (Exception e) {
            fail("Failed to verify jakarta.persistence annotations: " + e.getMessage());
        }
    }

    @Test
    public void shouldUseJakartaValidationAnnotations() {
        // BR-OP-02: Verify jakarta.validation annotations are present
        try {
            Class<?> ownerClass = Owner.class;
            
            // Check for jakarta.validation.constraints.NotEmpty
            java.lang.reflect.Field addressField = ownerClass.getDeclaredField("address");
            jakarta.validation.constraints.NotEmpty addressNotEmpty = addressField.getAnnotation(jakarta.validation.constraints.NotEmpty.class);
            assertNotNull(addressNotEmpty, "address field should have @NotEmpty annotation from jakarta.validation");
            
            java.lang.reflect.Field cityField = ownerClass.getDeclaredField("city");
            jakarta.validation.constraints.NotEmpty cityNotEmpty = cityField.getAnnotation(jakarta.validation.constraints.NotEmpty.class);
            assertNotNull(cityNotEmpty, "city field should have @NotEmpty annotation from jakarta.validation");
            
            java.lang.reflect.Field telephoneField = ownerClass.getDeclaredField("telephone");
            jakarta.validation.constraints.NotEmpty telephoneNotEmpty = telephoneField.getAnnotation(jakarta.validation.constraints.NotEmpty.class);
            assertNotNull(telephoneNotEmpty, "telephone field should have @NotEmpty annotation from jakarta.validation");
            
        } catch (Exception e) {
            fail("Failed to verify jakarta.validation annotations: " + e.getMessage());
        }
    }

    @Test
    public void shouldValidateOwnerFields() {
        // Test basic validation using Jakarta Bean Validation
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        Owner owner = createValidOwner();
        Set<ConstraintViolation<Owner>> violations = validator.validate(owner);
        assertTrue(violations.isEmpty(), "Valid owner should pass validation");
        
        // Test empty address
        owner.setAddress("");
        violations = validator.validate(owner);
        assertFalse(violations.isEmpty(), "Owner with empty address should fail validation");
        
        // Test empty city
        owner = createValidOwner();
        owner.setCity("");
        violations = validator.validate(owner);
        assertFalse(violations.isEmpty(), "Owner with empty city should fail validation");
        
        // Test empty telephone
        owner = createValidOwner();
        owner.setTelephone("");
        violations = validator.validate(owner);
        assertFalse(violations.isEmpty(), "Owner with empty telephone should fail validation");
    }

    @Test
    public void shouldAddAndRetrievePet() {
        // Test pet management functionality
        Owner owner = createValidOwner();
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setOwner(owner);
        
        owner.addPet(pet);
        
        assertEquals(1, owner.getPets().size(), "Owner should have one pet");
        assertEquals("Buddy", owner.getPet("Buddy").getName(), "Should retrieve pet by name");
        assertEquals(pet, owner.getPet("Buddy"), "Should retrieve correct pet");
    }

    @Test
    public void shouldReturnNullWhenPetNotFound() {
        // Test pet lookup edge case
        Owner owner = createValidOwner();
        assertNull(owner.getPet("NonExistent"), "Should return null for non-existent pet");
        assertNull(owner.getPet("NonExistent", false), "Should return null for non-existent pet with ignoreNew=false");
    }

    @Test
    public void shouldHandleCaseInsensitivePetSearch() {
        // Test case-insensitive pet name search
        Owner owner = createValidOwner();
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setOwner(owner);
        
        owner.addPet(pet);
        
        // Test uppercase search
        assertEquals("Buddy", owner.getPet("BUDDY").getName(), "Should find pet with uppercase name");
        // Test mixed case search
        assertEquals("Buddy", owner.getPet("BuDdY").getName(), "Should find pet with mixed case name");
    }

    private Owner createValidOwner() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("123-456-7890");
        return owner;
    }
}
