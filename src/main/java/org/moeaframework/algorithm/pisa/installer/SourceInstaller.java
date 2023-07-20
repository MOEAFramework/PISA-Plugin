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
import org.moeaframework.util.io.RedirectStream;

/**
 * Downloads, compiles, and configures the PISA selector from source code.  This is experimental and may
 * not work on all systems.
 */
public class SourceInstaller extends AbstractPISAInstaller {
	
	private static final String ROOT = "https://github.com/MOEAFramework/PISA/raw/main/selectors/source";
	
	public SourceInstaller() {
		super();
		
		register("ecea", ROOT + "ecea_c_source.tar.gz");
		register("epsmoea", ROOT + "epsmoea_c_source.tar.gz");
		register("femo", ROOT + "femo_c_source.tar.gz");
		register("hype", ROOT + "hype_c_source.tar.gz");
		register("ibea", ROOT + "ibea_c_source.tar.gz");
		register("msops", ROOT + "msops_c_source.tar.gz");
		register("nsga2", ROOT + "nsga2_c_source.tar.gz");
		register("semo2", ROOT + "semo2_c_source.tar.gz");
		register("semo", ROOT + "semo_c_source.tar");
		register("shv", ROOT + "shv_c_source.rar");
		//register("sibea", ROOT + "sibea_source.zip"); // This is Java, so no Makefile provided
		register("spam", ROOT + "spam_c_source.tar.gz");
		register("spea2", ROOT + "spea2_c_source.tar.gz");
	}
	
	@Override
	public boolean isOSSupported() {
		return true;
	}

	@Override
	public void postInstall(File installPath) throws IOException {
		System.out.println("Running make");
		
		try {
			Process process = new ProcessBuilder("make").directory(installPath).start();
			RedirectStream.redirect(process.getInputStream(), System.out);
			RedirectStream.redirect(process.getErrorStream(), System.err);
			
			if (process.waitFor() != 0) {
				throw new IOException("make exited with an error code (" + process.exitValue() + ")");
			}
		} catch (InterruptedException e) {
			throw new IOException("make interrupted", e);
		}
	}
	
	public static void main(String[] args) throws IOException {
		new SourceInstaller().installAll();
	}

}
