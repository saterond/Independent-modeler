/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.actions;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.AttributeModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditMessageDialog;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelSaveEditMessageDialog extends SequenceModelAbstractAction {

    public static final String ACTION_NAME = "Save";
    private static final Logger LOG = Logger.getLogger(SequenceModelSaveEditMessageDialog.class.getName());
    
    private MessageModel model;
    private SequenceModelEditMessageDialog dialog;

    public SequenceModelSaveEditMessageDialog(MessageModel model, SequenceModelEditMessageDialog dialog) {
        super(ACTION_NAME, null);
        this.model = model;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newMessageName = this.dialog.getMessageName();
        if (!newMessageName.equals(model.getName())) {
            if (newMessageName.matches("^[A-Za-z][0-9A-Za-z]*$")) {
                LOG.info("Changing the name of the class");
                model.setName(newMessageName);
            } else {
                LOG.warning("Bad name of the class!");
            }
        }
        TypeModel type = this.dialog.getType();
        if(type != null) model.setType(type);

        Set<AttributeModel> attributes = this.dialog.getAttributes();
        if(!attributes.isEmpty()) model.setAttributeModels(attributes);
        
        this.dialog.updateCell();
        this.dialog.dispose();
    }
}
