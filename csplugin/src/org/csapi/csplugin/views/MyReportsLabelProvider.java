/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Report;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * <p>The Label provider for the MyReports view.</p>
 * 
 * <p>The Label providers returns to the Eclipse view mechanism the Strings 
 * or pictures to be displayed in the view, for a given object and index
 * in the display. </p>
 * 
 * @author Boris Baldassari
 */
public class MyReportsLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		Report report = (Report)element;
		switch (columnIndex) {
			case 0:
				return report.getName();
			case 1:
				return report.getQuery();
			case 2:
				return report.getAttributesString();
			default:
				return null;
		}
	}

}
