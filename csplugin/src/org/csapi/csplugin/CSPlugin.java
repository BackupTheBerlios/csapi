/*
 * Created on 23 juillet 2005
 */
package org.csapi.csplugin;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csplugin.exceptions.CSPluginException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class CSPlugin extends AbstractUIPlugin {

    /** The shared instance. */
    private static CSPlugin plugin;

    /** Resource bundle. */
    private ResourceBundle resourceBundle;

    /**
     * The constructor.
     */
    public CSPlugin() {
        plugin = this;
        try {
            resourceBundle = ResourceBundle
                    .getBundle("org.csapi.csplugin.CsPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {

        super.start(context);

        IPreferenceStore store = getPreferenceStore();

        // TOTO throw an exception
        if (store.getString("CSAPI_USER_LOGIN").equalsIgnoreCase("")) {
            System.out.println("csapiuserlogin is empty ["
                    + store.getString("CSAPI_USER_LOGIN") + "]");
        }

        /* Initializes the shared instance of SessionMgr. */
        SessionMgr sessionMgr = new SessionMgr(store
                .getString("CSAPI_USER_LOGIN"), store
                .getString("CSAPI_USER_PASSWORD"), store
                .getString("CSAPI_USER_ROLE"), store
                .getString("CSAPI_SERVER_IP"), store
                .getString("CSAPI_SERVER_DB"));
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static CSPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = CSPlugin.getDefault().getResourceBundle();
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
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin("csplugin", path);
    }

    /**
     * Defines the default preferences settings (needed to reset preference
     * page).
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     * @see csapi.preferences.UserDataPreferences
     */
    protected void initializeDefaultPreferences(IPreferenceStore store) {
        store.setDefault("CSAPI_USER_LOGIN", "");
        store.setDefault("CSAPI_USER_PASSWORD", "");
        store.setDefault("CSAPI_USER_ROLE", "");
        store.setDefault("CSAPI_SERVER_IP", "");
        store.setDefault("CSAPI_SERVER_DB", "");
    }

    public void sendMessage(CSPluginException cse) {
        // MessageDialog.openInformation(view.getSite().getShell(),"Readme
        // Editor","View Action executed");
    }
}
