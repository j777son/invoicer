package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import com.engineworksva.invoicer.App;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.Cursor;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfigurationEditorScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField addressInput;
	private JTextField phoneInput;
	private JTextField emailInput;
	private JTextField websiteInput;
	private JSpinner taxInput;

	/**
	 * Create the frame.
	 */
	public ConfigurationEditorScreen() {
		setTitle("CONFIGURATION");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		setSize(589, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("SAVE TO DB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSettingsFromInput();
				App.database.saveSettings();
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(28, 358, 103, 26);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "COMPANY INFO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(27, 74, 504, 209);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADDRESS");
		lblNewLabel.setBounds(22, 39, 55, 16);
		panel.add(lblNewLabel);
		
		addressInput = new JTextField();
		addressInput.setBounds(108, 35, 373, 20);
		panel.add(addressInput);
		addressInput.setColumns(10);
		
		JLabel lblPhone = new JLabel("PHONE");
		lblPhone.setBounds(22, 80, 55, 16);
		panel.add(lblPhone);
		
		phoneInput = new JTextField();
		phoneInput.setColumns(10);
		phoneInput.setBounds(108, 76, 373, 20);
		panel.add(phoneInput);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(22, 121, 55, 16);
		panel.add(lblEmail);
		
		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(108, 117, 373, 20);
		panel.add(emailInput);
		
		JLabel lblWebsite = new JLabel("WEBSITE");
		lblWebsite.setBounds(22, 162, 55, 16);
		panel.add(lblWebsite);
		
		websiteInput = new JTextField();
		websiteInput.setColumns(10);
		websiteInput.setBounds(108, 158, 373, 20);
		panel.add(websiteInput);
		
		JLabel lblTaxPercentage = new JLabel("TAX PERCENTAGE");
		lblTaxPercentage.setBounds(28, 301, 102, 16);
		contentPane.add(lblTaxPercentage);
		
		taxInput = new JSpinner();
		taxInput.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));
		taxInput.setBounds(142, 299, 76, 20);
		contentPane.add(taxInput);
		
		JButton btnNewButton_1 = new JButton("RELOAD FROM DB");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reloadFromDB();
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBounds(395, 23, 136, 26);
		contentPane.add(btnNewButton_1);
		
		reloadInputData();
	}
	
	public void reloadInputData() {
		addressInput.setText(App.settings.get("address").toString());
		phoneInput.setText(App.settings.get("phone").toString());
		emailInput.setText(App.settings.get("email").toString());
		websiteInput.setText(App.settings.get("website").toString());
		taxInput.setValue(Double.parseDouble(App.settings.get("taxValue").toString()));
	}
	
	public void setSettingsFromInput() {
		App.settings.replace("address", addressInput.getText());
		App.settings.replace("phone", phoneInput.getText());
		App.settings.replace("email", emailInput.getText());
		App.settings.replace("website", websiteInput.getText());
		App.settings.replace("taxValue", (double) taxInput.getValue());
	}
	
	public void reloadFromDB() {
		App.database.reloadSettings();
		reloadInputData();
	}
	
	public JSpinner getTaxInput() {
		return taxInput;
	}
}
