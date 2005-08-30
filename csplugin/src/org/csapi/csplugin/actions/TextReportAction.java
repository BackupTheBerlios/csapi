/**
 * Created on 22 août 2005
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
public class TextReportAction extends Action {

	/**
	 * 
	 */
	public TextReportAction() {
		super();
		this.setDescription("Export the current report as a text file.");
		this.setText("To Text");
	}
	
	public void run() {
		IWorkbenchWindow wkbw = PlatformUI.getWorkbench()
			.getWorkbenchWindows()[0];
		IViewPart view = null;
		view = wkbw.getActivePage()
			.findView("org.csapi.csplugin.views.ShowReportView");
		
		if (view != null) {
			((ShowReportView)view).getTextReport();
		}
	}
}
