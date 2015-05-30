package org.casaca.gpx4j.core.driver.jaxb;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.casaca.gpx4j.core.data.Bounds;
import org.casaca.gpx4j.core.data.Copyright;
import org.casaca.gpx4j.core.data.Degrees;
import org.casaca.gpx4j.core.data.DgpsStation;
import org.casaca.gpx4j.core.data.Email;
import org.casaca.gpx4j.core.data.Extension;
import org.casaca.gpx4j.core.data.Extensions;
import org.casaca.gpx4j.core.data.Fix;
import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.IGpxAdapter;
import org.casaca.gpx4j.core.data.Link;
import org.casaca.gpx4j.core.data.Metadata;
import org.casaca.gpx4j.core.data.Person;
import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.logging.Logger;

public class JaxbAdapter implements IGpxAdapter {
	
	private Logger logger;
	
	public JaxbAdapter(){
		this.logger = Logger.getLogger(this.getClass());
	}

	@Override
	public Bounds toBounds(Object bounds) {
		if(bounds==null)
			return null;
		
		BoundsType bType = (BoundsType) bounds;
		Bounds b = new Bounds(bType.getMinlat(), bType.getMinlon(), bType.getMaxlat(), bType.getMaxlon());
		
		return b;
	}

	@Override
	public Object fromBounds(Bounds bounds) {
		if(bounds==null)
			return null;
		
		BoundsType bType = new BoundsType();
		bType.setMinlat(bounds.getMinLatitude());
		bType.setMinlon(bounds.getMinLongitude());
		bType.setMaxlat(bounds.getMaxLatitude());
		bType.setMaxlon(bounds.getMaxLongitude());
		
		return bType;
	}

	@Override
	public Copyright toCopyright(Object copyright) {
		if(copyright==null)
			return null;
		
		CopyrightType cType = (CopyrightType)copyright;
		Copyright c = new Copyright((cType.getYear()==null)?null:cType.getYear().toGregorianCalendar(), cType.getAuthor(), cType.getLicense());
		
		return c;
	}

