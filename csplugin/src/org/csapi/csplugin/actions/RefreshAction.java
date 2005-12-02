/*
 * Created on 23 août 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.RefreshJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * 
 * 
 * @author grandpas
 */
public class RefreshAction extends Action {

	/**
	 * 
	 */
	public RefreshAction() {
		super();
		
		this.setDescription("Re-Run the current report.");
		this.setText("Refresh Report");
	}

	public void run() {
		RefreshJob job = new RefreshJob("Refresh Report");
		
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
