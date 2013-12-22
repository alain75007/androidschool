package com.myschool.game.model;

public class Product {
	private long id;
	private String name;
	private int price;
	private int count;
	private int shopId;
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Product(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Product() {
	}
	public long getId() {
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
	
}
