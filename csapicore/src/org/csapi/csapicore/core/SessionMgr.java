/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

import org.csapi.csapicore.exceptions.PluginException;
import org.csapi.csapicore.utils.SimpleSaxParser;
import org.csapi.csapicore.utils.XMLProvider;


/**
 * <p>The SessionMgr class takes care of the communication with the server. It
 * contains all needed information to handle a full session, from login to 
 * reports.</p>
 * 
 * <p>It also provides a single point of access to methods related to session 
 * handling, with items such as csapi_token. Since there is only one SessionMgr for the current eclipse session, the 
 * singleton design pattern is used.</p>
 * 
 * <p>For more information about the protocol used by the Telelogic Perl API,
 * please refer to documentation on the <a 
 * href="http://developer.berlios.de/docman/?group_id=4391">project 
 * site</a>.</p>
 * 
 * @author Boris Baldassari
 */
public class SessionMgr {
	
	/** The shared instance, for the singleton design pattern. */
	private static SessionMgr instance;
	
	/** The favorite object loaded for this instance of SessionMgr. */
	private Favorites favorites;

	/** The token to be used when communicating with the server. */
	private String csapiToken = "";

	/** The login of the current user. */
	private String csapiUser = "";

	/** The password of the current user. */
	private String csapiPassword = "";

	/** Always false, needed by the protocol. */
	private boolean csapiEncodedPassword = false;

	/** The CS role of the current user. */
	private String csapiRole = "";
	
	/** The IP of the CS server. */
	private String csapiServerIP = "";
	
	/** The path to the database, url encoded. */
	private String csapiDatabase = "";
	
	/** The full path to the csapi on server, including protocol, address,
	 *  port, and path on server. */
	private String fullServerAddress = "";

	/**
	 * Constructor -- public for initialization to force clients to use 
	 * getDefault().
	 */
	public SessionMgr(
			String csapiUser,
			String csapiPassword,
			String csapiRole,
			String serverIP,
			String serverDB) {
		super();
		
		this.csapiUser = csapiUser;
		this.csapiPassword = csapiPassword;
		this.csapiRole = csapiRole;
		this.csapiServerIP = serverIP;
		this.csapiDatabase = serverDB;
		fullServerAddress = "http://" + this.csapiServerIP
		+ "/servlet/com.continuus.websynergy.servlet.CsAPI";
		
		favorites = new Favorites();
		
		instance = this;
	}
	
	/**
	 * The singleton accessor. Returns the shared instance or null if the 
	 * object has not been initialized. This method is useless as long as 
	 * a SessionMgr object has been initialized through the above 
	 * constructor.
	 * 
	 * @return The shared instance, null if the object has not been 
	 * initialized.
	 */
	public static SessionMgr getDefault() {
		return instance;
	}

	/** 
	 * Returns a ready-to-use xml string corresponding to a login attempt.
	 * This String can safely be sent through a socket to the Synergy/Change
	 * server.
	 * 
	 * @return the xml string.
	 */	
	private String getXMLLogin() {
		String myXML = "";

		myXML += XMLProvider.getXMLSoapHead();
		myXML += XMLProvider.getXMLActionFlagLogin();
		myXML += XMLProvider.getXMLEncodedPassword(csapiEncodedPassword);
		myXML += XMLProvider.getXMLUser(csapiUser);
		myXML += XMLProvider.getXMLPassword(csapiPassword);
		myXML += XMLProvider.getXMLRole(csapiRole);
		myXML += XMLProvider.getXMLDatabase(csapiDatabase);
		myXML += XMLProvider.getXMLSoapTail();

		return myXML;
	}

