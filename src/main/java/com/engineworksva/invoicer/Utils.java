package com.engineworksva.invoicer;

import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class Utils {
	
// 	// https://stackoverflow.com/a/2808648/6907110
//	public static double rounds(double value, int places) {
//	    if (places < 0) throw new IllegalArgumentException();
//
//	    BigDecimal bd = BigDecimal.valueOf(value);
//	    bd = bd.setScale(places, RoundingMode.HALF_UP);
//	    return bd.doubleValue();
//	}

	public static String formatDouble(double value) {
		String doubleWithTwoDecimal = String.format("%.2f", value);
		//return doubleWithTwoDecimal;
		return NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(doubleWithTwoDecimal));
	}
	
	public static void msgBox(Object obj) {
		msgBox(obj.toString());
	}
	
	public static void msgBox(String msg) {
		msgBox("Notification", msg, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	public static void msgBox(String msg, int messageType) {
		msgBox("Notification", msg, JOptionPane.DEFAULT_OPTION, messageType, null);
	}
	
	public static void msgBox(String title, String msg) {
		msgBox(title, msg, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	public static void msgBox(String title, String msg, int messageType) {
		msgBox(title, msg, JOptionPane.DEFAULT_OPTION, messageType, null);
	}
	
    /*
    public static final int         DEFAULT_OPTION = -1;
    public static final int         YES_NO_OPTION = 0;
    public static final int         YES_NO_CANCEL_OPTION = 1;
    public static final int         OK_CANCEL_OPTION = 2;
    */
	/*
    public static final int  ERROR_MESSAGE = 0;
    public static final int  INFORMATION_MESSAGE = 1;
    public static final int  WARNING_MESSAGE = 2;
    public static final int  QUESTION_MESSAGE = 3;
	public static final int   PLAIN_MESSAGE = -1;
	 */
	public static void msgBox(String title, String msg, int optionType, int messageType, Icon icon) {
		JOptionPane.showConfirmDialog(null, msg, title, optionType, messageType, icon);
	}
	
	public static void print(Object msg) {
		print(msg.toString());
	}
	
	public static void print(String msg) {
		System.out.println(msg);
	}
	
	public static void debug(String msg) {
		if (App.DEBUG)
			print("[DEBUG]: " + msg);
	}
	
	/*
	 
	 Format Specifiers
	Here are the commonly used format specifiers:
	
	Specifier	Description
	%b, %B	"true" or "false" based on the argument
	%s, %S	a string
	%c, %C	a Unicode character
	%d	a decimal integer (used for integers only)
	%o	an octal integer (used for integers only)
	%x, %X	a hexadecimal integer (used for integers only)
	%e, %E	for scientific notation (used for floating-point numbers)
	%f	for decimal numbers (used for floating-point numbers)
	 
	 * 
	 */
	
}
