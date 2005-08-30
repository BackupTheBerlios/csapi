/*
 * Created on 29 mai 2005
 */
package org.csapi.csplugin.jobs;

import org.csapi.csapicore.core.SessionMgr;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A job.
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
		System.out.println("avant getdefault");
		SessionMgr sessionMgr = SessionMgr.getDefault();
		System.out.println("avant login");
		sessionMgr.login();
		System.out.println("après login");
		return new Status(Status.OK, "CSplugin", 0,
				"Connection successful.", null);
	}
}
