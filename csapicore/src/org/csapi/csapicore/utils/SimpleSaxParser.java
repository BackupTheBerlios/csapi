/**
 * Created on 11 Aout 2005.
 */

package org.csapi.csapicore.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;

import org.csapi.csapicore.core.Report;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Creates and instanciates the SimpleContentHandler. Handles XML features
 * related to the XML parser (xerces) and run the effective parsing of the 
 * XML stream.
 * 
 * @author Boris Baldassari
 */
public class SimpleSaxParser {

	private String csapiToken;
	private Report currentReport;

	/**
	 * Contructor.
	 * 
	 * @param inputStream The inputStream to read from.
	 * @throws SAXException 
	 * @throws IOException
	 */
	protected SimpleSaxParser(InputStream inputStream) 
			throws SAXException, IOException {
		
		/* Indicates what parser we want to use. */
		System.setProperty(
		    "org.xml.sax.driver", 
		    "org.apache.xerces.parsers.SAXParser"
		    );
		
		/* Change charset on-the-fly. */
		InputStreamReader isr = new InputStreamReader(inputStream, 
				Charset.forName("ISO-8859-1"));
		
		/* Read XML, save it in fullXML and apply some modifications to
		 * make it more parser-friendly. Don't know how the perl module
		 * can handle such a crap. 
	 	 * No CDATA section is used in the stream, and some characters put
		 * a real mess while parsing. The only solution for now is to insert
		 * CDATA sections in attribute values. Time- and resource consuming,
		 * but needed. */
		BufferedReader bufReader = new BufferedReader(isr);
		String fullXML = "";
		String line = "";
		while ((line = bufReader.readLine()) != null) {
			fullXML = fullXML + line + "\n"; 
		}
		fullXML = fullXML.replaceAll("<csapi_cobject_data_value>",
				"<csapi_cobject_data_value><![CDATA[");
		fullXML = fullXML.replaceAll("</csapi_cobject_data_value>",
				"]]></csapi_cobject_data_value>");
		fullXML = fullXML.replaceAll("<csapi_cobject_data_date>",
				"<csapi_cobject_data_date><![CDATA[");
		fullXML = fullXML.replaceAll("</csapi_cobject_data_date>",
				"]]></csapi_cobject_data_date>");
		StringReader sReader = new StringReader(fullXML);

		InputSource inputSource = new InputSource(sReader);
		
		XMLReader saxReader = XMLReaderFactory.createXMLReader();

		/* Set the namespace-aware feature of the parser to false
		 * (XML is not validating). Also force the parser to continue
		 * parsing on fatal error. */
		saxReader.setFeature("http://xml.org/sax/features/namespaces", false);
		saxReader.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);		

		SimpleContentHandler mySimpleContentHandler = new SimpleContentHandler();
		saxReader.setContentHandler(mySimpleContentHandler);
		saxReader.parse(inputSource);
		
		/* csapiToken and currentReport are get from SimpleContentHandler 
		 * and forwarded (or rather get by) this class caller. */
		csapiToken = mySimpleContentHandler.getCsapiToken();
		currentReport = mySimpleContentHandler.getReport();
	}

	/**
	 * Parse the inputStream provided as parameter. This really invokes 
	 * the SimpleContentHandler on XML stream and creation of objects.
	 * 
	 * @param inputStream The XML stream to be parsed.
	 * @return A SimpleSaxParser object with objects created during parsing.
	 */
	public static SimpleSaxParser run(InputStream inputStream) {
		try {
			SimpleSaxParser parser = new SimpleSaxParser(inputStream);
			return parser;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get csapiToken private attribute.
	 * @return The csapiToken String.
	 */
	public String getCsapiToken() {
		return csapiToken;
	}

	/**
	 * Get the currentReport private attribute.
	 * @return The report attached to this object.
	 */
	public Report getReport() {
		return currentReport;
	}
}
