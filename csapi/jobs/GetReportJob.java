/**
 * 
 */
package csapi.jobs;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import csapi.core.Report;
import csapi.core.SessionMgr;
import csapi.views.ShowReportView;

/**
 * @author grandpas *
 */
public class GetReportJob extends Job {
	
	public GetReportJob(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {
		
        // Get the query to be executed.
        JFrame myJFrame = new JFrame();
        final String query = (String) JOptionPane.showInputDialog(myJFrame,
                "Please provide query:", "Query",
                JOptionPane.OK_CANCEL_OPTION, null, null, "(problem_number='11587')");
        // if cancel is hit
        if (query == null) { 
            return new Status(Status.CANCEL, "ClearCS", 0, 
            		"ClearCS findRecord cancelled", null); 
            }
        
        // Get the list of attributes.
        final String attributesList = (String) JOptionPane.showInputDialog(myJFrame,
                "Please provide attributes list, separated by pipes:", "Attributes needed",
                JOptionPane.OK_CANCEL_OPTION, null, null, "problem_number|problem_synopsis");
        // if cancel is hit
        if (attributesList == null) { 
            return new Status(Status.CANCEL, "ClearCS", 0, "ClearCS bugval " +
    	            "cancelled", null); 
            }
		
		Display.getDefault().asyncExec(new Runnable() { public void run() {
		    try {
				System.out.println("avant getdefault");
				SessionMgr sessionMgr = SessionMgr.getDefault();
				System.out.println("avant getReport");
				Report report = sessionMgr.getReport(query, attributesList);
				System.out.println("apr�s getReport");
				
		        IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		        IViewPart inst = null;
		        
		        // Get the instance of the view and focus it.
		        inst = wkbch.getActivePage().showView("csapi.views.ShowReportView");
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
		
		return new Status(Status.OK, "MyFirstPlugin", 0,
				"Hello, Eclipse world Tasks Completed successfully", null);
	}

}
