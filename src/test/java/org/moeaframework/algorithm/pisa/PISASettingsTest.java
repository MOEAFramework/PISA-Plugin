/* Copyright 2009-2024 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.moeaframework.algorithm.pisa;

import org.junit.Assert;
import org.junit.Test;

public class PISASettingsTest {
	
	@Test
	public void testPISAAlgorithms() {
		Assert.assertTrue(PISASettings.getPISAAlgorithms().length >= 0);
	}

	@Test
	public void testPISAPollRate() {
		Assert.assertTrue(PISASettings.getPISAPollRate() >= 0);
	}

	@Test
	public void testPISACommand() {
		for (String algorithm : PISASettings.getPISAAlgorithms()) {
			Assert.assertNotNull(PISASettings.getPISACommand(algorithm));
		}
	}		

	@Test
	public void testPISAConfiguration() {
		for (String algorithm : PISASettings.getPISAAlgorithms()) {
			Assert.assertNotNull(PISASettings.getPISAConfiguration(algorithm));
		}
	}
	
	@Test
	public void testPISAParameters() {
		for (String algorithm : PISASettings.getPISAAlgorithms()) {
			for (String parameter : PISASettings.getPISAParameters(algorithm)) {
				Assert.assertNotNull(PISASettings.getPISAParameterDefaultValue(algorithm, parameter));
			}
		}
	}

}