	/** 
	 * Returns a ready-to-use xml string corresponding to a record search.
	 * This String can safely be sent through a socket to the Synergy/Change
	 * server.
	 * 
	 * @param query The query to be executed on the server. 
	 * @param attributes The list of attributes to be retrieved, 
	 * separated by pipes.
	 * 
	 * @return The full xml string.
	 */	
	private String getXMLFindRecord(String query, String attributes) {
		
		/* Report additional information used in request. */
		String reportName = "Basic%20Summary";
		String reportTitle = "csapi%20project%20data%20report";
		
		if (csapiToken.equalsIgnoreCase("")) {
			/* XXX throw an exception
			 * Login has not been successfully requested, and
			 * csapi_token is not set correctly. */
			return "You must login before acting.";
		}
		
		String myXML = "";
		String encodedQuery = "";
		String encodedAttributes = "";
		
		try {
			encodedQuery = URLEncoder.encode(query, "UTF-8");
			encodedAttributes = URLEncoder.encode(attributes, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			return null;
		}
			
		myXML += XMLProvider.getXMLSoapHead();
		myXML += XMLProvider.getXMLCsapiToken(csapiToken);
		myXML += XMLProvider.getXMLRole(csapiRole);
		myXML += XMLProvider.getXMLDatabase(csapiDatabase);
		myXML += XMLProvider.getXMLUser(csapiUser);
		myXML += XMLProvider.getXMLActionFlagDataReport();
		myXML += XMLProvider.getXMLChosenReport(reportName);
		myXML += XMLProvider.getXMLQueryString(encodedQuery);
		myXML += XMLProvider.getXMLReportTitle(reportTitle);
		myXML += XMLProvider.getXMLAttributeList(encodedAttributes);
		myXML += XMLProvider.getXMLSoapTail();

		return myXML;
	}
	
	
	/**
	 * Creates the HTTP connection. Mostly useful to set correct headers. 
	 * 
	 * @return The connection to be used for communications.
	 */
	private HttpURLConnection getConnection() throws PluginException {

		String server = fullServerAddress;

		try {
			URL u = new URL(server);
			URLConnection uc = u.openConnection();
			HttpURLConnection connection = (HttpURLConnection) uc;

			/* Try to reproduce headers of the Perl client. */
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.addRequestProperty("TE", "deflate,gzip;q=0.3");
			connection.setRequestProperty("Connection", "TE, close");

			return connection;
		} catch (Exception e) {
			throw new PluginException("Could not connect to the server. Please "
					+ "check your settings.");
		}
	}

	/**
	 * Connects to the server, sends the XML request furnished as parameter,
	 * reads feedback from server through a SimpleSaxParser and returns the 
	 * corresponding object.
	 * 
	 * It handles all the network-related stuff and provides a high level
	 * interface to methods like login() or getReport(String, String), which
	 * should not care about buffered readers.
	 * 
	 * @param xmlRequest The XML full request to send to the server.
	 * @return A SimpleSaxParser object, containing XML analysis result.
	 */
	private SimpleSaxParser connect(String xmlRequest) throws PluginException {
		/* Ask for the connection. */
		HttpURLConnection connection = getConnection();
		
		/* Send the xmlRequest. */
		try {
			/* Get the output stream to the server. */
			OutputStream out = connection.getOutputStream();
			Writer wout = new OutputStreamWriter(out);

			/* Write the XML string on the writer. */
			wout.write(xmlRequest);

			wout.flush();
			wout.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new PluginException("Error " 
					+ e.getMessage() + ": \n"
					+ e.getMessage() + ".");
		}
		
		/* Receive feedback from server. */
		try {
			/* Get the input stream for the server response. */
			InputStream in = connection.getInputStream();
			
			/* Read it through the xml parser. The SimpleSaxParser creates
			 * on-the-fly objects (e.g. report), and holds them until its
			 * disposal. */
			SimpleSaxParser simpleSaxParser = SimpleSaxParser.run(in);
			
			if (simpleSaxParser.getFaultCode() != 0) {
				throw new PluginException("Error " 
						+ simpleSaxParser.getFaultCode() + ": \n"
						+ simpleSaxParser.getFaultString() + ".");
			}
			
			return simpleSaxParser;
		} catch (IOException e) {
			throw new PluginException("IOException: " + e.getMessage());
		}
	}
	
	
	/**
	 * Login to the server and get the token identifying the user for this 
	 * session. Must be the first method to be invoked, since the token is
	 * mandatory for any other action.
	 */
	public void login() throws PluginException {
		/* Get the XML string to be sent to the server. */
		String xmlRequest = getXMLLogin();
		
		SimpleSaxParser simpleSaxParser = connect(xmlRequest);

		this.csapiToken = simpleSaxParser.getCsapiToken();
	}
	
	
	/**
	 * Get a report, once login() has been done.
	 * 
	 * If no csapiToken is available (i.e. the user is not logged in 
	 * Change Synergy) returns a null report.
	 * 
	 * @param query The query to execute on the server, as it would have 
	 * been entered into the "Query" web interface.
	 * @param attributesList The list of attributes to retrieve, separated by 
	 * pipes.
	 * @return A report containing all records returned by the server.
	 */
	public Report getReport(String query, String attributesList) 
		throws PluginException {

		Report myReport = null;
		
		/* Checks if the csapiToken is set (i.e. not null). If not (i.e.
		 * csapiToken is null) return an empty report for now.
		 * TODO throw an exception. */
		if (csapiToken.equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "You have to login first.");
			return new Report();
		}

		/* Get the XML string to be sent to the server. */
		String xmlRequest = getXMLFindRecord(query, attributesList);
		
		SimpleSaxParser simpleSaxParser = connect(xmlRequest);
		
		/* Get the report from the XML analyse. */
		myReport = simpleSaxParser.getReport();
		myReport.setQuery(query);
		myReport.setAttributes(attributesList.split("\\|"));
			
		// DEBUG ------------------------------------
		String[] myStrings = myReport.toStrings();
		for (int i = 0 ; i < myStrings.length ; i++)
			System.out.println(myStrings[i]);
		// DEBUG ------------------------------------

		favorites.addFavoriteReport(myReport);
		
		return myReport;
	}

	/**
	 * Set the csapiDatabase private attribute.
	 * @param csapiDatabase The csapiDatabase to set.
	 */
	public void setCsapiDatabase(String csapiDatabase) {
		this.csapiDatabase = csapiDatabase;
	}

	/**
	 * Set the csapiPassword private attribute.
	 * 
	 * @param csapiPassword The csapiPassword to set.
	 */
	public void setCsapiPassword(String csapiPassword) {
		this.csapiPassword = csapiPassword;
	}

	/**
	 * Set the csapiRole private attribute.
	 * 
	 * @param csapiRole The csapiRole to set.
	 */
	public void setCsapiRole(String csapiRole) {
		this.csapiRole = csapiRole;
	}

	/**
	 * Set the csapiServerIP private attribute.
	 * 
	 * @param csapiServerIP The csapiServerIP to set.
	 */
	public void setCsapiServerIP(String csapiServerIP) {
		this.csapiServerIP = csapiServerIP;

		fullServerAddress = "http://" + csapiServerIP
			+ "/servlet/com.continuus.websynergy.servlet.CsAPI";
	}

	/**
	 * Set the csapiUser private attribute.
	 * 
	 * @param csapiUser The csapiUser to set.
	 */
	public void setCsapiUser(String csapiUser) {
		this.csapiUser = csapiUser;
	}
	
	public void setFavorites(Favorites favorites) {
		this.favorites = favorites;
	}
	
	public Favorites getDefaultFavorites() {
		if (favorites == null) {
			this.favorites = new Favorites();
			return this.favorites;
		}
		return favorites;
	}
}