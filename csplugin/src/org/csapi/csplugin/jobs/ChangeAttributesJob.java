/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.jobs;

import javax.swing.JOptionPane;

import org.csapi.csapicore.core.Report;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csapicore.exceptions.PluginException;
import org.csapi.csplugin.views.ShowReportView;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * <p>The Job class for the ChangeAttributes command. Through a asyncExec 
 * session, it prompts for a new set of attributes re-runs the report with 
 * the new attributes. Finaly, it sets the new report as viewer and refreshes
 * the ShowReport view.</p>
 * 
 * @author Boris Baldassari
 *
 */
public class ChangeAttributesJob extends Job {

public ChangeAttributesJob(String name) {
super(name);
}

/* (non-Javadoc)
 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
 */
protected IStatus run(IProgressMonitor monitor) {
        
        Display.getDefault().asyncExec(new Runnable() { public void run() {
        
        Report report = null;
        
        try {
        IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
        IViewPart inst = null;
        
        // Get the instance of the view and focus it.
        inst = wkbch.getActivePage().showView("org.csapi.csplugin.views.ShowReportView");
        if (inst != null) {
            report = (Report)((ShowReportView) inst).getReport();
        }
    } catch (PartInitException e) {
        e.printStackTrace();
    }
        
        // Get the list of attributes.
        final String attributesList = (String) JOptionPane.showInputDialog(null,
                "Please provide attributes list, separated by pipes:", "Attributes needed",
                JOptionPane.OK_CANCEL_OPTION, null, null, 
                report.getAttributesString());
        // if cancel is hit
        if (attributesList == null) {
        return;
        }
        
        SessionMgr sessionMgr = SessionMgr.getDefault();
        
        try {
        // Re-Run the report with new attributes.
        report = sessionMgr.getReport(report.getQuery(), attributesList);
        } catch (PluginException pe) {
        report = null;
        // Print an Error dialog.
        JOptionPane.showMessageDialog(null, pe.getMessage());
        }
        
        
        try {
        IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
        IViewPart inst = null;
        
        /* Get the instance of the view and focus it.
         * Set the new columns (they have changed) and set the
         * new report as viewer. */
        inst = wkbch.getActivePage().showView("org.csapi.csplugin.views.ShowReportView");
        if (inst != null) {
            ((ShowReportView) inst).setColumns(report.getAttributes());
            ((ShowReportView) inst).setInput(report);
            ((ShowReportView) inst).setFocus();
        }
    } catch (PartInitException e) {
        e.printStackTrace();
    }
}
        });

return new Status(Status.OK, "CSPlugin", 0,
"Change of Attributes was successfull.", null);
}

}
