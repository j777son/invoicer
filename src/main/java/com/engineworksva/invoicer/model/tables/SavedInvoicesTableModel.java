package com.engineworksva.invoicer.model.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.engineworksva.invoicer.model.Invoice;

public class SavedInvoicesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5437644606045034995L;
	private String[] columnNames = { "Number", "Customer", "Date", "Total" };
	private ArrayList<Invoice> rowsData = new ArrayList<Invoice>();

	public SavedInvoicesTableModel() {
		
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
				return rowsData.get(rowIndex).getNumber();
			case 1:
				return rowsData.get(rowIndex).getCustomer();
			case 2:
				return rowsData.get(rowIndex).getDate();
			case 3:
				return rowsData.get(rowIndex).getTotal();
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		return; // invoices are not editable
	}

	public void insertInvoice(Invoice invoice) {
		rowsData.add(invoice);
		int thisRowIdx = rowsData.size() - 1;
		fireTableRowsInserted(thisRowIdx, thisRowIdx);
	}

	public void removeInvoice(int index) {
		rowsData.remove(index);
		fireTableRowsUpdated(index, index);
	}
	
	public Invoice getInvoice(int index) {
		return rowsData.get(index);
	}
	
	public ArrayList<Invoice> getData() {
		return rowsData;
	}
	
	public void setData(ArrayList<Invoice> newData) {
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
