/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**
 * <p>
 * This class features a set of Change Synergy records, along with informations
 * relative to the report: attribute list and query string.
 * </p>
 * 
 * <p>
 * All records in this class <b>must</b> have at least the attributes specified
 * in the attributes String[]. The Report class is responsible for checking
 * records attributes when entered.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class Report {

    /**
     * <p>
     * The name of the report. it identifies the report (used e.g. in
     * favorites).
     * </p>
     */
    private String name;

    /**
     * <p>
     * The set of records associated with the report.
     * </p>
     */
    private Vector records = new Vector();

    /**
     * <p>
     * The query string associated with the report.
     * </p>
     */
    private String query;

    /**
     * <p>
     * The attributes displayed with the record. It is mandatory for the
     * consistence of records over the report.
     * </p>
     */
    private String[] attributes;

    /**
     * <p>
     * Constructor.
     * </p>
     * 
     * @param newQuery
     *            The query string used for this report.
     * @param newAttributes
     *            An array of Strings representing the list of attributes used
     *            for this report.
     */
    public Report(final String newQuery, final String[] newAttributes) {
        super();

        this.query = newQuery;
        this.attributes = newAttributes;
        this.name = easyDateFormat("dd.MM.yy-HH:mm:ss");
    }

    /**
     * A private utility for the name of the report, which is the date and hour
     * of the report execution.
     * 
     * @param format
     *            The time format (C-like) for date.
     * @return A string representing the date with the given format.
     */
    private String easyDateFormat(final String format) {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String datenewformat = formatter.format(today);
        return datenewformat;
    }

    /**
     * <p>
     * Add a Record object to the current set of records. We must verify before
     * adding that attributes of the record are ok (at least equal to attributes
     * from the attributes array of Strings).
     * </p>
     * 
     * @param record
     *            The record to be added to the report set.
     */
    @SuppressWarnings("unchecked")
    public final void addRecord(final Record record) {
        // TODO check if attributes present in report are also
        // present in record.
        // record.;
//        for (int i = 0; i < attributes.length; i++) {
//            if (!record.getAttributesString().contains(attributes[i])) {
                // launch new query to update list of attributes.
//            }
//        }
        records.add(record);
    }

    /**
     * <p>
     * Returns a String representation of this report.
     * </p>
     * 
     * @see java.lang.Object#toString()
     * @return The String representation of the report.
     */
    public final String toString() {
        String myReturn = "Report \"" + this.name + "\" with query \""
                + this.query + "\".";
        return myReturn;
    }

    /**
     * <p>
     * Returns an array of String representing the report. Each element of the
     * array is a toString representation of a record. The Vector implementation
     * allows to keep the sorting order of records used when building the
     * report.
     * </p>
     * 
     * @return An array of Strings, one record per line.
     */
    public final String[] toStrings() {
        String[] txtRecords = new String[records.size() + 1];
        String headers = "";
        boolean debut = true;

        /*
         * If the line is the first line, print headers (name of attributes
         * printed).
         */
        for (int i = 0; i < attributes.length; i++) {
            if (debut) {
                debut = false;
            } else {
                headers += ";";
            }
            headers += attributes[i];
        }
        txtRecords[0] = headers;

        /*
         * Get an iterator from the Vector (note that hashmap sets also support
         * iterators), and iterate over the set.
         */
        Iterator iterator = records.iterator();
        for (int i = 1; i < records.size() + 1; i++) {
            Record tmpRecord = (Record) iterator.next();
            txtRecords[i] = "";
            debut = true;

            /*
             * For each record, extract all attributes in the order they have
             * been entered in the report.
             */
            for (int j = 0; j < attributes.length; j++) {
                if (debut) {
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
     * <p>
     * Returns an array of Records from the current set.
     * </p>
     * 
     * @return The records attached to the report.
     */
    public final Record[] getRecords() {
        Record[] myRecords = new Record[records.size()];

        Iterator iterator = records.iterator();
        for (int i = 0; i < records.size(); i++) {
            myRecords[i] = (Record) iterator.next();
        }

        return myRecords;
    }

    /**
     * <p>
     * Get the attributes private attribute.
     * </p>
     * 
     * @return An array of Strings describing the attributes used in this
     *         report.
     */
    public final String[] getAttributes() {
        return this.attributes;
    }

    /**
     * <p>
     * Get the attributes private attribute, set as one string. Separator is
     * pipe (|).
     * </p>
     * 
     * @return A String describing the attributes used in this report.
     */
    public final String getAttributesString() {
        String returnAttributes = "";
        boolean debut = true;
        for (int i = 0; i < this.attributes.length; i++) {
            if (debut) {
                debut = false;
            } else {
                returnAttributes += "|";
            }
            returnAttributes += this.attributes[i];
        }
        return returnAttributes;
    }

    /**
     * <p>
     * Get the query private attribute.
     * </p>
     * 
     * @return Returns the query used for this report.
     */
    public final String getQuery() {
        return this.query;
    }

    /**
     * <p>
     * Set the query private attribute.
     * </p>
     * 
     * @param newQuery
     *            The query to set.
     */
    public final void setQuery(final String newQuery) {
        this.query = newQuery;
    }

    /**
     * <p>
     * Set the attributes private attribute, as an array of Strings.
     * </p>
     * 
     * @param newAttributes
     *            The attributes to set.
     */
    public final void setAttributes(final String[] newAttributes) {
        this.attributes = newAttributes;
    }

    /**
     * <p>
     * Set the attributes private attribute, as an array of Strings.
     * </p>
     * 
     * @param newAttributes
     *            The attributes to set.
     */
    public final void setAttributes(final String newAttributes) {
        this.attributes = newAttributes.split("\\|");
    }

    /**
     * Return the name of the current report.
     * 
     * @return The name of the current report.
     */
    public final String getName() {
        return name;
    }

    /**
     * Set the name of the current report.
     * 
     * @param newName
     *            the name to set on the report.
     */
    public final void setName(final String newName) {
        this.name = newName;
    }
}
