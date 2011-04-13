package org.casaca.gpx4j.tools.converter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Properties;

import org.casaca.gpx4j.tools.Tool;
import org.casaca.gpx4j.tools.util.Constants;

public class Converter extends Tool{
	
	private MathContext formatMathContext;
	
	public Converter(Properties props) {
		super(props);
		
		int scale = Integer.parseInt(this.getProperties().getProperty(Constants.TOOLS_PRECISION_OPERATORS, String.valueOf(Constants.APPLICATION_DEFAULT_PRECISION_OPERATIONS)));
		RoundingMode rm;
		switch(Integer.parseInt(this.getProperties().getProperty(Constants.TOOLS_ROUNDING_MODE, String.valueOf(Constants.APPLICATION_DEFAULT_ROUNDING_MODE.ordinal())))){
			case 0:{//UP
				rm = RoundingMode.UP;
				break;
			}
			case 1:{//DOWN
				rm = RoundingMode.DOWN;
				break;
			}
			case 2:{//CEILING
				rm = RoundingMode.CEILING;
				break;
			}
			case 3:{//FLOOR
				rm = RoundingMode.FLOOR;
				break;
			}
			case 4:{//HALF UP
				rm = RoundingMode.HALF_UP;
				break;
			}
			case 5:{//HALF DOWN
				rm = RoundingMode.HALF_DOWN;
				break;
			}
			case 6:{//HALF EVEN
				rm = RoundingMode.HALF_EVEN;
				break;
			}
			case 7:{//UNNECESSARY
				rm = RoundingMode.UNNECESSARY;
				break;
			}
			default:{
				rm = Constants.APPLICATION_DEFAULT_ROUNDING_MODE;
				break;
			}
		}
		this.formatMathContext = new MathContext(scale, rm);
	}

	public String latitudeFromDecimalToSexagesimal(BigDecimal latitude){
		if(latitude==null) return "00¼00'00\"N";
		
		BigDecimal[] d = latitude.divideAndRemainder(BigDecimal.valueOf(1), this.formatMathContext);
		StringBuffer l = new StringBuffer();
		l.append(d[0].intValue()).append("¼");
		d=d[1].divideAndRemainder(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);//NOT 1/60 --> 1/60.0 forces to double, 1/60 forces to int and divides by zero
		l.append(d[0].intValue()).append("'");
		d[0]=d[1].divide(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT).setScale(this.formatMathContext.getPrecision(), this.formatMathContext.getRoundingMode());
		l.append(d[0])
		//.append((d[1].compareTo(BigDecimal.valueOf(0.0))==0)?"":"."+d[1].toPlainString().split("[.]")[1].substring(0,2))
		.append("\"")
		.append((latitude.signum()>0)?"N":"S");
		
		return l.toString();
	}
	
	public String longitudeFromDecimalToSexagesimal(BigDecimal longitude){
		if(longitude==null) return "00¼00'00\"E";
		
		BigDecimal[] d = longitude.divideAndRemainder(BigDecimal.valueOf(1), this.formatMathContext);
		StringBuffer l = new StringBuffer();
		l.append(d[0].intValue()).append("¼");
		d=d[1].divideAndRemainder(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);//NOT 1/60 --> 1/60.0 forces to double, 1/60 forces to int and divides by zero
		l.append(d[0].intValue()).append("'");
		d[0]=d[1].divide(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT).setScale(this.formatMathContext.getPrecision(), this.formatMathContext.getRoundingMode());
		l.append(d[0])
		//.append((d[1].compareTo(BigDecimal.valueOf(0.0))==0)?"":"."+d[1].toPlainString().split("[.]")[1].substring(0,2))
		.append("\"")
		.append((longitude.signum()>0)?"E":"W");
		
		return l.toString();
	}
	
	public BigDecimal latitudeFromSexagesimalToDecimal(String latitude) throws NumberFormatException{
		if(latitude==null) return BigDecimal.ZERO;
		
		BigDecimal r = BigDecimal.ZERO;
		r = r.add(BigDecimal.valueOf(Long.valueOf(latitude.substring(0, latitude.indexOf("¼")))));
		String tmp = latitude.substring(latitude.indexOf("¼")+1);
		if(tmp.contains("'")){
			r = r.add(BigDecimal.valueOf(Long.valueOf(tmp.substring(0, tmp.indexOf("'")))).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
			tmp = tmp.substring(tmp.indexOf("'")+1);
			if(tmp.contains("\"")){
				r = r.add(BigDecimal.valueOf(Double.valueOf(tmp.substring(0, tmp.indexOf("\"")))).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
			}
		}
		if(tmp.substring(tmp.length()-1).toUpperCase().equals("S")) r = r.negate();
		
		return r.setScale(this.formatMathContext.getPrecision(), this.formatMathContext.getRoundingMode());
	}
	
	public BigDecimal longitudeFromSexagesimalToDecimal(String longitude) throws NumberFormatException{
		if(longitude==null) return BigDecimal.ZERO;
		
		BigDecimal r = BigDecimal.ZERO;
		r = r.add(BigDecimal.valueOf(Long.valueOf(longitude.substring(0, longitude.indexOf("¼")))));
		String tmp = longitude.substring(longitude.indexOf("¼")+1);
		if(tmp.contains("'")){
			r = r.add(BigDecimal.valueOf(Long.valueOf(tmp.substring(0, tmp.indexOf("'")))).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
			tmp = tmp.substring(tmp.indexOf("'")+1);
			if(tmp.contains("\"")){
				r = r.add(BigDecimal.valueOf(Double.valueOf(tmp.substring(0, tmp.indexOf("\"")))).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
			}
		}
		if(tmp.substring(tmp.length()-1).toUpperCase().equals("W")) r = r.negate();
		
		return r.setScale(this.formatMathContext.getPrecision(), this.formatMathContext.getRoundingMode());
	}
}
