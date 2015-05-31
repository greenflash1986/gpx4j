package org.casaca.gpx4j.tools.exporter;

public abstract class AbstractField implements IField {
	
	public AbstractField(){
		name = "";
		o = null;
	}
	
	private String name;
	private Object o;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getObject() {
		return this.o;
	}

	@Override
	public void setObject(Object o) {
		this.o = o;
	}
}
