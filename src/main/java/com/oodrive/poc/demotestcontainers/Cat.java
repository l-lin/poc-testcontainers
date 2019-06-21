package com.oodrive.poc.demotestcontainers;

public class Cat {

	private Integer catId;

	private String name;

	private String type;

	public Cat() {
	}

	public Cat(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
