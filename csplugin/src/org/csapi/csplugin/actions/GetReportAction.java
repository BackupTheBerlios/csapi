/*
 * Created on 23 juin 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.GetReportJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * <p>
 * Get a Change Synergy report from the menu.
 * </p>
 * 
 * <p>
 * Most of the code is in the GetReportJob class, since we want the UI to be
 * scheduled even when the report is running.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class GetReportAction implements IWorkbenchWindowActionDelegate {

    /**
     * Constructor for GetReportAction.
     */
    public GetReportAction() {
        super();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose() {
        // not used but kept for standards
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init(final IWorkbenchWindow window) {
        // not used but kept for standards
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        GetReportJob job = new GetReportJob("Get A Change/Synergy Report");

        /* Prevent display of the progress bar. */
        job.setUser(false);
        job.schedule();
        job.addJobChangeListener(new JobChangeAdapter() {
            public void done(IJobChangeEvent event) {
                event.getResult();
                // if (status.is)
                // CsapiPlugin.getDefault().getLog().log(status);
                // else
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(final IAction action, final ISelection selection) {
        // no action here
    }

}
