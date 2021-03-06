/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Favorites;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * The ContentProvider for the ReportHistoryView. This view shows reports
 * executed by the user.
 * </p>
 * 
 * <p>
 * ReportHistoryContentProvider extracts an array of elements (objects, namely
 * an array of Reports) from the model (a Favorite) to display in the view.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class ReportHistoryContentProvider implements IStructuredContentProvider {

    /**
     * Constructor for ShowReportContentProvider.
     */
    public ReportHistoryContentProvider() {
        super();
    }

    /**
     * The core of the ContentProvider mechanism. Extracts from the model and
     * returns an array of Objects to be displayed.
     * 
     * @param inputElement
     *            The report to extract objects from.
     * @return An array of Records.
     */
    public final Object[] getElements(final Object inputElement) {
        Favorites favorites = (Favorites) inputElement;
        return favorites.getArrayFavorites();
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public final void dispose() {
        // not used but kept for standards
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer.
     *      java.lang.Object, java.lang.Object)
     *      @param viewer The viewer.
     *      @param oldInput The oldinput.
     *      @param newInput The newInput.
     */
    public final void inputChanged(final Viewer viewer, final Object oldInput, 
            final Object newInput) {
        // not used but kept for standards
    }

}
