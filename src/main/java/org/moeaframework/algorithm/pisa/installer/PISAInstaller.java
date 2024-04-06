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
package org.moeaframework.algorithm.pisa.installer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.moeaframework.algorithm.pisa.PISASettings;
import org.moeaframework.core.FrameworkException;
import org.moeaframework.util.io.CommentedLineReader;

public interface PISAInstaller {
	
	public boolean isOSSupported();
		
	public void install(String algorithm) throws IOException;
	
	public void installAll() throws IOException;

	public File getInstallPath(String algorithm);
		
	public String getCommand(String algorithm);
	
	public default File getDefaultParameterFile(String algorithm) {
		return new File(getInstallPath(algorithm), algorithm + "_param.txt");
	}
	
	public default boolean isInstalled(String algorithm) {
		return getInstallPath(algorithm).exists();
	}
	
	// The order of parameters matters!
	public default Map<String, String> getDefaultParameters(String algorithm) throws FileNotFoundException, IOException {
		File defaultParameterFile = getDefaultParameterFile(algorithm);
		Map<String, String> defaultParameters = new LinkedHashMap<String, String>();
		
		try (CommentedLineReader reader = new CommentedLineReader(new FileReader(defaultParameterFile))) {
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				defaultParameters.put(tokens[0], tokens[1]);
			}
		}
		
		return defaultParameters;
	}
	
	public default void clearAll() throws IOException {
		FileUtils.deleteDirectory(PISASettings.getPISAInstallPath());
	}
	
	public static PISAInstaller getInstaller() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return new WindowsInstaller();
		} else if (SystemUtils.IS_OS_LINUX) {
			return new LinuxInstaller();
		} else {
			throw new FrameworkException("PISA selectors not supported on " + SystemUtils.OS_NAME);
			//return new SourceInstaller();
		}
	}

}
