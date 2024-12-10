package com.engineworksva.invoicer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import com.engineworksva.invoicer.model.Customer;
import com.engineworksva.invoicer.model.Invoice;
import com.engineworksva.invoicer.model.InvoiceItem;
import com.engineworksva.invoicer.model.LaborItem;
import com.engineworksva.invoicer.model.PartItem;
import com.engineworksva.invoicer.model.Vehicle;

public class Database {

	private Connection _conn = null;

	public Database() {
		Utils.debug("database instance created.");
		connect();
		if (_conn != null) {
			reloadSettings();
		}
	}

	/*
	  To execute a query, call an execute method from Statement such as the
	  following:
	  
	  execute: Returns true if the first object that the query returns is a
	  ResultSet object. Use this method if the query could return one or more
	  ResultSet objects. Retrieve the ResultSet objects returned from the query by
	  repeatedly calling Statement.getResultSet.
	  
	  executeQuery: Returns one ResultSet object.
	  
	  executeUpdate: Returns an integer representing the number
	  of rows affected by the SQL statement. Use this method if you are using
	  INSERT, DELETE, or UPDATE SQL statements.
	 */

	public void saveCustomer(Customer customer) {
		customer.setId(UUID.randomUUID().toString());
		Statement statement;
		try {
			statement = _conn.createStatement();
			int result = statement.executeUpdate(
					String.format("INSERT INTO customers (id, name, address, phone, email) VALUES('%s', '%s', '%s', '%s', '%s');",
							customer.getId(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail()));
			if (result == 1)
				Utils.debug("customer was saved in database.");
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Customer getCustomerById(String id) {
		Customer customer = new Customer();
		Statement statement;
		ResultSet rs;
		try {
			statement = _conn.createStatement();
			rs = statement.executeQuery(String.format("SELECT * FROM customers WHERE id='%s'", id));
			while (rs.next()) {
					// read the result set
					customer.setId(rs.getString("id"));
					customer.setName(rs.getString("name"));
					customer.setAddress(rs.getString("address"));
					customer.setPhone(rs.getString("phone"));
					customer.setEmail(rs.getString("email"));
			}
			statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return customer;
	}
	
	public ArrayList<Customer> getCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
		Statement statement = _conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM customers");
		while (rs.next()) {
			// read the result set
			Customer customer = new Customer();
			customer.setId(rs.getString("id"));
			customer.setName(rs.getString("name"));
			customer.setAddress(rs.getString("address"));
			customer.setPhone(rs.getString("phone"));
			customer.setEmail(rs.getString("email"));
			customers.add(customer);
		}
		statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public void deleteCustomer(String id) {
		Statement statement;
		try {
			statement = _conn.createStatement();
			int result = statement.executeUpdate(String.format("DELETE FROM customers WHERE id='%s'", id));
			if (result == 1)
				Utils.debug("customer was deleted from database.");
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveInvoice(Invoice invoice) throws SQLException {
		
		invoice.setId(UUID.randomUUID().toString());
		invoice.setNumber(String.valueOf(getInvoicesCount() + 1));
		invoice.formatNumber();
		
		String subTotal = Utils.formatDouble(invoice.getSubTotalSum());
		String taxAmount = Utils.formatDouble(invoice.getTaxesSum());
		String total = Utils.formatDouble(invoice.getTotalSum());
		
		invoice.setSubTotal(subTotal);
		invoice.setTaxes(taxAmount);
		invoice.setTotal(total);
		
		Statement statement;
		try {
			// creates and saves the invoice data described below
			statement = _conn.createStatement();
			int result = statement.executeUpdate(String.format("INSERT INTO invoices (id, number, customer_id, date, subtotal, taxes, total, veh_make, veh_model, veh_year, veh_mileage, veh_vin) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", invoice.getId(), invoice.getNumber(), invoice.getCustomer().getId(), invoice.getDate(), invoice.getSubTotal(), invoice.getTaxes(), invoice.getTotal(), invoice.getVehicle().getMake(), invoice.getVehicle().getModel(), invoice.getVehicle().getYear(), invoice.getVehicle().getMileage(), invoice.getVehicle().getVin()));
			if (result == 1)
				Utils.debug("invoice model was saved in database.");
			statement.close();
			// creates all the invoice items in DB
			Utils.debug("attemping to save " + invoice.getItems().size() + " invoice items.");
			for (InvoiceItem item : invoice.getItems()) {
				item.setId(UUID.randomUUID().toString());
				statement = _conn.createStatement();
				result = statement.executeUpdate(String.format("INSERT INTO invoice_items (id, invoice_id, item_title) VALUES('%s', '%s', '%s');", item.getId(), invoice.getId(), item.getTitle()));
				if (result == 1)
					Utils.debug("invoice item saved");
				statement.close();
				// saves all the part items of the invoice
				for (PartItem partItem : item.getPartItems()) {
					partItem.setId(UUID.randomUUID().toString());
					statement = _conn.createStatement();
					result = statement.executeUpdate(String.format("INSERT INTO parts_items (id, invoice_item_id, desc, qty, price, total) VALUES('%s', '%s', '%s', '%s', '%s', '%s');", partItem.getId(), item.getId(), partItem.getDescription(), partItem.getQuantity(), partItem.getPrice(), partItem.getTotalSum()));
					statement.close();
					Utils.debug("saved part item of invoiceItemId: " + item.getId());
				}
				for (LaborItem laborItem : item.getLaborItems()) {
					laborItem.setId(UUID.randomUUID().toString());
					statement = _conn.createStatement();
					result = statement.executeUpdate(String.format("INSERT INTO labor_items (id, invoice_item_id, desc, hrs, price, total) VALUES('%s', '%s', '%s', '%s', '%s', '%s');", laborItem.getId(), item.getId(), laborItem.getDescription(), laborItem.getHours(), laborItem.getPrice(), laborItem.getTotalSum()));
					statement.close();
					Utils.debug("saved labor item of invoiceItemId: " + item.getId());
				}
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utils.debug("INVOICE SAVED!");
	}
	
	public int getInvoicesCount() {
		int count = -1;
		Statement statement;
		try {
			statement = _conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM invoices");
			while (rs.next()) {
				count = rs.getInt("total");
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getCustomersCount() {
		int count = -1;
		Statement statement;
		try {
			statement = _conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM customers");
			while (rs.next()) {
				count = rs.getInt("total");
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public Invoice getInvoiceById(String invoiceId) {
		Invoice invoice = new Invoice();
		
		Statement statement;
		ResultSet rs;
		try {
			// getting invoice database model data & vehicle data
			statement = _conn.createStatement();
			rs = statement.executeQuery(String.format("SELECT * FROM invoices WHERE id='%s'", invoiceId));
			String customerId = "";
			Customer customer = null;
			Vehicle vehicle = new Vehicle();
			while (rs.next()) { // read
				invoice.setId(rs.getString("id")); // this is the invoice_id but formatted
				invoice.setNumber(rs.getString("id")); // this is the invoice_id but formatted
				customerId = rs.getString("customer_id");
				invoice.setDate(rs.getString("date"));
				invoice.setSubTotal(rs.getString("subtotal"));
				invoice.setTaxes(rs.getString("taxes"));
				invoice.setTotal(rs.getString("total"));
				//setting the vehicle info
				vehicle.setMake(rs.getString("veh_make"));
				vehicle.setModel(rs.getString("veh_model"));
				vehicle.setYear(rs.getString("veh_year"));
				vehicle.setMileage(rs.getString("veh_mileage"));
				vehicle.setVin(rs.getString("veh_vin"));
				invoice.setVehicle(vehicle);
			}
			statement.close();
			//getting customer data
			customer = this.getCustomerById(customerId);
			invoice.setCustomer(customer);
			//getting ALL invoice items
			ArrayList<InvoiceItem> invoiceItemsModels = this.getInvoiceItemsByInvoiceId(invoiceId);
			//getting PARTS AND LABORS of EVERY invoice item
			for (InvoiceItem ii : invoiceItemsModels) {
				ArrayList<PartItem> partItems = this.getPartsItemsByInvoiceItemId(ii.getId());
				ArrayList<LaborItem> laborItems = this.getLaborItemsByInvoiceItemId(ii.getId());
				ii.setPartItems(partItems);
				ii.setLaborItems(laborItems);
			}
			invoice.setItems(invoiceItemsModels);
			Utils.debug("database loaded a single invoice with id: " + invoiceId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return invoice;
	}
	
	public ArrayList<Invoice> getInvoices() {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		
		Statement statement;
		ResultSet rs;
		try {
			// getting ALL invoices database models data & vehicles data
			statement = _conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM invoices");
			while (rs.next()) {
				Invoice singleInvoice = new Invoice();
				Vehicle vehicle = new Vehicle();
				singleInvoice.setId(rs.getString("id"));
				singleInvoice.setNumber(rs.getString("number")); // this is the invoice_id but formatted
				Customer customer = this.getCustomerById(rs.getString("customer_id"));
				singleInvoice.setDate(rs.getString("date"));
				singleInvoice.setSubTotal(rs.getString("subtotal"));
				singleInvoice.setTaxes(rs.getString("taxes"));
				singleInvoice.setTotal(rs.getString("total"));
				//setting the vehicle info
				vehicle.setMake(rs.getString("veh_make"));
				vehicle.setModel(rs.getString("veh_model"));
				vehicle.setYear(rs.getString("veh_year"));
				vehicle.setMileage(rs.getString("veh_mileage"));
				vehicle.setVin(rs.getString("veh_vin"));
				singleInvoice.setVehicle(vehicle);
				singleInvoice.setCustomer(customer);
				invoices.add(singleInvoice);
			}
			statement.close();
			for (Invoice invoice : invoices) {
				//getting ALL invoice items
				ArrayList<InvoiceItem> invoiceItems = this.getInvoiceItemsByInvoiceId(invoice.getId());
				//getting PARTS AND LABORS of EVERY invoice item
				for (InvoiceItem ii : invoiceItems) {
					ArrayList<PartItem> partItems = this.getPartsItemsByInvoiceItemId(ii.getId());
					ArrayList<LaborItem> laborItems = this.getLaborItemsByInvoiceItemId(ii.getId());
					ii.setPartItems(partItems);
					ii.setLaborItems(laborItems);
				}
				invoice.setItems(invoiceItems);
			}
			Utils.debug("database loaded ALL INVOICES.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return invoices;
	}
	
	public ArrayList<InvoiceItem> getInvoiceItemsByInvoiceId(String invoiceId) {
		ArrayList<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
		Statement statement;
		ResultSet rs;
		try {
			statement = _conn.createStatement();
			rs = statement.executeQuery(String.format("SELECT * FROM invoice_items WHERE invoice_id='%s'", invoiceId));
			while (rs.next()) {
				InvoiceItem invoiceItem = new InvoiceItem();
				invoiceItem.setId(rs.getString("id"));
				invoiceItem.setTitle(rs.getString("item_title"));
				invoiceItems.add(invoiceItem);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoiceItems;
	}
	
	public ArrayList<PartItem> getPartsItemsByInvoiceItemId(String invoiceItemId) {
		ArrayList<PartItem> partsItems = new ArrayList<PartItem>();
		Statement statement;
		ResultSet rs;
		try {
			//parts items
			statement = _conn.createStatement();
			rs = statement.executeQuery(String.format("SELECT * FROM parts_items WHERE invoice_item_id='%s'", invoiceItemId));
			while (rs.next()) {
				PartItem partItemTemp = new PartItem();
				partItemTemp.setId(rs.getString("id"));
				partItemTemp.setDescription(rs.getString("desc"));
				partItemTemp.setQuantity(rs.getDouble("qty"));
				partItemTemp.setPrice(rs.getDouble("price"));
				partItemTemp.setTotal(rs.getDouble("total"));
				partsItems.add(partItemTemp); //add to list
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return partsItems;
	}
	
	public ArrayList<LaborItem> getLaborItemsByInvoiceItemId(String invoiceItemId) {
		ArrayList<LaborItem> laborItems = new ArrayList<LaborItem>();
		Statement statement;
		ResultSet rs;
		try {
			//labor items
			statement = _conn.createStatement();
			rs = statement.executeQuery(String.format("SELECT * FROM labor_items WHERE invoice_item_id='%s'", invoiceItemId));
			while (rs.next()) {
				LaborItem laborItemTemp = new LaborItem();
				laborItemTemp.setId(rs.getString("id"));
				laborItemTemp.setDescription(rs.getString("desc"));
				laborItemTemp.setHours(rs.getDouble("hrs"));
				laborItemTemp.setPrice(rs.getDouble("price"));
				laborItemTemp.setTotal(rs.getDouble("total"));
				laborItems.add(laborItemTemp);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return laborItems;
	}
	
	public Invoice getInvoicesByCustomer(int customerId) {
		
		return null;
	}
	
	public void reloadSettings() {
		App.settings.clear();
		try {
			Statement statement = _conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM settings");
			while (rs.next()) {
				App.settings.put("taxValue", rs.getString("taxValue"));
				App.settings.put("address", rs.getString("address"));
				App.settings.put("phone", rs.getString("phone"));
				App.settings.put("email", rs.getString("email"));
				App.settings.put("website", rs.getString("website"));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSettings() {
		Statement statement;
		try {
			statement = _conn.createStatement();
			int result = statement.executeUpdate(
					String.format("UPDATE settings SET taxValue = '%s', address = '%s', phone = '%s', email = '%s', website = '%s' WHERE id=1;",
							App.settings.get("taxValue"), App.settings.get("address"), App.settings.get("phone"), App.settings.get("email"), App.settings.get("website")));
			if (result == 1)
				Utils.debug("settings were saved in database.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLastInvoiceId() {
		String theId = "";
		Statement statement;
		try {
			statement = _conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM invoices LIMIT 1");
			
			while (rs.next()) { // read
				theId = rs.getString("id");
			}
			if (theId.length() > 0)
				Utils.debug("invoice id acquired from the db is: " + theId);
			statement.close();
			return theId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theId;
	}
	
	public int getLastCustomerId() {
		Statement statement;
		try {
			statement = _conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM customers LIMIT 1");
			int theId = -1;
			while (rs.next()) { // read
				theId = rs.getInt("id");
			}
			if (theId != -1)
				Utils.debug("customer id acquired from the db is: " + theId);
			statement.close();
			return theId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void connect() {
		 String url = "jdbc:sqlite:database.db3";

	        try {
	        	_conn = DriverManager.getConnection(url);
	            Utils.debug("Connection to SQLite has been established.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}

	public void disconnect() {
		try {
			_conn.close();
			Utils.debug("database system is not working anymore.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Object is being garbage collected");
		disconnect();
	}

	public ResultSet executeSingle(String query) {
		ResultSet resultSet;
		try {
			Statement statement = _conn.createStatement();
			resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
