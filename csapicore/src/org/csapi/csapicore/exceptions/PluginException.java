/**
 * 
 */
package org.csapi.csapicore.exceptions;

/**
 * @author Boris Baldassari
 * 
 */
public class PluginException extends Exception {

    /**
     * serialVersionUID, used (and generated) by Eclipse.
     */
    private static final long serialVersionUID = 3060844557073465309L;

    /**
     * @param msg the message to display to user.
     */
    public PluginException(final String msg) {
        super(msg);
    }
}
