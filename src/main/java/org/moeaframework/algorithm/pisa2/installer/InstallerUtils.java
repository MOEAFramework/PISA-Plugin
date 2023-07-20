package org.moeaframework.algorithm.pisa2.installer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.moeaframework.core.FrameworkException;
import org.moeaframework.util.io.RedirectStream;

public class InstallerUtils {
	
	private InstallerUtils() {
		super();
	}
	
	public static void extractFile(File zipFile, File destinationPath) throws IOException {
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
	
	public static String[] buildExtractCommand(File zipFile, File destinationPath) {
		String extension = FilenameUtils.getExtension(zipFile.getPath());
		
		if (extension.equalsIgnoreCase("zip")) {
			if (SystemUtils.IS_OS_WINDOWS) {
				return new String[] {
						"pwsh",
						"-Command",
						"Expand-Archive -Path \"" + zipFile.getAbsolutePath() + "\" -DestinationPath \"" + destinationPath.getAbsolutePath() + "\" -Force"
				};
			} else {
				return new String[] {
						"unzip",
						zipFile.getAbsolutePath(),
						"-d",
						destinationPath.getAbsolutePath()
				};
			}
		} else if (extension.equalsIgnoreCase("tar") || extension.equalsIgnoreCase("gz") || extension.equalsIgnoreCase("tar.gz")) {
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
	
	public static void cleanupNestedFolder(File path) throws IOException {
		File[] files = path.listFiles();
		
		if (files.length == 1 && files[0].isDirectory()) {
			File nestedFolder = files[0];
			System.out.println("Cleaning up nested directory in " + path.getAbsolutePath());
			
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
