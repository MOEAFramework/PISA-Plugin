# PISA-Plugin

Enables using [PISA - A Platform and Programming Language Independent Interface for Search Algorithms](http://sop.tik.ee.ethz.ch/pisa) selectors within the MOEA Framework.  If using any of the PISA
algorithms, please cite:

> Bleuler, Stefan, Marco Laumanns, Lothar Thiele and Eckart Zitzler. “PISA: A Platform and Programming Language Independent Interface for Search Algorithms.” International Conference on Evolutionary Multi-Criterion Optimization (2003).

## Installation

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>org.moeaframework</groupId>
    <artifactId>pisa-plugin</artifactId>
    <version>1.0.2</version>
</dependency>
```

Or download the JAR from the [Releases](https://github.com/MOEAFramework/PISA-Plugin/releases) into the MOEA Framework's `lib/` folder.

## Usage

Once this plugin is added, you can reference PISA selectors as you would any other algorithm:

```java

NondominatedPopulation result = new Executor()
		.withProblem("DTLZ2_3")
		.withAlgorithm("hype-pisa")
		.withMaxEvaluations(10000)
		.run();
```

One key difference, however, is that PISA selectors are third-party executables.  Precompiled binaries are
available for Windows and Linux.

The following PISA selectors are supported.  We recommend adding `-pisa` to the algorithm name to ensure
the PISA version is used.

Selector | MOEA Framework Algorithm Name
-------- | -----------------------------
ECEA     | `ecea-pisa`
EpsMOEA  | `epsmoea-pisa`
FEMO     | `femo-pisa`
Hype     | `hype-pisa`
IBEA     | `ibea-pisa`
MSOPS    | `msops-pisa`
NSGA2    | `nsga2-pisa`
SEMO     | `semo-pisa`
SEMO2    | `semo2-pisa`
SHV      | `shv-pisa`
SIBEA    | `sibea-pisa`
SPAM     | `spam-pisa`
SPAM2    | `spea2-pisa`

### Install All

Selectors are automatically downloaded and installed from our [GitHub Mirror](https://github.com/MOEAFramework/PISA/).
To avoid this setup or to support running without an internet connection, you can preinstall all selectors by running:

```bash

java -cp "lib/*" org.moeaframework.algorithm.pisa.PISAAlgorithms install
```

### Blocking Downloads

Additionally, if you don't want to allow PISA selectors to be downloaded automatically, add the following to 
`moeaframework.properties`:

```
org.moeaframework.algorithm.pisa.allow_install = false
```

### Building from Source

Precompiled binaries are provided for Windows and Linux.  Attempting to use on a different OS will try to
compile from source code.  This is experimental and is not supported on all systems.  You may also compile
all selectors by running:

```bash

java -cp "lib/*" org.moeaframework.algorithm.pisa.installer.PISAInstaller install_source
```

Compiling will require the following dependencies: `make`, `gcc`, `unrar`, `unzip`, and `tar`.


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
