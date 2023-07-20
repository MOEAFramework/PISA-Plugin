# PISA-Plugin

Enables using [PISA - A Platform and Programming Language Independent Interface for Search Algorithms](http://sop.tik.ee.ethz.ch/pisa) selectors withing the MOEA Framework.  If using any of the PISA
algorithms, please cite:

> Bleuler, Stefan, Marco Laumanns, Lothar Thiele and Eckart Zitzler. “PISA: A Platform and Programming Language Independent Interface for Search Algorithms.” International Conference on Evolutionary Multi-Criterion Optimization (2003).

## Installation

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>org.moeaframework</groupId>
    <artifactId>pisa-plugin</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

Once this plugin is added as a Maven dependency, you can reference PISA selectors as you would
any other:

```java

NondominatedPopulation result = new Executor()
		.withProblem("DTLZ2_3")
		.withAlgorithm("HYPE")
		.withMaxEvaluations(10000)
		.run();
```

One key difference, however, is that PISA selectors are third-party executables.  The first time you
attempt to use an algorithm, it will be downloaded from [our mirror](https://github.com/MOEAFramework/PISA/).
Binaries are pre-compiled for Windows and Linux.

### Install All

Instead of installing each selector when it's first used, you may also install all at once by running:

```bash

java -cp "lib/*" org.moeaframework.algorithm.pisa.installer.PISAInstaller install
```

### Blocking Downloads

If you want to prevent the automatic download of PISA selectors, add the following line to `moeaframework.properties`:

```
org.moeaframework.algorithm.pisa.allow_install = false
```

### Building from Source

We can also build the PISA selector from source using:

```bash

java -cp "lib/*" org.moeaframework.algorithm.pisa.installer.PISAInstaller install_source
```

Please ensure `make`, `gcc`, `unrar`, `unzip`, and `tar` are installed on the system.

## Limitations

Some algorithms are implemented in both PISA and the MOEA Framework.  If such cases, you can request the PISA
version by appending `-PISA` to the name, such as `NSGA2-PISA`.

## License

Copyright 2009-2023 David Hadka and other contributors.  All rights reserved.

The MOEA Framework is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

The MOEA Framework is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
License for more details.

You should have received a copy of the GNU Lesser General Public License
along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
