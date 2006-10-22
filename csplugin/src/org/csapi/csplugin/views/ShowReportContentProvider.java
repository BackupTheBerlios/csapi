/**
 * Created on 22 ao?t 2005.
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Report;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * The ContentProvider for the ShowReportView.
 * </p>
 * 
 * <p>
 * ShowReportContentProvider extracts an array of elements (objects, namely an
 * array of Records) from the model (a Report) to display in the view.
 * </p>
 * 
 * <p>
 * The CS Report view shows a set of records with their attribute values. Recods
 * are displayed in the order they are retrieved.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class ShowReportContentProvider implements ITreeContentProvider {

    /**
     * Constructor for ShowReportContentProvider.
     */
    public ShowReportContentProvider() {
//        super();
    }

    /**
     * The core of the ContentProvider mechanism. Extracts from the model and
     * returns an array of Objects to be displayed.
     * 
     * @param inputElement
     *            The report to extract objects from.
     * @return An array of Records.
     */
    public Object[] getElements(Object inputElement) {
        Report report = (Report) inputElement;
        return report.getRecords();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        // not used but kept for standards
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // not used but kept for standards
    }

	public Object[] getChildren(Object parentElement) {
        Report report = (Report) parentElement;
        return report.getRecords();
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

}
