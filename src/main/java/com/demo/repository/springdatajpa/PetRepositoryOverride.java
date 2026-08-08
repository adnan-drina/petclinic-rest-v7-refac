package com.demo.repository.springdatajpa;

import com.demo.model.Pet;

/**
 * Extension interface for PetRepository to override default Spring Data JPA behavior.
 */
public interface PetRepositoryOverride {
    void delete(Pet pet);
}
