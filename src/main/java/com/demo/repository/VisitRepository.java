package com.demo.repository;

import com.demo.model.Visit;

import java.util.Collection;
import java.util.List;

/**
 * HARVEST of legacy VisitRepository (package rename only).
 * Locus: legacy-at-3 .../repository/VisitRepository.java
 */
public interface VisitRepository {

    void save(Visit visit);

    List<Visit> findByPetId(Integer petId);

    Visit findById(int id);

    Collection<Visit> findAll();

    void delete(Visit visit);
}
