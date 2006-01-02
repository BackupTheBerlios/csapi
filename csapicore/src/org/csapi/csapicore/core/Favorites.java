/**
 * 
 */
package org.csapi.csapicore.core;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A class which holds informations about a set of reports, such as a report
 * history. This class provides facilities to add and get reports. 
 * 
 * @author Boris Baldassari
 */
public class Favorites {
	
	private Hashtable favorites = new Hashtable();
	
	public Favorites() {
	}
	
	public void addFavoriteReport(Report report) {
//		Report newReport = new Report();
//		newReport.setQuery(report.getQuery());
//		newReport.setAttributes(report.getAttributes());
		favorites.put(report.getName(), report);
	}
	
	public Report[] getArrayFavorites() {
		Report[] myReports = new Report[favorites.size()];
		 
		int i = 0;
		for (Enumeration e = favorites.elements() ; 
			e.hasMoreElements() ; ) {
	         myReports[i++] = (Report)e.nextElement();
	     }
		return myReports;
	}
	
	public Report getFavoriteReport(String name) {
		return (Report)favorites.get(name);
	}
	
	public void clearFavorites() {
		favorites.clear();
	}
}
