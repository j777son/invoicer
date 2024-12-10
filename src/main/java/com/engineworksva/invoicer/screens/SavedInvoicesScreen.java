package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.engineworksva.invoicer.App;
import com.engineworksva.invoicer.InvoiceGenerator;
import com.engineworksva.invoicer.model.tables.SavedInvoicesTableModel;

import freemarker.template.TemplateException;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class SavedInvoicesScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	SavedInvoicesTableModel savedInvoicesTableModel = new SavedInvoicesTableModel();
	private JButton btnNewButton_1;

	/**
	 * Create the frame.
	 */
	public SavedInvoicesScreen() {
		setTitle("SAVED INVOICES");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		setSize(938, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("PREVIEW");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idx = table.getSelectedRow();
					if (idx < 0)
						return;
					InvoiceGenerator.make(savedInvoicesTableModel.getInvoice(idx), true);
				} catch (IOException | TemplateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(807, 26, 87, 26);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 28, 761, 441);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(savedInvoicesTableModel);
		
		btnNewButton_1 = new JButton("RELOAD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadInvoices();
			}
		});
		btnNewButton_1.setBounds(796, 443, 81, 26);
		contentPane.add(btnNewButton_1);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setMinWidth(250);
		
		loadInvoices();
	}
	
	private void loadInvoices() {
		if (App.database.getInvoicesCount() > 0) {
			savedInvoicesTableModel.setData(App.database.getInvoices());
		}
	}
	
}
