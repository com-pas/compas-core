// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.testhelpers;

import org.lfenergy.compas.repository.IScdSQLCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DefaultSCDSQLRepository extends IScdSQLCrudRepository<SimpleSCD, UUID> {
}
