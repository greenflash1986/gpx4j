package org.casaca.gpx4j.tools.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.casaca.gpx4j.tools.Tool;

public class Formatter extends Tool {

	private Locale locale;
	private MathContext formatMathContext;
	
	public Formatter(Properties props) {
		super(props);
		
		this.locale = null;
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
	
	public Locale getLocale(){
		if(locale==null){
			String l = this.getProperties().getProperty(Constants.TOOLS_LOCALE, Constants.APPLICATION_DEFAULT_LOCALE);
			if(l.toUpperCase().equals("DEFAULT"))
				this.locale = Locale.getDefault();
			else
				try{
					this.locale = new Locale(l.split("_")[0], l.split("_")[1]);
				}
				catch(Exception e){
					this.locale = Locale.getDefault();
				}
		}
		
		return this.locale;
	}
	
	public BigDecimal formatNumber(BigDecimal number){
		return number.setScale(this.formatMathContext.getPrecision(), this.formatMathContext.getRoundingMode());
	}
	
	public String formatDate(int dateFormat, Date date){
		DateFormat df = DateFormat.getDateInstance(dateFormat, this.getLocale());
		return df.format(date);
	}
	
	public String formatTime(int timeFormat, Date time){
		DateFormat tf = DateFormat.getTimeInstance(timeFormat, this.getLocale());
		return tf.format(time);
	}
}