/**
 * 
 */
package org.csapi.csplugin.jobs;

import javax.swing.JOptionPane;

import org.csapi.csapicore.core.Report;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csapicore.exceptions.PluginException;
import org.csapi.csplugin.views.MyReportsView;
import org.csapi.csplugin.views.ShowReportView;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * @author Boris Baldassari
 *
 */
public class RunMyReportsJob extends Job {
	
	public RunMyReportsJob(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {
		
		Display.getDefault().asyncExec(new Runnable() { public void run() {
		    try {
		        
				SessionMgr sessionMgr = SessionMgr.getDefault();
				
				Report report;
					
		        IWorkbenchWindow wkbch = PlatformUI.getWorkbench()
		        	.getWorkbenchWindows()[0];
		        IViewPart instMyReports = null;
		        IViewPart instShowReport = null;
		        
		        // Get the instance of the view and focus it.
		        instMyReports = wkbch.getActivePage().showView(
		        		"org.csapi.csplugin.views.MyReportsView");
		        
		        // Get the instance of the view and focus it.
		        instShowReport = wkbch.getActivePage().showView(
		        		"org.csapi.csplugin.views.ShowReportView");
		
		        if (instMyReports != null || instShowReport != null) {
		        	StructuredSelection mySel = 
		        		(StructuredSelection)((MyReportsView)instMyReports)
		        		.getViewer().getSelection();
		        	report = (Report)mySel.toArray()[0];
		        	
		        	try {
		        		report = sessionMgr.getReport(report.getQuery(),
		        				report.getAttributesString());
		        		((ShowReportView) instShowReport).setColumns(
		        				report.getAttributes());
		        		((ShowReportView) instShowReport).setInput(report);
		        		((ShowReportView) instShowReport).setFocus();
		        	} catch (PluginException pe) {
		        		report = null;
		        		JOptionPane.showMessageDialog(null, pe.getMessage());
		        	}
		        }
		    } catch (PartInitException e) {
		        e.printStackTrace();
		    }
		}
		});
		
		return new Status(Status.OK, "CSPlugin", 0,
				"Reports ran successfully.", null);
	}

}
