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

import com.demo.model.Owner;

import java.util.Collection;

/**
 * Repository interface for Owner entities.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Thomas Risberg
 * @author Mark Fisher
 * @author Antoine Rey
 * @author Vitaliy Fedoriv
 */
public interface OwnerRepository {

    /**
     * Retrieves an {@link Owner} with the supplied <code>id</code>; also loads the {@link com.demo.model.Pet Pets} and {@link com.demo.model.Visit Visits}
     * for the corresponding owner, if not already loaded.
     */
    Owner findById(int id);

    /**
     * Loads {@link Owner Owners} from the data store by last name, returning all owners whose last name <i>starts</i> with
     * the given name; also loads the {@link com.demo.model.Pet Pets} and {@link com.demo.model.Visit Visits} for the corresponding owners, if not
     * already loaded.
     */
    Collection<Owner> findByLastName(String lastName);

    /**
     * Loads all {@link Owner Owners} from the data store; also loads the {@link com.demo.model.Pet Pets} and {@link com.demo.model.Visit Visits} for the
     * corresponding owners, if not already loaded.
     */
    Collection<Owner> findAll();

    /**
     * Saves an {@link Owner} to the data store, either inserting or updating it.
     */
    void save(Owner owner);

    /**
     * Deletes an {@link Owner} from the data store.
     */
    void delete(Owner owner);
}
