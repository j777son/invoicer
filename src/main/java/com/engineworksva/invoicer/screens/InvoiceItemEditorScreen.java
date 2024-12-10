package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.engineworksva.invoicer.App;
import com.engineworksva.invoicer.model.InvoiceItem;
import com.engineworksva.invoicer.model.LaborItem;
import com.engineworksva.invoicer.model.PartItem;
import com.engineworksva.invoicer.model.EditorScreenModel;
import com.engineworksva.invoicer.model.tables.LaborItemTableModel;
import com.engineworksva.invoicer.model.tables.PartItemTableModel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Locale;

public class InvoiceItemEditorScreen extends JFrame implements EditorScreenModel {
	
	public InvoiceItem currentInvoiceItem = null;
	public PartItemTableModel partItemTableModel = new PartItemTableModel();
	public LaborItemTableModel laborItemTableModel = new LaborItemTableModel();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	private JTextField titleInput;
	private JTable partsTable;
	private JTable laborTable;

	/**
	 * Create the frame.
	 */
	public InvoiceItemEditorScreen() {
		setTitle("INVOICE ITEM EDITOR");
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(797, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TITLE");
		lblNewLabel.setBounds(25, 27, 55, 16);
		contentPane.add(lblNewLabel);
		
		titleInput = new JTextField();
		titleInput.setBounds(25, 50, 387, 20);
		contentPane.add(titleInput);
		titleInput.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "PARTS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(23, 91, 714, 179);
		contentPane.add(panel);
		panel.setLayout(null);
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 27, 558, 135);
		panel.add(scrollPane);
		
		
		partsTable = new JTable();
		partsTable.setLocale(Locale.ENGLISH);
		partsTable.getTableHeader().setReorderingAllowed(false);
		partsTable.setModel(partItemTableModel);
		// right text alignment
		DefaultTableCellRenderer customCellRenderer = new DefaultTableCellRenderer();
		customCellRenderer.setHorizontalAlignment( JLabel.RIGHT );
		partsTable.getColumnModel().getColumn(1).setCellRenderer(customCellRenderer);
		partsTable.getColumnModel().getColumn(2).setCellRenderer(customCellRenderer);
		partsTable.getColumnModel().getColumn(3).setCellRenderer(customCellRenderer);
		//
		//total sum
		
		//
		partsTable.getColumnModel().getColumn(0).setResizable(false);
		partsTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		partsTable.getColumnModel().getColumn(0).setMinWidth(300);
		partsTable.getColumnModel().getColumn(1).setResizable(false);
		partsTable.getColumnModel().getColumn(2).setResizable(false);
		partsTable.getColumnModel().getColumn(3).setResizable(false);		
		scrollPane.setViewportView(partsTable);
		
		JButton btnNewButton_1 = new JButton("ADD ROW");
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partItemTableModel.addEmptyRow();
				partsTable.revalidate(); // redraws, update the table data - https://www.daniweb.com/programming/software-development/threads/26443/jtable-reloading-refreshing-updating-whatever
			}
		});
		btnNewButton_1.setBounds(589, 22, 90, 26);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("DELETE ROW");
		btnNewButton_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIdx = partsTable.getSelectedRow();
				if (selectedRowIdx < 0 || partItemTableModel.getPartItem(selectedRowIdx) == null) {
					//Utils.msgBox(selectedRowIdx);
					return;
				}
				partItemTableModel.removePartItem(selectedRowIdx);
				partsTable.getSelectionModel().clearSelection();
				partsTable.revalidate();
			}
		});
		btnNewButton_1_1.setBounds(589, 60, 109, 26);
		panel.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("MOVE UP");
		btnNewButton_1_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIdx = partsTable.getSelectedRow();
				if (selectedRowIdx < 0 || partItemTableModel.getPartItem(selectedRowIdx) == null) {
					//Utils.msgBox(selectedRowIdx);
					return;
				}
				// swaps the selected row with the one in the bottom
				int upperItemIdx = selectedRowIdx - 1;
				if (upperItemIdx >= 0) {
					partItemTableModel.swapRowPosition(selectedRowIdx, upperItemIdx);
					partsTable.revalidate();
				}
			}
		});
		btnNewButton_1_1_1.setBounds(589, 98, 109, 26);
		panel.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("MOVE DOWN");
		btnNewButton_1_1_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIdx = partsTable.getSelectedRow();
				if (selectedRowIdx < 0 || partItemTableModel.getPartItem(selectedRowIdx) == null) {
					//Utils.msgBox(selectedRowIdx);
					return;
				}
				// swaps the selected row with the one in the bottom
				int lowerItemIdx = selectedRowIdx + 1;
				if (lowerItemIdx < partItemTableModel.getRowCount()) {
					partItemTableModel.swapRowPosition(selectedRowIdx, lowerItemIdx);
					partsTable.revalidate();
				}
			}
		});
		btnNewButton_1_1_1_1.setBounds(589, 135, 109, 26);
		panel.add(btnNewButton_1_1_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "LABOR", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setBounds(23, 291, 714, 179);
		contentPane.add(panel_1);
		
		JButton btnNewButton_1_2 = new JButton("ADD ROW");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laborItemTableModel.addEmptyRow();
				laborTable.revalidate(); // redraws, update the table data - https://www.daniweb.com/programming/software-development/threads/26443/jtable-reloading-refreshing-updating-whatever
			}
		});
		btnNewButton_1_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_2.setBounds(588, 23, 90, 26);
		panel_1.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_1_2 = new JButton("DELETE ROW");
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIdx = laborTable.getSelectedRow();
				if (selectedRowIdx < 0 || laborItemTableModel.getLaborItem(selectedRowIdx) == null) {
					//Utils.msgBox(selectedRowIdx);
					return;
				}
				laborItemTableModel.removeLaborItem(selectedRowIdx);
				laborTable.getSelectionModel().clearSelection();
				laborTable.revalidate();
			}
		});
		btnNewButton_1_1_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1_2.setBounds(588, 61, 109, 26);
		panel_1.add(btnNewButton_1_1_2);
		
		JButton btnNewButton_1_1_1_2 = new JButton("MOVE UP");
		btnNewButton_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIdx = laborTable.getSelectedRow();
				if (selectedRowIdx < 0 || laborItemTableModel.getLaborItem(selectedRowIdx) == null) {
					//Utils.msgBox(selectedRowIdx);
					return;
				}
				// swaps the selected row with the one in the bottom
				int upperItemIdx = selectedRowIdx - 1;
				if (upperItemIdx >= 0) {
					laborItemTableModel.swapRowPosition(selectedRowIdx, upperItemIdx);
					laborTable.revalidate();
				}
			}
		});
		btnNewButton_1_1_1_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1_1_2.setBounds(588, 99, 109, 26);
		panel_1.add(btnNewButton_1_1_1_2);
		
		JButton btnNewButton_1_1_1_1_1 = new JButton("MOVE DOWN");
		btnNewButton_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIdx = laborTable.getSelectedRow();
				if (selectedRowIdx < 0 || laborItemTableModel.getLaborItem(selectedRowIdx) == null) {
					//Utils.msgBox(selectedRowIdx);
					return;
				}
				// swaps the selected row with the one in the bottom
				int lowerItemIdx = selectedRowIdx + 1;
				if (lowerItemIdx < laborItemTableModel.getRowCount()) {
					laborItemTableModel.swapRowPosition(selectedRowIdx, lowerItemIdx);
					laborTable.revalidate();
				}
			}
		});
		btnNewButton_1_1_1_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1_1_1_1.setBounds(588, 136, 109, 26);
		panel_1.add(btnNewButton_1_1_1_1_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(18, 28, 558, 135);
		panel_1.add(scrollPane_1);
		
		laborTable = new JTable();
		laborTable.getTableHeader().setReorderingAllowed(false);
		laborTable.setModel(laborItemTableModel);
		// right text alignment
		customCellRenderer.setHorizontalAlignment( JLabel.RIGHT );
		laborTable.getColumnModel().getColumn(1).setCellRenderer(customCellRenderer);
		laborTable.getColumnModel().getColumn(2).setCellRenderer(customCellRenderer);
		laborTable.getColumnModel().getColumn(3).setCellRenderer(customCellRenderer);
		//
		laborTable.getColumnModel().getColumn(0).setResizable(false);
		laborTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		laborTable.getColumnModel().getColumn(0).setMinWidth(300);
		laborTable.getColumnModel().getColumn(1).setResizable(false);
		laborTable.getColumnModel().getColumn(2).setResizable(false);
		laborTable.getColumnModel().getColumn(3).setResizable(false);
		scrollPane_1.setViewportView(laborTable);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateModelFromInputs();
				// update list on invoice modifier screen
				App.invoiceEditorScreen.getList().repaint();
			}
		});
		btnNewButton.setBounds(23, 486, 65, 26);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("RESET");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetInputs();
			}
		});
		btnNewButton_2.setBounds(100, 486, 71, 26);
		contentPane.add(btnNewButton_2);
	}


	public void updateInputsFromModel() {
		titleInput.setText(currentInvoiceItem.getTitle());
		partItemTableModel.setData(currentInvoiceItem.getPartItems());
		laborItemTableModel.setData(currentInvoiceItem.getLaborItems());
	}
	
	public void updateModelFromInputs() {
		currentInvoiceItem.setTitle(titleInput.getText());
		currentInvoiceItem.setPartItems(partItemTableModel.getData());
		currentInvoiceItem.setLaborItems(laborItemTableModel.getData());
	}

	@Override
	public void resetInputs() {
		// TODO Auto-generated method stub
		titleInput.setText("");
		partItemTableModel.setData(new ArrayList<PartItem>());
		laborItemTableModel.setData(new ArrayList<LaborItem>());
	}
	
}
