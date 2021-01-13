// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.service;

import org.lfenergy.compas.repository.IScdSQLCrudRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Setter
@Getter
@Slf4j
public abstract class AbstractSCDService<T,R extends IScdSQLCrudRepository<T, UUID>>
        implements ISCDService<T> {

    @Autowired
    protected R repository;

    public abstract UUID getNextID(T value);
}
