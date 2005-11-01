/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


/**
 * <p>This class features a Change Synergy record. It provides methods to 
 * easily play with attributes (add, remove, etc.) and the record itself
 * (conversion of data to text or csv format, etc.).</p>
 * 
 * <p>Some methods are targeted to mandatory values, such as problem_number,
 * which is often a useful key.</p>
 * 
 * @author Boris Baldassari
 */
public class Record {

	/** A set of attributes. The implementation of this set MUST be hidden
	 * from external resources. We actually use a HashTable, but encapsulation
	 * has to be carefully preserved.
	 * 
	 * Keys are attribute names, values are Attribute objects. */
	private Hashtable attributes = new Hashtable();
	
	/**
	 * Constructor.
	 */
	public Record() {
		super();
	}

	/**
	 * Adds an Attribute object to the current set. Also checks that the
	 * attribute does not already exists.
	 * 
	 * @param newAttribute The Attribute object to add to the set.
	 */
	public void addAttribute(Attribute newAttribute){
		if (!attributes.containsKey(newAttribute.getName())) {
			attributes.put(newAttribute.getName(), newAttribute);
		}
	}
	
	/**
	 * Get the problem_number attribute of the current record, if defined.
	 * If problem_number does not belong to the attribute set requested, 
	 * returns null.
	 * 
	 * @return Returns the problem_number String, null if not defined.
	 */
	public String getProblemNumber() {
		return ((Attribute)attributes.get("problem_number")).getValue();
	}
	
	
	/**
	 * Returns the value of the attribute provided as parameter.
	 * The parameter must be an existing attribute name. If the name
	 * does not exist, null is returned.
	 * 
	 * @param attributeName The name of the attribute as defined in the
	 * ChangeSynergy workflow. 
	 * @return Returns the value of attribute, or null if attributeName does
	 * not exist.
	 */
	public String getAttribute(String attributeName) {
		if (attributes.containsKey(attributeName)) {
			return ((Attribute)attributes.get(attributeName)).getValue();
		}
		return null;
	}
	
	
	/**
	 * A facility to get a CSV (comma separated values) version of 
	 * the current Record.
	 * 
	 * @return Returns a coma-separated list of attributes in this record.
	 */
	public String toCSVString() {
		// TODO fill in with iterator to build a single String.
		return "";
	}

	/**
	 * A facility to get a String representation of the Record. Actually
	 * returns a list of attributes separated with ';'.
	 * 
	 * @return Returns a list of attributes in this record, separated by ';'.
	 */
	public String toString() {
		String record = "";
		
		Set keys = attributes.keySet();
		Iterator iterator = keys.iterator();
		boolean debut = true;
		for (int i = 0; i < keys.size(); i++) {
			if (debut == true) {
				debut = false;
			} else {
				record += ";";
			}
			record += attributes.get((String) iterator.next()).toString();
		}
		return record;
	}
}
