/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.util.Iterator;
import java.util.Vector;


/**
 * This class features a set of Change Synergy records, along with 
 * informations relative to the report: attribute list and query string.
 * 
 * All records in this class <b>must</b> have at least the attributes 
 * specified in the attributes String[]. The Report class is responsible for
 * checking records attributes when entered.
 * 
 * @author Boris Baldassari
 */
public class Report {

	/** The set of records associated with the report. */
	private Vector records = new Vector();
	
	/** The query string associated with the report. */
	private String query;
	
	/** The attributes displayed with the record. It is mandatory for
	 * the consistence of records over the report. */
	private String[] attributes;
	
	/**
	 * Constructor.
	 */
	public Report() {
		super();
	}

	/**
	 * Add a Record object to the current set of records. We must verify 
	 * before adding that attributes of the record are ok (at least equal 
	 * to attributes from the attributes array of Strings).  
	 * 
	 * @param record The record to be added to the report set.
	 */
	public void addRecord(Record record) {
		// TODO check if attributes are ok.
		records.add(record);
	}

	/** 
	 * Returns a String representation of this report. 
	 * TODO implement me
	 * 
	 * @see java.lang.Object#toString()
	 * @return The String representation of the report.
	 */
	public String toString() {
		return "";
	}

	/**
	 * Returns an array of String representing the report. Each element of 
	 * the array is a toString representation of a record. The Vector 
	 * implementation allows to keep the sorting order of records used
	 * when building the report. 
	 * 
	 * @return An array of Strings, one record per line.
	 */
	public String[] toStrings() {
		String[] txtRecords = new String[records.size()+1];
		String headers= "";
		boolean debut = true;
		
		/* If the line is the first line, print headers (name of
		 * attributes printed). */
		for (int i = 0 ; i < attributes.length ; i++) {
			if (debut == true) {
				debut = false;
			} else {
				headers += ";";
			}
			headers += attributes[i];
		}
		txtRecords[0] = headers;

		/* Get an iterator from the Vector (note that hashmap sets also
		 * support iterators), and iterate over the set. */
		Iterator iterator = records.iterator();
		for (int i = 1 ; i < records.size()+1; i++) {
			Record tmpRecord = (Record)iterator.next();
			txtRecords[i] = "";
			debut=true;
			
			/* For each record, extract all attributes in the order
			 * they have been entered in the report. */
			for (int j = 0 ; j < attributes.length ; j++) {
				if (debut == true) {
					debut = false;
				} else {
					txtRecords[i] += ";";
				}
				txtRecords[i] += tmpRecord.getAttribute(attributes[j]);
			}
		}
		return txtRecords;
	}
	
	/**
	 * Returns an array of Records from the current set.
	 * 
	 * @return The records attached to the report.
	 */
	public Record[] getRecords() {
		Record[] myRecords = new Record[records.size()];
		
		Iterator iterator = records.iterator();
		for (int i = 0 ; i < records.size(); i++) {
			myRecords[i] = (Record)iterator.next();
		}
		
		return myRecords;
	}

	/**
	 * Get the attributes private attribute.
	 * 
	 * @return An array of Strings describing the attributes used in this 
	 * report.
	 */
	public String[] getAttributes() {
		return this.attributes;
	}

	/**
	 * Get the query private attribute.
	 * 
	 * @return Returns the query used for this report.
	 */
	public String getQuery() {
		return this.query;
	}

	/**
	 * Set the query private attribute.
	 * 
	 * @param query The query to set.
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Set the attributes private attribute, as an array of Strings.
	 * 
	 * @param attributes The attributes to set.
	 */
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}
}
