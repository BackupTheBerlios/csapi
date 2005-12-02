/*
 * Created on 23 août 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.ChangeAttributesJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * 
 * 
 * @author grandpas
 */
public class ChangeAttributesAction extends Action {

	/**
	 * 
	 */
	public ChangeAttributesAction() {
		super();
		
		this.setDescription("Change attributes of this report.");
		this.setText("Change Attributes...");
	}

	public void run() {
		ChangeAttributesJob job = new ChangeAttributesJob("Change Attributes");
		
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
