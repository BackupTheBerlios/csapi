/**
 * Created on 13 ao?t 2005
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

    // The shared instance.
    private static CsapiCorePlugin plugin;

    /**
     * The constructor.
     */
    public CsapiCorePlugin() {
        super();
    }

    /**
     * This method is called upon plug-in activation
     * 
     * @param context
     *            The BundleContext of the plugin.
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
     * Returns the default instance of the CsapiCorePlugin class. If it does not
     * exist, then create it.
     * 
     * @return The default instance of the plugin.
     */
    public static CsapiCorePlugin getDefault() {
        if (plugin == null) {
            plugin = new CsapiCorePlugin();
        }
        return plugin;
    }
}
