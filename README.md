# PISA-Plugin

Enables using [PISA - A Platform and Programming Language Independent Interface for Search Algorithms](http://sop.tik.ee.ethz.ch/pisa)
selectors within the MOEA Framework.  If using any of the PISA algorithms, please cite:

> Bleuler, Stefan, Marco Laumanns, Lothar Thiele and Eckart Zitzler. “PISA: A Platform and Programming Language Independent Interface for Search Algorithms.” International Conference on Evolutionary Multi-Criterion Optimization (2003).


## Installation

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>org.moeaframework</groupId>
    <artifactId>pisa-plugin</artifactId>
    <version>3.0.0</version>
</dependency>
```

Or download the JAR from the [Releases](https://github.com/MOEAFramework/PISA-Plugin/releases) into the MOEA
Framework's `lib/` folder.


## Supported Versions

The latest versions of the MOEA Framework and the PISA-Plugin are compatible.  If using an older version of the MOEA
Framework, use the table below to identify which version of this plugin to use.

MOEA Framework Version | Compatible PISA-Plugin Version
---------------------- | ------------------------------
**`>= 5.0`**           | **`3.0.0`**
`4.0 - 4.5`            | `2.0.0`
`3.8 - 3.11`           | `1.0.3`      
`3.7`                  | `1.0.2`
`<= 3.6`               | Not available


## Usage

Once this plugin is added, you can reference PISA selectors as you would any other algorithm:

```java
Problem problem = new DTLZ2(3);

Algorithm algorithm = AlgorithmFactory.getInstance().getAlgorithm("hype-pisa", problem);
algorithm.run(10000);

NondominatedPopulation result = algorithm.getResult();
```

One key difference, however, is that PISA selectors are third-party executables.  Precompiled binaries are available
for Windows and Linux.

The following PISA selectors are supported.  We recommend adding `-pisa` to the algorithm name to ensure the PISA
version is used.

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
java -classpath "lib/*" org.moeaframework.algorithm.pisa.PISAAlgorithms install
```

### Blocking Downloads

Additionally, if you don't want to allow PISA selectors to be downloaded automatically, add the following to 
`moeaframework.properties`:

```
org.moeaframework.algorithm.pisa.allow_install = false
```

### Building from Source

Precompiled binaries are provided for Windows and Linux.  Attempting to use on a different OS will try to compile from
source code.  This is experimental and is not supported on all systems.  You may also compile all selectors by running:

```bash
java -classpath "lib/*" org.moeaframework.algorithm.pisa.PISAAlgorithms install_source
```

Compiling will require the following dependencies: `make`, a C/C++ compiler such as `gcc`, `unrar`, `unzip`, and `tar`.

