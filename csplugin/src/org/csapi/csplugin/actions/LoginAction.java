/*
 * Created on 29 mai 2005
 */
package org.csapi.csplugin.actions;

import org.csapi.csplugin.jobs.LoginJob;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * <p>This class is the action attached to the Login command
 * in the menu. It basically just fires the ChangeAttributesJob 
 * run method.</p>
 * 
 * @author Boris Baldassari
 */
public class LoginAction implements IWorkbenchWindowActionDelegate {

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
LoginJob job = new LoginJob("Login");
job.setUser(true);
job.schedule();
job.addJobChangeListener(new JobChangeAdapter(){
public void done(IJobChangeEvent event) {
        event.getResult();
//        CsapiPlugin.getDefault().getLog().log(status);
        //if (status.isOK())  
        //;
        //  else
        //;
}});
}

/* (non-Javadoc)
 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
 */
public void selectionChanged(IAction action, ISelection selection) {

}
}
