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
import org.moeaframework.core.FrameworkException;
import org.moeaframework.util.io.RedirectStream;

public class SourceInstaller implements PISAInstaller {
	
	private static final Map<String, URL> files = new HashMap<String, URL>();
	
	private static final String root = "https://github.com/MOEAFramework/PISA/raw/main/selectors/source";
	
	private static void register(String algorithm, String remoteFile) {
		try {
			files.put(getCanonicalName(algorithm), new URL(root + "/" + remoteFile));
		} catch (MalformedURLException e) {
			throw new FrameworkException(e);
		}
	}
	
	private static String getCanonicalName(String algorithm) {
		return algorithm.toLowerCase();
	}
	
	static {
		register("ecea", "ecea_c_source.tar.gz");
		register("epsmoea", "epsmoea_c_source.tar.gz");
		register("femo", "femo_c_source.tar.gz");
		register("hype", "hype_c_source.tar.gz");
		register("ibea", "ibea_c_source.tar.gz");
		register("msops", "msops_c_source.tar.gz");
		register("nsga2", "nsga2_c_source.tar.gz");
		register("semo2", "semo2_c_source.tar.gz");
		register("semo", "semo_c_source.tar");
		register("shv", "shv_c_source.rar");
		//register("sibea", "sibea_source.zip"); // This is Java, no Makefile provided
		register("spam", "spam_c_source.tar.gz");
		register("spea2", "spea2_c_source.tar.gz");
	}

	@Override
	public void install(String algorithm) throws IOException {
		if (!allowInstall()) {
			throw new FrameworkException("Installation of PISA selectors is not enabled");
		}

		URL downloadURL = getDownloadURL(algorithm);
		
		if (downloadURL == null) {
			throw new FrameworkException(algorithm + " is not the name of a recognized PISA selector");
		}
		
		File localFile = new File(FilenameUtils.getName(downloadURL.getPath()));
		System.out.println("Downloading " + algorithm + " to " + localFile.getAbsolutePath());
		FileUtils.copyURLToFile(downloadURL, localFile);
		
		File installPath = getInstallPath(algorithm);
		System.out.println("Extracting " + localFile.getAbsolutePath() + " to " + installPath.getAbsolutePath());
		InstallerUtils.extractFile(localFile, installPath);
		
		runMake(installPath);
		
		System.out.println("Deleting " + localFile.getAbsolutePath());
		localFile.delete();
	}
	
	@Override
	public File getInstallPath(String algorithm) {
		return new File(getInstallRoot(), getCanonicalName(algorithm));
	}
	
	@Override
	public String getCommand(String algorithm) {
		File executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm) + ".exe");
		
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
		for (String algorithm : files.keySet()) {
			install(algorithm);
		}
	}
	
	public static void runMake(File folder) throws IOException {
		System.out.println("Running make");
		
		try {
			Process process = new ProcessBuilder("make").directory(folder).start();
			RedirectStream.redirect(process.getInputStream(), System.out);
			RedirectStream.redirect(process.getErrorStream(), System.err);
			
			if (process.waitFor() != 0) {
				throw new IOException("make exited with an error code (" + process.exitValue() + ")");
			}
		} catch (InterruptedException e) {
			throw new IOException("make interrupted", e);
		}
	}
	
	private URL getDownloadURL(String algorithm) {
		return files.get(getCanonicalName(algorithm));
	}
	
	public static void main(String[] args) throws IOException {
		new SourceInstaller().installAll();
	}

}
