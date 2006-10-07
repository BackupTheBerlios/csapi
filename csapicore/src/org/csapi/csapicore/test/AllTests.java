/**
 * 
 */
package org.csapi.csapicore.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.csapi.csapicore.test.core.AttributeTest;
import org.csapi.csapicore.test.core.FavoritesTest;
import org.csapi.csapicore.test.core.RecordTest;
import org.csapi.csapicore.test.core.ReportTest;
import org.csapi.csapicore.test.core.SessionMgrTest;

/**
 * @author grandpas
 * 
 */
public class AllTests {

    /**
     * Main method for test class.
     * 
     * @param args
     *            Arguments.
     */
    public static void main(final String[] args) {
        // constructor
    }

    /**
     * @return The test suit.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Tests from org.csapi.csapicore.test");
        // $JUnit-BEGIN$
        suite.addTestSuite(CsapiCorePluginTest.class);
        suite.addTestSuite(AttributeTest.class);
        suite.addTestSuite(FavoritesTest.class);
        suite.addTestSuite(RecordTest.class);
        suite.addTestSuite(ReportTest.class);
        suite.addTestSuite(SessionMgrTest.class);
        // $JUnit-END$
        return suite;
    }

}
