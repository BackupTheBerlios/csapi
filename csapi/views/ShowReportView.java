/**
 * Created on 23 july 2005
 */
package csapi.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import csapi.core.Report;

/**
 * The view displaying the content of a report.
 * 
 * @author grandpas
 */
public class ShowReportView extends ViewPart {

	// The shared instance.
	private static ShowReportView instance;
	
	private TableViewer viewer;
//	private Report report = new Report();
//	private String[] headers;
	
	public ShowReportView() {
		super();
		instance = this;
	}
	
	public static ShowReportView getDefault() {
		return instance;
	}

	/* Creates the UI. 
	 * 
	 * For our purpose, just creates a TableViewer, and link it to a 
	 * LabelProvider and a ContentProvider.
	 * 
	 * A dropdown menu is added also, for operations on the list.
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
		
		/* The viewer to be displayed. */ 
		viewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION);
		
		/* The Table used for data display; also set options. */
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
//		report
		setColumns("problem_number|problem_synopsis".split("\\|"));
		
		/* Defines the LabelProvider and ContentProvider for the 
		 * current Viewer, and set the input (model) for it. */
		viewer.setContentProvider(new ShowReportContentProvider());
		viewer.setLabelProvider(new ShowReportLabelProvider());
//		viewer.setInput(report);
	}
	
	public String getColumnName(int columnIndex) {
		return getReport().getAttributes()[columnIndex];
	}

	public void setColumns(String[] columnTitles) {
	    /* We first need to delete all columns before adding new ones.
	     * The number of attributes displayed can vary between functions,
	     * and the whole layer may have to be redrawn. */
	    int delCol = viewer.getTable().getColumns().length;
	    for (int j = delCol-1 ; j >= 0 ; j--)
	        viewer.getTable().getColumns()[j].dispose();
	    
	    /* Then create columns according to the attributes needed
	     * for the function. */
	    for (int i = columnTitles.length-1 ; i >= 0 ; i--) {
    		TableColumn column = new TableColumn(viewer.getTable(), 
    		        SWT.NONE, 0);
    		column.setText(columnTitles[i]);
    		column.setAlignment(SWT.LEFT);
    		if (columnTitles[i].equalsIgnoreCase("problem_number")) {
    		    column.setWidth(40);
    		} else if (columnTitles[i].equalsIgnoreCase("problem_synopsis")) {
    		    column.setWidth(400);
    		} else {
    		    column.pack();
    		}
	    }
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {
		viewer.refresh();
		viewer.getControl().setFocus();
	}

	public void setInput(Report report) {
	    viewer.setInput(report);
		viewer.refresh();
	}
	
	public Report getReport() {
		Report myReport = (Report)viewer.getInput();
		return myReport;
	}
}
