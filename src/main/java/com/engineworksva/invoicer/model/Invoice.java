package com.engineworksva.invoicer.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.engineworksva.invoicer.App;

public class Invoice {
	
	private String id;
	
	private String number;
	
	private String date;
	
	private Customer customer;
	
	private Vehicle vehicle;
	
	private String subTotal;
	
	private String taxes;
	
	private String total;
	
	private ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
	
	public Invoice() {
		
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() { return this.number; };
	
	public void setNumber(String number) { this.number = number; }
	
	public String getDate() { return this.date; }
	
	public void setDate(String date) { this.date = date; }
	
	public Customer getCustomer() { return this.customer; }
	
	public void setCustomer(Customer customer) { this.customer = customer; }
	
	public Vehicle getVehicle() { return this.vehicle; }
	
	public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
	
	public String getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getTaxes() {
		return this.taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public ArrayList<InvoiceItem> getItems() { return this.items; }
	
	public void setItems(ArrayList<InvoiceItem> items) { this.items = items; }
	
	/** USE THIS STRICTLY for generating new invoices */
	public double getPartsTotalOnly() {
		double amount = 0;
		for(InvoiceItem invoiceI : this.items) {
			amount += invoiceI.getPartsTotal();
		}
		return amount;
	}
	
	/** USE THIS STRICTLY for generating new invoices */
	public double getLaborTotalOnly() {
		double amount = 0;
		for(InvoiceItem invoiceI : this.items) {
			amount += invoiceI.getLaborTotal();
		}
		return amount;
	}
	
	/** USE THIS STRICTLY for generating new invoices */
	public double getSubTotalSum() {
		double amount = 0;
		for(InvoiceItem invoiceI : this.items) {
			amount += invoiceI.getTotal();
		}
		return amount;
	}
	
	/** USE THIS STRICTLY for generating new invoices */
	public double getTaxesSum() {
		double subTotal = getSubTotalSum();
		double taxValue = Double.parseDouble(App.settings.get("taxValue").toString());
		//(percent/100) * number
		return (taxValue/100) * subTotal;
	}
	
	/** USE THIS STRICTLY for generating new invoices */
	public double getTotalSum() {
		return getSubTotalSum() + getTaxesSum();
	}
	
	/** USE THIS STRICTLY for generating new invoices
	 * 
	 * this will add the moment's date and time
	 * with this format "MM/dd/yyyy hh:mm a"
	 *  
	 *  */
	public void setDateNTime() {
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
	    String formattedDate = myDateObj.format(myFormatObj);
	    this.date = formattedDate;
	}
	
	public void formatNumber() {
		this.number = String.format("%07d", Integer.parseInt(number));
	}
	
}
