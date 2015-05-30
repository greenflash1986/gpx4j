package org.casaca.gpx4j.core.data;

public class Person extends BaseObject {
	private String name;
	private Email email;
	private Link link;
	
	public Person(){
		this.name = null;
		this.email = null;
		this.link = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
