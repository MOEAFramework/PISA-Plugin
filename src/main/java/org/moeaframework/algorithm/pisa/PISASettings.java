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

import java.io.File;

import org.moeaframework.core.Settings;

public class PISASettings {
	
	/**
	 * The prefix for all PISA property keys.
	 */
	static final String KEY_PISA_PREFIX = Settings.createKey("org", "moeaframework", "algorithm", "pisa");
	
	/**
	 * The property key for the list of available PISA algorithms.
	 */
	static final String KEY_PISA_ALGORITHMS = Settings.createKey(KEY_PISA_PREFIX, "algorithms");
	
	/**
	 * The property key for the poll rate.
	 */
	static final String KEY_PISA_POLL = Settings.createKey(KEY_PISA_PREFIX, "poll");
	
	/**
	 * The property key for enabling or disabling automatic PISA installations.
	 */
	static final String KEY_PISA_ALLOW_INSTALL = Settings.createKey(KEY_PISA_PREFIX, "allow_install");
	
	/**
	 * The property key for overriding the installation path.
	 */
	static final String KEY_PISA_INSTALL_PATH = Settings.createKey(KEY_PISA_PREFIX, "install_path");
	
	private PISASettings() {
		super();
	}
	
	/**
	 * Returns the list of available PISA selectors.
	 * 
	 * @return the list of available PISA selectors
	 */
	public static String[] getPISAAlgorithms() {
		return Settings.PROPERTIES.getStringArray(KEY_PISA_ALGORITHMS, new String[0]);
	}
	
	/**
	 * Returns the poll rate, in milliseconds, for how often PISA checks the state file.
	 * 
	 * @return the poll rate, in milliseconds, for how often PISA checks the state file
	 */
	public static int getPISAPollRate() {
		return Settings.PROPERTIES.getInt(KEY_PISA_POLL, 100);
	}
	
	/**
	 * Returns {@code true} if automatic installation of PISA selectors is enabled; {@code false} otherwise.
	 * 
	 * @return {@code true} if automatic installation of PISA selectors is enabled; {@code false} otherwise
	 */
	public static boolean getPISAAllowInstall() {
		return Settings.PROPERTIES.getBoolean(KEY_PISA_ALLOW_INSTALL, true);
	}
	
	/**
	 * Returns the path to install the PISA binaries.  If relative, will be created relative to the working directory
	 * when running Java.
	 * 
	 * @return the path to install the PISA binaries
	 */
	public static File getPISAInstallPath() {
		return new File(Settings.PROPERTIES.getString(KEY_PISA_INSTALL_PATH, "pisa_binaries"));
	}
	
	/**
	 * Returns the command, invokable through {@link Runtime#exec(String)}, for starting the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @return the command, invokable through {@link Runtime#exec(String)}, for starting the PISA selector
	 */
	public static String getPISACommand(String algorithmName) {
		return Settings.PROPERTIES.getString(Settings.createKey(KEY_PISA_PREFIX, algorithmName, "command"), null);
	}
	
	/**
	 * Returns the configuration file for the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @return the configuration file for the PISA selector
	 */
	public static String getPISAConfiguration(String algorithmName) {
		return Settings.PROPERTIES.getString(Settings.createKey(KEY_PISA_PREFIX, algorithmName, "configuration"), null);
	}
	
	/**
	 * Returns the list of parameter names for the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @return the list of parameter names for the PISA selector
	 */
	public static String[] getPISAParameters(String algorithmName) {
		return Settings.PROPERTIES.getStringArray(Settings.createKey(KEY_PISA_PREFIX, algorithmName, "parameters"), new String[0]);
	}
	
	/**
	 * Returns the default value of the specified parameter for the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @param parameterName the name of the parameter
	 * @return the default value of the specified parameter for the PISA selector
	 */
	public static String getPISAParameterDefaultValue(String algorithmName, String parameterName) {
		return Settings.PROPERTIES.getString(Settings.createKey(KEY_PISA_PREFIX, algorithmName, "parameter", parameterName), null);
	}

}
