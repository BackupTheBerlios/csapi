/*
 * Created on 23 août 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.RunMyReportsJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * 
 * 
 * @author grandpas
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
