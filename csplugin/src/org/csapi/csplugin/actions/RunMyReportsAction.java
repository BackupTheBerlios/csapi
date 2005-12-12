/*
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.RunMyReportsJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * <p>This class is the action attached to the Run Report View command
 * in the MyReportsView view's menu. It basically just fires the
 * RunMyReportsJob run method.</p>
 * 
 * @author Boris Baldassari
 */
public class RunMyReportsAction extends Action {

	/**
	 * 
	 */
	public RunMyReportsAction() {
		super();
		
		this.setDescription("Run selected report.");
		this.setText("Run Report");
	}

	public void run() {
		RunMyReportsJob job = new RunMyReportsJob("Get My Reports");
		
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
