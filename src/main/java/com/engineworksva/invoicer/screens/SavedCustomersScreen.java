package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.engineworksva.invoicer.App;
import com.engineworksva.invoicer.model.tables.SavedCustomersTableModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SavedCustomersScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	SavedCustomersTableModel savedCustomersTableModel = new SavedCustomersTableModel();
	private JButton btnNewButton_1;


	/**
	 * Create the frame.
	 */
	public SavedCustomersScreen() {
		setTitle("SAVED CUSTOMERS SCREEN");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 941, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 26, 757, 461);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(savedCustomersTableModel);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("DELETE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = savedCustomersTableModel.getCustomer(table.getSelectedRow()).getId();
				App.database.deleteCustomer(id);
				savedCustomersTableModel.fireTableDataChanged();
			}
		});
		btnNewButton.setBounds(805, 24, 98, 26);
		btnNewButton.setVisible(false);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("RELOAD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCustomers();
			}
		});
		btnNewButton_1.setBounds(792, 461, 98, 26);
		contentPane.add(btnNewButton_1);
		
		loadCustomers();
	}
	
	public void loadCustomers() {
		if (App.database.getCustomersCount() > 0)
			savedCustomersTableModel.setData(App.database.getCustomers());
	}
	
}
