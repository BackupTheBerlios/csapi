/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.jobs;

import javax.swing.JOptionPane;

import org.csapi.csapicore.core.Record;
import org.csapi.csapicore.core.Report;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csapicore.exceptions.PluginException;
import org.csapi.csplugin.views.ShowReportView;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * <p>The job class for the Query Selected command in the ShowReportView 
 * menu.</p>
 * 
 * <p>It retrieves through an asyncExec session the selection from the viewer
 * of ShowReportView, build a query and re-run the report with same 
 * attributes. If the report is successfull, the viewer is updated and the
 * view refreshed. </p>
 * 
 * @author Boris Baldassari
 *
 */
public class QuerySelectJob extends Job {

public QuerySelectJob(String name) {
super(name);
}

/* (non-Javadoc)
 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
 */
protected IStatus run(IProgressMonitor monitor) {

Display.getDefault().asyncExec(new Runnable() { public void run() {
    try {
    
    IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
    IViewPart inst = null;
            
            /* The query string to be build. */
            String query = "";
    
    // Get the instance of the view and focus it.
    inst = wkbch.getActivePage().showView("org.csapi.csplugin.views.ShowReportView");
    if (inst != null) {
    
    /* Get the selection from the viewer of ShowReportView,
     * and build a query according to selection contents. */
                    StructuredSelection mySel = 
                        (StructuredSelection)((ShowReportView)inst)
                        .getViewer().getSelection();
                    boolean debut = true;
                    for (int i = 0 ; i < mySel.size() ; i++) {
                        if (debut == false) { 
                            query += "or";
                        } else {
                        debut = false;
                        }
                        Record record = (Record)mySel.toArray()[i];
                      query += "(problem_number='" 
                      + record.getProblemNumber() + "')";
                    }
                    
                    System.out.println("query is " + query);
                    
                    SessionMgr sessionMgr = SessionMgr.getDefault();
                    
                    Report report;
                    
                    // Get the list of attributes.
                    final String attributes = (String) JOptionPane.showInputDialog(null,
                    "Please provide attributes list, separated by pipes:", "Attributes needed",
                    JOptionPane.OK_CANCEL_OPTION, null, null, "problem_number|problem_synopsis");
                    // if cancel is hit
                    if (attributes == null) { 
                    return; 
                    }
                    
                    // Actually get the report from sessionMgr.
                    try {
                    report = sessionMgr.getReport(query, attributes);
                    } catch (PluginException pe) {
                    report = null;
                    JOptionPane.showMessageDialog(null, pe.getMessage());
                    }
                    
                    /* If the report has been successfully retrieved, then
                    * update the viewer and refresh the ShowReport view. */
                    ((ShowReportView) inst).setColumns(report.getAttributes());
                    ((ShowReportView) inst).setInput(report);
                    ((ShowReportView) inst).setFocus();
    }
    else return;
    } catch (PartInitException e) {
    e.printStackTrace();
    }
}
});

return new Status(Status.OK, "CSPlugin", 0,
"Get Report successfull.", null);
}

}
