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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.moeaframework.algorithm.pisa.PISASettings;
import org.moeaframework.core.FrameworkException;
import org.moeaframework.util.io.RedirectStream;

public abstract class AbstractPISAInstaller implements PISAInstaller {
	
	private final Map<String, URL> files = new HashMap<String, URL>();
		
	protected void register(String algorithm, String remoteUrl) {
		try {
			files.put(getCanonicalName(algorithm), URI.create(remoteUrl).toURL());
		} catch (MalformedURLException e) {
			throw new FrameworkException(e);
		}
	}

	@Override
	public void install(String algorithm) throws IOException {
		if (!PISASettings.getPISAAllowInstall()) {
			throw new FrameworkException("installation of PISA selectors is not enabled");
		}
		
		if (!isOSSupported()) {
			throw new FrameworkException("installer not supported on current OS (" + SystemUtils.OS_NAME + ")");
		}
		
		URL downloadURL = getDownloadURL(algorithm);
		
		if (downloadURL == null) {
			throw new FrameworkException(algorithm + " is not the name of a recognized PISA selector");
		}
		
		File localFile = new File(FilenameUtils.getName(downloadURL.getPath()));
		
		try {
			System.out.println("Installing " + algorithm + "...");
			
			System.out.println("  > Downloading " + algorithm + " to " + localFile.getAbsolutePath());
			FileUtils.copyURLToFile(downloadURL, localFile);
			
			File installPath = getInstallPath(algorithm);
			System.out.println("  > Extracting " + localFile.getAbsolutePath() + " to " + installPath.getAbsolutePath());
			extractFile(localFile, installPath);
			
			System.out.println("  > Executing post install steps (if any)");
			postInstall(algorithm, installPath);
		} finally {
			System.out.println("  > Removing downloaded file");
			localFile.delete();
		}
	}
	
	protected void postInstall(String algorithm, File installPath) throws IOException {
		// intentionally left blank
	}
	
	@Override
	public File getInstallPath(String algorithm) {
		return new File(PISASettings.getPISAInstallPath(), getCanonicalName(algorithm));
	}
	
	@Override
	public void installAll() throws IOException {
		for (String algorithm : files.keySet()) {
			install(algorithm);
		}
	}
	
	@Override
	public String getCommand(String algorithm) {
		File executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm) + ".jar");
		
		if (executableFile.exists()) {
			return "java -jar \"" + executableFile.getAbsolutePath() + "\"";
		}
		
		if (SystemUtils.IS_OS_WINDOWS) {
			executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm) + ".exe");
		} else {
			executableFile = new File(getInstallPath(algorithm), getCanonicalName(algorithm));
		}
		
		if (executableFile.exists()) {
			return executableFile.getAbsolutePath();
		}
		
		throw new FrameworkException("no executable command in " + getInstallPath(algorithm));
	}
	
	protected static String getCanonicalName(String algorithm) {
		return algorithm.toLowerCase();
	}
	
	protected URL getDownloadURL(String algorithm) {
		return files.get(getCanonicalName(algorithm));
	}
	
	protected void extractFile(File zipFile, File destinationPath) throws IOException {
		FileUtils.forceMkdir(destinationPath);
		
		try {
			Process process = new ProcessBuilder(buildExtractCommand(zipFile, destinationPath)).start();
			RedirectStream.redirect(process.getInputStream(), System.out);
			RedirectStream.redirect(process.getErrorStream(), System.err);
			
			if (process.waitFor() != 0) {
				throw new FrameworkException("Extraction exited with an error code (" + process.exitValue() + ")");
			}
		} catch (InterruptedException e) {
			throw new FrameworkException(e);
		}
		
		cleanupNestedFolder(destinationPath);
	}
	
	protected String[] buildExtractCommand(File zipFile, File destinationPath) {
		String extension = FilenameUtils.getExtension(zipFile.getPath());
		
		if (extension.equalsIgnoreCase("zip")) {
			if (SystemUtils.IS_OS_WINDOWS) {
				return new String[] {
						"pwsh",
						"-Command",
						"Expand-Archive -Path \"" + zipFile.getAbsolutePath() + "\" -DestinationPath \"" +
								destinationPath.getAbsolutePath() + "\" -Force"
				};
			} else {
				return new String[] {
						"unzip",
						zipFile.getAbsolutePath(),
						"-d",
						destinationPath.getAbsolutePath()
				};
			}
		} else if (extension.equalsIgnoreCase("tar") || extension.equalsIgnoreCase("gz") ||
				extension.equalsIgnoreCase("tar.gz")) {
			return new String[] {
					"tar",
					"-x",
					"-f",
					zipFile.getAbsolutePath(),
					"-C",
					destinationPath.getAbsolutePath()
			};
		} else if (extension.equalsIgnoreCase("rar")) {
			return new String[] {
					"unrar",
					"e",
					zipFile.getAbsolutePath(),
					destinationPath.getAbsolutePath()
			};
		} else {
			throw new FrameworkException("Unsupported archive extension " + extension);
		}
	}
	
	protected void cleanupNestedFolder(File path) throws IOException {
		File[] files = path.listFiles();
		
		if (files.length == 1 && files[0].isDirectory()) {
			File nestedFolder = files[0];
			
			for (File nestedFile : nestedFolder.listFiles()) {
				if (nestedFile.isDirectory()) {
					FileUtils.moveDirectoryToDirectory(nestedFile, path, true);
				} else {
					FileUtils.moveFileToDirectory(nestedFile, path, false);
				}
			}
			
			nestedFolder.delete();
		}
	}

}
