/**
 * 
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Report;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The ContentProvider for the ShowReportView.
 * 
 * ShowReportContentProvider extracts an array of elements (objects) 
 * from the model (a Report) to display in the view.
 * 
 * @author Boris Baldassari
 */
public class ShowReportContentProvider implements IStructuredContentProvider {

	/* Constructor for ShowReportContentProvider. */
	public ShowReportContentProvider() {
		super();
	}
	
	/* The core of the ContentProvider mechanism. Extracts from the model 
	 * and returns an array of Objects to be displayed.
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		Report report = (Report)inputElement;
		return report.getRecords();
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
