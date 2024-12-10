package com.engineworksva.invoicer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import com.engineworksva.invoicer.model.Invoice;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class InvoiceGenerator {
	
	//FileSystemView.getFileSystemView().getHomeDirectory().getPath();
	private static String outFilePath = "./lastPreview.html";

	public static void make(Invoice invoice, boolean open) throws IOException, TemplateException {
		// Create your Configuration instance, and specify if up to what FreeMarker
		// version (here 2.3.32) do you want to apply the fixes that are not 100%
		// backward-compatible. See the Configuration JavaDoc for details.
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_33);

		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
		cfg.setDirectoryForTemplateLoading(new File("./templates/"));

		// From here we will set the settings recommended for new projects. These
		// aren't the defaults for backward compatibility.

		// Set the preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
		cfg.setLogTemplateExceptions(false);

		// Wrap unchecked exceptions thrown during template processing into TemplateException-s:
		cfg.setWrapUncheckedExceptions(true);

		// Do not fall back to higher scopes when reading a null loop variable:
		cfg.setFallbackOnNullLoopVariable(false);

		// To accommodate to how JDBC returns values; see Javadoc!
		cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
		
		// Create the root hash. We use a Map here, but it could be a JavaBean too.
		Map<String, Object> root = new HashMap<>();
				
		root.put("companyAddress", App.settings.get("address"));
		root.put("companyPhone", App.settings.get("phone"));
		root.put("companyEmail", App.settings.get("email"));
		root.put("companyWebsite", App.settings.get("website"));
		
		// and put it into the root
		root.put("invoice", invoice);

		// format them first
		root.put("laborSum", Utils.formatDouble(invoice.getLaborTotalOnly()));
		root.put("partsSum", Utils.formatDouble(invoice.getPartsTotalOnly()));
		root.put("taxesSum", Utils.formatDouble(invoice.getTaxesSum()));
		root.put("totalSum", Utils.formatDouble(invoice.getTotalSum()));
		
		Template temp = cfg.getTemplate("template.ftlh");
		FileOutputStream fos = new FileOutputStream(outFilePath);
		Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8); 
		temp.process(root, out); //System.out 
		
		if (open) {
			if (Desktop.isDesktopSupported()) {
			    try {
			        File myFile = new File(outFilePath);
			        Desktop.getDesktop().open(myFile);
			    } catch (IOException ex) {
			        // no application registered to open the file
			    	Utils.msgBox("Invoice couldn't be opened because there is not application registered to open HTML files.");
			    }
			}
		}
		
	}

}
