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

import java.io.IOException;
import org.apache.commons.lang3.SystemUtils;

public class WindowsInstaller extends AbstractPISAInstaller {

	private static final String ROOT = "https://github.com/MOEAFramework/PISA/raw/main/selectors/win/";
	
	public WindowsInstaller() {
		super();
		
		register("ecea", ROOT + "ecea_win.zip");
		register("epsmoea", ROOT + "epsmoea_win.tar.gz");
		register("femo", ROOT + "femo_win.zip");
		register("hype", ROOT + "hype_win.zip");
		register("ibea", ROOT + "ibea_win.zip");
		register("msops", ROOT + "msops_win.zip");
		register("nsga2", ROOT + "nsga2_win.zip");
		register("semo2", ROOT + "semo2_win.zip");
		register("semo", ROOT + "semo_win.zip");
		register("shv", ROOT + "shv_win.zip");
		register("sibea", ROOT + "sibea_binary.zip");
		register("spam", ROOT + "spam_win.zip");
		register("spea2", ROOT + "spea2_win.zip");
	}
	
	@Override
	public boolean isOSSupported() {
		return SystemUtils.IS_OS_WINDOWS;
	}

	public static void main(String[] args) throws IOException {
		new WindowsInstaller().installAll();
	}

}
