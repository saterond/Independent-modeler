/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import org.jgraph.graph.DefaultGraphCellEditor;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelVertexEditor  extends DefaultGraphCellEditor {

    @Override
    public Object getCellEditorValue() {
        return new MessageModel(this.realEditor.getCellEditorValue().toString());
    }
}