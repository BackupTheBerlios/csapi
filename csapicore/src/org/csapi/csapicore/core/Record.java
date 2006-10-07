/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * This class features a Change Synergy record. It provides methods to easily
 * play with attributes (add, remove, etc.) and the record itself (conversion of
 * data to text or csv format, etc.).
 * </p>
 * 
 * <p>
 * Some methods are targeted to mandatory values, such as problem_number, which
 * is often a useful key.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class Record {

    /**
     * <p>
     * A set of attributes. The implementation of this set MUST be hidden from
     * external resources. We actually use a HashTable, but encapsulation has to
     * be carefully preserved.
     * </p>
     * 
     * <p>
     * Keys are attribute names, values are Attribute objects.
     * </p>
     */
    private Hashtable attributes = new Hashtable();

    /**
     * Constructor.
     * 
     * @param problemNumber
     *            problem_number of the record.
     */
    public Record(final String problemNumber) {
        super();
        Attribute problemNumberAttribute = new Attribute();
        problemNumberAttribute.setName("problem_number");
        problemNumberAttribute.setValue(problemNumber);
        problemNumberAttribute.setType("CCM_STRING");
        this.addAttribute(problemNumberAttribute);
    }

    
    /**
     * <p>
     * Adds an Attribute object to the current set. Also checks that the
     * attribute does not already exists. 
     * </p>
     * 
     * @param newAttribute
     *            The Attribute object to add to the set.
     */
    @SuppressWarnings("unchecked")
    public final void addAttribute(final Attribute newAttribute) {
        if (!attributes.containsKey(newAttribute.getName())) {
            attributes.put(newAttribute.getName(), newAttribute);
        } else {
            ((Attribute) attributes.get(newAttribute.getName()))
                .setValue(newAttribute.getValue());
        }
    }
    

    /**
     * <p>
     * Get the problem_number attribute of the current record, if defined. If
     * problem_number does not belong to the attribute set requested, returns
     * null.
     * </p>
     * 
     * @return Returns the problem_number String, null if not defined.
     */
    public final String getProblemNumber() {
        return ((Attribute) attributes.get("problem_number")).getValue();
    }
    

    /**
     * <p>
     * Returns the value of the attribute provided as parameter. The parameter
     * must be an existing attribute name. If the name does not exist, null is
     * returned.
     * </p>
     * 
     * @param attributeName
     *            The name of the attribute as defined in the ChangeSynergy
     *            workflow.
     * @return Returns the value of attribute, or null if attributeName does not
     *         exist.
     */
    public final String getAttribute(final String attributeName) {
        if (attributes.containsKey(attributeName)) {
            return ((Attribute) attributes.get(attributeName)).getValue();
        }
        return null;
    }

    
    /**
     * <p>
     * Get the list of attributes as an array of Strings.
     * </p>
     * 
     * @return Returns an array of Strings with all attributes.
     */
    public final String[] getAttributes() {
        String[] strings = new String[attributes.size()];
        Set keys = attributes.keySet();
        Iterator iterator = keys.iterator();
        for (int i = 0; i < keys.size(); i++) {
            strings[i] = attributes.get((String) iterator.next()).toString();
        }
        return strings;
    }

    
    /**
     * <p>
     * Get the list of attributes as a String, separated by pipes.
     * </p>
     * TODO implement test
     * 
     * @return Returns a String with all attributes, separated by pipes.
     */
    public final String getAttributesString() {
        String myString = "";
        Set keys = attributes.keySet();
        Iterator iterator = keys.iterator();

        boolean debut = true;
        for (int i = 0; i < keys.size(); i++) {
            if (debut) {
                debut = false;
            } else {
                myString += "|";
            }
            myString += (String) iterator.next();
        }
        return myString;
    }

    
    /**
     * <p>
     * A facility to get a CSV (comma separated values) version of the current
     * Record. The separator for the output can be selected.
     * </p>
     * 
     * @param separator
     *            A separator single or multiple char to separate items in the
     *            string returned.
     * @return Returns a coma-separated list of attributes in this record.
     */
    public final String toCSVString(final String separator) {
        String record = "";

        Set keys = attributes.keySet();
        Iterator iterator = keys.iterator();
        boolean debut = true;
        for (int i = 0; i < keys.size(); i++) {
            if (debut) {
                debut = false;
            } else {
                record += separator;
            }
            record += attributes.get((String) iterator.next()).toString();
        }
        return record;
    }

    
    /**
     * <p>
     * A facility to get a String representation of the Record. Actually returns
     * a list of attributes, one on each line.
     * </p>
     * 
     * @return Returns a list of attributes in this record, separated by ';'.
     */
    public final String toString() {
        String record = "";

        Set keys = attributes.keySet();
        Iterator iterator = keys.iterator();
        boolean debut = true;
        for (int i = 0; i < keys.size(); i++) {
            if (debut) {
                debut = false;
            } else {
                record += ", ";
            }
            record += attributes.get((String) iterator.next()).toString();
        }
        return record;
    }
    

    /**
     * <p>
     * Returns the type of the attribute provided as parameter. The parameter
     * must be an existing attribute name. If the name does not exist, null is
     * returned.
     * </p>
     * 
     * @param attributeName
     *            The name of the attribute as defined in the ChangeSynergy
     *            workflow.
     * @return Returns the type of attribute, or null if attributeName does not
     *         exist.
     */
    public final String getAttributeType(final String attributeName) {
        if (attributes.containsKey(attributeName)) {
            return ((Attribute) attributes.get(attributeName)).getType();
        }
        return null;
    }
}
