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

import java.io.IOException;
import java.util.function.BiFunction;

import org.moeaframework.algorithm.pisa.installer.PISAInstaller;
import org.moeaframework.algorithm.pisa.installer.SourceInstaller;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.core.spi.ProviderNotFoundException;
import org.moeaframework.core.spi.RegisteredAlgorithmProvider;
import org.moeaframework.util.TypedProperties;

/**
 * Algorithm provider for PISA selectors.
 */
public class PISAAlgorithms extends RegisteredAlgorithmProvider {

	/**
	 * Constructs an algorithm provider for PISA selectors.
	 */
	public PISAAlgorithms() {
		super();
		
		if (PISASettings.getPISAAlgorithms().length > 0) {
			for (String algorithm : PISASettings.getPISAAlgorithms()) {
				register(fromProblem(algorithm), algorithm, algorithm + "-pisa");
			}
		} else {
			register(fromProblem("ecea"), "ecea", "ecea-pisa");
			register(fromProblem("epsmoea"), "epsmoea", "epsmoea-pisa");
			register(fromProblem("femo"), "femo", "femo-pisa");
			register(fromProblem("hype"), "hype", "hype-pisa");
			register(fromProblem("ibea"), "ibea", "ibea-pisa");
			register(fromProblem("msops"), "msops", "msops-pisa");
			register(fromProblem("nsga2"), "nsga2", "nsga2-pisa");
			register(fromProblem("semo2"), "semo2", "semo2-pisa");
			register(fromProblem("semo"), "semo", "semo-pisa");
			register(fromProblem("shv"), "shv", "shv-pisa");
			register(fromProblem("sibea"), "sibea", "sibea-pisa");
			register(fromProblem("spam"), "spam", "spam-pisa");
			register(fromProblem("spea2"), "spea2", "spea2-pisa");
		}
	}
	
	private BiFunction<TypedProperties, Problem, Algorithm> fromProblem(String name) {
		return (TypedProperties properties, Problem problem) -> {
			try {
				Variation variation = OperatorFactory.getInstance().getVariation(null, properties, problem);
				return new PISAAlgorithm(name, problem, variation, properties);
			} catch (Exception e) {
				throw new ProviderNotFoundException(name, e);
			}
		};
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			showUsage();
			System.exit(-1);
		}
		
		if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("--help")) {
			showUsage();
		} else if (args[0].equalsIgnoreCase("clear")) {
			PISAInstaller.getInstaller().clearAll();
		} else if (args[0].equalsIgnoreCase("install")) {
			PISAInstaller.getInstaller().installAll();	
		} else if (args[0].equalsIgnoreCase("install_source")) {
			new SourceInstaller().installAll();
		}
		
		System.out.println();
		System.out.println("Done!");
	}
	
	private static void showUsage() {
		 System.out.println("Usage: java -cp \"lib/*\" " + PISAAlgorithms.class.getName() + " <arg>");
		 System.out.println();
		 System.out.println("Arguments:");
		 System.out.println("    clear            Removes all existing PISA selectors");
		 System.out.println("    help             Displays this help message");
		 System.out.println("    install          Downloads and installs all PISA selectors");
		 System.out.println("    install_source   Downloads and compiles all PISA selectors from source code (experimental)");
	}
	
}
