/*
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.RefreshJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * <p>
 * This class is the action attached to the Refresh View command in the
 * ShowReportView view's menu. It basically just fires the RefreshJob run
 * method.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class RefreshAction extends Action {

    /**
     * The constructor of the action.
     */
    public RefreshAction() {
        super();

        this.setDescription("Re-Run the current report.");
        this.setText("Refresh Report");
    }

    /**
     * The execution method of the action.
     */
    public void run() {
        RefreshJob job = new RefreshJob("Refresh Report");

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
}
