/**
 * Created on 13 août 2005
 */
package org.csapi.csapicore;
 
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main class for the CsapiCore plugin.
 * 
 * @author Boris Baldassari
 */
public class CsapiCorePlugin extends AbstractUIPlugin {
	
	//The shared instance.
	private static CsapiCorePlugin plugin;

	/**
	 * The constructor.
	 * 
	 * @param descriptor
	 */
	public CsapiCorePlugin() {
		super();
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}
	
	/**
	 * Returns the default instance of the CsapiCorePlugin class. If it does
	 * not exist, then create it.
	 * 
	 * @returns The default instance of the plugin.
	 */
	public static CsapiCorePlugin getDefault() {
		if (plugin == null) {
			plugin = new CsapiCorePlugin();
		}			
		return plugin;
	}
	
	/**
	 * Defines the default preferences settings (needed to reset 
	 * preference page).
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin
	 * @see csapi.preferences.UserDataPreferences
	 */
//	protected void initializeDefaultPreferences(IPreferenceStore store) {
//	    store.setDefault("CSAPI_USER_LOGIN", "");
//	    store.setDefault("CSAPI_USER_PASSWORD", "");
//	    store.setDefault("CSAPI_USER_ROLE", "");
//	    store.setDefault("CSAPI_SERVER_IP", "");
//	    store.setDefault("CSAPI_SERVER_DB", "");
//	}
}
