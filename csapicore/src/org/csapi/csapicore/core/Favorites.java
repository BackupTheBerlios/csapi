/*
 * Created on 29 mai 2005
 */
package org.csapi.csapicore.core;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>
 * A class which holds informations about a set of reports, such as a report
 * history. Reports are entirely stored, which allow to save them.
 * </p>
 *
 * <p>
 * This class provides facilities to add and get reports.
 * </p>
 *
 * @author Boris Baldassari
 */
public class Favorites {

    /**
     * <p>
     * A hashtable is used for implementation, but access functions have to
     * provide enough isolation: implementation may change.
     * </p>
     * <p>
     * For now, the name of the report is the key and the report object is
     * stored as value.
     * </p>
     */
    private Hashtable favorites = new Hashtable();

    /**
     * Constructor for the class.
     */
    public Favorites() {
        super();
    }

    /**
     * <p>
     * Add a given report in the favorites container. The name of the report is
     * used for identification, and the whole object is stored.
     * </p>
     *
     * @param report
     *            The Report object to be added to the set.
     */
    public final void addFavoriteReport(final Report report) {
        favorites.put(report.getName(), report);
    }

    /**
     * <p>
     * Get the content of the container, presented as an Array of Reports.
     * </p>
     *
     * @return An array of Reports.
     */
    public final Report[] getArrayFavorites() {
        Report[] myReports = new Report[favorites.size()];

        // Build the array
        int i = 0;
        for (Enumeration e = favorites.elements(); e.hasMoreElements();) {
            myReports[i++] = (Report) e.nextElement();
        }
        return myReports;
    }

    /**
     * <p>
     * Get a report among the set, identified by its name.
     * </p>
     *
     * @param name
     *            The name of the report to be retrieved.
     * @return A Report object.
     */
    public final Report getFavoriteReport(final String name) {
        return (Report) favorites.get(name);
    }

    /**
     * Get the number of reports stored.
     *
     * @return The number of reports contained.
     */
    public final int getSize() {
        return favorites.size();
    }

    /**
     * <p>Empty the set, by deleting all records.</p>
     */
    public final void clearFavorites() {
        favorites.clear();
    }
}
