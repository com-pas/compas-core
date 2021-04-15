// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.commons.MarshallerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {
	@Autowired
	private MarshallerWrapper marshallerWrapper;

	@Test
	public void contextLoads() {
		// do nothing
		Assertions.assertNotNull(marshallerWrapper);
		Assertions.assertNotNull(marshallerWrapper.getMarshaller());
	}

	@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
	})
	static class TestConfiguration {
	}
}
