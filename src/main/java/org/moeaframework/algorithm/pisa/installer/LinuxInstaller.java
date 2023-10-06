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

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

public class LinuxInstaller extends AbstractPISAInstaller {
	
	private static final String ROOT = "https://github.com/MOEAFramework/PISA/raw/main/selectors/linux/";
	
	public LinuxInstaller() {
		super();
		
		register("ecea", ROOT + "ecea_linux.tar.gz");
		register("epsmoea", ROOT + "epsmoea_linux.tar.gz");
		register("femo", ROOT + "femo_linux.tar.gz");
		register("hype", ROOT + "hype_linux_32.tar.gz");
		register("ibea", ROOT + "ibea_linux.tar.gz");
		register("msops", ROOT + "msops_linux.tar.gz");
		register("nsga2", ROOT + "nsga2_linux.tar.gz");
		register("semo2", ROOT + "semo2_linux.tar.gz");
		register("semo", ROOT + "semo_linux.tar.gz");
		register("shv", ROOT + "shv_linux32.rar");
		register("sibea", ROOT + "sibea_binary.zip");
		register("spam", ROOT + "spam_linux_32.tar.gz");
		register("spea2", ROOT + "spea2_linux.tar.gz");
	}
	
	@Override
	public boolean isOSSupported() {
		return SystemUtils.IS_OS_LINUX;
	}
	
	@Override
	public void postInstall(String algorithm, File installPath) throws IOException {
		File executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm));
		
		if (executableFile.exists() && !executableFile.canExecute()) {
			System.out.println("Setting executable flag on " + executableFile);
			executableFile.setExecutable(true);
		}
	}
	
	public static void main(String[] args) throws IOException {
		new LinuxInstaller().installAll();
	}

}
