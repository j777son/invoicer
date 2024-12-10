package com.engineworksva.invoicer.model;

public class PartItem {
	
	private String id;
	
	private String description;
	
	private double quantity;
	
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
	
	public double getQuantity() { return this.quantity; }
	
	public void setQuantity(double quantity) { this.quantity = quantity; }
	
	public double getPrice() { return this.price; }
	
	public void setPrice(double price) { this.price = price; }
	
	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotalSum() {
		return this.quantity * this.price;
	}
	
	public PartItem() {
		
	}
	
	public PartItem(String description, double quantity, double price) {
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}

}
