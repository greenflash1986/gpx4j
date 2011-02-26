package org.casaca.gpx4j.core.data;

public class DgpsStation extends BaseObject {
	public static final int DGPS_STATION_MIN_VALUE = 0;
	public static final int DGPS_STATION_MAX_VALUE = 1023;
	
	private Integer dgpsStation;
	
	public DgpsStation(Integer dgpsStation){
		if(dgpsStation == null || dgpsStation<DGPS_STATION_MIN_VALUE || dgpsStation>DGPS_STATION_MAX_VALUE) throw new IllegalArgumentException("DgpsStation must be greater than "+DGPS_STATION_MIN_VALUE+" and less than "+DGPS_STATION_MAX_VALUE);
		
		this.dgpsStation=dgpsStation;
	}
	
	public Integer getDgpsStation(){
		return this.dgpsStation;
	}
}
