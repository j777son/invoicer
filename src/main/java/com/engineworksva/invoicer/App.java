package com.engineworksva.invoicer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.engineworksva.invoicer.screens.ConfigurationEditorScreen;
import com.engineworksva.invoicer.screens.CustomerEditorScreen;
import com.engineworksva.invoicer.screens.InvoiceEditorScreen;
import com.engineworksva.invoicer.screens.MenuScreen;
import com.engineworksva.invoicer.screens.SavedCustomersScreen;
import com.engineworksva.invoicer.screens.SavedInvoicesScreen;

public class App {

	// enables debug message printing and some tests execution (not sure yet)
	public static boolean DEBUG = false;
	
	// some constants used as settings
	public static HashMap<String, Object> settings;
	
	// Database manager
	public static Database database;

	// Screens, ui's, GUI or however you want to call it
	public static MenuScreen menuScreen;
	public static InvoiceEditorScreen invoiceEditorScreen;
	public static CustomerEditorScreen customerEditorScreen;
	public static ConfigurationEditorScreen configurationEditorScreen;
	public static SavedInvoicesScreen savedInvoicesScreen;
	public static SavedCustomersScreen savedCustomersScreen;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		// an instance opened check
		new Thread(() -> {
			try {
				new ServerSocket(9777).accept();
			} catch (IOException e) {
				System.err.println("the application is probably already started, " + e.getLocalizedMessage());
				Utils.msgBox("Error", "cannot run the program twice.", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}).start();

		// program arguments
		if (args.length > 0) {
			DEBUG = Boolean.parseBoolean(args[0].split("=")[1]);
			Utils.print(args[0]);
		}

		// re-install the Metal Look and Feel
		// the theme of the ui's
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		settings = new HashMap<String, Object>();
		
		database = new Database();
		
		menuScreen = new MenuScreen();
		invoiceEditorScreen = new InvoiceEditorScreen();
		customerEditorScreen = new CustomerEditorScreen();
		configurationEditorScreen = new ConfigurationEditorScreen();
		savedInvoicesScreen = new SavedInvoicesScreen();
		savedCustomersScreen = new SavedCustomersScreen();

		//open the menu UI
		menuScreen.setVisible(true);

		//Tests.createSampleInvoice();
		
		//this should be used to handle decimal stuff
		/*BigDecimal a = new BigDecimal("10.39");
		BigDecimal b = new BigDecimal("15.39");
		String test = (a.add(b)) + ""; 
		System.out.println(test);*/
		
	}

}
