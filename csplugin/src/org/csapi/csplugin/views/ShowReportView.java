/**
 * Created on 23 july 2005
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Report;
import org.csapi.csplugin.actions.ChangeAttributesAction;
import org.csapi.csplugin.actions.ClearReportViewAction;
import org.csapi.csplugin.actions.QuerySelectAction;
import org.csapi.csplugin.actions.RefreshAction;
import org.csapi.csplugin.actions.TextReportAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

/**
 * <p>The view displaying the content of a report. It is composed of a 
 * TableViewer, which displays data a record per line. All chosen attributes 
 * are displayed in columns.</p>
 * 
 * @author Boris Baldassari
 */
public class ShowReportView extends ViewPart {

// The shared instance.
private static ShowReportView instance;

/* The (Table)viewer used for the view. */
private TableViewer viewer;

/**
 * Constructor.
 */
public ShowReportView() {
super();
instance = this;
}

/**
 * Singleton accessor. Only one instance of the view may be running.
 * 
 * @return The shared instance.
 */
public static ShowReportView getDefault() {
return instance;
}

/**
 * Creates the UI.  For our purpose, just creates a TableViewer, and 
 * link it to a LabelProvider and a ContentProvider.
 * 
 * A dropdown menu is added also, for operations on the list.
 * 
 * (non-Javadoc)
 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
 */
public void createPartControl(Composite parent) {

/* The viewer to be displayed. */ 
viewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION
| SWT.H_SCROLL | SWT.V_SCROLL);

/* The Table used for data display; also set options. */
Table table = viewer.getTable();
table.setHeaderVisible(true);
table.setLinesVisible(true);

setColumns("problem_number|problem_synopsis".split("\\|"));

/* Defines the LabelProvider and ContentProvider for the 
 * current Viewer, and set the input (model) for it. */
viewer.setContentProvider(new ShowReportContentProvider());
viewer.setLabelProvider(new ShowReportLabelProvider());

TextReportAction textReportAction = new TextReportAction();
ClearReportViewAction clearReport = new ClearReportViewAction();
ChangeAttributesAction changeAttributesAction = 
new ChangeAttributesAction();
QuerySelectAction querySelectAction = new QuerySelectAction();
RefreshAction refreshAction = new RefreshAction();

MenuManager menuManager = (MenuManager)getViewSite().getActionBars()
.getMenuManager();

menuManager.add(refreshAction);
menuManager.add(querySelectAction);
menuManager.add(changeAttributesAction);
menuManager.add(new Separator());
menuManager.add(textReportAction);
menuManager.add(clearReport);
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

/**
 *
 */
public void setFocus() {
viewer.refresh();
viewer.getControl().setFocus();
}

/**
 * @param report
 */
public void setInput(Report report) {
    viewer.setInput(report);
viewer.refresh();
}

/**
 * Get the report attached to the viewer.
 * 
 * @return The report attached to the viewer.
 */
public Report getReport() {
Report myReport = (Report)viewer.getInput();
return myReport;
}

/**
 * @return
 */
public String getTextReport() {

FileDialog fileDialog = new FileDialog(
new Shell(Display.getDefault(), SWT.CANCEL|SWT.OK), SWT.SAVE);
fileDialog.setFileName("report.txt");
String path = fileDialog.open();
System.out.println(path);
return path;
}

/**
 * 
 */
public void clearViewer() {
//Report report = new Report("", );
//    viewer.setInput(report);
viewer.refresh();
}

/**
 * Get the report attached to the viewer.
 * 
 * @return The report attached to the viewer.
 */
public TableViewer getViewer() {
return viewer;
}

}
