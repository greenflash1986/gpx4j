# gpx4j #

**gpx4** is a free software tool developed in Java for obtaining, managing and maintaining the information contained in the files [gpx (GPS eXchange Format](gpxIntroduction.md).

This tool is divided into two parts: [libGpx4J](libIntroduction.md) and [gpxTools](toolsIntroduction.md). [libGpx4J](libIntroduction.md) is responsible for reading and writing [gpx](gpxIntroduction.md) files and the definition of the classes that represent the information contained in the files. Instead [gpxTools](toolsIntroduction.md) is a set of tools for manipulating the data obtained through [libGpx4J](libIntroduction.md).

## License ##

**gpx4j** use the license [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) for both source code and the documentation.

## Requirements ##

The virtual machine needed to run **gpx4j** is Java 6 (is developed under Java 6 update 24).

The tool uses the following libraries:
  * [log4j 1.2.16](http://logging.apache.org/log4j/1.2/) (required): Used to create the logs.
  * [jdom 1.1.1](http://www.jdom.org/) (optional): Required if you use the classes in this package `org.casaca.gpx4j.core.driver.jdom`.
  * [JAXB 2.2.3 update 2](http://jaxb.java.net/) (optional): Required if you use the classes in this package `org.casaca.gpx4j.core.driver.jaxb`.