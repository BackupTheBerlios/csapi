/*
 * Created on 08 dec. 2005.
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.views.MyReportsView;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * <p>This class is the action attached to the ClearView command
 * in the MyReportsView view's menu. There is no need to run an 
 * aSyncExec session because actions do entirely take place in the
 * current thread worspace.</p>
 * 
 * <p>It Gets the view's viewer and resets the current viewer through
 * the clearViewer() method. Since the viewer has its own clearing 
 * method, this method belongs to the view object.</p>
 * 
 * @author Boris Baldassari
 */
public class ClearMyReportsViewAction extends Action {

	/**
	 * The constructor for this action.
	 */
	public ClearMyReportsViewAction() {
		super();
		
		this.setDescription("Clear the My Report view.");
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
			.findView("org.csapi.csplugin.views.MyReportsView");
		
		if (view != null) {
			((MyReportsView)view).clearViewer();
		}
	}
}
