package org.casaca.gpx4j.core.driver.jdom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.casaca.gpx4j.core.data.Bounds;
import org.casaca.gpx4j.core.data.Copyright;
import org.casaca.gpx4j.core.data.Degrees;
import org.casaca.gpx4j.core.data.DgpsStation;
import org.casaca.gpx4j.core.data.Email;
import org.casaca.gpx4j.core.data.Extension;
import org.casaca.gpx4j.core.data.Extensions;
import org.casaca.gpx4j.core.data.Fix;
import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.IExtensible;
import org.casaca.gpx4j.core.data.Link;
import org.casaca.gpx4j.core.data.Metadata;
import org.casaca.gpx4j.core.data.Person;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.StringExtensible;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.IGpxReader;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxReaderException;
import org.casaca.gpx4j.core.exception.GpxValidationException;
import org.casaca.gpx4j.core.logging.Logger;
import org.casaca.gpx4j.core.util.Constants;
import org.casaca.gpx4j.core.util.SortedList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class GpxReader implements IGpxReader {
	
	public static final String GPX_VERSION = "1.1";
	
	private Properties tags;
	private Properties appProperties;
	private GpxDriver driver;
	private Logger logger;
	private Namespace namespace;
	
	public GpxReader() throws GpxFileNotFoundException, GpxPropertiesException{
		String defaultPropertiesFileName = Constants.APPLICATION_KEYS_FILE_PREFIX_FILENAME+Constants.APPLICATION_KEYS_FILE_DEFAULT_BODY_FILENAME+Constants.APPLICATION_KEYS_FILE_SUFIX_FILENAME;
		try {
			this.logger = Logger.getLogger(GpxReader.class);
			this.driver = GpxDriver.getGpxDriver();
			this.tags =  driver.getTagsProperties();
			this.appProperties = driver.getDriverProperties();
		} catch (IOException e) {
			throw new GpxFileNotFoundException(defaultPropertiesFileName+" properties file does not found.\n"+e.getMessage());
		}
	}
	
	private String getChildText(Element element, String tag){
		Element child = null;
		return (child = element.getChild(this.tags.getProperty(tag), namespace))!=null?child.getText():null;
	}
	
	private void readRootAttributes(Element root, GpxDocument document) throws GpxIOException{
		logger.debug("Reading gpx document version");
		document.setVersion(root.getAttributeValue(this.tags.getProperty(Constants.TAG_GPX_VERSION)));
		logger.info("Gpx document version: "+document.getVersion());
		try {
			String propertiesFileName = Constants.APPLICATION_KEYS_FILE_PREFIX_FILENAME+document.getVersion()+Constants.APPLICATION_KEYS_FILE_SUFIX_FILENAME;
			this.tags.load(GpxDriver.class.getResourceAsStream(propertiesFileName));
			logger.debug("Loading new properties file: "+propertiesFileName);
		}
		catch(FileNotFoundException except){
			this.logger.error("Does not exists a tags file for this version. Using default tags file");
		}
		catch (IOException e) {
			throw new GpxIOException(e.getMessage());
		}
		
		logger.debug("Reading gpx document creator");
		document.setCreator(root.getAttributeValue(this.tags.getProperty(Constants.TAG_GPX_CREATOR)));
	}
	
	private Metadata readMetadata(Element element){
		if(element==null){
			this.logger.info("Gpx document has not metadata");
			return null;
		}
		else{
			this.logger.info("Reading the gpx metadata");
			Metadata metadata = new Metadata();
			Element child = null;
			metadata.setName(getChildText(element, Constants.TAG_METADATA_NAME));
			metadata.setDesc(getChildText(element, Constants.TAG_METADATA_DESC));
			metadata.setAuthor(this.readPerson(element.getChild(this.tags.getProperty(Constants.TAG_METADATA_AUTHOR), namespace)));
			metadata.setCopyright(this.readCopyright(element.getChild(this.tags.getProperty(Constants.TAG_METADATA_COPYRIGHT), namespace)));
			metadata.getLinks().addAll(this.readLinks(element.getChildren(this.tags.getProperty(Constants.TAG_METADATA_LINK), namespace)));
			metadata.setDate(this.readDate((child = element.getChild(this.tags.getProperty(Constants.TAG_METADATA_TIME), namespace))!=null?child.getText():null));
			metadata.setKeywords(getChildText(element, Constants.TAG_METADATA_KEYWORDS));
			metadata.setBounds(this.readBounds(element.getChild(this.tags.getProperty(Constants.TAG_METADATA_BOUNDS), namespace)));
			Extensions ext = this.readExtensions(element.getChild(this.tags.getProperty(Constants.TAG_METADATA_EXTENSIONS), namespace));
			if(ext!=null && ext.getExtensions()!=null) metadata.getExtensions().getExtensions().putAll(ext.getExtensions());
			
			return metadata;
		}
	}
	
	private IExtensible readExtensible(Element element) throws GpxReaderException{
		String classname = element.getAttributeValue(this.tags.getProperty(Constants.TAG_EXTENSIBLE_CANONICAL_NAME));
		
		if(classname==null)
			throw new GpxReaderException("Unknown element. No class name defined");

		try {
			Constructor<? extends IExtensible> constructor = Class.forName(classname).asSubclass(IExtensible.class).getDeclaredConstructor(null);
			boolean accesible = constructor.isAccessible();
			constructor.setAccessible(true);
			IExtensible extensible = constructor.newInstance(null);
			constructor.setAccessible(accesible);
			List<Element> children = element.getChildren();
			String name;
			for (Element element2 : children) {
				name = element2.getName();
				Field f = extensible.getClass().getDeclaredField(name);
				accesible = f.isAccessible();
				f.setAccessible(true);
				if(element2.getChildren().size()>0)
					f.set(extensible, this.readExtensible(element2));
				else{
					try{
						Constructor cons = f.getType().getDeclaredConstructor(String.class);
						boolean flag = cons.isAccessible();
						cons.setAccessible(true);
						Object o = cons.newInstance(element2.getText());
						f.set(extensible, o);
						cons.setAccessible(flag);
					}
					catch(NoSuchMethodException except){
						//TODO: IMPROVE THIS PORTION OF CODE
						if(f.getType().equals(int.class))
							f.setInt(extensible, Integer.parseInt(element2.getText()));
						else if(f.getType().equals(boolean.class))
							f.setBoolean(extensible, Boolean.parseBoolean(element2.getText()));
						else if(f.getType().equals(char.class))
							f.setChar(extensible, (element2.getText().length()>0)?element2.getText().charAt(0):' ');
						else if(f.getType().equals(byte.class))
							f.setByte(extensible, Byte.parseByte(element2.getText()));
						else if(f.getType().equals(short.class))
							f.setShort(extensible, Short.parseShort(element2.getText()));
						else if(f.getType().equals(long.class))
							f.setLong(extensible, Long.parseLong(element2.getText()));
						else if(f.getType().equals(float.class))
							f.setFloat(extensible, Float.parseFloat(element2.getText()));
						else if(f.getType().equals(double.class))
							f.setDouble(extensible, Double.parseDouble(element2.getText()));
						else
							throw new GpxReaderException(f.getType().getClass().getCanonicalName()+" has no constructor wich accepts a string as a parameter", except);
					}
				}
				f.setAccessible(accesible);
			}
			
			return extensible;
		} catch(ClassCastException e) {
			throw new GpxReaderException(classname+" not implements "+IExtensible.class.getCanonicalName()+" interface", e);
		} catch (IllegalArgumentException e) {
			throw new GpxReaderException(e);
		} catch (SecurityException e) {
			throw new GpxReaderException(e);
		} catch (InstantiationException e) {
			throw new GpxReaderException(e);
		} catch (IllegalAccessException e) {
			throw new GpxReaderException(e);
		} catch (InvocationTargetException e) {
			throw new GpxReaderException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxReaderException(e);
		} catch (ClassNotFoundException e) {
			throw new GpxReaderException(e);
		} catch (NoSuchFieldException e) {
			throw new GpxReaderException(e);
		}
	}
	
	private Extension<? extends IExtensible> readExtension(Element element) throws GpxReaderException{
		if(element.getChildren().size()==0){
			StringExtensible s = new StringExtensible(element.getName(), element.getValue());
			Extension<StringExtensible> extension = new Extension<StringExtensible>(element.getName()+"_"+System.currentTimeMillis(), s);
			return extension;
		}
		else{
			Extension<IExtensible> extension = new Extension<IExtensible>(element.getName(), this.readExtensible(element));
			return extension;
		}
	}
	
	private Extensions readExtensions(Element element){
		if(element == null){
			this.logger.info("Extensions element is null. Unable to read information about the extensions");
			return null;
		}
		else{
			this.logger.debug("Reading extensions information");
			Extensions extensions = new Extensions();
			List<Element> list = element.getChildren();
			Iterator<Element> iterator = list.iterator();
			Element e;
			while(iterator.hasNext()){
				e = iterator.next();
				try {
					extensions.addExtension(this.readExtension(e));
				} catch (GpxReaderException e1) {
					this.logger.info("Element "+e.getName()+" has not been read. "+e1.getMessage());
				}
			}
			
			return extensions;
		}
	}
	
	private Bounds readBounds(Element element){
		if(element==null){
			this.logger.info("Bounds element is null. Unable to read information about the copyright");
			return null;
		}
		else{
			this.logger.debug("Reading bounds information");
			Bounds bounds;
			BigDecimal minLat, minLon, maxLat, maxLon;
			String s = null;
			
			minLat = (s=getChildText(element, Constants.TAG_BOUNDS_MINLAT))!=null?BigDecimal.valueOf(Double.valueOf(s)):null;
			minLon = (s=getChildText(element, Constants.TAG_BOUNDS_MINLON))!=null?BigDecimal.valueOf(Double.valueOf(s)):null;
			maxLat = (s=getChildText(element, Constants.TAG_BOUNDS_MAXLAT))!=null?BigDecimal.valueOf(Double.valueOf(s)):null;
			maxLon = (s=getChildText(element, Constants.TAG_BOUNDS_MAXLON))!=null?BigDecimal.valueOf(Double.valueOf(s)):null;
			
			try{
				bounds = new Bounds(minLat, minLon, maxLat, maxLon);
			}
			catch(IllegalArgumentException except){
				this.logger.error(except.getMessage());
				bounds = null;
			}
			
			return bounds;
		}
	}
	
	private Route readRoute(Element element) throws GpxIOException{
		if(element == null){
			this.logger.info("Route element is null. Unable to read information about the route");
			return null;
		}
		else{
			this.logger.debug("Readig route information");
			Route route = new Route();
			String s = null;
			
			route.setName(getChildText(element, Constants.TAG_RTE_NAME));
			route.setCmt(getChildText(element, Constants.TAG_RTE_CMT));
			route.setDesc(getChildText(element, Constants.TAG_RTE_DESC));
			route.setSrc(getChildText(element, Constants.TAG_RTE_SRC));
			route.getLinks().addAll(this.readLinks(element.getChildren(this.tags.getProperty(Constants.TAG_RTE_LINK), namespace)));
			route.setNumber((s=getChildText(element, Constants.TAG_RTE_NUMBER))!=null?BigInteger.valueOf(Long.valueOf(s)):null);
			route.setType(getChildText(element, Constants.TAG_RTE_TYPE));
			Extensions ext = this.readExtensions(element.getChild(this.tags.getProperty(Constants.TAG_RTE_EXTENSIONS), namespace));
			if(ext!=null && ext.getExtensions()!=null) route.getExtensions().getExtensions().putAll(ext.getExtensions());
			route.getWaypoints().addAll(this.readWaypoints(element.getChildren(this.tags.getProperty(Constants.TAG_RTE_RTEPT), namespace)));
			
			return route;
		}
	}
	
	private List<Route> readRoutes(List<Element> elements) throws GpxIOException{
		List<Route> routes = new ArrayList<Route>();
		if(elements == null || elements.size() == 0){
			this.logger.info("Elements are null. Document has not routes");
		}
		else{
			Iterator<Element> iterator = elements.iterator();
			Route route = null;
			while(iterator.hasNext()){
				route = this.readRoute(iterator.next());
				if(route!=null) routes.add(route);
			}
		}
		
		return routes;
	}
	
	private Track readTrack(Element element) throws GpxIOException{
		if(element == null){
			this.logger.info("Track element is null. Unable to read information about the track");
			return null;
		}
		else{
			this.logger.info("Reading track information");
			Track track = new Track();
			String s = null;
			
			track.setName(getChildText(element, Constants.TAG_TRK_NAME));
			track.setCmt(getChildText(element, Constants.TAG_TRK_CMT));
			track.setDesc(getChildText(element, Constants.TAG_TRK_DESC));
			track.setSrc(getChildText(element, Constants.TAG_TRK_SRC));
			track.getLinks().addAll(this.readLinks(element.getChildren(this.tags.getProperty(Constants.TAG_TRK_LINK), namespace)));
			track.setNumber((s=getChildText(element, Constants.TAG_TRK_NUMBER))!=null?BigInteger.valueOf(Long.valueOf(s)):null);
			track.setType(getChildText(element, Constants.TAG_TRK_TYPE));
			track.setTrackSegments(this.readTrackSegments(element.getChildren(this.tags.getProperty(Constants.TAG_TRK_TRKSEG), namespace)));
			
			return track;
		}
	}
	
	private TrackSegment readTrackSegment(Element element) throws GpxIOException{
		if(element == null){
			this.logger.info("TrackSegment element is null. Unable to read information about the track segment");
			return null;
		}
		else{
			this.logger.debug("Reading tracksegment information");
			TrackSegment trackSegment = new TrackSegment();
			
			trackSegment.getWaypoints().addAll(this.readWaypoints(element.getChildren(this.tags.getProperty(Constants.TAG_TRKSEG_TRKPT), namespace)));
			Extensions ext = this.readExtensions(element.getChild(this.tags.getProperty(Constants.TAG_TRKSEG_EXTENSIONS), namespace));
			if(ext!=null && ext.getExtensions()!=null) trackSegment.getExtensions().getExtensions().putAll(ext.getExtensions());
			
			return trackSegment;
		}
	}
	
	private List<TrackSegment> readTrackSegments(List<Element> elements) throws GpxIOException{
		List<TrackSegment> trackSegments = new ArrayList<TrackSegment>();
		if(elements == null){
			this.logger.info("Elements are null. Document has not track segments");
		}
		else{
			Iterator<Element> iterator = elements.iterator();
			TrackSegment trackSegment = null;
			while(iterator.hasNext()){
				trackSegment = this.readTrackSegment(iterator.next());
				if(trackSegment!=null)
					trackSegments.add(trackSegment);
			}
		}
		
		return trackSegments;
	}
	
	private List<Track> readTracks(List<Element> elements) throws GpxIOException{
		List<Track> tracks = new ArrayList<Track>();
		if(elements == null || elements.size() == 0){
			this.logger.info("Elements are null. Document has not tracks");
		}
		else{
			Iterator<Element> iterator = elements.iterator();
			Track track = null;
			while(iterator.hasNext()){
				track = this.readTrack(iterator.next());
				if(track!=null) tracks.add(track);
			}
		}
		
		return tracks;
	}
	
	private Waypoint readWaypoint(Element element) throws GpxIOException{
		if(element == null){
			this.logger.info("Waypoint element is null. Unable to read information about the waypoint");
			return null;
		}
		else{
			this.logger.debug("Reading waypoint information");
			BigDecimal latitude, longitude;
			String s = null;
			
			latitude = (s=element.getAttributeValue(this.tags.getProperty(Constants.TAG_WPT_LAT)))!=null?BigDecimal.valueOf(Double.valueOf(s)):null;
			longitude = (s=element.getAttributeValue(this.tags.getProperty(Constants.TAG_WPT_LON)))!=null?BigDecimal.valueOf(Double.valueOf(s)):null;
			Waypoint waypoint = null;
			try{
				waypoint = new Waypoint(latitude, longitude);
				
				waypoint.setElevation((s=getChildText(element, Constants.TAG_WPT_ELE))!=null?BigDecimal.valueOf(Double.valueOf(s)):null);
				waypoint.setTime(this.readDate(getChildText(element, Constants.TAG_WPT_TIME)));
				waypoint.setMagvar(this.readDegrees(getChildText(element, Constants.TAG_WPT_MAGVAR)));
				waypoint.setGeoIdHeight((s=getChildText(element, Constants.TAG_WPT_GEOIDHEIGHT))!=null?BigDecimal.valueOf(Double.valueOf(s)):null);
				waypoint.setName(getChildText(element, Constants.TAG_WPT_NAME));
				waypoint.setCmt(getChildText(element, Constants.TAG_WPT_CMT));
				waypoint.setDescription(getChildText(element, Constants.TAG_WPT_DESC));
				waypoint.setSrc(getChildText(element, Constants.TAG_WPT_SRC));
				waypoint.getLinks().addAll(this.readLinks(element.getChildren(this.tags.getProperty(Constants.TAG_WPT_LINK), namespace)));
				waypoint.setSym(getChildText(element, Constants.TAG_WPT_SYM));
				waypoint.setType(getChildText(element, Constants.TAG_WPT_TYPE));
				waypoint.setFix((s=getChildText(element, Constants.TAG_WPT_FIX))!=null?Fix.createFix(s):null);
				waypoint.setSat((s=getChildText(element, Constants.TAG_WPT_SAT))!=null?BigInteger.valueOf(Long.valueOf(s)):null);
				waypoint.setHdop((s=getChildText(element, Constants.TAG_WPT_HDOP))!=null?BigDecimal.valueOf(Double.valueOf(s)):null);
				waypoint.setVdop((s=getChildText(element, Constants.TAG_WPT_VDOP))!=null?BigDecimal.valueOf(Double.valueOf(s)):null);
				waypoint.setPdop((s=getChildText(element, Constants.TAG_WPT_PDOP))!=null?BigDecimal.valueOf(Double.valueOf(s)):null);
				waypoint.setAgeOfDgpsData((s=getChildText(element, Constants.TAG_WPT_AGEOFDGPSDATA))!=null?BigDecimal.valueOf(Double.valueOf(s)):null);
				waypoint.setdGpsId(this.readDgpsStation(getChildText(element, Constants.TAG_WPT_DGPSID)));
				Extensions ext = this.readExtensions(element.getChild(this.tags.getProperty(Constants.TAG_WPT_EXTENSIONS), namespace));
				if(ext!=null && ext.getExtensions()!=null) waypoint.getExtensions().getExtensions().putAll(ext.getExtensions());
			}
			catch(NumberFormatException except){
				this.logger.error("Error reading waypoint information. Waypoint: "+latitude+" "+longitude+" Number format not valid");
			}
			catch(IllegalArgumentException except){
				this.logger.error(except.getMessage());
			}
			
			return waypoint;
		}
	}
	
	private SortedList<Waypoint> readWaypoints(List<Element> elements) throws GpxIOException{
		SortedList<Waypoint> waypoints = new SortedList<Waypoint>();
		if(elements == null || elements.size() == 0){
			this.logger.info("Elements are null. Document has not waypoints");
		}
		else{
			Iterator<Element> iterator = elements.iterator();
			Waypoint waypoint = null;
			while(iterator.hasNext()){
				waypoint = this.readWaypoint(iterator.next());
				if(waypoint!=null) waypoints.add(waypoint);
			}
		}
		
		return waypoints;
	}
	
	private DgpsStation readDgpsStation(String text){
		if(text == null){
			this.logger.debug("DgpsStation does not created. Text is null");
			return null;
		}
		else{
			DgpsStation dgpsStation = null;
			try{
				dgpsStation = new DgpsStation(text!=null?Integer.valueOf(text):null);
			}
			catch(NumberFormatException except){
				this.logger.error("Error converting to DgpsStation. Argument is not valid: "+text);
			}
			catch(IllegalArgumentException except){
				this.logger.error("Error converting to DgpsStation. Argument must be between "+DgpsStation.DGPS_STATION_MIN_VALUE+" and "+DgpsStation.DGPS_STATION_MAX_VALUE);
			}
			
			return dgpsStation;
		}
	}
	
	private Degrees readDegrees(String degrees){
		if(degrees == null){
			this.logger.debug("Degrees do not created. Argument is null");
			return null;
		}
		else{
			Degrees d = null;
			try{
				d = new Degrees(BigDecimal.valueOf(Double.valueOf(degrees)));
			}
			catch(NumberFormatException except){
				this.logger.error("Error creating the degrees. Argument: "+degrees+" is not valid");
			}
			catch(IllegalArgumentException except){
				this.logger.error(except.getMessage());
			}
			
			return d;
		}
	}
	
	
	private Calendar readDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat(this.appProperties.getProperty(Constants.DRIVER_DATE_FORMAT));//2011-02-03T13:41:04Z
		try {
			Date d = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			
			return c;
		} catch (ParseException e) {
			this.logger.error("Error parsing the date: "+date);
			return null;
		}
	}
	
	private Copyright readCopyright(Element element){
		if(element == null){
			this.logger.debug("Copyright element is null. Unable to read information about the copyright");
			return null;
		}
		else{
			this.logger.debug("Reading information about a copyright");
			String author;
			String license;
			Calendar year;
			
			author = element.getAttributeValue(this.tags.getProperty(Constants.TAG_COPYRIGHT_AUTHOR));
			if(author == null)
				this.logger.error("Copyright data error. Required field. Author field is empty");
			try{
				year = this.readDate(getChildText(element, Constants.TAG_COPYRIGHT_YEAR));
			}
			catch(NumberFormatException except){
				this.logger.error("Copyright data error. Malformed field. Year field exists but is not valid");
				year = null;
			}
			license = getChildText(element, Constants.TAG_COPYRIGHT_LICENSE);
			
			return new Copyright(year, author, license);
		}
	}
	
	private Person readPerson(Element element){
		if(element==null){
			this.logger.debug("Person element is null. Unable to read information about the person");
			return null;
		}
		else{
			this.logger.debug("Reading information about a person");
			Person person = new Person();

			person.setName(getChildText(element, Constants.TAG_PERSON_NAME));
			person.setEmail(this.readEmail(element.getChild(this.tags.getProperty(Constants.TAG_PERSON_EMAIL), namespace)));
			person.setLink(this.readLink(element.getChild(this.tags.getProperty(Constants.TAG_PERSON_LINK), namespace)));
			
			return person;
		}
	}
	
	private Email readEmail(Element element){
		if(element==null){
			this.logger.debug("Email element is null. Unable to read information about the email");
			return null;
		}
		else{
			this.logger.debug("Reading information about an email address");
			Email email;
			String id, domain;

			id = getChildText(element, Constants.TAG_EMAIL_ID);
			domain = getChildText(element, Constants.TAG_EMAIL_DOMAIN);
			try{
				email = new Email(id, domain);
			}
			catch(IllegalArgumentException except){
				this.logger.warn("Information about the email is empty. Email set to null");
				email = null;
			}
			
			return email;
		}
	}
	
	private List<Link> readLinks(List<Element> elements){
		List<Link> links = new ArrayList<Link>();
		if(elements == null){
			this.logger.info("Elements are null. Document has not links");
		}
		else{
			Iterator<Element> iterator = elements.iterator();
			Link link;
			while(iterator.hasNext()){
				link = this.readLink(iterator.next());
				if(link!=null) links.add(link);
			}
		}
		
		return links;
	}
	
	private Link readLink(Element element){
		if(element == null){
			this.logger.debug("Link element is null. Unable to read information about the link");
			return null;
		}
		else{
			this.logger.debug("Reading information about a link");
			Link link = new Link();
			
			String url = element.getAttributeValue(this.tags.getProperty(Constants.TAG_LINK_HREF));
			if(url!=null){
				try {
					link.setHref(new URL(url));
				} catch (MalformedURLException e) {
					this.logger.error("Link data error. Required field. Href exists but is not valid");
					link.setHref(null);
				}
			}
			else{
				this.logger.error("Link data error. Required field. Href field is empty");
			}
			
			link.setText(getChildText(element, Constants.TAG_LINK_TEXT));
			link.setType(getChildText(element, Constants.TAG_LINK_TYPE));
			
			return link;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GpxDocument readGpxDocument(InputStream input, boolean validateDocument) throws GpxIOException, GpxReaderException, GpxValidationException {
		try {
			SAXBuilder builder = new SAXBuilder(validateDocument);
			Document document = builder.build(input);
			Element root = document.getRootElement();
			GpxDocument gpxDoc = new GpxDocument();
			this.namespace = root.getNamespace();
			
			this.readRootAttributes(root, gpxDoc);
			gpxDoc.setMetadata(this.readMetadata(root.getChild(this.tags.getProperty(Constants.TAG_METADATA), namespace)));
			gpxDoc.getWaypoints().addAll(this.readWaypoints(root.getChildren(this.tags.getProperty(Constants.TAG_WPT), namespace)));
			gpxDoc.getRoutes().addAll(this.readRoutes(root.getChildren(this.tags.getProperty(Constants.TAG_RTE), namespace)));
			gpxDoc.getTracks().addAll(this.readTracks(root.getChildren(this.tags.getProperty(Constants.TAG_TRK), namespace)));
			Extensions ext =  this.readExtensions(root.getChild(this.tags.getProperty(Constants.TAG_GPX_EXTENSIONS), namespace));
			if(ext!=null && ext.getExtensions()!=null) gpxDoc.getExtensions().getExtensions().putAll(ext.getExtensions());
			input.close();
			
			return gpxDoc;
		} catch (JDOMException e) {
			throw new GpxValidationException("Validation failed", e);
		} catch (IOException e) {
			throw new GpxIOException(e);
		}
	}

	@Override
	public GpxDocument readGpxDocument(String filepath, boolean validateDocument) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxValidationException {
		return this.readGpxDocument(new File(filepath), validateDocument);
	}

	@Override
	public GpxDocument readGpxDocument(String filepath) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxPropertiesException, GpxValidationException {
		return this.readGpxDocument(new File(filepath));
	}

	@Override
	public GpxDocument readGpxDocument(File input, boolean validateDocument) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxValidationException {
		try {
			return this.readGpxDocument(new FileInputStream(input), validateDocument);
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException(e.getMessage());
		}
	}

	@Override
	public GpxDocument readGpxDocument(File input) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxPropertiesException, GpxValidationException{
		Properties properties = GpxDriver.getGpxDriver().getDriverProperties();
		if(properties == null) throw new GpxPropertiesException("Driver properties not loaded. Please load properties from driver");
		
		return this.readGpxDocument(input, Boolean.valueOf(properties.getProperty(Constants.DRIVER_VALIDATE_GPX_FILE, "false")));
	}
	

	@Override
	public GpxDocument readGpxDocument(InputStream input) throws GpxIOException, GpxReaderException, GpxPropertiesException, GpxValidationException {
		if(appProperties == null) throw new GpxPropertiesException("Driver properties not loaded. Please load properties from driver");
		
		return this.readGpxDocument(input, Boolean.valueOf(appProperties.getProperty(Constants.DRIVER_VALIDATE_GPX_FILE, "false")));
	}
}
