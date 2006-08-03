/**
 * Created on 22 ao?t 2005.
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Record;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * <p>The Label provider for the ShowReport view.</p>
 * 
 * <p>The Label providers returns to the Eclipse view mechanism the Strings 
 * or pictures to be displayed in the view, for a given object and index
 * in the display. </p>
 * 
 * @author Boris Baldassari
 */
public class ShowReportLabelProvider extends LabelProvider implements
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
Record record = (Record)element;
String header = ShowReportView.getDefault().getColumnName(columnIndex);
return record.getAttribute(header);
}

}
