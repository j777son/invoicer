package com.engineworksva.invoicer;

import java.io.IOException;
import java.sql.SQLException;

import com.engineworksva.invoicer.model.Customer;
import com.engineworksva.invoicer.model.Invoice;
import com.engineworksva.invoicer.model.InvoiceItem;
import com.engineworksva.invoicer.model.LaborItem;
import com.engineworksva.invoicer.model.PartItem;
import com.engineworksva.invoicer.model.Vehicle;

import freemarker.template.TemplateException;

public class Tests {
	
	public static void createSampleInvoice() {
		
		Invoice invoice = new Invoice();

		//invoice.setNumber(String.valueOf(App.database.getInvoicesCount() + 1));
		invoice.setDateNTime();
		
        Customer customer = new Customer();
        customer.setName("Pollo Campero Raleigh");
        customer.setAddress("8300 Litchford Rd, Raleigh, NC 27615");
        customer.setPhone("(919) 301-8475");
        customer.setEmail("Jessica.McLain@Campero.com");
        App.database.saveCustomer(customer);
        invoice.setCustomer(customer);
        
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear("2025");
        vehicle.setMileage("10000");
        vehicle.setVin("KMHDU4AD6AU193713");
        invoice.setVehicle(vehicle);
        
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setTitle("Spark Plug Replacement");
        
        PartItem partItem1 = new PartItem();
        partItem1.setDescription("Iridium Spark Plug");
        partItem1.setQuantity(2.15);
        partItem1.setPrice(155.24);
        partItem1.setTotal(partItem1.getTotalSum());
        PartItem partItem2 = new PartItem();
        partItem2.setDescription("Genuine Toyota Oil");
        partItem2.setQuantity(3.01);
        partItem2.setPrice(65.11);
        partItem2.setTotal(partItem2.getTotalSum());
        invoiceItem.getPartItems().add(partItem1);
        invoiceItem.getPartItems().add(partItem2);
        
        LaborItem laborItem1 = new LaborItem();
        laborItem1.setDescription("Replacement of Spark Plug");
        laborItem1.setHours(1.37);
        laborItem1.setPrice(15.00);
        laborItem1.setTotal(laborItem1.getTotalSum());
        LaborItem laborItem2 = new LaborItem();
        laborItem2.setDescription("Oil Inspection and Replacement");
        laborItem2.setHours(4.58);
        laborItem2.setPrice(80456.05);
        laborItem2.setTotal(laborItem2.getTotalSum());
        invoiceItem.getLaborItems().add(laborItem1);
        invoiceItem.getLaborItems().add(laborItem2);
        
        invoice.getItems().add(invoiceItem);
        invoice.getItems().add(invoiceItem);
        invoice.getItems().add(invoiceItem);
        invoice.getItems().add(invoiceItem);
        invoice.getItems().add(invoiceItem);
        
        try {
			App.database.saveInvoice(invoice);
		} catch (SQLException e) {
			e.printStackTrace();
		}
                
        try {
			InvoiceGenerator.make(invoice, true);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
        
	}

}
