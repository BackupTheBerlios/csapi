/**
 * Created on 23 ao?t 2005.
 */
package org.csapi.csplugin.preferences;

import org.csapi.csapicore.core.SessionMgr;
import org.csapi.csplugin.CSPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * <p>The Preference page for user login and co.</p>
 * 
 * <p>We use here a FieldEditorPreferencePage; this allow easy handling of 
 * preference attributes, but is far less flexible (e.g. password appears 
 * in clear form).</p>
 * 
 * <p>Preferences are initialized in {@link csapi.CsapiPlugin CsapiPlugin}.</p> 
 * 
 * @author Boris Baldassari
 */
public class UserDataPreferences extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

// User-related preferences.
private StringFieldEditor userLogin;
private StringFieldEditor userPassword;
private StringFieldEditor userRole;

// Server-related preferences.
private StringFieldEditor serverIP;
private StringFieldEditor serverDB;

/**
 * Constructor.
 */
public UserDataPreferences() {
super(GRID);

/* Set some fields for the current preference page. */
setTitle("CSAPI Preference page");
setDescription("CSAPI Preference page");
setMessage("Fill in the variables used to connect to the server.");

/* Set the preference store for this preference page. */
        IPreferenceStore store = CSPlugin.getDefault()
    .getPreferenceStore();
        setPreferenceStore(store);
}

/** 
 * Creates the Fields for the Preferences page.
 * 
 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
 */
protected void createFieldEditors() {
/* Create Fields for the preference page. */
userLogin = new StringFieldEditor(
"CSAPI_USER_LOGIN", "User login", 
getFieldEditorParent());
userPassword = new StringFieldEditor(
            "CSAPI_USER_PASSWORD", "User password", 
            getFieldEditorParent());
userRole = new StringFieldEditor(
            "CSAPI_USER_ROLE", "User role", 
            getFieldEditorParent());
serverIP = new StringFieldEditor(
            "CSAPI_SERVER_IP", "Server IP:Port", 
            getFieldEditorParent());
serverDB = new StringFieldEditor(
            "CSAPI_SERVER_DB", "Server database", 
            getFieldEditorParent());

/* Add fields to the composite. */
addField(userLogin);
addField(userPassword);
addField(userRole);
addField(serverIP);
addField(serverDB);

        
        /* Fill the boxes with registered values. */
        userLogin.setStringValue(getPreferenceStore()
                .getString("CSAPI_USER_LOGIN"));
        userRole.setStringValue(getPreferenceStore()
                .getString("CSAPI_USER_ROLE"));
        serverIP.setStringValue(getPreferenceStore()
                .getString("CSAPI_SERVER_IP"));
        serverDB.setStringValue(getPreferenceStore()
                .getString("CSAPI_SERVER_DB"));
}

/* (non-Javadoc)
 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
 */
public void init(IWorkbench workbench) {

}


    /**
     * When the OK button is clicked, update the preference and
     * set values in the CSWrapper object.
     * 
     * {@inheritDoc}
     */
    public boolean performOk()
    {
        getPreferenceStore().setValue("CSAPI_USER_LOGIN", 
                userLogin.getStringValue());
        getPreferenceStore().setValue("CSAPI_USER_PASSWORD", 
                userPassword.getStringValue());
        getPreferenceStore().setValue("CSAPI_USER_ROLE", 
                userRole.getStringValue());
        getPreferenceStore().setValue("CSAPI_SERVER_IP", 
                serverIP.getStringValue());
        getPreferenceStore().setValue("CSAPI_SERVER_DB", 
                serverDB.getStringValue());
        
        SessionMgr.getDefault().setCsapiUser(userLogin.getStringValue());
        SessionMgr.getDefault().setCsapiPassword(userPassword.getStringValue());
        SessionMgr.getDefault().setCsapiRole(userRole.getStringValue());
        SessionMgr.getDefault().setCsapiServerIP(serverIP.getStringValue());
        SessionMgr.getDefault().setCsapiDatabase(serverDB.getStringValue());

        return true;
    }
}
