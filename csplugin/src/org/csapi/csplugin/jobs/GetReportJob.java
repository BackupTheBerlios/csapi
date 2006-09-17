/**
 * 
 */
package org.csapi.csplugin.jobs;

import javax.swing.JFrame;
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
 * <p>The Job class for the Get Report command in the global menu.</p>
 * 
 * <p>It prompts for query and attributes (in the current thread workspace,
 * then get the default sessionMgr and execute the query. When result is
 * returned, the view's viewer is updated.</p>
 * 
 * @author Boris Baldassari
 *
 */
public class GetReportJob extends Job {

public GetReportJob(String name) {
super(name);
}

/* (non-Javadoc)
 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
 */
protected IStatus run(IProgressMonitor monitor) {

        // Get the query to be executed.
        JFrame myJFrame = new JFrame();
        final String query = (String) JOptionPane.showInputDialog(myJFrame,
                "Please provide query:", "Query",
                JOptionPane.OK_CANCEL_OPTION, null, null, 
                "(product_name='Product A')");
        // if cancel is hit
        if (query == null) { 
            return new Status(Status.CANCEL, "CSPlugin", 0, 
            "Query cancelled", null); 
            }
        
        // Get the list of attributes.
        final String attributesList = (String) JOptionPane.showInputDialog(myJFrame,
                "Please provide attributes list, separated by pipes:", "Attributes needed",
                JOptionPane.OK_CANCEL_OPTION, null, null, "problem_number|problem_synopsis");
        // if cancel is hit
        if (attributesList == null) { 
            return new Status(Status.CANCEL, "CSPlugin", 0, 
            "Attributes cancelled", null); 
            }

Display.getDefault().asyncExec(new Runnable() { public void run() {
    try {
SessionMgr sessionMgr = SessionMgr.getDefault();

Report report;

try {
report = sessionMgr.getReport(query, attributesList);
} catch (PluginException pe) {
report = null;
JOptionPane.showMessageDialog(null, pe.getMessage());
}

        IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
        IViewPart inst = null;
        
        // Get the instance of the view and focus it.
        inst = wkbch.getActivePage().showView("org.csapi.csplugin.views.ShowReportView");
        if (inst != null && report != null) {
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
"Get Report successfull.", null);
}

}
