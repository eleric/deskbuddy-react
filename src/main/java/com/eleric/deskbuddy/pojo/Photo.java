package com.eleric.deskbuddy.pojo;

public class Photo {
	private String name;
	private String location;

	public Photo(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public Photo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
