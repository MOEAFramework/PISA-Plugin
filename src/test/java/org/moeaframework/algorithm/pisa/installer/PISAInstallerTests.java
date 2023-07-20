/* Copyright 2009-2023 David Hadka
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
package org.moeaframework.algorithm.pisa.installer;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.Settings;

public class PISAInstallerTests {
	
	private PISAInstaller installer;
	
	@Before
	public void setUp() {
		installer = PISAInstaller.getInstaller();
	}
	
	@Test
	public void testInstall() throws IOException {
		installer.install("nsga2");
		Assert.assertTrue(installer.isInstalled("nsga2"));
	}
	
	@Test
	public void testOverwrite() throws IOException {
		installer.install("nsga2");
		installer.install("nsga2");
	}
	
	@Test
	public void testInstallDisabled() throws IOException {
		Settings.PROPERTIES.setBoolean("org.moeaframework.algorithm.pisa.allow_install", false);
		
		Assert.assertFalse(installer.allowInstall());
		Assert.assertThrows(FrameworkException.class, () -> installer.install("nsga2"));
		
		Settings.PROPERTIES.remove("org.moeaframework.algorithm.pisa.allow_install");
	}
	
}
