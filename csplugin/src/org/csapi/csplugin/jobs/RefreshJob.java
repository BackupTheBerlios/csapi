/**
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.jobs;

import javax.swing.JOptionPane;

import org.csapi.csapicore.core.Report;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csapicore.exceptions.PluginException;
import org.csapi.csplugin.views.ShowReportView;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * <p>The job class for the Refresh Report command in the ShowReport view.</p>
 * 
 * <p>It retrieves the viewer (Report) from ShowReportView, and re-run it
 * with same query and attributes. If the execution is successfull, then
 * it updates the viewer and refreshes the view.</p>
 * 
 * @author Boris Baldassari
 */
public class RefreshJob extends Job {
	
	public RefreshJob(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {
		
		Display.getDefault().asyncExec(new Runnable() { public void run() {
			
			Report report = null;
			
			try {
				IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
				IViewPart inst = null;
				
				// Get the instance of the view and focus it.
				inst = wkbch.getActivePage().showView("org.csapi.csplugin.views.ShowReportView");
				if (inst != null) {
					report = (Report)((ShowReportView) inst).getReport();
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			
			/* Get the current sessionMgr object. */
			SessionMgr sessionMgr = SessionMgr.getDefault();
			
			/* Actually get the report from the sessionMgr object. */
			try {
				report = sessionMgr.getReport(report.getQuery(), 
						report.getAttributesString());
			} catch (PluginException pe) {
				report = null;
				JOptionPane.showMessageDialog(null, pe.getMessage());
			}
			
			
			try {
				IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
				IViewPart inst = null;
				
				// Get the instance of the view and focus it.
				inst = wkbch.getActivePage().showView("org.csapi.csplugin.views.ShowReportView");
				if (inst != null) {
					((ShowReportView) inst).setColumns(report.getAttributes());
					((ShowReportView) inst).setInput(report);
					((ShowReportView) inst).setFocus();
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		});
		
		return new Status(Status.OK, "CSPlugin", 0,
				"Refresh was successfull.", null);
	}
	
}
