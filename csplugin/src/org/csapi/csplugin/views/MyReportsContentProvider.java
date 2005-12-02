/**
 * 
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Favorites;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The ContentProvider for the ShowReportView. This view
 * 
 * ShowReportContentProvider extracts an array of elements (objects, 
 * namely an array of Records) from the model (a Report) to display 
 * in the view.
 * 
 * The CS Report view shows a set of records with their attribute 
 * values. Recods are displayed in the order they are retrieved.
 * 
 * @author Boris Baldassari
 */
public class MyReportsContentProvider implements IStructuredContentProvider {

	/**
	 *  Constructor for ShowReportContentProvider. 
	 */
	public MyReportsContentProvider() {
		super();
	}
	
	/**
	 * The core of the ContentProvider mechanism. Extracts from the model 
	 * and returns an array of Objects to be displayed.
	 * 
	 * @param inputElement The report to extract objects from.
	 * @return An array of Records.
	 */
	public Object[] getElements(Object inputElement) {
		Favorites favorites = (Favorites)inputElement;
		return favorites.getArrayFavorites();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
