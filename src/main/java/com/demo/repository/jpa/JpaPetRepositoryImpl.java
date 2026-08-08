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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import com.demo.model.Pet;
import com.demo.model.PetType;
import com.demo.repository.PetRepository;

import java.util.Collection;
import java.util.List;

/**
 * JPA implementation of the {@link PetRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * 
 * BR-OP-04: Migrated from @PersistenceContext to @Inject for EntityManager injection
 * BR-OP-07: Added @Transactional to data modification methods
 */
@ApplicationScoped
public class JpaPetRepositoryImpl implements PetRepository {

    /**
     * BR-OP-04: Replaced @PersistenceContext with @Inject for EntityManager injection
     */
    @Inject
    EntityManager em;

    @Override
    public List<PetType> findPetTypes() {
        return em.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name", PetType.class).getResultList();
    }

    @Override
    public Pet findById(int id) {
        return em.find(Pet.class, id);
    }

    /**
     * BR-OP-07: Added @Transactional annotation for data modification method
     */
    @Override
    @Transactional
    public void save(Pet pet) {
        if (pet.getId() == null) {
            em.persist(pet);
        } else {
            em.merge(pet);
        }
    }
    
    @Override
    public Collection<Pet> findAll() {
        return em.createQuery("SELECT pet FROM Pet pet", Pet.class).getResultList();
    }

    /**
     * BR-OP-07: Added @Transactional annotation for data modification method at line 80 equivalent
     */
    @Override
    @Transactional
    public void delete(Pet pet) {
        String petId = pet.getId().toString();
        em.createQuery("DELETE FROM Visit visit WHERE pet_id=" + petId).executeUpdate();
        em.createQuery("DELETE FROM Pet pet WHERE id=" + petId).executeUpdate();
        if (em.contains(pet)) {
            em.remove(pet);
        }
    }
}
