package com.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Characterization tests targeting Owner/Pet/BaseEntity NO_COVERAGE + survivor mutants
 * for G-1 dual-denominator stringency (Lead:raise-coverage-before-m5-accept).
 */
public class OwnerCoverageTest {

    @Test
    public void shouldExposeOwnerContactFieldsAndToString() {
        Owner owner = new Owner();
        owner.setId(42);
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");

        assertEquals("110 W. Liberty St.", owner.getAddress());
        assertEquals("Madison", owner.getCity());
        assertEquals("6085551023", owner.getTelephone());
        assertEquals("George", owner.getFirstName());
        assertEquals("Franklin", owner.getLastName());
        assertEquals(Integer.valueOf(42), owner.getId());
        assertFalse(owner.isNew());

        String s = owner.toString();
        assertTrue(s.contains("George"));
        assertTrue(s.contains("Franklin"));
        assertTrue(s.contains("Madison"));
        assertTrue(s.contains("6085551023"));
    }

    @Test
    public void shouldTreatNullIdAsNew() {
        Owner owner = new Owner();
        assertNull(owner.getId());
        assertTrue(owner.isNew());
        owner.setId(1);
        assertFalse(owner.isNew());
    }

    @Test
    public void addPetShouldSetOwnerBidirectionally() {
        Owner owner = new Owner();
        owner.setFirstName("A");
        owner.setLastName("B");
        Pet pet = new Pet();
        pet.setName("Leo");
        // Do NOT pre-set owner — kills VoidMethodCallMutator on pet.setOwner(this)
        owner.addPet(pet);
        assertSame(owner, pet.getOwner());
        assertTrue(owner.getPets().contains(pet));
    }

    @Test
    public void getPetIgnoreNewSkipsUnpersistedPets() {
        Owner owner = new Owner();
        Pet newborn = new Pet();
        newborn.setName("Ghost");
        // id null => isNew()
        owner.addPet(newborn);
        assertNull(owner.getPet("Ghost", true), "ignoreNew=true must skip new pets");
        assertEquals(newborn, owner.getPet("Ghost", false));
    }

    @Test
    public void namedEntityToStringReturnsName() {
        PetType type = new PetType();
        type.setName("cat");
        assertEquals("cat", type.toString());
    }
}
