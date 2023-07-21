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
package org.moeaframework.algorithm.pisa;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.moeaframework.algorithm.pisa.installer.PISAInstaller;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Settings;
import org.moeaframework.core.spi.ProblemFactory;
import org.moeaframework.core.spi.ProviderNotFoundException;
import org.moeaframework.util.TypedProperties;
import org.moeaframework.util.io.FileUtils;

/**
 * Tests the {@link PISAAlgorithms} class using the old, static configuration
 * files.
 */
public class PISAAlgorithmsStaticConfigurationTest {
	
	/**
	 * The PISA installer implementation.
	 */
	private PISAInstaller installer;
	
	/**
	 * The real encoded test problem.
	 */
	private Problem realProblem;
	
	/**
	 * The properties for controlling the test problems.
	 */
	private TypedProperties properties;
	
	/**
	 * Creates the shared problem.
	 * 
	 * @throws IOException if an I/O error occurred
	 */
	@Before
	public void setUp() throws IOException {
		installer = PISAInstaller.getInstaller();
		
		realProblem = ProblemFactory.getInstance().getProblem("DTLZ2_2");
		properties = new TypedProperties();
		
		properties.setInt("populationSize", 100);
		properties.setInt("maxEvaluations", 1000);
	}

	/**
	 * Removes references to shared objects so they can be garbage collected.
	 */
	@After
	public void tearDown() {
		realProblem = null;
		properties = null;
	}
	
	private void run(String name, Problem problem) throws IOException {
		try {
			Settings.PROPERTIES.setString("org.moeaframework.algorithm.pisa.algorithms", name);
			Settings.PROPERTIES.setString("org.moeaframework.algorithm.pisa." + name + ".command", installer.getCommand(name));
			Settings.PROPERTIES.setString("org.moeaframework.algorithm.pisa." + name + ".configuration", installer.getDefaultParameterFile(name).getAbsolutePath());
			
			test(new PISAAlgorithms().getAlgorithm(name + "-pisa", properties, problem));
		} finally {
			Settings.PROPERTIES.remove("org.moeaframework.algorithm.pisa.algorithms");
			Settings.PROPERTIES.remove("org.moeaframework.algorithm.pisa." + name + ".command");
			Settings.PROPERTIES.remove("org.moeaframework.algorithm.pisa." + name + ".configuration");
		}
	}
	
	@Test(expected = ProviderNotFoundException.class)
	public void testConstraints() throws IOException {
		run("hype", ProblemFactory.getInstance().getProblem("CF1"));
	}
	
	@Test
	public void testECEA() throws IOException {
		properties.setInt("maxIterations", 100);
		run("ecea", realProblem);
	}
	
	@Test
	@Ignore("possible memory leak, favor built-in implementation")
	public void testEpsilonMOEA() throws IOException {
		properties.setInt("mu", 2);
		properties.setInt("lambda", 2);
		run("epsmoea", realProblem);
	}
	
	@Test
	public void testFEMO() throws IOException {
		run("femo", realProblem);
	}
	
	@Test
	public void testHypE() throws IOException {
		run("hype", realProblem);
	}
	
	@Test
	@Ignore("this works, but is very time consuming")
	public void testIBEA() throws IOException {
		run("ibea", realProblem);
	}
	
	@Test
	@Ignore("need to make design file an argument, otherwise can't parallelize")
	public void testMSOPS() throws IOException {
		FileUtils.copy(
				new File("./pisa/msops_win/msops_weights/" + 
						properties.getString("populationSize", "") + 
						"/space-filling-" +
						realProblem.getNumberOfObjectives() + "dim.des"), 
				new File("space-filling-" +
						realProblem.getNumberOfObjectives() + "dim.des"));
		run("msops", realProblem);
	}

	@Test
	public void testNSGAII() throws IOException {
		run("nsga2", realProblem);
	}
	
	@Test
	@Ignore("crashes with fp != NULL assertion on line 135 in semo_io.c, but recompiling from source fixes this bug")
	public void testSEMO() throws IOException {
		properties.setInt("populationSize", 1);
		properties.setString("operator", "PM");
		run("semo", realProblem);
	}
	
	@Test
	public void testSEMO2() throws IOException {
		run("semo2", realProblem);
	}
	
	@Test
	@Ignore("requires unrar")
	public void testSHV() throws IOException {
		run("shv", realProblem);
	}
	
	@Test
	public void testSIBEA() throws IOException {
		run("sibea", realProblem);
	}
	
	@Test
	@Ignore("this works, but is very time consuming")
	public void testSPAM() throws IOException {
		run("spam", realProblem);
	}
	
	@Test
	public void testSPEA2() throws IOException {
		run("spea2", realProblem);
	}
	
	@Test
	public void testUnaryOperators() throws IOException {
		properties.setString("operator", "PM");
		run("semo2", realProblem);
	}
	
	@Test
	public void testMultiparentOperators() throws IOException {
		properties.setInt("mu", 500);
		properties.setString("operator", "PCX");
		run("semo2", realProblem);
	}
	
	/**
	 * Tests if the given algorithm operates correctly.
	 * 
	 * @param algorithm the algorithm
	 */
	private void test(Algorithm algorithm) {
		Assert.assertTrue(algorithm instanceof PISAAlgorithm);
		Assert.assertEquals(0, algorithm.getNumberOfEvaluations());
		Assert.assertEquals(0, algorithm.getResult().size());
		Assert.assertFalse(algorithm.isTerminated());
		
		while (algorithm.getNumberOfEvaluations() < 1000) {
			algorithm.step();
		}
		
		algorithm.terminate();
		
		Assert.assertEquals(1000, algorithm.getNumberOfEvaluations());
		Assert.assertTrue(algorithm.getResult().size() > 0);
		Assert.assertTrue(algorithm.isTerminated());
	}
	
}
