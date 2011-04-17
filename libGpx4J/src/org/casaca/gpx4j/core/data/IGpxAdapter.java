package org.casaca.gpx4j.core.data;

public interface IGpxAdapter {
	public Bounds toBounds(Object bounds);
	
	public Object fromBounds(Bounds bounds);
	
	public Copyright toCopyright(Object copyright);
	
	public Object fromCopyright(Copyright copyright);
	
	public Degrees toDegrees(Object degrees);
	
	public Object fromDegrees(Degrees degrees);
	
	public DgpsStation toDgpsStation(Object dgpsStation);
	
	public Object fromDgpsStation(DgpsStation dgpsStation);
	
	public Email toEmail(Object email);
	
	public Object fromEmail(Email email);
	
	public Extension<? extends IExtensible> toExtension(Object extension);
	
	public Object fromExtension(Extension<? extends IExtensible> extension);
	
	public Extensions toExtensions(Object extensions);
	
	public Object fromExtensions(Extensions extensions);
	
	public Fix toFix(Object fix);
	
	public Object fromFix(Fix fix);
	
	public GpxDocument toGpxDocument(Object gpxDocument);
	
	public Object fromGpxDocument(GpxDocument document);
	
	public Link toLink(Object link);
	
	public Object fromLink(Link link);
	
	public Metadata toMetadata(Object metadata);
	
	public Object fromMetadata(Metadata metadata);
	
	public Person toPerson(Object person);
	
	public Object fromPerson(Person person);
	
	public Point toPoint(Object point);
	
	public Object fromPoint(Point point);
	
	public PointsSequence toPointSequence(Object pointSequence);
	
	public Object fromPointSequence(PointsSequence pointsSequence);
	
	public Route toRoute(Object route);
	
	public Object fromRoute(Route route);
	
	public Track toTrack(Object track);
	
	public Object fromTrack(Track track);
	
	public TrackSegment toTrackSegment(Object trackSegment);
	
	public Object fromTrackSegment(TrackSegment trackSegment);
	
	public Waypoint toWaypoint(Object waypoint);
	
	public Object fromWaypoint(Waypoint waypoint);
}
