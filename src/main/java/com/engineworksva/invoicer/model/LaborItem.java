package com.engineworksva.invoicer.model;

public class LaborItem {

	private String id;
	
	private String description;
	
	private double hours;
	
	private double price;
	
	private double total;
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() { return this.description; }
	
	public void setDescription(String description) { this.description = description; }
	
	public double getHours() { return this.hours; }
	
	public void setHours(double hours) { this.hours = hours; }
	
	public double getPrice() { return this.price; }
	
	public void setPrice(double price) { this.price = price; }
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotalSum() {
		return this.hours * this.price;
	}
	
	public LaborItem() {
		
	}
	
	public LaborItem(String description, double hours, double price) {
		this.description = description;
		this.hours = hours;
		this.price = price;
	}
	
}
