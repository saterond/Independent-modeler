/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.actions;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.AttributeModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditFragmentDialog;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditMessageDialog;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelSaveEditFragmentDialog extends SequenceModelAbstractAction {

    public static final String ACTION_NAME = "Save";
    private static final Logger LOG = Logger.getLogger(SequenceModelSaveEditFragmentDialog.class.getName());
    
    private FragmentModel model;
    private SequenceModelEditFragmentDialog dialog;
    private DefaultGraphCell cell;
    private SequenceModelGraph graph;

    public SequenceModelSaveEditFragmentDialog(FragmentModel model,DefaultGraphCell cell,SequenceModelGraph graph, SequenceModelEditFragmentDialog dialog) {
        super(ACTION_NAME, null);
        this.model = model;
        this.dialog = dialog;
        this.cell = cell;
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fragmentName = this.dialog.getFragmentName();

        List<ConditionModel> conditions = this.dialog.getConditions();
        if(!conditions.isEmpty()) model.setConditionModels(conditions);
        model.setName(fragmentName);

        DefaultGraphCell label = (DefaultGraphCell)cell.getChildAt(1);
        if(label != null) label.setUserObject(fragmentName);

        this.graph.insertConditionCell(cell,model);
        
        this.dialog.updateCell();
        this.dialog.dispose();
    }
}
