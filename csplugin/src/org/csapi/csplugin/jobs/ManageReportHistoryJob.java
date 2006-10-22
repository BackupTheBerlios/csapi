/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.jobs;

import org.csapi.csapicore.core.Favorites;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csplugin.views.ReportHistoryView;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * The Job class for the ChangeAttributes command. Through a asyncExec session,
 * it prompts for a new set of attributes re-runs the report with the new
 * attributes. Finaly, it sets the new report as viewer and refreshes the
 * ShowReport view.
 * </p>
 * 
 * @author Boris Baldassari
 * 
 */
public class ManageReportHistoryJob extends Job {

    public ManageReportHistoryJob(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    protected IStatus run(IProgressMonitor monitor) {
        
        try {
            Favorites favorites = SessionMgr.getDefault().getReportHistory();

            IWorkbenchWindow wkbch = PlatformUI.getWorkbench()
                    .getWorkbenchWindows()[0];
            IViewPart inst = null;

            // Get the instance of the view and focus it.
            inst = wkbch.getActivePage().showView(
                    "org.csapi.csplugin.views.ReportHistoryView");
            if (inst != null) {
                ((ReportHistoryView) inst).setInput(favorites);
                ((ReportHistoryView) inst).setFocus();
            }
        } catch (PartInitException e) {
            e.printStackTrace();
        }
        
        return new Status(Status.OK, "CSPlugin", 0,
                "Report History displayed.", null);
    }

}
