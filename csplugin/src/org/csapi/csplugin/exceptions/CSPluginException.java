/**
 * Created on november, 2nd 2005.
 */
package org.csapi.csplugin.exceptions;

/**
 * <p>An exception dedicated to the plugin, designed to handle contents of
 * known exceptions for the plugin. Since we do only use the message and
 * title facilities, it almost maps on the Java Exception. But we may add 
 * more customized information later on.</p>
 * 
 * @author Boris Baldassari
 *
 */
public class CSPluginException extends Exception {

/**
 * Auto(Eclipse)-generated serialVersionUID number.
 */
private static final long serialVersionUID = 1L;

private String title = "";
private String message = "";

/**
 * The constructor for the action.
 */
public CSPluginException(final String title, final String message) {
this.title = title;
this.message = message;
}

public String getMessage() {
return message;
}


public void setMessage(String message) {
this.message = message;
}


public String getTitle() {
return title;
}


public void setTitle(final String title) {
this.title = title;
}

}
