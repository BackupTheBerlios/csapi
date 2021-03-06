/**
 * Created on 22 ao?t 2005.
 */
package org.csapi.csplugin.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.csapi.csapicore.core.Report;
import org.csapi.csplugin.views.ShowReportView;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * This class is the action attached to the ClearView command in the
 * ReportHistoryView view's menu. There is no need to run an aSyncExec session
 * because actions do entirely take place in the current thread worspace.
 * </p>
 * 
 * <p>
 * It gets the current view's viewer and extract a text (String) version of the
 * report. It then prompts the user for a name and saves the text report as a
 * text file.
 * </p>
 * 
 * @author Boris Baldassari
 */
public class TextReportAction extends Action {

    /**
     * 
     */
    public TextReportAction() {
        super();
        this.setDescription("Export the current report as a text file.");
        this.setText("To Text...");
    }

    public void run() {
        String path = null;
        Report report = null;

        IWorkbenchWindow wkbw = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
        IViewPart view = null;
        view = wkbw.getActivePage().findView(
                "org.csapi.csplugin.views.ShowReportView");

        if (view != null) {
            path = ((ShowReportView) view).getTextReport();
            report = ((ShowReportView) view).getReport();
        } else {
            return;
        }

        String[] records = report.toStrings();

        try {
            PrintWriter out = new PrintWriter(new FileWriter(path));
            for (int index = 0; index < records.length; index++) {
                out.println(records[index]);
            }
            out.flush();
            out.close();
        } catch (IOException iox) {
            System.out.println("File read error...");
            iox.printStackTrace();
        }
        // } catch (FileNotFoundException fnf) {
        // System.out.println("File not found...");
        // fnf.printStackTrace();
        // }
    }
}
