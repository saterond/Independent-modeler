/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class ReturnMessageModel extends AbstractMessageModel{

    private String name;

    public ReturnMessageModel(String name) {
        this.name = name;
    }

    public ReturnMessageModel(String name, DefaultGraphCell cell, MessageModel message) {
        this.name = name;
        super.cell = cell;
        super.associateMessage = message;
    }
    
    /**
     * Returns the name of this attribute
     *
     * @return the name of this attribute
     */
    //@Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  this.name;
    }
}