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
 * 
 * 
 * @author grandpas
 */
public class ClearReportViewAction extends Action {

	/**
	 * 
	 */
	public ClearReportViewAction() {
		super();
		
		this.setDescription("Clear the Report view.");
		this.setText("Clear View");
	}

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
