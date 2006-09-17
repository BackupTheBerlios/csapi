/*
 * Created on 29 mai 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csapicore.core.Favorites;
import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csplugin.views.ReportHistoryView;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


/**
 * <p>This class is the action attached to the Manage My Reports... command
 * in the global menu. There is no need to run an aSyncExec session because 
 * actions do entirely take place in the current thread worspace.</p>
 * 
 * <p>The action simply shows the view, and creates it if it does not 
 * already exists.</p>
 * 
 * @author Boris Baldassari
 */
public class ManageReportHistoryAction implements IWorkbenchWindowActionDelegate {

/* (non-Javadoc)
 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
 */
public void dispose() {
    // not used but kept for standards
}

/* (non-Javadoc)
 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
 */
public void init(IWorkbenchWindow window) {
    // not used but kept for standards
}

/* (non-Javadoc)
 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
 */
public void run(IAction action) {
    try {
Favorites favorites = SessionMgr.getDefault().getReportHistory();

        IWorkbenchWindow wkbch = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
        IViewPart inst = null;
        
        // Get the instance of the view and focus it.
        inst = wkbch.getActivePage().showView(
        "org.csapi.csplugin.views.ReportHistoryView");
        if (inst != null) {
            ((ReportHistoryView) inst).setInput(favorites);
            ((ReportHistoryView) inst).setFocus();
        }
    } catch (PartInitException e) {
        e.printStackTrace();
    }
}

/* (non-Javadoc)
 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
 */
public void selectionChanged(IAction action, ISelection selection) {
    // not used but kept for standards
}
}
