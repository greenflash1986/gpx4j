package org.casaca.gpx4j.tools.compass;

import java.math.BigDecimal;

public class CardinalDirection {
	
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
		N.setId(1);
		N.setMinAngle(BigDecimal.valueOf(11.25));
		N.setMaxAngle(BigDecimal.valueOf(348.75));
		
		NNE.setId(2);
		NNE.setMinAngle(BigDecimal.valueOf(11.25));
		NNE.setMaxAngle(BigDecimal.valueOf(33.75));
		
		NE.setId(3);
		NE.setMinAngle(BigDecimal.valueOf(33.75));
		NE.setMaxAngle(BigDecimal.valueOf(56.25));
		
		ENE.setId(4);
		ENE.setMinAngle(BigDecimal.valueOf(56.25));
		ENE.setMaxAngle(BigDecimal.valueOf(78.75));
		
		E.setId(5);
		E.setMinAngle(BigDecimal.valueOf(78.75));
		E.setMaxAngle(BigDecimal.valueOf(101.25));
		
		ESE.setId(6);
		ESE.setMinAngle(BigDecimal.valueOf(101.25));
		ESE.setMaxAngle(BigDecimal.valueOf(123.75));
		
		SE.setId(7);
		SE.setMinAngle(BigDecimal.valueOf(123.75));
		SE.setMaxAngle(BigDecimal.valueOf(146.25));
		
		SSE.setId(8);
		SSE.setMinAngle(BigDecimal.valueOf(146.25));
		SSE.setMaxAngle(BigDecimal.valueOf(168.75));
		
		S.setId(9);
		S.setMinAngle(BigDecimal.valueOf(168.75));
		S.setMaxAngle(BigDecimal.valueOf(191.25));
		
		SSW.setId(10);
		SSW.setMinAngle(BigDecimal.valueOf(191.25));
		SSW.setMaxAngle(BigDecimal.valueOf(213.75));
		
		SW.setId(11);
		SSW.setMinAngle(BigDecimal.valueOf(213.75));
		SSW.setMaxAngle(BigDecimal.valueOf(236.25));
		
		WSW.setId(12);
		WSW.setMinAngle(BigDecimal.valueOf(236.25));
		WSW.setMaxAngle(BigDecimal.valueOf(258.75));
		
		W.setId(13);
		W.setMinAngle(BigDecimal.valueOf(258.75));
		W.setMaxAngle(BigDecimal.valueOf(281.25));
		
		WNW.setId(14);
		WNW.setMinAngle(BigDecimal.valueOf(281.25));
		WNW.setMaxAngle(BigDecimal.valueOf(303.75));
		
		NW.setId(15);
		NW.setMinAngle(BigDecimal.valueOf(303.75));
		NW.setMaxAngle(BigDecimal.valueOf(326.25));
		
		NNW.setId(16);
		NNW.setMinAngle(BigDecimal.valueOf(326.25));
		NNW.setMaxAngle(BigDecimal.valueOf(348.75));
	}
	
	//END STATIC

	private int id;
	private BigDecimal minAngle;
	private BigDecimal maxAngle;
	private String name;
	private String symbol;
	
	public CardinalDirection(String name, String symbol) {
		super();
		this.name = name;
		this.symbol = symbol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return this.getSymbol();
	}
}