# Legacy Configuration for PISA-Plugin

Starting with MOEA Framework 3.7 and the introduction of the PISA-Plugin extension, no configuration
is required to use PISA selectors.

However, with prior versions, one needed to edit `moeaframework.properties` to configure each
selector.  This legacy configuration is still supported for backwards compatibility.  Below is an
example of the settings:

```
## The following defines the available PISA selector names.
org.moeaframework.algorithm.pisa.algorithms = FEMO, HypE, IBEA, NSGA2, SEMO2, SVH, SPEA2

## The poll rate specifies, in milliseconds, how frequently the state file is 
## checked.
org.moeaframework.algorithm.pisa.poll = 100

## For each algorithm, define its configuration options below.  For an
## algorithm called NAME, specify the following:
##
##   1) The executable to run:
##        org.moeaframework.algorithm.pisa.NAME.command = <string>
##
##   2) The list of parameters:
##        org.moeaframework.algorithm.pisa.NAME.parameters = <list>
##      The order typically matters, so ensure the parameters are listed in the
##      same order as expected by the executable.
##
##   3) For each parameter PARAM, specify its default value:
##        org.moeaframework.algorithm.pisa.NAME.parameter.PARAM = <number>
##      It is not necessary to give a default for the seed parameter as it is
##      always set by the MOEA Framework.
##
## Note: Prior to version 1.14, the MOEA Framework only accepted a static
## version of the algorithm parameters using the option:
##   org.moeaframework.algorithm.pisa.NAME.configuration = <file>
## This is still accepted, but would mean the MOEA Framework is unable to
## change the algorithm parameters.

org.moeaframework.algorithm.pisa.ECEA.command = ./pisa/ecea_win/ecea.exe
org.moeaframework.algorithm.pisa.ECEA.parameters = seed, max_iterations
org.moeaframework.algorithm.pisa.ECEA.parameter.max_iterations = 100

org.moeaframework.algorithm.pisa.FEMO.command = ./pisa/femo_win/femo.exe
org.moeaframework.algorithm.pisa.FEMO.parameters = seed

org.moeaframework.algorithm.pisa.HypE.command = ./pisa/hype_win/hype.exe
org.moeaframework.algorithm.pisa.HypE.parameters = seed, tournament, mating, bound, nrOfSamples
org.moeaframework.algorithm.pisa.HypE.parameter.tournament = 5
org.moeaframework.algorithm.pisa.HypE.parameter.mating = 1
org.moeaframework.algorithm.pisa.HypE.parameter.bound = 2000
org.moeaframework.algorithm.pisa.HypE.parameter.nrOfSamples = -1

org.moeaframework.algorithm.pisa.IBEA.command = ./pisa/ibea_win/ibea.exe
org.moeaframework.algorithm.pisa.IBEA.parameters = seed, tournament, indicator, kappa, rho
org.moeaframework.algorithm.pisa.IBEA.parameter.tournament = 2
org.moeaframework.algorithm.pisa.IBEA.parameter.indicator = 0
org.moeaframework.algorithm.pisa.IBEA.parameter.kappa = 0.05
org.moeaframework.algorithm.pisa.IBEA.parameter.rho = 1.1

org.moeaframework.algorithm.pisa.NSGA2.command = ./pisa/nsga2_win/nsga2.exe
org.moeaframework.algorithm.pisa.NSGA2.parameters = seed, tournament
org.moeaframework.algorithm.pisa.NSGA2.parameter.tournament = 2

org.moeaframework.algorithm.pisa.SEMO2.command = ./pisa/semo2_win/semo2.exe
org.moeaframework.algorithm.pisa.SEMO2.parameters = seed

org.moeaframework.algorithm.pisa.SVH.command = ./pisa/shv_win/shv.exe
org.moeaframework.algorithm.pisa.SVH.parameters = seed, bound, junks, junksize
org.moeaframework.algorithm.pisa.SVH.parameter.bound = 2000
org.moeaframework.algorithm.pisa.SVH.parameter.junks = 100
org.moeaframework.algorithm.pisa.SVH.parameter.junksize = 100

org.moeaframework.algorithm.pisa.SIBEA.command = java -jar ./pisa/sibea_win/sibea.jar
org.moeaframework.algorithm.pisa.SIBEA.parameters = seed, bound
org.moeaframework.algorithm.pisa.SIBEA.parameter.bound = 10

org.moeaframework.algorithm.pisa.SPAM.command = ./pisa/SPAM_win/spam.exe
org.moeaframework.algorithm.pisa.SPAM.parameters = seed, bound, prefreltype
org.moeaframework.algorithm.pisa.SPAM.parameter.bound = 1.2
org.moeaframework.algorithm.pisa.SPAM.parameter.prefreltype = 6

org.moeaframework.algorithm.pisa.SPEA2.command = ./pisa/spea2_win/spea2.exe
org.moeaframework.algorithm.pisa.SPEA2.parameters = seed, tournament
org.moeaframework.algorithm.pisa.SPEA2.parameter.tournament = 2
```