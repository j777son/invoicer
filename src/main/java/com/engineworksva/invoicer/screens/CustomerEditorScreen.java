package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.engineworksva.invoicer.App;
import com.engineworksva.invoicer.Utils;
import com.engineworksva.invoicer.model.Customer;
import com.engineworksva.invoicer.model.EditorScreenModel;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class CustomerEditorScreen extends JFrame implements EditorScreenModel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameInput;
	private JTextField addressInput;
	private JTextField phoneInput;
	private JTextField emailInput;

	public Customer currentCustomer = new Customer();

	/**
	 * Create the frame.
	 */
	public CustomerEditorScreen() {
		setTitle("CUSTOMER EDITOR");
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 563, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NAME");
		lblNewLabel.setBounds(29, 33, 55, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblAddress = new JLabel("ADDRESS");
		lblAddress.setBounds(29, 107, 55, 16);
		contentPane.add(lblAddress);
		
		JLabel lblPhone = new JLabel("PHONE");
		lblPhone.setBounds(29, 181, 55, 16);
		contentPane.add(lblPhone);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(29, 255, 55, 16);
		contentPane.add(lblEmail);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = nameInput.getText();
				if(name.length() < 1) {
					Utils.msgBox("Name is required to create a customer in database.");
					return;
				}
				
				currentCustomer.setName(nameInput.getText());
				currentCustomer.setAddress(addressInput.getText());
				currentCustomer.setPhone(phoneInput.getText());
				currentCustomer.setEmail(emailInput.getText());
				
				App.database.saveCustomer(currentCustomer);
				Utils.msgBox("Customer saved successfully!");
			}
		});
		btnNewButton.setBounds(29, 321, 65, 26);
		contentPane.add(btnNewButton);
		
		nameInput = new JTextField();
		nameInput.setBounds(112, 31, 407, 20);
		contentPane.add(nameInput);
		nameInput.setColumns(10);
		
		addressInput = new JTextField();
		addressInput.setColumns(10);
		addressInput.setBounds(112, 105, 407, 20);
		contentPane.add(addressInput);
		
		phoneInput = new JTextField();
		phoneInput.setColumns(10);
		phoneInput.setBounds(112, 179, 407, 20);
		contentPane.add(phoneInput);
		
		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(112, 253, 407, 20);
		contentPane.add(emailInput);
		
		JLabel lblNewLabel_1 = new JLabel("required");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(29, 50, 55, 16);
		contentPane.add(lblNewLabel_1);
		
		JButton btnReset = new JButton("RESET");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setBounds(106, 321, 71, 26);
		contentPane.add(btnReset);
	}


	@Override
	public void resetInputs() {
		// TODO Auto-generated method stub
		
	}
}