	@Override
	public Object fromCopyright(Copyright copyright) {
		if(copyright==null)
			return null;
		
		CopyrightType cType = new CopyrightType();
		cType.setAuthor(copyright.getAuthor());
		cType.setLicense(copyright.getLicense());
		try {
			cType.setYear((copyright.getYear()==null)?null:DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)copyright.getYear()));
		} catch (DatatypeConfigurationException e) {
			this.logger.error(e.getMessage());
			return null;
		}
		
		return cType;
	}

	@Override
	public Degrees toDegrees(Object degrees) {
		if(degrees==null)
			return null;
		
		return new Degrees((BigDecimal)degrees);
	}

	@Override
	public Object fromDegrees(Degrees degrees) {
		if(degrees==null)
			return null;
		
		return degrees.getDegrees();
	}

	@Override
	public DgpsStation toDgpsStation(Object dgpsStation) {
		if(dgpsStation==null)
			return null;
		
		return new DgpsStation((Integer)dgpsStation);
	}

	@Override
	public Object fromDgpsStation(DgpsStation dgpsStation) {
		if(dgpsStation==null)
			return null;
		
		return dgpsStation.getDgpsStation();
	}

	@Override
	public Email toEmail(Object email) {
		if(email==null)
			return null;
		
		EmailType eType = (EmailType)email;
		Email e = new Email(eType.getId(), eType.getDomain());
		
		return e;
	}

	@Override
	public Object fromEmail(Email email) {
		if(email==null)
			return null;
		
		EmailType eType = new EmailType();
		eType.setId(email.getUser());
		eType.setDomain(email.getDomain());
		
		return eType;
	}

	@Override
	public Extension toExtension(Object extension) {
		//This driver version doen't support extensions 
		return null;
	}

	@Override
	public Object fromExtension(Extension extension) {
		//This driver version doen't support extensions 
		return null;
	}

	@Override
	public Extensions toExtensions(Object extensions) {
		//This driver version doen't support extensions 
		return null;
	}

	@Override
	public Object fromExtensions(Extensions extensions) {
		//This driver version doen't support extensions 
		return null;
	}

	@Override
	public Fix toFix(Object fix) {
		if(fix==null)
			return null;
		
		return new Fix(Integer.valueOf(String.valueOf(fix)));
	}

	@Override
	public Object fromFix(Fix fix) {
		if(fix==null)
			return null;
		
		return fix.getFix();
	}

	@Override
	public GpxDocument toGpxDocument(Object gpxDocument) {
		if(gpxDocument==null)
			return null;
		
		GpxType gType = (GpxType)gpxDocument;
		GpxDocument g = new GpxDocument();
		g.setVersion(gType.getVersion());
		g.setCreator(gType.getCreator());
		g.setMetadata(this.toMetadata(gType.getMetadata()));
		Iterator<WptType> wpts = gType.getWpt().iterator();
		while(wpts.hasNext())
			g.getWaypoints().add(this.toWaypoint(wpts.next()));
		Iterator<RteType> rtes = gType.getRte().iterator();
		while(rtes.hasNext())
			g.getRoutes().add(this.toRoute(rtes.next()));
		Iterator<TrkType> trks = gType.getTrk().iterator();
		while(trks.hasNext())
			g.getTracks().add(this.toTrack(trks.next()));
		//g.setExtensions(this.toExtensions(gType.getExtensions()));
		
		return g;
	}

	@Override
	public Object fromGpxDocument(GpxDocument document) {
		if(document==null)
			return null;
		
		GpxType gType = new GpxType();
		gType.setVersion(document.getVersion());
		gType.setCreator(document.getCreator());
		gType.setMetadata((MetadataType)this.fromMetadata(document.getMetadata()));
		Iterator<Waypoint> waypoints = document.getWaypoints().iterator();
		while(waypoints.hasNext())
			gType.getWpt().add((WptType)this.fromWaypoint(waypoints.next()));
		Iterator<Route> routes = document.getRoutes().iterator();
		while(routes.hasNext())
			gType.getRte().add((RteType)this.fromRoute(routes.next()));
		Iterator<Track> tracks = document.getTracks().iterator();
		while(tracks.hasNext())
			gType.getTrk().add((TrkType)this.fromTrack(tracks.next()));
		//gType.setExtensions((ExtensionsType)this.fromExtensions(document.getExtensions()));
		
		return gType;
	}

	@Override
	public Link toLink(Object link) {
		if(link==null)
			return null;
		
		LinkType lType = (LinkType)link;
		Link l = new Link();
		try {
			l.setHref(new URL(lType.getHref()));
			l.setText(lType.getText());
			l.setType(lType.getType());
			return l;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	@Override
	public Object fromLink(Link link) {
		if(link==null)
			return null;
		
		LinkType lType = new LinkType();
		lType.setHref((link.getHref()==null)?null:link.getHref().toString());
		lType.setText(link.getText());
		lType.setType(link.getType());
		
		return lType;
	}

	@Override
	public Metadata toMetadata(Object metadata) {
		if(metadata==null)
			return null;
		
		MetadataType mType = (MetadataType)metadata;
		Metadata m = new Metadata();
		m.setName(mType.getName());
		m.setDesc(mType.getDesc());
		m.setAuthor(this.toPerson(mType.getAuthor()));
		m.setCopyright(this.toCopyright(mType.getCopyright()));
		Iterator<LinkType> iterator = mType.getLink().iterator();
		while(iterator.hasNext())
			m.getLinks().add(this.toLink(iterator.next()));
		m.setDate((mType.getTime()==null)?null:mType.getTime().toGregorianCalendar());
		m.setKeywords(mType.getKeywords());
		m.setBounds(this.toBounds(mType.getBounds()));
		//m.setExtensions(this.toExtensions(mType.getExtensions()));
		
		return m;
	}

	@Override
	public Object fromMetadata(Metadata metadata) {
		if(metadata==null)
			return null;
		
		MetadataType mType = new MetadataType();
		mType.setAuthor((PersonType)this.fromPerson(metadata.getAuthor()));
		mType.setBounds((BoundsType)this.fromBounds(metadata.getBounds()));
		mType.setCopyright((CopyrightType)this.fromCopyright(metadata.getCopyright()));
		mType.setDesc(metadata.getDesc());
		//mType.setExtensions((ExtensionsType)this.fromExtensions(metadata.getExtensions()));
		mType.setKeywords(metadata.getKeywords());
		mType.setName(metadata.getName());
		try {
			mType.setTime((metadata.getDate()==null)?null:DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)metadata.getDate()));
		} catch (DatatypeConfigurationException e) {
			this.logger.error(e.getMessage());
			return null;
		}
		
		return mType;
	}

	@Override
	public Person toPerson(Object person) {
		if(person==null)
			return null;
		
		PersonType pType = (PersonType)person;
		Person p = new Person();
		p.setName(pType.getName());
		p.setLink(this.toLink(pType.getLink()));
		p.setEmail(this.toEmail(pType.getEmail()));
		
		return p;
	}

	@Override
	public Object fromPerson(Person person) {
		if(person==null)
			return null;
		
		PersonType pType = new PersonType();
		pType.setName(person.getName());
		pType.setEmail((EmailType)this.fromEmail(person.getEmail()));
		pType.setLink((LinkType)this.fromLink(person.getLink()));
		
		return pType;
	}

	@Override
	public Point toPoint(Object point) {
		if(point==null)
			return null;
		
		PtType pType = (PtType)point;
		Point p = new Point(pType.getLat(), pType.getLon());
		p.setTime((pType.getTime()==null)?null:pType.getTime().toGregorianCalendar());
		p.setElevation(pType.getEle());
		
		return p;
	}

	@Override
	public Object fromPoint(Point point) {
		if(point==null)
			return null;
		
		PtType pType = new PtType();
		pType.setLat(point.getLatitude());
		pType.setLon(point.getLongitude());
		pType.setEle(point.getElevation());
		try {
			pType.setTime((point.getTime()==null)?null:DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)point.getTime()));
		} catch (DatatypeConfigurationException e) {
			this.logger.error(e.getMessage());
			return null;
		}
		
		return pType;
	}

	@Override
	public PointsSequence toPointSequence(Object ptSeg) {
		if(ptSeg==null)
			return null;
		
		PtsegType pType = (PtsegType)ptSeg;
		PointsSequence p = new PointsSequence();
		Iterator<PtType> iterator = pType.getPt().iterator();
		while(iterator.hasNext())
			p.getPoints().add(this.toPoint(iterator.next()));
		
		return p;
	}

	@Override
	public Object fromPointSequence(PointsSequence pointsSequence) {
		if(pointsSequence==null)
			return null;
		
		PtsegType pType = new PtsegType();		
		Iterator<Point> points = pointsSequence.getPoints().iterator();
		while(points.hasNext())
			pType.getPt().add((PtType)this.fromPoint(points.next()));
		
		return pType;
	}

	@Override
	public Route toRoute(Object route) {
		if(route==null)
			return null;
		
		RteType rType = (RteType)route;
		Route r = new Route();
		r.setName(rType.getName());
		r.setCmt(rType.getCmt());
		r.setDesc(rType.getDesc());
		r.setSrc(rType.getSrc());
		Iterator<LinkType> iterator = rType.getLink().iterator();
		while(iterator.hasNext())
			r.getLinks().add(this.toLink(iterator.next()));
		r.setNumber(rType.getNumber());
		r.setType(rType.getType());
		//r.getExtensions().getExtensions().putAll(this.toExtensions(rType.getExtensions()).getExtensions());
		Iterator<WptType> iterator2 = rType.getRtept().iterator();
		while(iterator2.hasNext())
			r.getWaypoints().add(this.toWaypoint(iterator2.next()));
		
		return r;
	}

	@Override
	public Object fromRoute(Route route) {
		if(route==null)
			return null;
		
		RteType rType = new RteType();
		rType.setName(route.getName());
		rType.setCmt(route.getCmt());
		rType.setDesc(route.getDesc());
		rType.setSrc(route.getSrc());
		Iterator<Link> links = route.getLinks().iterator();
		while(links.hasNext())
			rType.getLink().add((LinkType)this.fromLink(links.next()));
		rType.setNumber(route.getNumber());
		rType.setType(route.getType());
		//rType.setExtensions((ExtensionsType)this.fromExtensions(route.getExtensions()));
		Iterator<Waypoint> waypoints = route.getWaypoints().iterator();
		while(waypoints.hasNext())
			rType.getRtept().add((WptType)this.fromWaypoint(waypoints.next()));
		
		return rType;
	}

	@Override
	public Track toTrack(Object track) {
		if(track==null)
			return null;
		
		TrkType tType = (TrkType)track;
		Track t = new Track();
		t.setName(tType.getName());
		t.setCmt(tType.getCmt());
		t.setDesc(tType.getDesc());
		t.setSrc(tType.getSrc());
		Iterator<LinkType> iterator = tType.getLink().iterator();
		while(iterator.hasNext())
			t.getLinks().add(this.toLink(iterator.next()));
		t.setNumber(tType.getNumber());
		t.setType(tType.getType());
		//t.getExtensions().getExtensions().putAll(this.toExtensions(tType.getExtensions()).getExtensions());
		Iterator<TrksegType> iterator2 = tType.getTrkseg().iterator();
		while(iterator2.hasNext())
			t.getTrackSegments().add(this.toTrackSegment(iterator2.next()));
		
		return t;
	}

	@Override
	public Object fromTrack(Track track) {
		if(track==null)
			return null;
		
		TrkType tType = new TrkType();
		tType.setName(track.getName());
		tType.setCmt(track.getCmt());
		tType.setDesc(track.getDesc());
		tType.setSrc(track.getSrc());
		Iterator<Link> links = track.getLinks().iterator();
		while(links.hasNext())
			tType.getLink().add((LinkType)this.fromLink(links.next()));
		tType.setNumber(track.getNumber());
		tType.setType(track.getType());
		//tType.setExtensions((ExtensionsType)this.fromExtensions(track.getExtensions()));
		Iterator<TrackSegment> trackSegments = track.getTrackSegments().iterator();
		while(trackSegments.hasNext())
			tType.getTrkseg().add((TrksegType)this.fromTrackSegment(trackSegments.next()));
		
		return tType;
	}

	@Override
	public TrackSegment toTrackSegment(Object trackSegment) {
		if(trackSegment==null)
			return null;
		
		TrksegType tType = (TrksegType)trackSegment;
		TrackSegment t = new TrackSegment();
		Iterator<WptType> iterator = tType.getTrkpt().iterator();
		while(iterator.hasNext())
			t.getWaypoints().add(this.toWaypoint(iterator.next()));
		//t.setExtensions(this.toExtensions(tType.getExtensions()));
		
		return t;
	}

	@Override
	public Object fromTrackSegment(TrackSegment trackSegment) {
		if(trackSegment==null)
			return null;
		
		TrksegType tType = new TrksegType();
		Iterator<Waypoint> waypoints = trackSegment.getWaypoints().iterator();
		while(waypoints.hasNext())
			tType.getTrkpt().add((WptType)this.fromWaypoint(waypoints.next()));
		//tType.setExtensions((ExtensionsType)this.fromExtensions(trackSegment.getExtensions()));
		
		return tType;
	}

	@Override
	public Waypoint toWaypoint(Object waypoint) {
		if(waypoint==null)
			return null;
		
		WptType wType = (WptType)waypoint;
		Waypoint w = new Waypoint(wType.getLat(), wType.getLon());
		w.setElevation(wType.getEle());
		w.setTime((wType.getTime()==null)?null:wType.getTime().toGregorianCalendar());
		w.setMagvar(this.toDegrees(wType.getMagvar()));
		w.setGeoIdHeight(wType.getGeoidheight());
		w.setName(wType.getName());
		w.setCmt(wType.getCmt());
		w.setDescription(wType.getDesc());
		w.setSrc(wType.getSrc());
		Iterator<LinkType> iterator = wType.getLink().iterator();
		while(iterator.hasNext())
			w.getLinks().add(this.toLink(iterator.next()));
		w.setSym(wType.getSym());
		w.setType(wType.getType());
		w.setFix(this.toFix(wType.getFix()));
		w.setSat(wType.getSat());
		w.setHdop(wType.getHdop());
		w.setVdop(wType.getVdop());
		w.setPdop(wType.getPdop());
		w.setAgeOfDgpsData(wType.getAgeofdgpsdata());
		w.setdGpsId(this.toDgpsStation(wType.getDgpsid()));
		//w.setExtensions(this.toExtensions(wType.getExtensions()));
		
		return w;
	}

	@Override
	public Object fromWaypoint(Waypoint waypoint) {
		if(waypoint==null)
			return null;
		
		WptType wType = new WptType();
		wType.setLat(waypoint.getLatitude());
		wType.setLon(waypoint.getLongitude());
		wType.setEle(waypoint.getElevation());
		try {
			wType.setTime((waypoint.getTime()==null)?null:DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)waypoint.getTime()));
		} catch (DatatypeConfigurationException e) {
			this.logger.error(e.getMessage());
			return null;
		}
		wType.setMagvar((BigDecimal)this.fromDegrees(waypoint.getMagvar()));
		wType.setGeoidheight(waypoint.getGeoIdHeight());
		wType.setName(waypoint.getName());
		wType.setCmt(waypoint.getCmt());
		wType.setDesc(waypoint.getDescription());
		wType.setSrc(waypoint.getSrc());
		Iterator<Link> links = waypoint.getLinks().iterator();
		while(links.hasNext())
			wType.getLink().add((LinkType)this.fromLink(links.next()));
		wType.setSym(waypoint.getSym());
		wType.setType(waypoint.getType());
		wType.setFix(String.valueOf(this.fromFix(waypoint.getFix())));
		wType.setSat(waypoint.getSat());
		wType.setHdop(waypoint.getHdop());
		wType.setVdop(waypoint.getVdop());
		wType.setPdop(waypoint.getPdop());
		wType.setAgeofdgpsdata(waypoint.getAgeOfDgpsData());
		wType.setDgpsid((Integer)this.fromDgpsStation(waypoint.getdGpsId()));
		//wType.setExtensions((ExtensionsType)this.fromExtensions(waypoint.getExtensions()));
		
		return wType;
	}
}