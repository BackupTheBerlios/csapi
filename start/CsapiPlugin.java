package csapi;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class CsapiPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static CsapiPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/**
	 * The constructor.
	 */
	public CsapiPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("csapi.CsapiPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
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
	}

	/**
	 * Returns the shared instance.
	 */
	public static CsapiPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = CsapiPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	/**
	 * Defines the default preferences settings (needed to reset 
	 * preference page).
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeDefaultPreferences(org.eclipse.jface.preference.IPreferenceStore)
	 * @see csapi.preferences.UserDataPreferences
	 */
	protected void initializeDefaultPreferences(IPreferenceStore store) {
	    store.setDefault("CSAPI_USER_LOGIN", "");
	    store.setDefault("CSAPI_USER_PASSWORD", "");
	    store.setDefault("CSAPI_USER_ROLE", "");
	    store.setDefault("CSAPI_SERVER_IP", "");
	    store.setDefault("CSAPI_SERVER_DB", "");
	}
}
