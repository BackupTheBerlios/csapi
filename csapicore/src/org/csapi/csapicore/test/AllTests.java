/**
 * 
 */
package org.csapi.csapicore.test;

import org.csapi.csapicore.test.core.FavoritesTest;
import org.csapi.csapicore.test.core.AttributeTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author grandpas
 *
 */
public class AllTests {

    /**
     * Main method for test class.
     *
     * @param args Arguments.
     */
    public static void main(final String[] args) {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.csapi.csapicore.test");
        //$JUnit-BEGIN$
        suite.addTestSuite(AttributeTest.class);
        suite.addTestSuite(FavoritesTest.class);
        suite.addTestSuite(CsapiCorePluginTest.class);
        //$JUnit-END$
        return suite;
    }

}
