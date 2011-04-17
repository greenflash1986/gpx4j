package org.casaca.gpx4j.tools.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.casaca.gpx4j.core.data.BaseObject;
import org.casaca.gpx4j.core.data.IExtensible;

public class CardinalDirection extends BaseObject implements IExtensible{
	
	//STATIC
	public static final CardinalDirection N = new CardinalDirection("north", "N");
	public static final CardinalDirection NNE = new CardinalDirection("north northeast", "NNE");
	public static final CardinalDirection NE = new CardinalDirection("northeast", "NE");
	public static final CardinalDirection ENE = new CardinalDirection("east northeast", "ENE");
	public static final CardinalDirection E = new CardinalDirection("east", "E");
	public static final CardinalDirection ESE = new CardinalDirection("east southeast", "ESE");
	public static final CardinalDirection SE = new CardinalDirection("southeast", "SE");
	public static final CardinalDirection SSE = new CardinalDirection("south southeast", "SSE");
	public static final CardinalDirection S = new CardinalDirection("south", "S");
	public static final CardinalDirection SSW = new CardinalDirection("south southwest", "SSW");
	public static final CardinalDirection SW = new CardinalDirection("southwest", "SW");
	public static final CardinalDirection WSW = new CardinalDirection("west southwest", "WSW");
	public static final CardinalDirection W = new CardinalDirection("west", "W");
	public static final CardinalDirection WNW = new CardinalDirection("west northwest", "WNW");
	public static final CardinalDirection NW = new CardinalDirection("norhtwest", "NW");
	public static final CardinalDirection NNW = new CardinalDirection("north northwest", "NNW");
	
	static{
		N.setCardinalId(1);
		N.setMinAngle(BigDecimal.valueOf(11.25));
		N.setMaxAngle(BigDecimal.valueOf(348.75));
		
		NNE.setCardinalId(2);
		NNE.setMinAngle(BigDecimal.valueOf(11.25));
		NNE.setMaxAngle(BigDecimal.valueOf(33.75));
		
		NE.setCardinalId(3);
		NE.setMinAngle(BigDecimal.valueOf(33.75));
		NE.setMaxAngle(BigDecimal.valueOf(56.25));
		
		ENE.setCardinalId(4);
		ENE.setMinAngle(BigDecimal.valueOf(56.25));
		ENE.setMaxAngle(BigDecimal.valueOf(78.75));
		
		E.setCardinalId(5);
		E.setMinAngle(BigDecimal.valueOf(78.75));
		E.setMaxAngle(BigDecimal.valueOf(101.25));
		
		ESE.setCardinalId(6);
		ESE.setMinAngle(BigDecimal.valueOf(101.25));
		ESE.setMaxAngle(BigDecimal.valueOf(123.75));
		
		SE.setCardinalId(7);
		SE.setMinAngle(BigDecimal.valueOf(123.75));
		SE.setMaxAngle(BigDecimal.valueOf(146.25));
		
		SSE.setCardinalId(8);
		SSE.setMinAngle(BigDecimal.valueOf(146.25));
		SSE.setMaxAngle(BigDecimal.valueOf(168.75));
		
		S.setCardinalId(9);
		S.setMinAngle(BigDecimal.valueOf(168.75));
		S.setMaxAngle(BigDecimal.valueOf(191.25));
		
		SSW.setCardinalId(10);
		SSW.setMinAngle(BigDecimal.valueOf(191.25));
		SSW.setMaxAngle(BigDecimal.valueOf(213.75));
		
		SW.setCardinalId(11);
		SSW.setMinAngle(BigDecimal.valueOf(213.75));
		SSW.setMaxAngle(BigDecimal.valueOf(236.25));
		
		WSW.setCardinalId(12);
		WSW.setMinAngle(BigDecimal.valueOf(236.25));
		WSW.setMaxAngle(BigDecimal.valueOf(258.75));
		
		W.setCardinalId(13);
		W.setMinAngle(BigDecimal.valueOf(258.75));
		W.setMaxAngle(BigDecimal.valueOf(281.25));
		
		WNW.setCardinalId(14);
		WNW.setMinAngle(BigDecimal.valueOf(281.25));
		WNW.setMaxAngle(BigDecimal.valueOf(303.75));
		
		NW.setCardinalId(15);
		NW.setMinAngle(BigDecimal.valueOf(303.75));
		NW.setMaxAngle(BigDecimal.valueOf(326.25));
		
		NNW.setCardinalId(16);
		NNW.setMinAngle(BigDecimal.valueOf(326.25));
		NNW.setMaxAngle(BigDecimal.valueOf(348.75));
	}
	
	//END STATIC

	private int cardinalId;
	private BigDecimal minAngle;
	private BigDecimal maxAngle;
	private String cardinalName;
	private String symbol;
	
	//IExtensible attributes
	private String name;
	//End IExtensible attributes
	
	//Coded for reflection
	private CardinalDirection(){
		super();
		this.cardinalId=0;
		this.minAngle=this.maxAngle=null;
		this.cardinalName=this.symbol=null;
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}
	
	public CardinalDirection(String name, String symbol) {
		super();
		this.cardinalName = name;
		this.symbol = symbol;
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}

	public int getCardinalId() {
		return cardinalId;
	}

	public void setCardinalId(int id) {
		this.cardinalId = id;
	}

	public BigDecimal getMinAngle() {
		return minAngle;
	}

	public void setMinAngle(BigDecimal minAngle) {
		this.minAngle = minAngle;
	}

	public BigDecimal getMaxAngle() {
		return maxAngle;
	}

	public void setMaxAngle(BigDecimal maxAngle) {
		this.maxAngle = maxAngle;
	}

	public String getCardinalDirectionName() {
		return cardinalName;
	}

	public String getSymbol() {
		return symbol;
	}

	//IExtensible methods
	@Override
	public String getName(){
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCanonicalClassName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public Map<String, Object> getFields() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cardinalId", this.cardinalId);
		map.put("minAngle", this.minAngle);
		map.put("maxAngle", this.maxAngle);
		map.put("cardinalName", this.cardinalName);
		map.put("symbol", this.symbol);
		
		return map;
	}
	//End IExtensible methods

	@Override
	public String toString() {
		return this.getSymbol();
	}
}