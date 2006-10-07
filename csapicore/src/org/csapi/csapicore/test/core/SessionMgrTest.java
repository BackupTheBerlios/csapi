/**
 * Created on Aug 4, 2006
 */
package org.csapi.csapicore.test.core;

import junit.framework.TestCase;

import org.csapi.csapicore.core.Favorites;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csapicore.exceptions.PluginException;

/**
 * @author grandpas
 * 
 */
public class SessionMgrTest extends TestCase {

    /**
     * The simpleSessionMgr to test.
     */
    private SessionMgr simpleSessionMgr;

    /**
     * Test for the getDefault() method, which is like a constructor in our
     * case.
     * 
     * @see TestCase#setUp()
     */
    protected final void setUp() {
        simpleSessionMgr = new SessionMgr("david", "daviddavid", "build_mgr",
                "10.1.1.1", "/data/ccmdb/eclipse");
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.login()'.
     * 
     * @throws PluginException
     *             if login fails.
     */
    @SuppressWarnings("unused")
    public final void testLogin() throws PluginException {
        // simpleSessionMgr.login();
        // TODO implement test.
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr. getReport(String,
     * String)'
     */
    public final void testGetReport() {
        // TODO implement test
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.
     * setCsapiDatabase(String)'
     */
    public final void testSetGetDatabase() {
        simpleSessionMgr.setCsapiDatabase("/data/ccmdb/tutorial");
        assertEquals("/data/ccmdb/tutorial", simpleSessionMgr
                .getCsapiDatabase());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.
     * setCsapiPassword(String)'
     */
    public final void testSetIsLoginOKCsapiPassword() {
        simpleSessionMgr.setCsapiPassword("borisboris");
        assertTrue(simpleSessionMgr.isLoginOK("borisboris"));
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.
     * setCsapiRole(String)'
     */
    public final void testSetGetCsapiRole() {
        simpleSessionMgr.setCsapiRole("developer");
        assertEquals("developer", simpleSessionMgr.getCsapiRole());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.
     * setCsapiServerIP(String)'
     */
    public final void testSetCsapiServerIP() {
        simpleSessionMgr.setCsapiServerIP("192.168.48.128");
        assertEquals("192.168.48.128", simpleSessionMgr.getCsapiServerIP());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.
     * setCsapiUser(String)'
     */
    public final void testSetGetCsapiUser() {
        simpleSessionMgr.setCsapiUser("boris");
        assertEquals("boris", simpleSessionMgr.getCsapiUser());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.SessionMgr.
     * setReportHistory(Favorites)'
     */
    public final void testSetGetReportHistory() {
        simpleSessionMgr.setReportHistory(new Favorites());
        // assertEquals()
        // TODO implement test
    }

}
