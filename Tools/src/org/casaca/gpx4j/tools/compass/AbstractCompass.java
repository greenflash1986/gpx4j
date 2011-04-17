package org.casaca.gpx4j.tools.compass;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.tools.Tool;
import org.casaca.gpx4j.tools.data.CardinalDirection;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.util.Constants;

public class AbstractCompass extends Tool implements ICompass {

	public AbstractCompass(Properties props) {
		super(props);
	}

	@Override
	public MeasurementUnit getUnit() {
		return MeasurementUnit.DEGREES;
	}

	@Override
	public <T extends CoordinatesObject> BigDecimal getBearing(T c1, T c2) {
		double rLat1 = Math.toRadians(c1.getLatitude().doubleValue());
		double rLon1 = Math.toRadians(c1.getLongitude().doubleValue());
		double rLat2 = Math.toRadians(c2.getLatitude().doubleValue());
		double rLon2 = Math.toRadians(c2.getLongitude().doubleValue());
		double dLon = rLon2-rLon1;
		
		double bearing = Math.atan2(
				Math.sin(dLon)*Math.cos(rLat2),
				Math.cos(rLat1)*Math.sin(rLat2)-Math.sin(rLat1)*Math.cos(rLat2)*Math.cos(dLon));
		
		bearing = Math.toDegrees(bearing);
		bearing = (bearing+360)%360;
		return new BigDecimal(Double.toString(bearing));
	}

	@Override
	public CardinalDirection getCardinalDirection(BigDecimal bearing) {
		if(bearing.compareTo(BigDecimal.ZERO)==-1 || bearing.compareTo(BigDecimal.valueOf(360))==1)
			throw new IllegalArgumentException("Bearing must be greater than zero and lesser than 360");
		
		int sector = bearing.divide(new BigDecimal("11.25"), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT).intValue();
		CardinalDirection cd;
		switch(sector){
			case 0:
			case 31:{
				cd = CardinalDirection.N;
				break;
			}
			case 1:
			case 2:{
				cd = CardinalDirection.NNE;
				break;
			}
			case 3:
			case 4:{
				cd = CardinalDirection.NE;
				break;
			}
			case 5:
			case 6:{
				cd = CardinalDirection.ENE;
				break;
			}
			case 7:
			case 8:{
				cd = CardinalDirection.E;
				break;
			}
			case 9:
			case 10:{
				cd = CardinalDirection.ESE;
				break;
			}
			case 11:
			case 12:{
				cd = CardinalDirection.SE;
				break;
			}
			case 13:
			case 14:{
				cd = CardinalDirection.SSE;
				break;
			}
			case 15:
			case 16:{
				cd = CardinalDirection.S;
				break;
			}
			case 17:
			case 18:{
				cd = CardinalDirection.SSW;
				break;
			}
			case 19:
			case 20:{
				cd = CardinalDirection.SW;
				break;
			}
			case 21:
			case 22:{
				cd = CardinalDirection.WSW;
				break;
			}
			case 23:
			case 24:{
				cd = CardinalDirection.W;
				break;
			}
			case 25:
			case 26:{
				cd = CardinalDirection.WNW;
				break;
			}
			case 27:
			case 28:{
				cd = CardinalDirection.NW;
				break;
			}
			case 29:
			case 30:{
				cd = CardinalDirection.NNW;
				break;
			}
			default:{
				cd = CardinalDirection.N;
				break;
			}
		}
		
		return cd;
	}
}