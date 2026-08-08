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
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import com.demo.model.Owner;
import com.demo.repository.OwnerRepository;

import java.util.Collection;

/**
 * JPA implementation of the {@link OwnerRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * 
 * BR-OP-03: Migrated from @PersistenceContext to @Inject for EntityManager injection
 */
@ApplicationScoped
public class JpaOwnerRepositoryImpl implements OwnerRepository {

    /**
     * BR-OP-03: Replaced @PersistenceContext with @Inject for EntityManager injection
     */
    @Inject
    EntityManager em;

    @Override
    public Collection<Owner> findByLastName(String lastName) {
        Query query = em.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    @Override
    public Owner findById(int id) {
        Query query = em.createQuery("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id");
        query.setParameter("id", id);
        @SuppressWarnings("unchecked")
        java.util.List<Owner> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @Transactional
    public void save(Owner owner) {
        if (owner.getId() == null) {
            em.persist(owner);
        } else {
            em.merge(owner);
        }
    }

    @Override
    public Collection<Owner> findAll() {
        Query query = em.createQuery("SELECT owner FROM Owner owner");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(Owner owner) {
        em.remove(em.contains(owner) ? owner : em.merge(owner));
    }
}
