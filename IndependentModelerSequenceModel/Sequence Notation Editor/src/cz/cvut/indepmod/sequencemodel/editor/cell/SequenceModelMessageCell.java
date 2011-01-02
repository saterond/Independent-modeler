/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import javax.swing.tree.MutableTreeNode;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelMessageCell extends DefaultGraphCell{

    public SequenceModelMessageCell(Object o, AttributeMap am, MutableTreeNode[] mtns) {
        super(o, am, mtns);
        this.initUserObjectPointer(o);
    }

    public SequenceModelMessageCell(Object o, AttributeMap am) {
        super(o, am);
        this.initUserObjectPointer(o);
    }

    public SequenceModelMessageCell(Object o) {
        super(o);
        this.initUserObjectPointer(o);
    }

    public SequenceModelMessageCell() {
    }

    @Override
    public void setUserObject(Object userObject) {
        super.setUserObject(userObject);
        this.initUserObjectPointer(userObject);
    }



    /**
     * If the object in the parameter is ClassModel instance, set its cell
     * pointer to this object. ClassModel user object needs to know to which
     * cell it belongs because it returns relations according to this cell.
     * @param o Object, which could be the ClassModel insance
     */
    private void initUserObjectPointer(Object o) {
        if (o instanceof MessageModel) {
            MessageModel mm = (MessageModel) o;
            mm.setCell(this);
        }
    }
}
