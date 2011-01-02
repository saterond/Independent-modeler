/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.actions;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.LifelineModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditLifelineDialog;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditMessageDialog;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import org.jgraph.graph.DefaultGraphCell;
import org.openide.windows.WindowManager;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelEditAction extends SequenceModelAbstractAction {

    public static final String ACTION_NAME = "Edit";
    private static final Logger LOG = Logger.getLogger(SequenceModelEditAction.class.getName());
    private SequenceModelGraph graph;

    public SequenceModelEditAction(SequenceModelGraph graph) {
        super(ACTION_NAME, null);
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            DefaultGraphCell cell = (DefaultGraphCell) this.graph.getSelectionCell();
            cell = (DefaultGraphCell) cell.getChildAt(0);
            if(cell.getUserObject() instanceof MessageModel){
            MessageModel model = (MessageModel) cell.getUserObject();

            SequenceModelEditMessageDialog dialog = new SequenceModelEditMessageDialog(
                    WindowManager.getDefault().getMainWindow(),
                    graph,
                    cell,
                    model);
            }else if(cell.getUserObject() instanceof LifelineModel){
            LifelineModel model = (LifelineModel) cell.getUserObject();

            SequenceModelEditLifelineDialog dialog = new SequenceModelEditLifelineDialog(
                    WindowManager.getDefault().getMainWindow(),
                    graph,
                    cell,
                    model);
            }

            //this.graph.getGraphLayoutCache().editCell(cell, map);
        } catch (NullPointerException ex) {
            LOG.severe("Edit Action was performed even is no cell was selected!");
        } catch (ClassCastException ex) {
            LOG.severe("Edit Action was performed on different vertex than class");
        }
    }
}

