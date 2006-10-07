/**
 * 
 */
package org.csapi.csapicore.exceptions;

/**
 * @author Boris Baldassari
 * 
 */
public class ServerException extends Exception {

    /**
     * serialVersionUID, used (and generated) by Eclipse.
     */
    private static final long serialVersionUID = 5566798586151869983L;

    /**
     * @param msg message to display to user about the server exception.
     */
    public ServerException(final String msg) {
        super(msg);
    }
}
