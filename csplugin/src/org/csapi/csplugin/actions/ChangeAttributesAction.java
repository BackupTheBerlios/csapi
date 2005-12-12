/*
 * Created on 08 dec. 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.ChangeAttributesJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * <p>This class is the action attached to the ChangeAttribute command
 * in the MyReportsView view's menu. It basically just fires the
 * ChangeAttributesJob run method.</p>
 * 
 * @author Boris Baldassari
 */
public class ChangeAttributesAction extends Action {

	/**
	 * The constructor for the Action.
	 */
	public ChangeAttributesAction() {
		super();
		
		this.setDescription("Change attributes of this report.");
		this.setText("Change Attributes...");
	}

	/**
	 * The execution method for this action.
	 */
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
