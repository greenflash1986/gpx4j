package org.casaca.gpx4j.tools;

import java.util.Properties;

public abstract class Tool {

	private Properties propsGpxTools;
	
	public Tool(Properties props){
		this.propsGpxTools = props;
	}
	
	protected Properties getProperties(){
		return this.propsGpxTools;
	}
}
