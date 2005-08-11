package csapi.utils;

import csapi.core.Report;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Cette classe est livree tel quel.
 * 
 * @author smeric
 * @version 1.0
 */
public class SimpleSaxParser {

	private String csapiToken;
	private Report currentReport;

	/**
	 * Contructeur.
	 */
	protected SimpleSaxParser(InputStream inputStream) throws SAXException,
			IOException {
		
		// Indicates what parser we want to use.
		System.setProperty(
		    "org.xml.sax.driver", 
		    "org.apache.xerces.parsers.SAXParser"
		    );
		
		XMLReader saxReader = XMLReaderFactory.createXMLReader();
		saxReader.setFeature("http://xml.org/sax/features/namespaces", false);
		
		SimpleContentHandler mySimpleContentHandler = new SimpleContentHandler();
		saxReader.setContentHandler(mySimpleContentHandler);
		saxReader.parse(new InputSource(inputStream));
		csapiToken = mySimpleContentHandler.getCsapiToken();
		currentReport = mySimpleContentHandler.getReport();
	}

	public static SimpleSaxParser run(InputStream inputStream) {
		try {
			SimpleSaxParser parser = new SimpleSaxParser(inputStream);
			return parser;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public String getCsapiToken() {
		return csapiToken;
	}


	public Report getReport() {
		return currentReport;
	}
}
