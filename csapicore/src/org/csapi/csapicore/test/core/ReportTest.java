/**
 * Created on Aug 4, 2006
 */
package org.csapi.csapicore.test.core;

import junit.framework.TestCase;

import org.csapi.csapicore.core.Record;
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
        Record record1 = new Record("2345");
        Record record2 = new Record("3456");
        simpleReport.addRecord(record1);
        simpleReport.addRecord(record2);
        Record[] records = simpleReport.getRecords();
        assertEquals(record1.getProblemNumber(), records[0].getProblemNumber());
        assertEquals(record2.getProblemNumber(), records[1].getProblemNumber());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.toString()'.
     */
    public final void testToString() {
        // System.out.println("DEBUG: " + simpleReport.toString());
        assertEquals("Report \"" + simpleReport.getName()
                + "\" with query \"query\".", simpleReport.toString());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.toStrings()'.
     */
    public final void testToStrings() {
        for (int i = 0; i < simpleReport.toStrings().length; i++) {
            System.out.println("DEBUGA : " + simpleReport.toStrings()[i]);
        }

        assertEquals("attributes", simpleReport.toStrings()[0]);
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getRecords()'.
     */
    public final void testGetRecords() {
        // TODO implement me
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getAttributes()'.
     */
    public final void testSetGetAttributes() {
        String[] attributes = new String[2];
        attributes[0] = "crstatus";
        attributes[1] = "problem_number";
        simpleReport.setAttributes(attributes);
        assertEquals(attributes, simpleReport.getAttributes());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getAttributesString()'.
     */
    public final void testSetGetAttributesString() {
        simpleReport.setAttributes("crstatus|problem_number|problem_synopsis");
        assertEquals("crstatus|problem_number|problem_synopsis", simpleReport
                .getAttributesString());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getQuery()'. Test method
     * for 'org.csapi.csapicore.core.Report.setQuery(String)'.
     */
    public final void testSetGetQuery() {
        simpleReport.setQuery("cvtype='problem' and crstatus='entered'");
        assertEquals("cvtype='problem' and crstatus='entered'", simpleReport
                .getQuery());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Report.getName()'.
     */
    public final void testSetGetName() {
        simpleReport.setName("My Default Report");
        assertEquals("My Default Report", simpleReport.getName());

        simpleReport.setName("avé des char@ct€res bîzaroïdes.");
        assertEquals("avé des char@ct€res bîzaroïdes.", simpleReport.getName());
    }
}
