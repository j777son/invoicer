package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.engineworksva.invoicer.App;
import com.engineworksva.invoicer.InvoiceGenerator;
import com.engineworksva.invoicer.Utils;
import com.engineworksva.invoicer.model.Customer;
import com.engineworksva.invoicer.model.Invoice;
import com.engineworksva.invoicer.model.InvoiceItem;
import com.engineworksva.invoicer.model.EditorScreenModel;
import com.engineworksva.invoicer.model.Vehicle;

import freemarker.template.TemplateException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Cursor;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class InvoiceEditorScreen extends JFrame implements EditorScreenModel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Customer> comboBox;
	private JButton btnNewButton_1;
	private JTextField makeInput;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JTextField modelInput;
	private JSpinner mileageInput;
	private JSpinner yearInput;
	private JTextField vinInput;
	private JList<InvoiceItem> list;
	private JButton btnAdd;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_2;
	
	public DefaultComboBoxModel<Customer> customerComboModel = new DefaultComboBoxModel<Customer>();
	public DefaultListModel<InvoiceItem> invoiceItemsListModel = new DefaultListModel<InvoiceItem>();
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnNewButton_5;

	public Invoice currentInvoice = new Invoice();
	
	private InvoiceItemEditorScreen invoiceItemEditorScreen = new InvoiceItemEditorScreen();
	private JButton btnNewButton_7;
	
	/**
	 * Create the frame.
	 */
	public InvoiceEditorScreen() {
		setSize(new Dimension(700, 650));
		setTitle("INVOICE EDITOR");
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CUSTOMER");
		lblNewLabel.setBounds(20, 34, 65, 16);
		contentPane.add(lblNewLabel);
		
		comboBox = new JComboBox<Customer>();
		comboBox.setMaximumRowCount(999);
		comboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		comboBox.setModel(customerComboModel);
		comboBox.setBounds(104, 28, 391, 28);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("RELOAD FROM DB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customerComboModel.removeAllElements();
				loadCustomers();
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(499, 28, 136, 28);
		contentPane.add(btnNewButton);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "VEHICLE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 82, 345, 186);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Make");
		lblNewLabel_2.setBounds(15, 30, 55, 16);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		lblNewLabel_3 = new JLabel("Model");
		lblNewLabel_3.setBounds(15, 58, 55, 16);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		lblNewLabel_4 = new JLabel("Year");
		lblNewLabel_4.setBounds(15, 86, 55, 16);
		panel.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		lblNewLabel_5 = new JLabel("Mileage");
		lblNewLabel_5.setBounds(15, 114, 55, 16);
		panel.add(lblNewLabel_5);
		lblNewLabel_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		lblNewLabel_6 = new JLabel("VIN");
		lblNewLabel_6.setBounds(15, 142, 55, 16);
		panel.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		modelInput = new JTextField();
		modelInput.setBounds(88, 58, 229, 20);
		panel.add(modelInput);
		modelInput.setToolTipText("model");
		modelInput.setColumns(10);
		
		mileageInput = new JSpinner();
		mileageInput.setModel(new SpinnerNumberModel(0, 0, 999999, 10));
		mileageInput.setBounds(88, 114, 229, 20);
		panel.add(mileageInput);
		mileageInput.setToolTipText("mileage");
		
		yearInput = new JSpinner();
		yearInput.setModel(new SpinnerNumberModel(Integer.valueOf(0), null, null, Integer.valueOf(1)));
		yearInput.setBounds(88, 86, 229, 20);
		panel.add(yearInput);
		yearInput.setToolTipText("year");
		
		vinInput = new JTextField();
		vinInput.setBounds(88, 142, 229, 20);
		panel.add(vinInput);
		vinInput.setToolTipText("vin");
		vinInput.setColumns(10);
		
		makeInput = new JTextField();
		makeInput.setBounds(88, 30, 229, 20);
		panel.add(makeInput);
		makeInput.setToolTipText("make");
		makeInput.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "ITEMS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 289, 630, 248);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 35, 447, 196);
		panel_1.add(scrollPane);
		
		list = new JList<InvoiceItem>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Dialog", Font.PLAIN, 12));
		list.setModel(invoiceItemsListModel);
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        //JList<InvoiceItem> list = (JList<InvoiceItem>)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            // Double-click detected
		            //int index = list.locationToIndex(evt.getPoint());
		            //Utils.msgBox(index);
		            btnNewButton_3.doClick();
		        } else if (evt.getClickCount() == 3) {
		            // Triple-click detected
		            //int index = list.locationToIndex(evt.getPoint());
		        }
		    }
		});
		scrollPane.setViewportView(list);
		
		btnAdd = new JButton("ADD NEW");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				InvoiceItem iItem = new InvoiceItem();
				iItem.setTitle("Sample title");
				invoiceItemsListModel.addElement(iItem);
				
			}
		});
		btnAdd.setBounds(483, 35, 88, 26);
		panel_1.add(btnAdd);
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnNewButton_3 = new JButton("MODIFY");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIdx = list.getSelectedIndex();
				if (selectedIdx < 0)
					return;
				invoiceItemEditorScreen.setVisible(true);
				invoiceItemEditorScreen.currentInvoiceItem = invoiceItemsListModel.getElementAt(selectedIdx);
				invoiceItemEditorScreen.updateInputsFromModel();
			}
		});
		btnNewButton_3.setBounds(483, 71, 77, 26);
		panel_1.add(btnNewButton_3);
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnNewButton_4 = new JButton("DELETE");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIdx = list.getSelectedIndex();
				if (selectedIdx < 0)
					return;
				invoiceItemsListModel.remove(selectedIdx);
			}
		});
		btnNewButton_4.setBounds(483, 107, 77, 26);
		panel_1.add(btnNewButton_4);
		btnNewButton_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnNewButton_2 = new JButton("MOVE UP");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIdx = list.getSelectedIndex();
				if (selectedIdx < 0)
					return;
				int upperItemIdx = selectedIdx - 1;
				if (upperItemIdx >= 0) {
					InvoiceItem first = invoiceItemsListModel.getElementAt(selectedIdx);
					InvoiceItem second = invoiceItemsListModel.getElementAt(upperItemIdx);
					invoiceItemsListModel.set(selectedIdx, second);
					invoiceItemsListModel.set(upperItemIdx, first);
					list.clearSelection();
					//list.repaint();
				}
			}
		});
		btnNewButton_2.setBounds(483, 143, 87, 26);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnNewButton_5 = new JButton("MOVE DOWN");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIdx = list.getSelectedIndex();
				if (selectedIdx < 0)
					return;
				int lowerItemIdx = selectedIdx + 1;
				if (lowerItemIdx < invoiceItemsListModel.size()) {
					InvoiceItem first = invoiceItemsListModel.getElementAt(selectedIdx);
					InvoiceItem second = invoiceItemsListModel.getElementAt(lowerItemIdx);
					invoiceItemsListModel.set(selectedIdx, second);
					invoiceItemsListModel.set(lowerItemIdx, first);
					list.clearSelection();
				}
			}
		});
		btnNewButton_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_5.setBounds(483, 179, 108, 26);
		panel_1.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("RESET");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetInputs();
			}
		});
		btnNewButton_6.setBounds(255, 559, 71, 26);
		contentPane.add(btnNewButton_6);
		
		btnNewButton_1 = new JButton("SAVE TO DB");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				updateModelFromInputs();
				try {
					currentInvoice.setDateNTime();
					App.database.saveInvoice(currentInvoice);
					Utils.msgBox("Invoice was successfully saved in the database.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBounds(20, 559, 103, 26);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_7 = new JButton("PREVIEW");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateModelFromInputs();
				
				// getting a new number from database
				int fakeNumber = App.database.getInvoicesCount();
				
				currentInvoice.setNumber(fakeNumber + "");
				currentInvoice.setDateNTime();
				
				try {
					InvoiceGenerator.make(currentInvoice, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TemplateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_7.setBounds(140, 559, 98, 26);
		contentPane.add(btnNewButton_7);
		
		loadCustomers();
	}
	
	public void loadCustomers() {
		customerComboModel.addAll(App.database.getCustomers());
	}

	@Override
	public void resetInputs() {
		comboBox.setSelectedIndex(-1);
		makeInput.setText("");
		modelInput.setText("");
		yearInput.setValue(0);
		mileageInput.setValue(0);
		vinInput.setText("");
		
		invoiceItemsListModel.removeAllElements();
		
	}
	
	private void updateModelFromInputs() {
		Customer selectedCustomer = (Customer) customerComboModel.getSelectedItem();
		if (selectedCustomer == null) {
			Utils.msgBox("don't forget to select a customer");
			return;
		}
		currentInvoice.setCustomer(selectedCustomer);
		
		Vehicle vehicle = new Vehicle();
		vehicle.setMake(makeInput.getText());
		vehicle.setModel(modelInput.getText());
		vehicle.setYear(yearInput.getValue() + "");
		vehicle.setMileage(mileageInput.getValue() + "");
		vehicle.setVin(vinInput.getText());
		currentInvoice.setVehicle(vehicle);
		
		ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
		for (int i = 0; i<invoiceItemsListModel.getSize(); i++) {
			items.add(invoiceItemsListModel.get(i));
		}
		currentInvoice.setItems(items);
	}

	public JList<InvoiceItem> getList() {
		return list;
	}
	
}
