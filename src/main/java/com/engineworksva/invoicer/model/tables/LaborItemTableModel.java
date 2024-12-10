package com.engineworksva.invoicer.model.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.engineworksva.invoicer.model.LaborItem;

public class LaborItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5437644606045034996L;
	private String[] columnNames = { "Description", "Hrs", "Price", "Total" };
	private ArrayList<LaborItem> rowsData = new ArrayList<LaborItem>();

	public LaborItemTableModel() {
		addEmptyRow();
	}

	/** returns the index of the new row */
	public int addEmptyRow() {
		rowsData.add(new LaborItem("Sample labor", 0, 0));
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
				return rowsData.get(rowIndex).getHours();
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
		LaborItem li = rowsData.get(rowIndex);
		double hrs, price;
		switch (columnIndex) {
		case 0:
			rowsData.get(rowIndex).setDescription(aValue + "");
			break;
		case 1:
			hrs = Double.parseDouble(aValue.toString());
			price = li.getPrice();
			li.setHours(hrs);
			rowsData.get(rowIndex).setTotal(hrs * price);
			break;
		case 2:
			hrs = li.getHours();
			price = Double.parseDouble(aValue.toString());
			li.setPrice(price);
			rowsData.get(rowIndex).setTotal(hrs * price);
			break;
		}
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	public void insertLaborItem(LaborItem newLaborItem) {
		rowsData.add(newLaborItem);
		int thisRowIdx = rowsData.size() - 1;
		fireTableRowsInserted(thisRowIdx, thisRowIdx);
	}

	public void removeLaborItem(int index) {
		rowsData.remove(index);
		fireTableRowsUpdated(index, index);
	}
	
	public LaborItem getLaborItem(int index) {
		return rowsData.get(index);
	}
	
	public void swapRowPosition(int first, int second) {
		LaborItem firstLI = rowsData.get(first);
		LaborItem secondLI = rowsData.get(second);
		rowsData.set(second, firstLI);
		rowsData.set(first, secondLI);
		fireTableDataChanged();
	}
	
	public ArrayList<LaborItem> getData() {
		return rowsData;
	}
	
	public void setData(ArrayList<LaborItem> newData) {
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
