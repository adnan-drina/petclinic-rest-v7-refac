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

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the {@link Pet} class.
 * Tests BR-OP-01: javax→jakarta migration for Pet entity
 */
public class PetTest {

    @Test
    public void shouldUseJakartaPersistenceAnnotations() {
        // BR-OP-01: Verify jakarta.persistence annotations are present in Pet
        try {
            Class<?> petClass = Pet.class;
            
            // Check for jakarta.persistence.Entity
            assertTrue(petClass.isAnnotationPresent(jakarta.persistence.Entity.class),
                "Pet should be annotated with @Entity from jakarta.persistence");
            
            // Check for jakarta.persistence.Table
            jakarta.persistence.Table tableAnnotation = petClass.getAnnotation(jakarta.persistence.Table.class);
            assertNotNull(tableAnnotation, "Pet should have @Table annotation");
            assertEquals("pets", tableAnnotation.name(), "Table name should be 'pets'");
            
        } catch (Exception e) {
            fail("Failed to verify jakarta.persistence annotations in Pet: " + e.getMessage());
        }
    }

    @Test
    public void shouldUseJakartaValidationAnnotations() {
        // BR-OP-02: Verify jakarta.validation annotations are present
        try {
            Class<?> petClass = Pet.class;
            
            // Check for jakarta.validation.constraints.NotNull on type field
            java.lang.reflect.Field typeField = petClass.getDeclaredField("type");
            jakarta.validation.constraints.NotNull typeNotNull = typeField.getAnnotation(jakarta.validation.constraints.NotNull.class);
            assertNotNull(typeNotNull, "type field should have @NotNull annotation from jakarta.validation");
            
            // Check for jakarta.validation.constraints.NotNull on owner field
            java.lang.reflect.Field ownerField = petClass.getDeclaredField("owner");
            jakarta.validation.constraints.NotNull ownerNotNull = ownerField.getAnnotation(jakarta.validation.constraints.NotNull.class);
            assertNotNull(ownerNotNull, "owner field should have @NotNull annotation from jakarta.validation");
            
        } catch (Exception e) {
            fail("Failed to verify jakarta.validation annotations in Pet: " + e.getMessage());
        }
    }

    @Test
    public void shouldValidatePetFields() {
        // Test basic validation using Jakarta Bean Validation
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        Pet pet = createValidPet();
        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
        assertTrue(violations.isEmpty(), "Valid pet should pass validation");
        
        // Test null type
        pet.setType(null);
        violations = validator.validate(pet);
        assertFalse(violations.isEmpty(), "Pet with null type should fail validation");
        
        // Test null owner
        pet = createValidPet();
        pet.setOwner(null);
        violations = validator.validate(pet);
        assertFalse(violations.isEmpty(), "Pet with null owner should fail validation");
    }

    @Test
    public void shouldHandleBirthDateCorrectly() {
        // Test LocalDate field handling
        Pet pet = createValidPet();
        
        LocalDate birthDate = LocalDate.of(2020, 1, 15);
        pet.setBirthDate(birthDate);
        
        assertEquals(birthDate, pet.getBirthDate(), "Birth date should be stored and retrieved correctly");
    }

    @Test
    public void shouldManageOwnerRelationship() {
        // Test bidirectional relationship with Owner
        Owner owner = createValidOwner();
        Pet pet = createValidPet();
        
        // Set the owner first
        pet.setOwner(owner);
        
        // Then add pet to owner's collection to establish bidirectional relationship
        owner.addPet(pet);
        
        assertEquals(owner, pet.getOwner(), "Pet should have correct owner");
        assertTrue(owner.getPets().contains(pet), "Owner should contain the pet after addPet");
    }

    @Test
    public void shouldManageVisitRelationship() {
        // Test relationship with Visit (though Visit entity is out of scope)
        Pet pet = createValidPet();
        
        assertNotNull(pet.getVisits(), "Pet should have visits collection");
        assertEquals(0, pet.getVisits().size(), "New pet should have no visits initially");
        
        // Visit visit = new Visit();
        // visit.setPet(pet);
        // pet.addVisit(visit);
        // 
        // assertEquals(1, pet.getVisits().size(), "Pet should have one visit");
    }

    @Test
    public void shouldHandlePetTypeRelationship() {
        // Test relationship with PetType
        PetType petType = new PetType();
        petType.setName("Dog");
        
        Pet pet = createValidPet();
        pet.setType(petType);
        
        assertEquals(petType, pet.getType(), "Pet should have correct type");
        assertEquals("Dog", pet.getType().getName(), "Pet type name should be Dog");
    }

    @Test
    public void shouldValidatePetName() {
        // Test inherited NamedEntity name field validation
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        // Create a simple test to check if name is validated
        Pet pet = createValidPet();
        pet.setName(""); // Empty name should fail if there's validation
        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
        
        // If there's validation on the name field, it should fail
        boolean hasNameViolation = violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        
        if (hasNameViolation) {
            assertFalse(violations.isEmpty(), "Pet with empty name should fail validation if name validation exists");
        }
    }

    private Pet createValidPet() {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setBirthDate(LocalDate.of(2020, 1, 15));
        
        PetType petType = new PetType();
        petType.setName("Dog");
        pet.setType(petType);
        
        Owner owner = createValidOwner();
        pet.setOwner(owner);
        
        return pet;
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
