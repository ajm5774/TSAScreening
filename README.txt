===================================================================
                        =================
                          TSA Screening
                        =================

             Andrew Mueller, Michael Guay, Khanh Ho
===================================================================

Compilation & Execution:
- The program can be compiled by the following command:
	scalac Main.java
- After compiling, the program can be run by the following command:
	scala Main
- Alternatively, the included script, tsa.sh, can be run to both
compile and execute the program.

** NOTE: Because the program is written in Scala, it is best to run
on a SE machine, where the Typesafe Stack has been set up correctly.
The shell script file provided is specifically written for SE
machines.

-------------------------------------------------------------------

Configurations:
- There is no command line argument.

- The number of security lines and passengers can be configured by
modifying NUM_LINES and NUM_PASSENGERS, respectively, in Main.scala.
The default values are 3 lines and 10 passengers.

- The rate of failure for each scanner and the document check can be
configured by modifying FAILURE_RATE in the corresponding file.
The FAILURE_RATE variable can be found in Documentcheck.scala,
BodyScan.scala, and BaggageScan.scala.

===================================================================