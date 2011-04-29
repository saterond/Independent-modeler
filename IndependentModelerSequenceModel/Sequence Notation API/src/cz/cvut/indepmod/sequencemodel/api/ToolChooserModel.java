package cz.cvut.indepmod.sequencemodel.api;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class ToolChooserModel {

    public static final String TOOL_CHOOSER_TITLE = "ToolChooser";

    public static final String TOOL_CONTROL_NAME = "Control";
    public static final String TOOL_LIFELINE_NAME = "Object";
    public static final String TOOL_MESSAGE_NAME = "Message";
    public static final String TOOL_RETURN_NAME = "Return message";
    public static final String TOOL_FRAGMENT_NAME = "Fragment";

    public static enum Tool {
        TOOL_LIFELINE,
        TOOL_MESSAGE,
        TOOL_CONTROL,
        TOOL_RETURN,
        TOOL_FRAGMENT,
    }

    public static final String SELECTED_TOOL_PROPERTY = "selectedTool";


    private Tool selectedTool;
    private List<ToolChooserModelListener> lsnrs;


    public ToolChooserModel() {
        this.selectedTool = Tool.TOOL_CONTROL;
        this.lsnrs = new LinkedList<ToolChooserModelListener>();
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        Tool oldTool = this.selectedTool;
        this.selectedTool = selectedTool;

        if (! this.selectedTool.equals(oldTool)) {
            this.fireSelectedToolChanged();
        }
    }

    public void addListener(ToolChooserModelListener lsnr) {
        if (! this.lsnrs.contains(lsnr)) {
            this.lsnrs.add(lsnr);
        }
    }

    public void removeListener(ToolChooserModelListener lsnr) {
        this.lsnrs.remove(lsnr);
    }

    private void fireSelectedToolChanged() {
        for (ToolChooserModelListener lsnr : this.lsnrs) {
            lsnr.selectedToolChanged(this.getSelectedTool());
        }
    }
}
