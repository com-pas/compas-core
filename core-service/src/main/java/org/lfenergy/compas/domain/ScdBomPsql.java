// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Scd model suitable for the PostgreSql DBMS
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class ScdBomPsql {
    /**
     * the raw XML content mapped to the PostgreSql XML type
     */
    @Column(name="RAW_XML")
    @Type(type="com.rte_france.rspace.compas.repository.PostgresXMLTypeMapper")
    private byte[] rawXml;
}
