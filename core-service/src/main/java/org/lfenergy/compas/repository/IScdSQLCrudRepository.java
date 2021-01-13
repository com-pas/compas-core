// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * SQL-based database DAO interface
 * @param <T> Object type that hold the xml content
 * @param <ID> unique object identify
 */
@NoRepositoryBean
public interface IScdSQLCrudRepository<T, ID> extends JpaRepository<T, ID> {
}
