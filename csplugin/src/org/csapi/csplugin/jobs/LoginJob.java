/*
 * Created on 29 mai 2005
 */
package org.csapi.csplugin.jobs;

import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csapicore.exceptions.PluginException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * <p>
 * The job class for the Login command in the global menu.
 * </p>
 * 
 * <p>
 * From the csapicore point of view, this is done with the login() method from
 * the sessionMgr. The singleton pattern implementation allows to retrieve the
 * sessionMgr instance easily from static methods in the sessionMgr class.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class LoginJob extends Job {

	public LoginJob(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.jobs.InternalJob#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {
		SessionMgr sessionMgr = SessionMgr.getDefault();
		try {
			sessionMgr.login();
		} catch (PluginException pe) {
			// JOptionPane.showMessageDialog(null, pe.getMessage());
			return new Status(Status.ERROR, "CSplugin", 0, pe.getMessage(),
					null);
		}
		return new Status(Status.OK, "CSplugin", 0, "Connection successful.",
				null);
	}
}
