// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {


	@Test
	public void contextLoads() {
		// do nothing
	}

	@SpringBootApplication
	static class TestConfiguration {
	}
}
