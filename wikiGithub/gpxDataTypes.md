# GPX data types #

This page contains a complete description of all data types that supports the GPX format in version 1.1.

## Gpx ##

This is the root element where all information is stored.

| _Name:_ | **Gpx** || |
|:--------|:--------|:|:|
| _Tag:_  | **gpx** || |
|         | | | |
| **_Attributes:_** |         | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| version | true    | string | 1 |
| creator | true    | string | 1 |
|         | | | |
| **_Content:_** |         | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| metadata | false   |[Metadata](gpxDataTypes#Metadata.md) | 0…1 |
| wpt     |false    | [Waypoint](gpxDataTypes#Waypoint.md) | 0…`*` |
| rte     |false    | [Route](gpxDataTypes#Route.md) | 0…`*` |
| trk     |false    | [Track](gpxDataTypes#Track.md) | 0…`*` |
| extensions | false   | [Extensions](gpxDataTypes#Extensions.md) |0…1 |

## Metadata ##

Information about the GPX file, author, and copyright restrictions goes in the metadata section. Providing rich, meaningful information about your GPX files allows others to search for and use your GPS data.

| _Name:_ | **Metadata** || |
|:--------|:-------------|:|:|
|         | | | |
| **_Attributes:_** | None         || |
|         | | | |
| **_Content:_** |              | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| name    | false        | string | 0...1 |
| desc    | false       | string | 0...1 |
| author  | false        | [Person](gpxDataTypes#Person.md) | 0...1 |
| copyright | false        | [Copyright](gpxDataTypes#Copyright.md) | 0...1 |
| link   | false       | [Link](gpxDataTypes#Link.md) | 0...1 |
| time    | false        | datetime | 0...1 |
| keywords | false       | string | 0...1 |
| bounds | false       | [Bounds](gpxDataTypes#Bounds.md) | 0...1 |
| extensions | false        | [Extensions](gpxDataTypes#Extensions.md) | 0...1 |

## Waypoint ##

Waypoint represents a point of interest, or named feature on a map.

| _Name:_ | **Waypoint** || |
|:--------|:-------------|:|:|
|         | | | |
| **_Attributes:_** |              | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| lat     | true         | decimal (-90 <= value <= 90) | 1 |
| lon     | true         | decimal (-90 <= value <= 90) | 1 |
|         | | | |
| **_Content:_** |              | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| ele     | false        | decimal | 0...1 |
| time    | false        | datetime | 0...1 |
| magvar  | false       | decimal (0 <= value <=360) | 0...1 |
| geoidheight | false       | decimal | 0...1 |
| name    | false       | string | 0...1 |
| cmt     | false       | string | 0...1 |
| desc    | false       | string | 0...1 |
| src    | false       | string | 0...1 |
| link   | false       | [Link](gpxDataTypes#Link.md) | 0...1 |
| sym     | false       | string | 0...1 |
| type   | false       | string | 0...1 |
| fix    | false       | enum: 'none' '2d' '3d' 'dgps' 'pps' | 0...1 |
| sat     | false       | integer (value >= 0) | 0...1 |
| hdop    | false       | decimal | 0...1 |
| vdop    | false       | decimal | 0...1 |
| pdop   | false       | decimal | 0...1 |
| ageofdgpsdata | false       | decimal | 0...1 |
| dgpsid | false       | integer (0 <= value <= 1023) | 0...1 |
| extensions | false       | [Extensions](gpxDataTypes#Extensions.md) | 0...1 |

## Route ##

Represents a route - an ordered list of waypoints representing a series of turn points leading to a destination.

| _Name:_ | **Route** || |
|:--------|:----------|:|:|
|         | | | |
| **_Attributes:_** | None      || |
|         | | | |
| **_Content:_** |           | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| name    | false    | string | 0...1 |
| cmt    | false    | string | 0...1 |
| desc    | false    | string | 0...1 |
| src     | false     | string | 0...1 |
| link    | false     | [Link](gpxDataTypes#Link.md) | 0...1 |
| number  | false    | integer (value >= 0) | 0...1 |
| type   | false    | string | 0...1 |
| extensions | false    | [Extensions](gpxDataType#Extensions.md) | 0...1 |
| rtept   | false    | [Waypoint](gpxDataType#Waypoint.md) | 0...`*`|

## Track ##

Represents a track - an ordered list of points describing a path.

| _Name:_ | **Track** || |
|:--------|:----------|:|:|
|         | | | |
| **_Attributes:_** | None      || |
|         | | | |
| **_Content:_** |           | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| name    | false   | string | 0...1 |
| cmt     | false    | string | 0...1 |
| desc   | false    | string | 0...1 |
| src     | false    | string | 0...1 |
| link    | false    | [Link](gpxDataTypes#Link.md) | 0...1 |
| number  | false    | integer (value >= 0) | 0...1 |
| type    | false     | string | 0...1 |
| extensions | false    | [Extensions](gpxDataTypes#Extensions.md) | 0...1 |
| trkseg  | false     | [TrackSegment](gpxDataTypes#TrackSegment.md) | 0...`*` |

## Extensions ##

This section can add extended GPX data by adding elements from another schema here.

## TrackSegment ##

A Track Segment holds a list of Track Points which are logically connected in order. To represent a single GPS track where GPS reception was lost, or the GPS receiver was turned off, start a new Track Segment for each continuous span of track data.

| _Name:_ | **TrackSegment** || |
|:--------|:-----------------|:|:|
|         | | | |
| **_Attributes:_** | None             || |
|         | | | |
| **_Content:_** |                  | | |
| **Name** | **Required**     | **Type** | **Cardinality** |
| trkpt  | false            | [Waypoint](gpxDataTypes#Waypoint.md) | 0...`*` |
| extensions | false           | [Extensions](gpxDataTypes#Extensions.md) | 0...1 |

## Copyright ##

Information about the copyright holder and any license governing use of this file. By linking to an appropriate license, you may place your data into the public domain or grant additional usage rights.

| _Name:_ | **Copyright** || |
|:--------|:--------------|:|:|
|         | | | |
| **_Attributes:_** |               | | |
| **Name** | **Required**  | **Type** | **Cardinality** |
| author  | true          | string | 1 |
|         | | | |
| **_Content:_** |               | | |
| **Name** | **Required**  | **Type** | **Cardinality** |
| year   | false        | integer | 0...1 |
| license | false        | url | 0...1 |

## Link ##

A link to an external resource (Web page, digital photo, video clip, etc) with additional information.

| _Name:_ | **Link** || |
|:--------|:---------|:|:|
|         | | | |
| **_Attributes:_** |          | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| href    | true     | url | 1 |
|         | | | |
| **_Content:_** |          | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| text   | false   | string | 0...1 |
| type    | false   | string | 0...1 |

## Email ##

An email address. Broken into two parts (id and domain) to help prevent email harvesting.

| _Name:_ | **Email** || |
|:--------|:----------|:|:|
|         | | | |
| **_Attributes:_** |           | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| id      | true      | string | 1 |
| domain  | true     | string | 1 |
|         | | | |
| **_Content:_** | None      || |

## Person ##

A person or organization.

| _Name:_ | **Person** || |
|:--------|:-----------|:|:|
|         | | | |
| **_Attributes:_** | None       || |
|         | | | |
| **_Content:_** |            | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| name    | false     | string | 0...1 |
| email   | false     | [Email](gpxDataTypes#Email.md) | 0...1 |
| link   | false     | [Link](gpxDataTypes#Link.md) | 0...1 |

## Point ##

A geographic point with optional elevation and time. Available for use by other schemas.

| _Name:_ | **Point** || |
|:--------|:----------|:|:|
|         | | | |
| **_Attributes:_** |           | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| lat     | true      | decimal (-90 <= value <= 90) | 1 |
| lon     | true     | decimal (-90 <= value <= 90) | 1 |
|         | | | |
| **_Content:_** |           | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| ele     | false    | decimal | 0...1 |
| time    | false    | datetime | 0...1 |

## PointSequence ##

An ordered sequence of points. (for polygons or polylines, e.g.)

| _Name:_ | **PointSequence** || |
|:--------|:------------------|:|:|
|         | | | |
| **_Attributes:_** | None              || |
|         | | | |
| **_Content:_** |                   | | |
| **Name** | **Required**      | **Type** | **Cardinality** |
| pt     | false            | [Point](gpxDataTypes#Point.md) | 0...`*` |

## Bounds ##

Two lat/lon pairs defining the extent of an element.

| _Name:_ | **Bounds** || |
|:--------|:-----------|:|:|
|         | | | |
| **_Attributes:_** |            | | |
| **Name** | **Required** | **Type** | **Cardinality** |
| minlat  | true       | decimal (-90 <= value <= 90) | 1 |
| minlon  | true      | decimal (-90 <= value <= 90) | 1 |
| maxlat  | true       | decimal (-90 <= value <= 90) | 1 |
| maxlon  | true      | decimal (-90 <= value <= 90) | 1 |
|         | | | |
| **_Content:_** | None       || |