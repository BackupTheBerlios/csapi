/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.views;

import org.csapi.csapicore.core.Favorites;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csplugin.actions.ClearReportHistoryViewAction;
import org.csapi.csplugin.actions.RunReportHistoryAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

/**
 * <p>
 * The view displaying the history of reports ran by the user. It is composed of
 * a TableViewer, which displays favorites a report per line. Attributes
 * displayed are name (date), query and attributes.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class ReportHistoryView extends ViewPart implements IDoubleClickListener {

    // The shared instance.
    private static ReportHistoryView instance;

    /* The (Table)viewer used for the view. */
    private TableViewer viewer;

    /**
     * Constructor.
     */
    public ReportHistoryView() {
        super();
        instance = this;
    }

    /**
     * Singleton accessor. Only one instance of the view may be running.
     * 
     * @return The shared instance.
     */
    public static ReportHistoryView getDefault() {
        return instance;
    }

    /**
     * Creates the UI. For our purpose, just creates a TableViewer, and link it
     * to a LabelProvider and a ContentProvider.
     * 
     * A dropdown menu is added also, for operations on the list.
     * 
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {

        /* The viewer to be displayed. */
        viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.H_SCROLL
                | SWT.V_SCROLL);

        /* The Table used for data display; also set options. */
        Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        setColumns("Name|Query|Attributes".split("\\|"));

        /*
         * Defines the LabelProvider and ContentProvider for the current Viewer,
         * and set the input (model) for it.
         */
        viewer.setContentProvider(new ReportHistoryContentProvider());
        viewer.setLabelProvider(new ReportHistoryLabelProvider());

        /* The double click is used to remove an item from the model. */
        viewer.addDoubleClickListener(this);

        setInput(SessionMgr.getDefault().getReportHistory());

        RunReportHistoryAction runRapportAction = new RunReportHistoryAction();
        ClearReportHistoryViewAction clearReport = new ClearReportHistoryViewAction();

        MenuManager menuManager = (MenuManager) getViewSite().getActionBars()
                .getMenuManager();

        menuManager.add(runRapportAction);
        menuManager.add(new Separator());
        menuManager.add(clearReport);
    }

    public void setColumns(String[] columnTitles) {
        /*
         * We first need to delete all columns before adding new ones. The
         * number of attributes displayed can vary between functions, and the
         * whole layer may have to be redrawn.
         */
        int delCol = viewer.getTable().getColumns().length;
        for (int j = delCol - 1; j >= 0; j--)
            viewer.getTable().getColumns()[j].dispose();

        /*
         * Then create columns according to the attributes needed for the
         * function.
         */
        for (int i = columnTitles.length - 1; i >= 0; i--) {
            TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE, 0);
            column.setText(columnTitles[i]);
            column.setAlignment(SWT.LEFT);
            if (columnTitles[i].equalsIgnoreCase("Name")) {
                column.setWidth(130);
            } else if (columnTitles[i].equalsIgnoreCase("Query")) {
                column.setWidth(300);
            } else if (columnTitles[i].equalsIgnoreCase("Attributes")) {
                column.setWidth(300);
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
    public void setInput(Favorites favorites) {
        viewer.setInput(favorites);
        viewer.refresh();
    }

    /**
     * 
     */
    public void clearViewer() {
        Favorites favorites = (Favorites) viewer.getInput();
        favorites.clearFavorites();
        // viewer.setInput(favorites);
        viewer.refresh();
    }

    public void doubleClick(DoubleClickEvent event) {
        RunReportHistoryAction gmrAction = new RunReportHistoryAction();
        gmrAction.run();
    }

    /**
     * Returns the current viewer (singleton accessor).
     * 
     * @return The current viewer.
     */
    public TableViewer getViewer() {
        return viewer;
    }
}
