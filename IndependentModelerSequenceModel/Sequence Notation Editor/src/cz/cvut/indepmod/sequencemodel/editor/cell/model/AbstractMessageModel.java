/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.IAbstractMessage;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class AbstractMessageModel extends AbstractModel implements IAbstractMessage,Comparable<AbstractMessageModel>{

    protected DefaultGraphCell cell;
    protected AbstractMessageModel associateMessage;
    protected double position;

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public DefaultGraphCell getCell() {
        return cell;
    }

    public void setCell(DefaultGraphCell cell) {
        this.cell = cell;
    }

    public AbstractMessageModel getAssociateMessage() {
        return associateMessage;
    }

    public void setAssociateMessage(AbstractMessageModel associateMessage) {
        this.associateMessage = associateMessage;
    }

    @Override
    public int compareTo(AbstractMessageModel o) {
        int res = 0;
        if(this.position < o.getPosition()) res = -1;
        else if(this.position > o.getPosition()) res = 1;
        return res;
    }


}
