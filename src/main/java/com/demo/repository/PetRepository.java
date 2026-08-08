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
package com.demo.repository;

import com.demo.model.Pet;
import com.demo.model.PetType;

import java.util.Collection;
import java.util.List;

/**
 * Repository interface for Pet entities.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
public interface PetRepository {

    /**
     * Retrieves a {@link Pet} with the supplied <code>id</code>.
     */
    Pet findById(int id);

    /**
     * Loads all {@link Pet Pets} from the data store.
     */
    Collection<Pet> findAll();

    /**
     * Retrieves a list of all {@link PetType PetTypes} from the data store.
     */
    List<PetType> findPetTypes();

    /**
     * Saves an {@link Pet} to the data store, either inserting or updating it.
     */
    void save(Pet pet);

    /**
     * Deletes an {@link Pet} from the data store.
     */
    void delete(Pet pet);
}
