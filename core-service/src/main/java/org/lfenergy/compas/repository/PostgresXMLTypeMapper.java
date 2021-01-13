// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.repository;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.*;

/**
 * XML type mapper for the PostgreSql DBM
 * Hibernate uses this class to store to or to fetch XML data from DB
 */
public class PostgresXMLTypeMapper extends SqlXmlTypeMapper {
    private final int[] SQL_TYPES = new int[] { Types.SQLXML };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor ssci, Object o) throws HibernateException, SQLException {
        byte[] rawXml = null;
        SQLXML sqlxml = null;
        try {
            sqlxml = rs.getSQLXML(names[0]);
            if(sqlxml != null) {
                rawXml = sqlxml.getBinaryStream().readAllBytes();
            }
        } catch (IOException e) {
            throw new HibernateException(e.getLocalizedMessage(),e.getCause());
        } finally {
            if (null != sqlxml) {
                sqlxml.free();
            }
        }
        return rawXml;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor ssci) throws HibernateException, SQLException {
        try {
            SQLXML xmlType = st.getConnection().createSQLXML();
            if (value != null && value instanceof byte[]) {
                xmlType.setString(new String((byte[]) value));
            }
            st.setObject(index, xmlType);
        } catch (SQLException e) {
            throw new HibernateException("Could not stored raw xml:" + e.getLocalizedMessage(),e.getCause());
        }
    }
}
