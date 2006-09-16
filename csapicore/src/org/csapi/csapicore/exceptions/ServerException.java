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

    public ServerException(String msg) {
        super(msg);
    }
}
