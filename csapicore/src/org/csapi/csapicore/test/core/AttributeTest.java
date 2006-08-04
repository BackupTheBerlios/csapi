/**
 * Test class for org.csapi.csapicore.core.Attribute
 *
 * Tests define "normal" behaviour of the class methods.
 */
package org.csapi.csapicore.test.core;

import org.csapi.csapicore.core.Attribute;

import junit.framework.TestCase;

/**
 * @author grandpas
 *
 */
public class AttributeTest extends TestCase {

    /**
     * The simpleAttribute to test.
     */
    private Attribute simpleAttribute;

    /**
     * Constructors should not be tested: use setup instead.
     */
    protected final void setUp() {
        simpleAttribute = new Attribute("crstatus", "entered", "CCM_STRING");
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Attribute.setType(String)'.
     */
    public final void testSetGetType() {
        simpleAttribute.setType("CCM_LISTBOX");
        assertEquals("CCM_LISTBOX", simpleAttribute.getType());
        simpleAttribute.setType("CCM_STRING");
        assertEquals("CCM_STRING", simpleAttribute.getType());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Attribute.setName(String)'.
     */
    public final void testSetGetName() {
        simpleAttribute.setName("modify_time");
        assertEquals("modify_time", simpleAttribute.getName());
        simpleAttribute.setName("crstatus");
        assertEquals("crstatus", simpleAttribute.getName());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Attribute.setValue(String)'.
     */
    public final void testSetValue() {
        simpleAttribute.setValue("concluded");
        assertEquals("concluded", simpleAttribute.getValue());
        simpleAttribute.setValue("entered");
        assertEquals("entered", simpleAttribute.getValue());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Attribute.toString()'.
     */
    public final void testToString() {
        assertEquals("crstatus = entered", simpleAttribute.toString());
    }

}
