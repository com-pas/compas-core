// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.repository;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

/**
 * Extension of Hibernate's PostgreSql dialect to support the XML type
 */
public class PostgreSqlXmlDialect extends PostgreSQL95Dialect {

    public PostgreSqlXmlDialect() {
        super();
        registerColumnType(Types.SQLXML, "XML");
        registerHibernateType(Types.OTHER, "pg-uuid");
    }
}
