/*
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.RunReportHistoryJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * <p>This class is the action attached to the Run Report View command
 * in the ReportHistoryView view's menu. It basically just fires the
 * RunReportHistoryJob run method.</p>
 * 
 * @author Boris Baldassari
 */
public class RunReportHistoryAction extends Action {

	/**
	 * 
	 */
	public RunReportHistoryAction() {
		super();
		
		this.setDescription("Run selected report.");
		this.setText("Run Report");
	}

	public void run() {
		RunReportHistoryJob job = new RunReportHistoryJob("Get My Reports");
		
		/* Prevent display of the progress bar. */
		job.setUser(false);
		job.schedule();
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				event.getResult();
//				 if (status.is)
//					CsapiPlugin.getDefault().getLog().log(status);
//				 else
			}
		});	
	}
}
