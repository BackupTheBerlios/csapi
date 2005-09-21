/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


/**
 * This class features a Change Synergy record. It provides methods to 
 * easily play with attributes (add, remove, etc.) and the record itself
 * (conversion of data to text or csv format, etc.). 
 * 
 * @author Boris Baldassari
 */
public class Record {

	/* A set of attributes. The implementation of this set MUST be hidden
	 * from external resources. We actually use a HashTable, but pay 
	 * attention to XXX.*/
	private Hashtable attributes = new Hashtable();
	
	/**
	 * Constructor.
	 */
	public Record() {
		super();
	}

	/**
	 * Adds an Attribute object to the current set.
	 * TODO: check if it does not already exists.
	 * 
	 * @param newAttribute The Attribute object to add to the set.
	 */
	public void addAttribute(Attribute newAttribute){
		attributes.put(newAttribute.getName(), newAttribute);
	}
	
	/**
	 * Get the problem_number attribute of the current record.
	 * @return Returns the problem_number String.
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
		if (attributes.containsKey(attributeName))
			return ((Attribute)attributes.get(attributeName)).getValue();
		 return null;
	}
	
	
	/**
	 * @return Returns a coma-separated list of attributes in this record.
	 */
	public String toCSVString() {
		// TODO fill in with iterator to build a single String.
		return "";
	}

	/**
	 * @return Returns a list of attributes in this record.
	 */
	public String toString() {
//		System.out.println("About to display " + attributes.size()
//				+ " att(s).");
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
		//System.out.println("Sent " + record);
		return record;
	}
}
