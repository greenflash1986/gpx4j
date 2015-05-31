# GPX #

**GPX**, or **GPS eXchange Format** is an [XML schema](http://en.wikipedia.org/wiki/XML_schema) designed as a common [GPS](http://en.wikipedia.org/wiki/GPS) data format for software applications.

**Currently the format is in version 1.1, which is the version that supports the tool gpx4j**

It can be used to describe [waypoints](gpxDataTypes#Waypoint.md), [tracks](gpxDataTypes#Track.md), and [routes](gpxDataTypes#Route.md). The format is open and can be used without the need to pay license fees. Its tags store location, elevation, and time and can in this way be used to interchange data between GPS devices and software packages. Such computer programs allow users, for example, to view their tracks, project their tracks on satellite images, annotate maps, and tag photographs with the [geolocation](http://en.wikipedia.org/wiki/Geolocation) in the Exif metadata.

## Data types ##

In GPX, a collection of points, with no sequential relationship (the county towns of England, say, or all Skyscrapers in New York), is deemed a collection of individual [waypoints](gpxDataTypes#Waypoint.md). An ordered collection of points may be expressed as a [track](gpxDataTypes#Track.md) or a [route](gpxDataTypes#Route.md). Conceptually, [tracks](gpxDataTypes#Track.md) are a record of where a person has been, [routes](gpxDataTypes#Route.md) are suggestions about where they might go in the future. For example, each point in a [track](gpxDataTypes#Track.md) may have a timestamp (because someone is recording where and when they were there), but the points in a [route](gpxDataTypes#Route.md) are unlikely to have timestamps, because the author is suggesting a route which nobody might ever have traveled.

In this [page](gpxDataTypes.md) there is a complete description of all data types supported in **GPX** format

## Sample GPX document ##

The following is a truncated (for brevity) GPX file produced by this library. This document does not show all functionality which can be stored in the GPX format - for example, there are no [waypoints](gpxDataTypes#Waypoint.md) or [extensions](gpxDataTypes#Extensions.md), and this is part of a track, not a route - but the purpose is to serve as a brief illustration.

```
<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
<gpx xmlns="http://www.topografix.com/GPX/1/1" creator="gpx4j" version="1.1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd">
	<trk>
		<name>290311</name>
		<desc>(null)</desc>
		<trkseg>
			<trkpt lat="39.114318" lon="-0.425692">
				<ele>126.951324</ele>
				<time>2011-03-29T14:45:52Z</time>
			</trkpt>
			<trkpt lat="39.114226" lon="-0.425648">
				<ele>122.974365</ele>
				<time>2011-03-29T14:46:03Z</time>
			</trkpt>
			<trkpt lat="39.114226" lon="-0.425648">
				<ele>122.974365</ele>
				<time>2011-03-29T14:46:07Z</time>
			</trkpt>
			<trkpt lat="39.114134" lon="-0.425608">
				<ele>120.310211</ele>
				<time>2011-03-29T14:46:09Z</time>
			</trkpt>
			<trkpt lat="39.114239" lon="-0.425657">
				<ele>117.177261</ele>
				<time>2011-03-29T14:46:18Z</time>
			</trkpt>
			<trkpt lat="39.114317" lon="-0.425718">
				<ele>116.083771</ele>
				<time>2011-03-29T14:46:21Z</time>
			</trkpt>
			<trkpt lat="39.114412" lon="-0.425789">
				<ele>115.956726</ele>
				<time>2011-03-29T14:46:25Z</time>
			</trkpt>
		</trkseg>
	</trk>
</gpx>
```

## Sources ##
  * [Wikipedia: GPS eXchange Format](http://en.wikipedia.org/wiki/GPS_eXchange_Format)