package com.engineworksva.invoicer.model.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.engineworksva.invoicer.model.PartItem;

public class PartItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5437644606045034995L;
	private String[] columnNames = { "Description", "Qty", "Price", "Total" };
	private ArrayList<PartItem> rowsData = new ArrayList<PartItem>();

	public PartItemTableModel() {
		addEmptyRow();
	}

	/** returns the index of the new row */
	public int addEmptyRow() {
		rowsData.add(new PartItem("Sample part", 0, 0));
		return getRowCount() - 1;
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
				return rowsData.get(rowIndex).getDescription();
			case 1:
				return rowsData.get(rowIndex).getQuantity();
			case 2:
				return rowsData.get(rowIndex).getPrice();
			case 3:
				return rowsData.get(rowIndex).getTotalSum();
				
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		PartItem pi = rowsData.get(rowIndex);
		double qty, price;
		switch (columnIndex) {
		case 0:
			rowsData.get(rowIndex).setDescription(aValue + "");
			break;
		case 1:
			qty = Double.parseDouble(aValue.toString());
			price = pi.getPrice();
			pi.setQuantity(qty);
			pi.setTotal(qty * price);
			break;
		case 2:
			qty = pi.getQuantity();
			price = Double.parseDouble(aValue.toString());
			pi.setPrice(qty);
			pi.setTotal(qty * price);
		}
		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	public void insertPartItem(PartItem newPartItem) {
		rowsData.add(newPartItem);
		int thisRowIdx = rowsData.size() - 1;
		fireTableRowsInserted(thisRowIdx, thisRowIdx);
	}

	public void removePartItem(int index) {
		rowsData.remove(index);
		fireTableRowsUpdated(index, index);
	}
	
	public PartItem getPartItem(int index) {
		return rowsData.get(index);
	}
	
	public void swapRowPosition(int first, int second) {
		PartItem firstPI = rowsData.get(first);
		PartItem secondPI = rowsData.get(second);
		rowsData.set(second, firstPI);
		rowsData.set(first, secondPI);
		fireTableDataChanged();
	}
	
	public ArrayList<PartItem> getData() {
		return rowsData;
	}
	
	public void setData(ArrayList<PartItem> newData) {
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
		// just total is not editable
		return columnIndex == 3 ? false : true;
	}

}
