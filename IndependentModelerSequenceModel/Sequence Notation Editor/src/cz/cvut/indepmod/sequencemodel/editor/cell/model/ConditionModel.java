/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.ICondition;
import java.util.ArrayList;
import java.util.List;
import org.jgraph.graph.DefaultEdge;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class ConditionModel extends AbstractModel implements ICondition{

    private String name;
    private DefaultEdge conditionCell;
    private List<AbstractMessageModel> messageModels;

    public ConditionModel(String name){
        this(name,null);
    }

    public ConditionModel(String name,DefaultEdge conditionCell) {
        this.name = name;
        this.conditionCell = conditionCell;
        this.messageModels = new ArrayList<AbstractMessageModel>();
    }

    public void setConditionCell(DefaultEdge conditionCell) {
        this.conditionCell = conditionCell;
    }

    public DefaultEdge getConditionCell() {
        return conditionCell;
    }

    @Override
    public List<AbstractMessageModel> getMessages() {
        return messageModels;
    }

    public void addMessageModel(AbstractMessageModel message){
        this.messageModels.add(message);
    }

    public void setMessageModels(List<AbstractMessageModel> messageModels) {
        this.messageModels = messageModels;
    }




    /**
     * Returns the name of this condition
     *
     * @return the name of this condition
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + this.name + "]";
    }
}