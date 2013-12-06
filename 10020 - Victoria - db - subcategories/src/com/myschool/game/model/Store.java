package com.myschool.game.model;

public class Store {
	private int id = -1;
	private String name;
	private int category_id;
	
	public Store(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Store() {
	}

	public Store(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	
}
