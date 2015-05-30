package org.casaca.gpx4j.tools.util;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.Tool;
import org.casaca.gpx4j.tools.data.MeasurementUnit;

public class Converter extends Tool{
	
	private Formatter fm;
	
	public Converter(Properties props) throws GpxPropertiesException {
		super(props);
		this.fm = GpxTools.getTools().getFormatter();
	}

	public String latitudeFromDecimalToSexagesimal(BigDecimal latitude){
		if(latitude==null) return "00¼00'00\"N";
		try{
			BigDecimal[] d = latitude.divideAndRemainder(BigDecimal.valueOf(1), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
			StringBuffer l = new StringBuffer();
			l.append(d[0].intValue()).append(MeasurementUnit.DEGREES.getSymbol());
			d=d[1].divideAndRemainder(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);//NOT 1/60 --> 1/60.0 forces to double, 1/60 forces to int and divides by zero
			l.append(d[0].intValue()).append(MeasurementUnit.DEGREES_MINUTES.getSymbol());
			d[0]=this.fm.formatNumber(d[1].divide(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
			l.append(d[0])
			//.append((d[1].compareTo(BigDecimal.valueOf(0.0))==0)?"":"."+d[1].toPlainString().split("[.]")[1].substring(0,2))
			.append(MeasurementUnit.DEGREES_SECONDS.getSymbol())
			.append((latitude.signum()>0)?"N":"S");
			
			return l.toString();
		}
		catch(StringIndexOutOfBoundsException except){
			throw new IllegalArgumentException("Malformed parameter. Coordinate example: 14"+MeasurementUnit.DEGREES.getSymbol()+" 28"+MeasurementUnit.DEGREES_MINUTES.getSymbol()+" 14"+MeasurementUnit.DEGREES_SECONDS.getSymbol()+"N");
		}
	}
	
	public String longitudeFromDecimalToSexagesimal(BigDecimal longitude){
		if(longitude==null) return "00¼00'00\"E";
		try{
			BigDecimal[] d = longitude.divideAndRemainder(BigDecimal.valueOf(1), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
			StringBuffer l = new StringBuffer();
			l.append(d[0].intValue()).append(MeasurementUnit.DEGREES.getSymbol());
			d=d[1].divideAndRemainder(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);//NOT 1/60 --> 1/60.0 forces to double, 1/60 forces to int and divides by zero
			l.append(d[0].intValue()).append(MeasurementUnit.DEGREES_MINUTES.getSymbol());
			d[0]=this.fm.formatNumber(d[1].divide(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
			l.append(d[0])
			//.append((d[1].compareTo(BigDecimal.valueOf(0.0))==0)?"":"."+d[1].toPlainString().split("[.]")[1].substring(0,2))
			.append(MeasurementUnit.DEGREES_SECONDS.getSymbol())
			.append((longitude.signum()>0)?"E":"W");
			
			return l.toString();
		}
		catch(StringIndexOutOfBoundsException except){
			throw new IllegalArgumentException("Malformed parameter. Coordinate example: 14"+MeasurementUnit.DEGREES.getSymbol()+" 28"+MeasurementUnit.DEGREES_MINUTES.getSymbol()+" 14"+MeasurementUnit.DEGREES_SECONDS.getSymbol()+"N");
		}
	}
	
	public BigDecimal latitudeFromSexagesimalToDecimal(String latitude) throws NumberFormatException{
		if(latitude==null) return BigDecimal.ZERO;
		try{
			BigDecimal r = BigDecimal.ZERO;
			r = r.add(BigDecimal.valueOf(Long.valueOf(latitude.substring(0, latitude.indexOf(MeasurementUnit.DEGREES.getSymbol())))));
			String tmp = latitude.substring(latitude.indexOf(MeasurementUnit.DEGREES.getSymbol())+1);
			if(tmp.contains(MeasurementUnit.DEGREES_MINUTES.getSymbol())){
				r = r.add(BigDecimal.valueOf(Long.valueOf(tmp.substring(0, tmp.indexOf(MeasurementUnit.DEGREES_MINUTES.getSymbol())))).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
				tmp = tmp.substring(tmp.indexOf(MeasurementUnit.DEGREES_MINUTES.getSymbol())+1);
				if(tmp.contains(MeasurementUnit.DEGREES_SECONDS.getSymbol())){
					r = r.add(BigDecimal.valueOf(Double.valueOf(tmp.substring(0, tmp.indexOf(MeasurementUnit.DEGREES_SECONDS.getSymbol())))).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
				}
			}
			if(tmp.substring(tmp.length()-1).toUpperCase().equals("S")) r = r.negate();
			
			return this.fm.formatNumber(r);
		}
		catch(StringIndexOutOfBoundsException except){
			throw new IllegalArgumentException("Malformed parameter. Coordinate example: 14"+MeasurementUnit.DEGREES.getSymbol()+" 28"+MeasurementUnit.DEGREES_MINUTES.getSymbol()+" 14"+MeasurementUnit.DEGREES_SECONDS.getSymbol()+"N");
		}
	}
	
	public BigDecimal longitudeFromSexagesimalToDecimal(String longitude) throws NumberFormatException{
		if(longitude==null) return BigDecimal.ZERO;
		try{
			BigDecimal r = BigDecimal.ZERO;
			r = r.add(BigDecimal.valueOf(Long.valueOf(longitude.substring(0, longitude.indexOf(MeasurementUnit.DEGREES.getSymbol())))));
			String tmp = longitude.substring(longitude.indexOf(MeasurementUnit.DEGREES.getSymbol())+1);
			if(tmp.contains(MeasurementUnit.DEGREES_MINUTES.getSymbol())){
				r = r.add(BigDecimal.valueOf(Long.valueOf(tmp.substring(0, tmp.indexOf(MeasurementUnit.DEGREES_MINUTES.getSymbol())))).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
				tmp = tmp.substring(tmp.indexOf(MeasurementUnit.DEGREES_MINUTES.getSymbol())+1);
				if(tmp.contains(MeasurementUnit.DEGREES_SECONDS.getSymbol())){
					r = r.add(BigDecimal.valueOf(Double.valueOf(tmp.substring(0, tmp.indexOf(MeasurementUnit.DEGREES_SECONDS.getSymbol())))).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
				}
			}
			if(tmp.substring(tmp.length()-1).toUpperCase().equals("W")) r = r.negate();
			
			return this.fm.formatNumber(r);
		}
		catch(StringIndexOutOfBoundsException except){
			throw new IllegalArgumentException("Malformed parameter. Coordinate example: 14"+MeasurementUnit.DEGREES.getSymbol()+" 28"+MeasurementUnit.DEGREES_MINUTES.getSymbol()+" 14"+MeasurementUnit.DEGREES_SECONDS.getSymbol()+"N");
		}
	}
}