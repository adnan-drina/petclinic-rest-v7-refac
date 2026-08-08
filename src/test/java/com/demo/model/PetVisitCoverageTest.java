package com.demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PetVisitCoverageTest {

    @Test
    public void addVisitShouldLinkPetAndAppearInCollection() {
        Pet pet = new Pet();
        pet.setName("Leo");
        Visit visit = new Visit();
        LocalDate fixed = LocalDate.of(2013, 1, 1);
        visit.setDate(fixed);
        visit.setDescription("rabies shot");

        pet.addVisit(visit);

        assertSame(pet, visit.getPet());
        assertEquals(1, pet.getVisits().size());
        assertTrue(pet.getVisits().contains(visit));
        assertEquals(fixed, visit.getDate());
        assertEquals("rabies shot", visit.getDescription());
    }

    @Test
    public void visitNoArgConstructorPinsLocalDateNow() {
        LocalDate before = LocalDate.now();
        Visit visit = new Visit();
        LocalDate after = LocalDate.now();
        assertNotNull(visit.getDate());
        assertFalse(visit.getDate().isBefore(before));
        assertFalse(visit.getDate().isAfter(after));
    }
}
