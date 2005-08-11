/*
 * Created on 23 juin 2005
 */
package csapi.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import csapi.CsapiPlugin;
import csapi.jobs.GetReportJob;

/**
 * Get a Change Synergy report from the menu.
 * 
 * Most of the code is in the GetReportJob class, since we want the UI 
 * to be scheduled even when the report is running.
 * 
 * @author grandpas
 */
public class GetReportAction implements IWorkbenchWindowActionDelegate {

	/**
	 * Constructor for GetReportAction.
	 */
	public GetReportAction() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		GetReportJob job = new GetReportJob("GetReportJob");
		job.setUser(true);
		job.schedule();
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				IStatus status = event.getResult();
//				CsapiPlugin.getDefault().getLog().log(status);
				// if (status.isOK())
				// ;
				// else
				// ;
			}
		});	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
