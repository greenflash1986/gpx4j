package org.casaca.gpx4j.tools.exporter.implementation;

import java.io.OutputStream;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.Metadata;
import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.tools.exception.GpxExporterException;
import org.casaca.gpx4j.tools.exporter.AbstractGeneratedField;
import org.casaca.gpx4j.tools.exporter.IExporter;
import org.casaca.gpx4j.tools.exporter.MethodClassField;

public class ExporterImplementation {
	
	public static void exportGpxDocuments(OutputStream output, IExporter exporter, boolean showHeader, List<GpxDocument> list) throws GpxExporterException{
		if(output==null || exporter==null || list==null) throw new GpxExporterException("This method not allow null arguments");
		
		try {
			MethodClassField mField;
			AbstractGeneratedField gField;
			
			mField = new MethodClassField();
			mField.setName("version");
			mField.setMethod(GpxDocument.class.getMethod("getVersion", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("creator");
			mField.setMethod(GpxDocument.class.getMethod("getCreator", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((GpxDocument)this.getObject()).getTracks().size());
				}
			};
			gField.setName("tracks");
			exporter.getFields().add(gField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((GpxDocument)this.getObject()).getWaypoints().size());
				}
			};
			gField.setName("waypoints");
			exporter.getFields().add(gField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((GpxDocument)this.getObject()).getRoutes().size());
				}
			};
			gField.setName("routes");
			exporter.getFields().add(gField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return (((GpxDocument)this.getObject()).getExtensions()==null)?"0":String.valueOf(((GpxDocument)this.getObject()).getExtensions().count());
				}
			};
			gField.setName("extensions");
			exporter.getFields().add(gField);
			
			exporter.getData().addAll(list);
			exporter.showHeader(showHeader);
			exporter.export(output);
		} catch (SecurityException e) {
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		}
	}
	
	public static void exportMetadatas(OutputStream output, IExporter exporter, boolean showHeader, List<Metadata> list) throws GpxExporterException{
		if(output==null || exporter==null || list==null) throw new GpxExporterException("This method not allow null arguments");
		
		try {
			MethodClassField mField;
			AbstractGeneratedField gField;
			
			mField = new MethodClassField();
			mField.setName("name");
			mField.setMethod(Metadata.class.getMethod("getName", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("description");
			mField.setMethod(Metadata.class.getMethod("getDesc", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("author");
			mField.setMethod(Metadata.class.getMethod("getAuthor", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("copyright");
			mField.setMethod(Metadata.class.getMethod("getCopyright", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Metadata)this.getObject()).getLinks().size());
				}
			};
			gField.setName("links");
			exporter.getFields().add(gField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					Locale l = Locale.getDefault();
					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, l);
					DateFormat tf = DateFormat.getTimeInstance(DateFormat.MEDIUM, l);
					return df.format(((Metadata)this.getObject()).getDate().getTime())+" "+tf.format(((Metadata)this.getObject()).getDate().getTime());
				}
			};
			
			mField = new MethodClassField();
			mField.setName("keywords");
			mField.setMethod(Metadata.class.getMethod("getKeywords", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("bounds");
			mField.setMethod(Metadata.class.getMethod("getBounds", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Metadata)this.getObject()).getExtensions().count());
				}
			};
			gField.setName("extensions");
			exporter.getFields().add(gField);
			
			exporter.getData().addAll(list);
			exporter.showHeader(showHeader);
			exporter.export(output);
		} catch (SecurityException e) {
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		}
	}
	
	public static void exportTracks(OutputStream output, IExporter exporter, boolean showHeader, List<Track> list) throws GpxExporterException{
		if(output==null || exporter==null || list==null) throw new GpxExporterException("This method not allow null arguments");
		
		try {
			MethodClassField mField;
			AbstractGeneratedField gField;
			
			mField = new MethodClassField();
			mField.setName("name");
			mField.setMethod(Track.class.getMethod("getName", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("cmt");
			mField.setMethod(Track.class.getMethod("getCmt", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("description");
			mField.setMethod(Track.class.getMethod("getDesc", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("src");
			mField.setMethod(Track.class.getMethod("getSrc", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Track)this.getObject()).getLinks().size());
				}
			};
			gField.setName("links");
			exporter.getFields().add(gField);
			
			mField = new MethodClassField();
			mField.setName("number");
			mField.setMethod(Track.class.getMethod("getNumber", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("type");
			mField.setMethod(Track.class.getMethod("getType", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return (((Track)this.getObject()).getExtensions()!=null)?String.valueOf(((Track)this.getObject()).getExtensions().count()):"0";
				}
			};
			gField.setName("extensions");
			exporter.getFields().add(gField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Track)this.getObject()).getTrackSegments().size());
				}
			};
			gField.setName("tracksegments");
			exporter.getFields().add(gField);
			
			exporter.getData().addAll(list);
			exporter.showHeader(showHeader);
			exporter.export(output);
		} catch (SecurityException e) {
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		}
	}
	
	public static void exportRoutes(OutputStream output, IExporter exporter, boolean showHeader, List<Route> list) throws GpxExporterException{
		if(output==null || exporter==null || list==null) throw new GpxExporterException("This method not allow null arguments");
		
		try {
			MethodClassField mField;
			AbstractGeneratedField gField;
						
			mField = new MethodClassField();
			mField.setName("name");
			mField.setMethod(Route.class.getMethod("getName", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("cmt");
			mField.setMethod(Route.class.getMethod("getCmt", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("desc");
			mField.setMethod(Route.class.getMethod("getDesc", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("src");
			mField.setMethod(Route.class.getMethod("getSrc", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Route)this.getObject()).getLinks().size());
				}
			};
			
			mField = new MethodClassField();
			mField.setName("number");
			mField.setMethod(Route.class.getMethod("getNumber", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("type");
			mField.setMethod(Route.class.getMethod("getType", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return (((Route)this.getObject()).getExtensions()!=null)?String.valueOf(((Route)this.getObject()).getExtensions().count()):"0";
				}
			};
			gField.setName("extensions");
			exporter.getFields().add(gField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Route)this.getObject()).getWaypoints().size());
				}
			};
			gField.setName("waypoints");
			exporter.getFields().add(gField);
			
			exporter.getData().addAll(list);
			exporter.showHeader(showHeader);
			exporter.export(output);
		} catch (SecurityException e) {
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		}
	}
	
	public static void exportWaypoints(OutputStream output, IExporter exporter, boolean showHeader, List<Waypoint> list) throws GpxExporterException{
		if(output==null || exporter==null || list==null) throw new GpxExporterException("This method not allow null arguments");
		
		try {
			MethodClassField mField;
			AbstractGeneratedField gField;
			
			mField = new MethodClassField();
			mField.setName("latitude");
			mField.setMethod(Waypoint.class.getMethod("getLatitude", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("longitude");
			mField.setMethod(Waypoint.class.getMethod("getLongitude", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("elevation");
			mField.setMethod(Waypoint.class.getMethod("getElevation", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					Locale l = Locale.getDefault();
					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, l);
					DateFormat tf = DateFormat.getTimeInstance(DateFormat.MEDIUM, l);
					return df.format(((Waypoint)this.getObject()).getTime().getTime())+" "+tf.format(((Waypoint)this.getObject()).getTime().getTime());
				}
			};
			gField.setName("time");
			exporter.getFields().add(gField);
			
			mField = new MethodClassField();
			mField.setName("magvar");
			mField.setMethod(Waypoint.class.getMethod("getMagvar", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("geoidheight");
			mField.setMethod(Waypoint.class.getMethod("getGeoIdHeight", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("name");
			mField.setMethod(Waypoint.class.getMethod("getName", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("cmt");
			mField.setMethod(Waypoint.class.getMethod("getCmt", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("description");
			mField.setMethod(Waypoint.class.getMethod("getDescription", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("src");
			mField.setMethod(Waypoint.class.getMethod("getSrc", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return String.valueOf(((Waypoint)this.getObject()).getLinks().size());
				}
			};
			gField.setName("links");
			exporter.getFields().add(gField);
			
			mField = new MethodClassField();
			mField.setName("sym");
			mField.setMethod(Waypoint.class.getMethod("getSym", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("type");
			mField.setMethod(Waypoint.class.getMethod("getType", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("fix");
			mField.setMethod(Waypoint.class.getMethod("getFix", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("sat");
			mField.setMethod(Waypoint.class.getMethod("getSat", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("hdop");
			mField.setMethod(Waypoint.class.getMethod("getHdop", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("vdop");
			mField.setMethod(Waypoint.class.getMethod("getVdop", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("pdop");
			mField.setMethod(Waypoint.class.getMethod("getPdop", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("ageofdgpsdata");
			mField.setMethod(Waypoint.class.getMethod("getAgeOfDgpsData", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("dgpsid");
			mField.setMethod(Waypoint.class.getMethod("getdGpsId", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					return (((Waypoint)this.getObject()).getExtensions()!=null)?String.valueOf(((Waypoint)this.getObject()).getExtensions().count()):"0";
				}
			};
			gField.setName("extensions");
			exporter.getFields().add(gField);
			
			exporter.getData().addAll(list);
			exporter.showHeader(showHeader);
			exporter.export(output);
		} catch (SecurityException e) {
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		}
	}
	
	public static void exportPoints(OutputStream output, IExporter exporter, boolean showHeader, List<Point> list) throws GpxExporterException{
		if(output==null || exporter==null || list==null) throw new GpxExporterException("This method not allow null arguments");
		
		try {
			MethodClassField mField;
			AbstractGeneratedField gField;
			
			mField = new MethodClassField();
			mField.setName("latitude");
			mField.setMethod(Point.class.getMethod("getLatitude", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("longitude");
			mField.setMethod(Point.class.getMethod("getLongitude", null));
			exporter.getFields().add(mField);
			
			mField = new MethodClassField();
			mField.setName("elevation");
			mField.setMethod(Point.class.getMethod("getElevation", null));
			exporter.getFields().add(mField);
			
			gField = new AbstractGeneratedField() {
				
				@Override
				public String getContent() throws GpxExporterException {
					Locale l = Locale.getDefault();
					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, l);
					DateFormat tf = DateFormat.getTimeInstance(DateFormat.MEDIUM, l);
					return df.format(((Point)this.getObject()).getTime().getTime())+" "+tf.format(((Point)this.getObject()).getTime().getTime());
				}
			};
			gField.setName("time");
			exporter.getFields().add(gField);
			
			exporter.getData().addAll(list);
			exporter.showHeader(showHeader);
			exporter.export(output);
		}
		catch (SecurityException e){
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		}
	}
}