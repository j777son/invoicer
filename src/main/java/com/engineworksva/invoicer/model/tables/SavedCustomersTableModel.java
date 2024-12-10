package com.engineworksva.invoicer.model.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.engineworksva.invoicer.model.Customer;

public class SavedCustomersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5437644606045034995L;
	private String[] columnNames = { "Name", "Address", "Phone", "Email" };
	private ArrayList<Customer> rowsData = new ArrayList<Customer>();

	public SavedCustomersTableModel() {
		
	}

	@Override
	public int getRowCount() {
		return rowsData.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return rowsData.get(rowIndex).getName();
			case 1:
				return rowsData.get(rowIndex).getAddress();
			case 2:
				return rowsData.get(rowIndex).getPhone();
			case 3:
				return rowsData.get(rowIndex).getEmail();
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		return; // invoices are not editable
	}

	public void insertCustomer(Customer customer) {
		rowsData.add(customer);
		int thisRowIdx = rowsData.size() - 1;
		fireTableRowsInserted(thisRowIdx, thisRowIdx);
	}

	public void removeCustomer(int index) {
		rowsData.remove(index);
		fireTableRowsUpdated(index, index);
	}
	
	public Customer getCustomer(int index) {
		return rowsData.get(index);
	}
	
	public ArrayList<Customer> getData() {
		return rowsData;
	}
	
	public void setData(ArrayList<Customer> newData) {
		rowsData = newData;
		fireTableDataChanged();
	}
	
	public void removeAll() {
		
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	// Implement other methods as needed...

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// nothing is editable
		return super.isCellEditable(rowIndex, columnIndex);
	}

}
