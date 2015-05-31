# gpx4j (Copied from <https://code.google.com/p/gpx4j/>)

## English
**gpx4j** is a free software tool developed in Java for obtaining, managing and
maintaining the information contained in the files gpx (GPS eXchange Format).

This tool is divided into two parts: libGpx4J and gpxTools. libGpx4J is
responsible for reading and writing gpx files and the definition of the
classes that represent the information contained in the files. Instead
gpxTools is a set of tools for manipulating the data obtained through libGpx4J.

Visit the wiki for further information. 

## Spanish
**gpx4j** es una herramienta de software libre desarrolladaen Java para la obtención,
manejo y persistencia de la información contenida en los ficheros gpx (Formato
de intercambio GPS).

Esta herramienta se divide en dos partes: libGpx4J y gpxTools. libGpx4J se encarga
de la lectura y escritura de los ficheros gpx y de la definición de las clases que
representan la información que se contiene en los ficheros. En cambio
[tools/Introduction gpxTools] es un conjunto de herramientas para la manipulación
de los datos que se obtienen a través de libGpx4J.

Visita el wiki para más información.

# Licence
Copyright 2015 greenflash1986

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

# Contributions
This library is based on the work of [superkas83](https://code.google.com/u/superkas83/)
and the original work can be found under <https://code.google.com/p/gpx4j/>. I fixed
some build / merge problems in his work and fixed some bugs.

# Changelog
* JaxbAdapter: added two lines provided by a patch from
[will.qu...@gmail.com](https://code.google.com/u/115281083828137929151/) to transfer
the lat and lon to wptType

* jaxb.GpxWriter: removed redundant code

* jaxb.GpxWriter, jaxb.package-info: some changes to fix the issue with wrong namespaces
in xml

* convert project to gradle project for automated build and dependency management

* JaxbGPXWriterTest: simple tests for correct conversion of lat and lon values and
for the namespace bug

# Disclaimer / Notes
I fixed the issues / made this project in my spare time and used this project as
playground to improve some of my skills. Because of this I won't promise to do
further work on this project. I provided it online on Github for you that you can
use the (partially) fixed version. Hopefully it's useful for some people and / or 
feel free to fork or made contributions. I will do my best to honor these efforts. 