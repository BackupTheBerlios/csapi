/*
 * Created on 29 mai 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csapicore.core.Favorites;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csplugin.views.MyReportsView;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


/**
 * @author Boris Baldassari
 * 
 * An Action.
 */
public class ManageMyReportsAction implements IWorkbenchWindowActionDelegate {

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
	    try {
			Favorites favorites = SessionMgr.getDefault().getDefaultFavorites();
			
	        IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
	        IViewPart inst = null;
	        
	        // Get the instance of the view and focus it.
	        inst = wkbch.getActivePage().showView(
	        		"org.csapi.csplugin.views.MyReportsView");
	        if (inst != null) {
	            ((MyReportsView) inst).setInput(favorites);
	            ((MyReportsView) inst).setFocus();
	        }
	    } catch (PartInitException e) {
	        e.printStackTrace();
	    }
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {

	}
}
