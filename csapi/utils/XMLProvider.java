/**
 * 
 */
package csapi.utils;

/**
 * 
 * 
 * @author grandpas
 */
public class XMLProvider {

	/**
	 * 
	 */
	public XMLProvider() {
		super();
	}

	/**
	 * @return the SOAP Header (a few xml tags always identical).
	 */
	public static String getXMLSoapHead() {
		return "XML_CONTENT=<SOAP-ENV:Envelope><SOAP-ENV:Body><SOAP-ENV:Request>";
	}

	/**
	 * @return the SOAP Tail (a few xml tags always identical)
	 */
	public static String getXMLSoapTail() {
		return "</SOAP-ENV:Request></SOAP-ENV:Body></SOAP-ENV:Envelope>";
	}

	/**
	 * @return the <action_flag>xml string.
	 */
	public static String getXMLActionFlagLogin() {
		String myXML = "";
		myXML += "%3Ccsapi_action_flag%3E" + "login"
				+ "%3C%2Fcsapi_action_flag%3E";
		return myXML;
	}

	/**
	 * @return the <action_flag>data_report xml string.
	 */
	public static String getXMLActionFlagDataReport() {
		String myXML = "";
		myXML += "%3Ccsapi_action_flag%3E" + "data_report"
				+ "%3C%2Fcsapi_action_flag%3E";
		return myXML;
	}

	/**
	 * @return Returns the csapiToken.
	 */
	public static String getXMLCsapiToken(String csapiToken) {
		String myXML = "";
		myXML += "%3Ccsapi_token%3E" + csapiToken + "%3C%2Fcsapi_token%3E";
		return myXML;
	}

	public static String getXMLPassword(String csapiPassword) {
		String myXML = "";
		myXML += "%3Ccsapi_password%3E" + csapiPassword
				+ "%3C%2Fcsapi_password%3E";
		return myXML;
	}

	public static String getXMLRole(String csapiRole) {
		String myXML = "";
		myXML += "%3Ccsapi_role%3E" + csapiRole + "%3C%2Fcsapi_role%3E";
		return myXML;
	}

	public static String getXMLDatabase(String csapiDatabase) {
		String myXML = "";
		myXML += "%3Ccsapi_database%3E" + csapiDatabase
				+ "%3C%2Fcsapi_database%3E";
		return myXML;
	}

	public static String getXMLEncodedPassword(boolean csapiEncodedPassword) {
		String myXML = "";
		myXML += "%3Ccsapi_encoded_password%3E" + csapiEncodedPassword
				+ "%3C%2Fcsapi_encoded_password%3E";
		return myXML;
	}

	public static String getXMLUser(String csapiUser) {
		String myXML = "";
		myXML += "%3Ccsapi_user%3E" + csapiUser + "%3C%2Fcsapi_user%3E";
		return myXML;
	}

}
