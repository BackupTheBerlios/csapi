/*
 * Created on 23 août 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.views.ShowReportView;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * <p>This class is the action attached to the ClearReport command
 * in the ShowReportView view's menu.  There is no need to run an 
 * aSyncExec session because actions do entirely take place in the
 * current thread worspace.</p>
 * 
 * <p>It Gets the view's viewer and resets the current viewer through
 * the clearViewer() method. Since the viewer has its own clearing 
 * method, this method belongs to the view object.</p>
 * 
 * @author Boris Baldassari
 */
public class ClearReportViewAction extends Action {

	/**
	 * The constructor for the action.
	 */
	public ClearReportViewAction() {
		super();
		
		this.setDescription("Clear the Report view.");
		this.setText("Clear View");
	}

	/**
	 * The execution method for the action.
	 */
	public void run() {
		IWorkbenchWindow wkbw = PlatformUI.getWorkbench()
			.getWorkbenchWindows()[0];
		IViewPart view = null;
		view = wkbw.getActivePage()
			.findView("org.csapi.csplugin.views.ShowReportView");
		
		if (view != null) {
			((ShowReportView)view).clearViewer();
		}
	}
}
