/*
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.QuerySelectJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;

/**
 * <p>This class is the action attached to the Query Select command
 * in the MyReportsView view's menu. It basically just fires the
 * QuerySelectJob run method.</p>
 * 
 * @author Boris Baldassari
 */
public class QuerySelectAction extends Action {

	/**
	 * The constructor for the action.
	 */
	public QuerySelectAction() {
		super();
		
		this.setDescription("Query selected records only.");
		this.setText("Query Selection");
	}

	/**
	 * The execution method for the action.
	 */
	public void run() {
		QuerySelectJob job = new QuerySelectJob("Query selected records");
		
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
