// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.Set;

/**
 * Any NoSql-based database DAO interface
 * @param <T> Object type that hold the xml content
 * @param <ID> unique object identify
 */
@NoRepositoryBean
public interface IScdCrudRepository<T,ID> {

    <S extends T> S save(S bom);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    Set<T> findAll();
    Set<T> findAllById(Iterable<ID> ids);
    long count();
    void deleteById(ID id);
    void deleteAll();
}
