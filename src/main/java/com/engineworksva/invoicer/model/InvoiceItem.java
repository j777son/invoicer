package com.engineworksva.invoicer.model;

import java.util.ArrayList;

public class InvoiceItem {

	private String id;
	
	private String title;
	
	private ArrayList<PartItem> partItems = new ArrayList<PartItem>();
	
	private ArrayList<LaborItem> laborItems = new ArrayList<LaborItem>();
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() { return this.title; }
	
	public void setTitle(String title) { this.title = title; }
	
	public ArrayList<PartItem> getPartItems() { return this.partItems; }
	
	public void setPartItems(ArrayList<PartItem> partItems) { this.partItems = partItems; }
	
	public ArrayList<LaborItem> getLaborItems() { return this.laborItems; }
	
	public void setLaborItems(ArrayList<LaborItem> laborItems) { this.laborItems = laborItems; }

	public double getPartsTotal() {
		double amount = 0;
		for(PartItem partI : this.partItems) {
			amount += partI.getTotalSum();
		}
		return amount;
	}
	
	public double getLaborTotal() {
		double amount = 0;
		for(LaborItem laborI : this.laborItems) {
			amount += laborI.getTotalSum();
		}
		return amount;
	}
	
	public double getTotal() {
		return getPartsTotal() + getLaborTotal();
	}
	
	@Override
	public String toString() {
		return this.title;
	}
	
}
