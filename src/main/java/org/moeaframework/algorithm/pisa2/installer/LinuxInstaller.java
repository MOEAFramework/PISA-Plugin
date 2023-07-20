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
package org.moeaframework.algorithm.pisa2.installer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.moeaframework.core.FrameworkException;

public class LinuxInstaller implements PISAInstaller {
	
	private static final Map<String, URL> binaries = new HashMap<String, URL>();
	
	private static final String root = "https://github.com/MOEAFramework/PISA/raw/main/selectors/linux";
	
	private static void register(String algorithm, String remoteFile) {
		try {
			binaries.put(getCanonicalName(algorithm), new URL(root + "/" + remoteFile));
		} catch (MalformedURLException e) {
			throw new FrameworkException(e);
		}
	}
	
	private static String getCanonicalName(String algorithm) {
		return algorithm.toLowerCase();
	}
	
	static {
		register("ecea", "ecea_linux.tar.gz");
		register("epsmoea", "epsmoea_linux.tar.gz");
		register("femo", "femo_linux.tar.gz");
		register("hype", "hype_linux_32.tar.gz");
		register("ibea", "ibea_linux.tar.gz");
		register("msops", "msops_linux.tar.gz");
		register("nsga2", "nsga2_linux.tar.gz");
		register("semo2", "semo2_linux.tar.gz");
		register("semo", "semo_linux.tar.gz");
		register("shv", "shv_linux32.rar");
		register("sibea", "sibea_binary.zip");
		register("spam", "spam_linux_32.tar.gz");
		register("spea2", "spea2_linux.tar.gz");
	}

	@Override
	public void install(String algorithm) throws IOException {
		if (!allowInstall()) {
			throw new FrameworkException("Installation of PISA selectors is not enabled");
		}
		
		if (!SystemUtils.IS_OS_LINUX) {
			throw new FrameworkException("Only supported on Linux systems");
		}
		
		URL downloadURL = getDownloadURL(algorithm);
		
		if (downloadURL == null) {
			throw new FrameworkException(algorithm + " is not the name of a recognized PISA selector");
		}
		
		File localFile = new File(FilenameUtils.getName(downloadURL.getPath()));
		
		try {
			System.out.println("Downloading " + algorithm + " to " + localFile.getAbsolutePath());
			FileUtils.copyURLToFile(downloadURL, localFile);
			
			File installPath = getInstallPath(algorithm);
			System.out.println("Extracting " + localFile.getAbsolutePath() + " to " + installPath.getAbsolutePath());
			InstallerUtils.extractFile(localFile, installPath);
		} finally {
			System.out.println("Deleting " + localFile.getAbsolutePath());
			localFile.delete();
		}
	}
	
	@Override
	public File getInstallPath(String algorithm) {
		return new File(getInstallRoot(), getCanonicalName(algorithm));
	}
	
	@Override
	public String getCommand(String algorithm) {
		File executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm));
		
		if (executableFile.exists()) {
			return executableFile.getAbsolutePath();
		}
		
		executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm) + ".jar");
		
		if (executableFile.exists()) {
			return "java -jar \"" + executableFile.getAbsolutePath() + "\"";
		}
		
		throw new FrameworkException("No command known for " + algorithm);
	}
	
	@Override
	public void installAll() throws IOException {
		for (String algorithm : binaries.keySet()) {
			install(algorithm);
		}
	}
	
	private URL getDownloadURL(String algorithm) {
		return binaries.get(getCanonicalName(algorithm));
	}
	
	public static void main(String[] args) throws IOException {
		new LinuxInstaller().installAll();
	}

}
