package com.engineworksva.invoicer.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.engineworksva.invoicer.App;
import com.engineworksva.invoicer.Utils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class MenuScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel versionLabel;

	/**
	 * Create the frame.
	 */
	public MenuScreen() {
		setTitle("MENU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(512, 300);
		setLocation(100, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSettings = new JButton("CONFIGURATION");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.configurationEditorScreen.setVisible(true);
			}
		});
		btnSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSettings.setBounds(183, 173, 129, 26);
		contentPane.add(btnSettings);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "INVOICES", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(27, 27, 188, 120);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("CREATE INVOICE");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(24, 33, 128, 26);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("SAVED INVOICES");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.savedInvoicesScreen.setVisible(true);
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBounds(24, 71, 130, 26);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "CUSTOMERS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setBounds(243, 27, 217, 120);
		contentPane.add(panel_1);
		
		JButton btnNewCustomer = new JButton("CREATE CUSTOMER");
		btnNewCustomer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewCustomer.setBounds(34, 33, 147, 26);
		panel_1.add(btnNewCustomer);
		
		JButton btnSavedCustomer = new JButton("SAVED CUSTOMERS");
		btnSavedCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.savedCustomersScreen.setVisible(true);
			}
		});
		btnSavedCustomer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSavedCustomer.setBounds(34, 71, 149, 26);
		panel_1.add(btnSavedCustomer);
		
		JLabel lblNewLabel = new JLabel("@");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setForeground(new Color(245, 245, 245));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Utils.msgBox("jefersson.com");
			}
		});
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.setBounds(472, 233, 24, 16);
		contentPane.add(lblNewLabel);
		
		versionLabel = new JLabel("Last update on Dec 1, 2024");
		versionLabel.setForeground(new Color(192, 192, 192));
		versionLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		versionLabel.setBounds(12, 233, 131, 14);
		contentPane.add(versionLabel);
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.customerEditorScreen.setVisible(true);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.invoiceEditorScreen.setVisible(true);
			}
		});
	}
	public JLabel getVersionLabel() {
		return versionLabel;
	}
}
