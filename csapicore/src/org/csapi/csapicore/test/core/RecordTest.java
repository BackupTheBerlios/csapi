/**
 * Created on Aug 4, 2006.
 */
package org.csapi.csapicore.test.core;

import junit.framework.TestCase;

import org.csapi.csapicore.core.Attribute;
import org.csapi.csapicore.core.Record;

/**
 * @author grandpas
 *
 */
public class RecordTest extends TestCase {

    /**
     * the Record to test.
     */
    private Record simpleRecord;

    /**
     * @see TestCase#setUp()
     */
    protected final void setUp() {
        simpleRecord = new Record("1234");
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Record.
     * addAttribute(Attribute)'.
     */
    public final void testAddGetAttribute() {
        Attribute attribute1 = new Attribute();
        attribute1.setName("crstatus");
        attribute1.setValue("entered");
        attribute1.setType("CCM_STRING");
        simpleRecord.addAttribute(attribute1);

        assertEquals("entered", simpleRecord.getAttribute("crstatus"));
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Record.getProblemNumber()'.
     */
    public final void testGetProblemNumber() {
        assertEquals("1234", simpleRecord.getProblemNumber());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Record.toCSVString(String)'.
     */
    public final void testToCSVString() {
        Attribute attribute1 = new Attribute();
        attribute1.setName("crstatus");
        attribute1.setValue("entered");
        attribute1.setType("CCM_STRING");
        simpleRecord.addAttribute(attribute1);

        assertEquals("crstatus = entered|problem_number = 1234",
                simpleRecord.toCSVString("|"));

    }

    /**
     * Test method for 'org.csapi.csapicore.core.Record.toString()'.
     */
    public final void testToString() {
        Attribute attribute1 = new Attribute();
        attribute1.setName("crstatus");
        attribute1.setValue("entered");
        attribute1.setType("CCM_STRING");
        simpleRecord.addAttribute(attribute1);

        assertEquals("crstatus = entered, problem_number = 1234",
                simpleRecord.toString());
    }

}
