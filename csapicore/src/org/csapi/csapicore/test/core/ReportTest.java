/**
 * Created on Aug 4, 2006
 */
package org.csapi.csapicore.test.core;

import junit.framework.TestCase;

import org.csapi.csapicore.core.Report;

/**
 * @author grandpas
 *
 */
public class ReportTest extends TestCase {

    /**
     * the report to test.
     */
    private Report simpleReport;

    /**
     * @see TestCase#setUp()
     */
    protected final void setUp() {
        String[] attributes = new String[1];
        attributes[0] = "attributes";
        simpleReport = new Report("query", attributes);
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.addRecord(Record)'.
     */
    public final void testAddGetRecord() {
//        Record record1 = new Record("2345");
//        Record record2 = new Record("3456");
//        simpleReport.addRecord(record1);
//        Record[] records = simpleReport.getRecords();
//        assertEquals(records[1], record1);
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.toString()'.
     */
    public final void testToString() {
        assertEquals("k", simpleReport.toString());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.toStrings()'.
     */
    public final void testToStrings() {
        String[] strings = simpleReport.toStrings();
        assertEquals("k", strings);
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getRecords()'.
     */
    public final void testGetRecords() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getAttributes()'.
     */
    public final void testGetAttributes() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getAttributesString()'.
     */
    public final void testGetAttributesString() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getQuery()'.
     */
    public final void testGetQuery() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.setQuery(String)'.
     */
    public final void testSetQuery() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.
     * setAttributes(String[])'.
     */
    public final void testSetAttributes() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getName()'.
     */
    public final void testGetName() {

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.setName(String)'.
     */
    public final void testSetName() {

    }

}
