/**
 * 
 */
package org.csapi.csapicore.utils;

/**
 * <p>XML provider utility for SessionMgr.</p> 
 * 
 * <p>SessionMgr provides a set of static methods to get XML-enrobed Strings 
 * for communications with the server. Basically, a request is modeled after
 * this structure:</p>
 * <ol>
 * <li>SOAP Head: some invariant XML for SOAP headers.</li>
 * <li>CSAPI Token: the identifier of the current session, relative to last
 * login on the server.</li>
 * <li>CSAPI Role: role to be used for this session.</li>
 * <li>CSAPI Database: path of the database to be used on the server.</li>
 * <li>CSAPI User: the login to be used for this session.</li>
 * <li>Action Flag: a keyword to state reason of request (either login, 
 * report, transition, etc.).</li>
 * <li>Some options for the selected action flag.</li>
 * <li>SOAP Tail: some invariant XML for SOAP tail.</li>
 * </ol>
 * 
 * @author Boris Baldassari
 */
public class XMLProvider {

	/**
	 * Constructor. Since all methods are static, should not be called -- 
	 * thus the private modifier.
	 */
	private XMLProvider() {
		super();
	}

	
	/**
	 * Static method to get XML-enrobed SOAP headers.
	 * 
	 * @return the SOAP Header (a few xml tags always identical).
	 */
	public static String getXMLSoapHead() {
		return "XML_CONTENT=<SOAP-ENV:Envelope><SOAP-ENV:Body>" 
			+ "<SOAP-ENV:Request>";
	}
	

	/**
	 * Static method to get XML-enrobed SOAP tail.
	 * 
	 * @return the SOAP Tail (a few xml tags always identical)
	 */
	public static String getXMLSoapTail() {
		return "</SOAP-ENV:Request></SOAP-ENV:Body></SOAP-ENV:Envelope>";
	}

	
	/**
	 * Static method to get XML-enrobed 'login' value of the action_flag tag.
	 * 
	 * @return the action_flag xml string for login.
	 */
	public static String getXMLActionFlagLogin() {
		String myXML = "";
		myXML += "%3Ccsapi_action_flag%3E" + "login"
				+ "%3C%2Fcsapi_action_flag%3E";
		return myXML;
	}
	

	/**
	 * Static method to get XML-enrobed 'data_report' value of the 
	 * action_flag tag. Value passed as parameter is assumed to be 
	 * already URL-encoded
	 * 
	 * @return the action_flag xml string for data_report.
	 */
	public static String getXMLActionFlagDataReport() {
		String myXML = "";
		myXML += "%3Ccsapi_action_flag%3E" + "data_report"
				+ "%3C%2Fcsapi_action_flag%3E";
		return myXML;
	}
	

	/**
	 * Static method to get XML-enrobed value for a provided csapi_token 
	 * value.
	 * 
	 * @param csapiToken The csapi_token to be used when sending the request (ensures 
	 * that user has successfuly logged in. 
	 * @return Returns the csapiToken enrobed with XML.
	 */
	public static String getXMLCsapiToken(String csapiToken) {
		String myXML = "";
		myXML += "%3Ccsapi_token%3E" + csapiToken + "%3C%2Fcsapi_token%3E";
		return myXML;
	}
	

	/**
	 * Static method to get XML-enrobed value for a provided csapi_password 
	 * value.
	 * 
	 * @param csapiPassword The password to be used for login.
	 * @return Returns the csapiPassword enrobed with XML.
	 */
	public static String getXMLPassword(String csapiPassword) {
		String myXML = "";
		myXML += "%3Ccsapi_password%3E" + csapiPassword
				+ "%3C%2Fcsapi_password%3E";
		return myXML;
	}
	

	/**
	 * Static method to get XML-enrobed value for a provided csapi_role
	 * value.
	 * 
	 * @param csapiRole The CS Role to be used for login.
	 * @return Returns the csapiRole enrobed with XML.
	 */
	public static String getXMLRole(String csapiRole) {
		String myXML = "";
		myXML += "%3Ccsapi_role%3E" + csapiRole + "%3C%2Fcsapi_role%3E";
		return myXML;
	}


	/**
	 * Static method to get XML-enrobed value for a provided csapi_database
	 * value. Value passed as parameter is assumed to be already URL-encoded.
	 * 
	 * @param csapiDatabase The path to the database to be used on the 
	 * server.
	 * @return Returns the csapiDatabase enrobed with XML.
	 */
	public static String getXMLDatabase(String csapiDatabase) {
		String myXML = "";
		myXML += "%3Ccsapi_database%3E" + csapiDatabase
				+ "%3C%2Fcsapi_database%3E";
		return myXML;
	}


	/**
	 * Static method to get XML-enrobed value for csapi_encoded_password
	 * value. This method is only used on login.
	 * 
	 * @param csapiEncodedPassword The csapiEncodedPassword value to be used.
	 * @return Returns the csapiEncodedPassword value enrobed with XML.
	 */
	public static String getXMLEncodedPassword(boolean csapiEncodedPassword) {
		String myXML = "";
		myXML += "%3Ccsapi_encoded_password%3E" + csapiEncodedPassword
				+ "%3C%2Fcsapi_encoded_password%3E";
		return myXML;
	}


	/**
	 * Static method to get XML-enrobed value for a provided csapi_user
	 * value. This method is only used on login.
	 * 
	 * @param csapiUser The User to be used for login.
	 * @return Returns the csapiUser enrobed with XML.
	 */
	public static String getXMLUser(String csapiUser) {
		String myXML = "";
		myXML += "%3Ccsapi_user%3E" + csapiUser + "%3C%2Fcsapi_user%3E";
		return myXML;
	}


	/**
	 * Static method to get XML-enrobed value for a provided csapiChosenReport
	 * value. This method is used when requesting a report. Value passed as 
	 * parameter is assumed to be already URL-encoded.
	 * 
	 * @param reportName The name of the report to be used.
	 * @return Returns the report name enrobed with XML.
	 */
	public static String getXMLChosenReport(String reportName) {
		String myXML = "";
		myXML += "%3Ccsapi_chosen_report%3E" + reportName + "%3C%2Fcsapi_chosen_report%3E";
		return myXML;
	}


	/**
	 * Static method to get XML-enrobed value for a provided query string
	 * value. This method is used when requesting a report. Value passed as 
	 * parameter is assumed to be already URL-encoded.
	 * 
	 * @param csapiRole The queryString to be used for the report.
	 * @return Returns the csapiRole enrobed with XML.
	 */
	public static String getXMLQueryString(String queryString) {
		String myXML = "";
		myXML += "%3Ccsapi_query_string%3E" + queryString + "%3C%2Fcsapi_query_string%3E";
		return myXML;
	}


	/**
	 * Static method to get XML-enrobed value for a provided reportTitle
	 * value. This method is used when requesting a report. Value passed 
	 * as parameter is assumed to be already URL-encoded.
	 * 
	 * @param reportTitle The report title to be used for report.
	 * @return Returns the csapiRole enrobed with XML.
	 */
	public static String getXMLReportTitle(String reportTitle) {
		String myXML = "";
		myXML += "%3Ccsapi_report_title%3E" + reportTitle + "%3C%2Fcsapi_report_title%3E";
		return myXML;
	}
	

	/**
	 * Static method to get XML-enrobed value for an attribute list, separated
	 * by pipes. This method is used when requesting a report.
	 * */
	public static String getXMLAttributeList(String attributeList) {
		String myXML = "";
		myXML += "%3Ccsapi_attribute_list%3E" + attributeList + "%3C%2Fcsapi_attribute_list%3E";
		return myXML;
	}
}
