package com.demo.repository.jpa;

import com.demo.model.Visit;
import com.demo.repository.VisitRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * HARVEST of JpaVisitRepositoryImpl — @PersistenceContext→@Inject; @Profile removed (CDI).
 * Locus: legacy-at-3 .../repository/jpa/JpaVisitRepositoryImpl.java
 */
@ApplicationScoped
public class JpaVisitRepositoryImpl implements VisitRepository {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public void save(Visit visit) {
        if (visit.getId() == null) {
            em.persist(visit);
        } else {
            em.merge(visit);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Visit> findByPetId(Integer petId) {
        Query query = em.createQuery("SELECT v FROM Visit v where v.pet.id= :id");
        query.setParameter("id", petId);
        return query.getResultList();
    }

    @Override
    public Visit findById(int id) {
        return em.find(Visit.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Visit> findAll() {
        return em.createQuery("SELECT v FROM Visit v").getResultList();
    }

    @Override
    @Transactional
    public void delete(Visit visit) {
        em.remove(em.contains(visit) ? visit : em.merge(visit));
    }
}
