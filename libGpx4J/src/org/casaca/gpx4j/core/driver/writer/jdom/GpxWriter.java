package org.casaca.gpx4j.core.driver.writer.jdom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.casaca.gpx4j.core.data.Bounds;
import org.casaca.gpx4j.core.data.Copyright;
import org.casaca.gpx4j.core.data.Degrees;
import org.casaca.gpx4j.core.data.DgpsStation;
import org.casaca.gpx4j.core.data.Email;
import org.casaca.gpx4j.core.data.Extensions;
import org.casaca.gpx4j.core.data.Fix;
import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.Link;
import org.casaca.gpx4j.core.data.Metadata;
import org.casaca.gpx4j.core.data.Person;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.reader.sax.GpxReader;
import org.casaca.gpx4j.core.driver.writer.IGpxWriter;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.logging.Logger;
import org.casaca.gpx4j.core.util.Constants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class GpxWriter implements IGpxWriter {
	
	private Properties tags;
	private Properties appProperties;
	private Logger logger;
	
	public GpxWriter() throws GpxFileNotFoundException{
		String defaultPropertiesFileName = Constants.APPLICATION_KEYS_FILE_PREFIX_FILENAME+Constants.APPLICATION_KEYS_FILE_DEFAULT_BODY_FILENAME+Constants.APPLICATION_KEYS_FILE_SUFIX_FILENAME;
		try {
			this.logger = Logger.getLogger(GpxReader.class);
			this.tags = new Properties();
			this.logger.debug("Loading properties file: "+defaultPropertiesFileName);
			this.tags.load(GpxDriver.class.getResourceAsStream(defaultPropertiesFileName));
		} catch (IOException e) {
			throw new GpxFileNotFoundException(defaultPropertiesFileName+" properties file does not found.\n"+e.getMessage());
		}
	}
	
	private Element getElement(GpxDocument doc) throws IllegalArgumentException{
		if(doc==null)
			throw new IllegalArgumentException("Error creating gpx document element. GpxDocument must not be null");
		
		String creator = doc.getCreator();
		String version = doc.getVersion();
		if(creator == null || version == null)
			throw new IllegalArgumentException("Creator and version of the gpx document must not be null");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_GPX));
		//Namespace namespace = Namespace.XML_NAMESPACE;
		Namespace namespace = Namespace.getNamespace(this.tags.getProperty(Constants.APPLICATION_NAMESPACE_1_1));
		element.setNamespace(namespace);
		element.setAttribute(this.tags.getProperty(Constants.TAG_GPX_CREATOR), creator);
		element.setAttribute(this.tags.getProperty(Constants.TAG_GPX_VERSION), version);
		
		Metadata metadata = doc.getMetadata();
		List<Waypoint> waypoints = doc.getWaypoints();
		List<Route> routes = doc.getRoutes();
		List<Track> tracks = doc.getTracks();
		Extensions extensions = doc.getExtensions();
		
		if(metadata!=null)
			element.addContent(this.getElement(metadata).setNamespace(null));
		if(tracks!=null && tracks.size()>0)
			for(int i=0;i<tracks.size();i++)
				element.addContent(this.getElement(tracks.get(i)).setNamespace(null));
		if(waypoints!=null && waypoints.size()>0)
			for(int i=0;i<waypoints.size();i++)
				element.addContent(this.getElement(waypoints.get(i)).setNamespace(null));
		if(routes!=null && routes.size()>0)
			for(int i=0;i<routes.size();i++)
				element.addContent(this.getElement(routes.get(i)).setNamespace(null));
		if(extensions!=null && extensions.count()>0)
			element.addContent(this.getElement(extensions).setNamespace(null));
		return element;
	}
	
	private Element getElement(Metadata metadata) throws IllegalArgumentException{
		if(metadata==null)
			throw new IllegalArgumentException("Error creating metadata argument. Metadata must not be null");
		
		String name = metadata.getName();
		String desc = metadata.getDesc();
		Person author = metadata.getAuthor();
		Copyright copyright = metadata.getCopyright();
		List<Link> links = metadata.getLinks();
		Calendar time = metadata.getDate();
		String keywords = metadata.getKeywords();
		Bounds bounds = metadata.getBounds();
		Extensions extensions = metadata.getExtensions();
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_METADATA));
		if(name!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_METADATA_NAME)).setText(name));
		if(desc!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_METADATA_DESC)).setText(desc));
		if(author!=null)
			element.addContent(this.getElement(author).setName(this.tags.getProperty(Constants.TAG_METADATA_AUTHOR)));
		if(copyright!=null)
			element.addContent(this.getElement(copyright));
		if(links!=null && links.size()>0)
			for(int i=0;i<links.size();i++)
				element.addContent(this.getElement(links.get(i)));
		if(time!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_METADATA_TIME)).setText(this.dateToStrint(time)));
		if(keywords!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_METADATA_KEYWORDS)).setText(keywords));
		if(bounds!=null)
			element.addContent(this.getElement(bounds));
		if(extensions!=null)
			element.addContent(this.getElement(extensions));
		
		return element;
	}
	
	private String dateToStrint(Calendar date) throws IllegalArgumentException{
		if(date==null)
			throw new IllegalArgumentException("Error converting from date to string. Date must not be null");
		
		SimpleDateFormat sdf = new SimpleDateFormat(this.tags.getProperty(Constants.DATE_FORMAT));
		return sdf.format(date.getTime());
	}
	
	private Calendar stringToDate(String date) throws ParseException{
		if(date==null)
			throw new IllegalArgumentException("Error converting from string to date. String must not be null");
		
		SimpleDateFormat sdf = new SimpleDateFormat(this.tags.getProperty(Constants.DATE_FORMAT));
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		
		return c;
	}
	
	private Element getElement(Person person) throws IllegalArgumentException{
		if(person==null)
			throw new IllegalArgumentException("Error creating person element. Person must not be null");
		
		String name = person.getName();
		Email email = person.getEmail();
		Link link = person.getLink();
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_PERSON));
		if(name!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_PERSON_NAME)).setText(name));
		if(email!=null)
			element.addContent(this.getElement(email));
		if(link!=null)
			element.addContent(this.getElement(link));
		
		return element;
	}
	
	private Element getElement(Bounds bounds) throws IllegalArgumentException{
		if(bounds==null)
			throw new IllegalArgumentException("Error creating bounds element. Bounds must not be null");
		
		Double minLat = bounds.getMinLatitude();
		Double minLon = bounds.getMinLongitude();
		Double maxLat = bounds.getMaxLatitude();
		Double maxLon = bounds.getMaxLongitude();
		
		if(minLat==null || minLon==null || maxLat==null || maxLon==null)
			throw new IllegalArgumentException("Bounds content must not be null");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_BOUNDS));
		
		element.addContent(new Element(this.tags.getProperty(Constants.TAG_BOUNDS_MINLAT)).setText(String.valueOf(minLat)));
		element.addContent(new Element(this.tags.getProperty(Constants.TAG_BOUNDS_MINLON)).setText(String.valueOf(minLon)));
		element.addContent(new Element(this.tags.getProperty(Constants.TAG_BOUNDS_MAXLAT)).setText(String.valueOf(maxLat)));
		element.addContent(new Element(this.tags.getProperty(Constants.TAG_BOUNDS_MAXLON)).setText(String.valueOf(maxLon)));
		
		return element;
	}
	
	private Element getElement(Email email) throws IllegalArgumentException{
		if(email==null)
			throw new IllegalArgumentException("Error creating email element. Email must not be null");
		
		String user = email.getUser();
		String domain = email.getDomain();
		
		if(user==null || domain==null)
			throw new IllegalArgumentException("Error creating email element. Email content must not be null.");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_EMAIL));
		element.addContent(new Element(this.tags.getProperty(Constants.TAG_EMAIL_ID)).setText(String.valueOf(user)));
		element.addContent(new Element(this.tags.getProperty(Constants.TAG_EMAIL_DOMAIN)).setText(String.valueOf(domain)));
		
		return element;
	}
	
	private Element getElement(Link link) throws IllegalArgumentException{
		if(link==null)
			throw new IllegalArgumentException("Error creating link element. Link must not be null");
		
		String href = link.getHref().toString();
		String text = link.getText();
		String type = link.getType();
		
		if(href==null)
			throw new IllegalArgumentException("Error creating link element. Href must not be null");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_LINK));
		element.setAttribute(this.tags.getProperty(Constants.TAG_LINK_HREF), href);
		if(text!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_LINK_TEXT)).setText(text));
		if(type!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_LINK_TYPE)).setText(type));
		
		return element;
	}
	
	private Element getElement(Copyright copyright) throws IllegalArgumentException{
		if(copyright==null)
			throw new IllegalArgumentException("Error creating copyright element. Copyright must not be null");
		
		String author = copyright.getAuthor();
		Integer year = copyright.getYear();
		String license = copyright.getLicense();
		
		if(author!=null)
			throw new IllegalArgumentException("Error creating copyright element. Author must not be null");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_COPYRIGHT));
		element.setAttribute(this.tags.getProperty(Constants.TAG_COPYRIGHT_AUTHOR), author);
		if(year!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_COPYRIGHT_YEAR)).setText(String.valueOf(year)));
		if(license!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_COPYRIGHT_LICENSE)).setText(license));
		
		return element;
	}
	
	private Element getElement(Extensions extensions) throws IllegalArgumentException{
		if(extensions==null)
			throw new IllegalArgumentException("Error creating extensions element. Extensions must not be null");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_GPX_EXTENSIONS));
		Iterator<String> keys = extensions.getKeys().iterator();
		String key = null;
		while(keys.hasNext()){
			key = keys.next();
			element.addContent(new Element(key).setText(extensions.getValue(key)));
		}
		
		return element;
	}
	
	private Element getElement(Waypoint waypoint) throws IllegalArgumentException{
		if(waypoint==null)
			throw new IllegalArgumentException("Error creating waypoint element. Waypoint must not be null");
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_WPT));
		Double latitude = waypoint.getLatitude();
		Double longitude = waypoint.getLongitude();
		if(latitude==null || longitude==null)
			throw new IllegalArgumentException("Error creating waypoint element. Latitude and longitude must not be null");
		
		element.setAttribute(this.tags.getProperty(Constants.TAG_WPT_LAT), String.valueOf(latitude));
		element.setAttribute(this.tags.getProperty(Constants.TAG_WPT_LON), String.valueOf(longitude));
		
		Double elevation = waypoint.getElevation();
		Calendar time = waypoint.getTime();
		Degrees magvar  =waypoint.getMagvar();
		Double geoidheight = waypoint.getGeoIdHeight();
		String name = waypoint.getName();
		String cmt = waypoint.getCmt();
		String desc = waypoint.getDescription();
		String src = waypoint.getSrc();
		List<Link> links = waypoint.getLinks();
		String sym = waypoint.getSym();
		String type = waypoint.getType();
		Fix fix = waypoint.getFix();
		Integer sat = waypoint.getSat();
		Double hdop = waypoint.getHdop();
		Double vdop = waypoint.getVdop();
		Double pdop = waypoint.getPdop();
		Double ageofdgpsdata = waypoint.getAgeOfDgpsData();
		DgpsStation dgpsstation = waypoint.getdGpsId();
		Extensions extensions = waypoint.getExtensions();
		
		if(elevation!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_ELE)).setText(String.valueOf(elevation)));
		if(time!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_TIME)).setText(this.dateToStrint(time)));
		if(magvar!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_MAGVAR)).setText(String.valueOf(magvar.getDegrees())));
		if(geoidheight!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_GEOIDHEIGHT)).setText(String.valueOf(geoidheight)));
		if(name!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_NAME)).setText(name));
		if(cmt!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_CMT)).setText(cmt));
		if(desc!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_DESC)).setText(desc));
		if(src!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_SRC)).setText(src));
		if(links!=null && links.size()>0)
			for(int i=0;i<links.size();i++)
				element.addContent(this.getElement(links.get(i)));
		if(sym!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_SYM)).setText(sym));
		if(type!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_TYPE)).setText(type));
		if(fix!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_FIX)).setText(String.valueOf(fix.getFix())));
		if(sat!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_SAT)).setText(String.valueOf(sat)));
		if(hdop!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_HDOP)).setText(String.valueOf(hdop)));
		if(vdop!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_VDOP)).setText(String.valueOf(vdop)));
		if(pdop!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_PDOP)).setText(String.valueOf(pdop)));
		if(ageofdgpsdata!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_AGEOFDGPSDATA)).setText(String.valueOf(ageofdgpsdata)));
		if(dgpsstation!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_WPT_DGPSID)).setText(String.valueOf(dgpsstation.getDgpsStation())));
		if(extensions!=null && extensions.count()>0)
			element.addContent(this.getElement(extensions));
		
		return element;
	}
	
	private Element getElement(Route route) throws IllegalArgumentException{
		if(route==null)
			throw new IllegalArgumentException("Error creating route element. Route must not be null");
		
		String name = route.getName();
		String cmt = route.getCmt();
		String desc = route.getDesc();
		String src = route.getSrc();
		List<Link> links = route.getLinks();
		Integer number = route.getNumber();
		String type = route.getType();
		Extensions extensions = route.getExtensions();
		List<Waypoint> waypoints = route.getWaypoints();
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_RTE));
		if(name!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_RTE_NAME)).setText(name));
		if(cmt!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_RTE_CMT)).setText(cmt));
		if(desc!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_RTE_DESC)).setText(desc));
		if(src!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_RTE_SRC)).setText(src));
		if(links!=null && links.size()>0)
			for(int i=0;i<links.size();i++)
				element.addContent(this.getElement(links.get(i)));
		if(number!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_RTE_NUMBER)).setText(String.valueOf(number)));
		if(type!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_RTE_TYPE)).setText(type));
		if(extensions!=null)
			element.addContent(this.getElement(extensions));
		if(waypoints!=null && waypoints.size()>0)
			for(int i=0;i<waypoints.size();i++)
				element.addContent(this.getElement(waypoints.get(i)).setName(this.tags.getProperty(Constants.TAG_RTE_RTEPT)));
		
		return element;
	}
	
	private Element getElement(Track track) throws IllegalArgumentException{
		if(track==null)
			throw new IllegalArgumentException("Error creating track element. Track must not be null");
		
		String name = track.getName();
		String cmt = track.getCmt();
		String desc = track.getDesc();
		String src = track.getSrc();
		List<Link> links = track.getLinks();
		Integer number = track.getNumber();
		String type = track.getType();
		Extensions extensions = track.getExtensions();
		List<TrackSegment> trackSegments = track.getTrackSegments();
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_TRK));
		if(name!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_TRK_NAME)).setText(name));
		if(cmt!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_TRK_CMT)).setText(cmt));
		if(desc!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_TRK_DESC)).setText(desc));
		if(src!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_TRK_SRC)).setText(src));
		if(links!=null && links.size()>0)
			for(int i=0;i<links.size();i++)
				this.getElement(links.get(i));
		if(number!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_TRK_NUMBER)).setText(String.valueOf(number)));
		if(type!=null)
			element.addContent(new Element(this.tags.getProperty(Constants.TAG_TRK_TYPE)).setText(type));
		if(extensions!=null && extensions.count()>0)
			element.addContent(this.getElement(extensions));
		if(trackSegments!=null && trackSegments.size()>0)
			for(int i=0;i<trackSegments.size();i++)
				element.addContent(this.getElement(trackSegments.get(i)));
		
		return element;
	}
	
	private Element getElement(TrackSegment trackSegment) throws IllegalArgumentException{
		if(trackSegment==null)
			throw new IllegalArgumentException("Error creating tracksegment element. TrackSegment must not be null");
		
		List<Waypoint> waypoints = trackSegment.getWaypoints();
		Extensions extensions = trackSegment.getExtensions();
		
		Element element = new Element(this.tags.getProperty(Constants.TAG_TRKSEG));
		if(waypoints!=null && waypoints.size()>0)
			for(int i=0;i<waypoints.size();i++)
				element.addContent(this.getElement(waypoints.get(i)).setName(this.tags.getProperty(Constants.TAG_TRKSEG_TRKPT)));
		if(extensions!=null)
			element.addContent(this.getElement(extensions));
		
		return element;
	}
	
	public void write(GpxDocument doc, String filePath) throws GpxIOException, GpxPropertiesException, GpxFileNotFoundException{
		try {
			this.write(doc, new FileOutputStream(filePath));
		}catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException(e.getMessage());
		}
	}
	
	public void write(GpxDocument doc, OutputStream output) throws GpxPropertiesException, GpxIOException{
		this.appProperties = GpxDriver.getGpxDriver().getProperties();
		if(this.appProperties == null) throw new GpxPropertiesException("Properties not loaded. Please load properties from driver");
		
		Document root = new Document(this.getElement(doc));
		
		Format format = Format.getPrettyFormat();
		format.setIndent(this.appProperties.getProperty(Constants.DRIVER_WRITER_INDENTATION_TEXT, "\t"));
		format.setLineSeparator((Boolean.valueOf(this.appProperties.getProperty(Constants.DRIVER_WRITER_NEW_LINE, "true")))?"\n":"");
		format.setExpandEmptyElements(Boolean.valueOf(this.appProperties.getProperty(Constants.DRIVER_EXPAND_EMPTY_ELEMENTS, "false")));
		XMLOutputter outputter = new XMLOutputter(format);
		
		try {
			outputter.output(root, output);
		} catch (IOException e) {
			throw new GpxIOException("Error creating the gpx file", e);
		}
	}
	
	public String writeToString(GpxDocument doc) throws GpxPropertiesException{
		this.appProperties = GpxDriver.getGpxDriver().getProperties();
		if(this.appProperties == null) throw new GpxPropertiesException("Properties not loaded. Please load properties from driver");
		
		Document root = new Document(this.getElement(doc));
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		return outputter.outputString(root);
	}
}
