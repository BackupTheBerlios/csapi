/**
 * Created on november, 2nd 2005.
 */
package org.csapi.csplugin.exceptions;

/**
 * @author grandpas
 *
 */
public class CSPluginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CSPluginException(String title, String message) {
		this.title = title;
		this.message = message;
	}
	
	private String title = "";
	private String message = "";

	public String getMessage() {
		return message;
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}

}
