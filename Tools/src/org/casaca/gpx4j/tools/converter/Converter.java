package org.casaca.gpx4j.tools.converter;

import java.math.BigDecimal;

import org.casaca.gpx4j.tools.util.Constants;

public class Converter {
	
	public Converter(){
	}

	public String latitudeFromDecimalToSexagesimal(BigDecimal latitude){
		if(latitude==null) return "00¼00'00\"N";
		
		BigDecimal[] d = latitude.divideAndRemainder(BigDecimal.valueOf(1), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
		StringBuffer l = new StringBuffer();
		l.append(d[0].intValue()).append("¼");
		d=d[1].divideAndRemainder(BigDecimal.valueOf(1/60.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);//NOT 1/60 --> 1/60.0 forces to double, 1/60 forces to int and divides by zero
		l.append(d[0].intValue()).append("'");
		d=d[1].divideAndRemainder(BigDecimal.valueOf(1/3600.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
		l.append(d[0].intValue())
		.append((d[1].compareTo(BigDecimal.valueOf(0.0))==0)?"":"."+d[1].toPlainString().split("[.]")[1].substring(0,2))
		.append("\"")
		.append((latitude.signum()>0)?"N":"S");
		
		return l.toString();
	}
	
	public String longitudeFromDecimalToSexagesimal(BigDecimal longitude){
		if(longitude==null) return "00¼00'00\"E";
		
		BigDecimal[] d = longitude.divideAndRemainder(BigDecimal.valueOf(1));
		StringBuffer l = new StringBuffer();
		l.append(d[0].intValue()).append("¼");
		d=d[1].divideAndRemainder(BigDecimal.valueOf(1/60.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);//NOT 1/60 --> 1/60.0 forces to double, 1/60 forces to int and divides by zero
		l.append(d[0].intValue()).append("'");
		d=d[1].divideAndRemainder(BigDecimal.valueOf(1/3600.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
		l.append(d[0].intValue())
		.append((d[1].compareTo(BigDecimal.valueOf(0.0))==0)?"":"."+d[1].toPlainString().split("[.]")[1].substring(0,2))
		.append("\"")
		.append((longitude.signum()>0)?"E":"W");
		
		return l.toString();
	}
	
	public BigDecimal latitudeFromSexagesimalToDecimal(String latitude) throws NumberFormatException{
		if(latitude==null) return new BigDecimal(0);
		
		BigDecimal r = new BigDecimal(0.0);
		r = r.add(BigDecimal.valueOf(Long.valueOf(latitude.substring(0, latitude.indexOf("¼")))));
		String tmp = latitude.substring(latitude.indexOf("¼")+1);
		if(tmp.contains("'")){
			r = r.add(BigDecimal.valueOf(Long.valueOf(tmp.substring(0, tmp.indexOf("'")))/60.0));
			tmp = tmp.substring(tmp.indexOf("'")+1);
			if(tmp.contains("\"")){
				r = r.add(BigDecimal.valueOf(Double.valueOf(tmp.substring(0, tmp.indexOf("\"")))/3600.0));
			}
		}
		if(tmp.substring(tmp.length()-1).toUpperCase().equals("S")) r = r.negate();
		
		return r;
	}
	
	public BigDecimal longitudeFromSexagesimalToDecimal(String longitude) throws NumberFormatException{
		if(longitude==null) return new BigDecimal(0);
		
		BigDecimal r = new BigDecimal(0.0);
		r = r.add(BigDecimal.valueOf(Long.valueOf(longitude.substring(0, longitude.indexOf("¼")))));
		String tmp = longitude.substring(longitude.indexOf("¼")+1);
		if(tmp.contains("'")){
			r = r.add(BigDecimal.valueOf(Long.valueOf(tmp.substring(0, tmp.indexOf("'")))/60.0));
			tmp = tmp.substring(tmp.indexOf("'")+1);
			if(tmp.contains("\"")){
				r = r.add(BigDecimal.valueOf(Double.valueOf(tmp.substring(0, tmp.indexOf("\"")))/3600.0));
			}
		}
		if(tmp.substring(tmp.length()-1).toUpperCase().equals("W")) r = r.negate();
		
		return r;
	}
}
