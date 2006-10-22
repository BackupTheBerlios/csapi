/**
 * Created on 22 ao?t 2005.
 */
package org.csapi.csplugin.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.csapi.csapicore.core.Record;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * The Label provider for the ShowReport view.
 * </p>
 * 
 * <p>
 * The Label providers returns to the Eclipse view mechanism the Strings or
 * pictures to be displayed in the view, for a given object and index in the
 * display.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class ShowReportLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
     *      int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
     *      int)
     */
    public String getColumnText(Object element, int columnIndex) {
        Record record = (Record) element;
        String header = ShowReportView.getDefault().getColumnName(columnIndex);
        String attributeType = record.getAttributeType(header);
        String attributeValue = record.getAttribute(header);
        if (attributeType.equalsIgnoreCase("CCM_DATE")) {
            Date myDate = new Date(Long.parseLong(attributeValue));
            return easyDateFormat("dd.MM.yy-HH:mm:ss", myDate);
        }
        
        // fall-back for security
        return attributeValue;
    }
    

    /**
     * A private utility for the name of the report, which is the date and hour
     * of the report execution.
     * 
     * @param format
     *            The time format (C-like) for date.
     * @return A string representing the date with the given format.
     */
    private String easyDateFormat(final String format, final Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String datenewformat = formatter.format(date);
        return datenewformat;
    }

}
