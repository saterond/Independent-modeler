/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.IFragment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class FragmentModel extends AbstractModel implements IFragment{

    private static int counter = 0;
    private DefaultGraphCell cell;
    private String name;
    private List<ConditionModel> conditionModels;
    private List<AbstractMessageModel> messageModels;

    public FragmentModel() {
        this("Fragment" + ++counter);
    }

    public FragmentModel(String name) {
        this.name = name;
        this.conditionModels = new ArrayList<ConditionModel>();
        this.messageModels = new ArrayList<AbstractMessageModel>();
    }
    
    public FragmentModel(FragmentModel model) {
        this.name = model.toString();
        this.cell = model.cell;
    }

    /**
     * Creates new FragmentModel with conditionModels
     *
     */
    public FragmentModel(String name, List<ConditionModel> conditionModels) {
        this.name = name;

        if (conditionModels != null) {
            this.conditionModels = new ArrayList<ConditionModel>(conditionModels);
        } else {
            this.conditionModels = new ArrayList<ConditionModel>();
        }
        this.cell = null;
    }

    @Override
    public List<AbstractMessageModel> getMessages() {
        return messageModels;
    }

    public void setMessageModels(List<AbstractMessageModel> messageModels) {
        this.messageModels = messageModels;
    }

    public void setCell(DefaultGraphCell cell) {
        this.cell = cell; //TODO - shouldn't this throw an exception when the cell is already sat?
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<ConditionModel> getConditionModels() {
        return new ArrayList<ConditionModel>(this.conditionModels);
    }

    public void setConditionModels(List<ConditionModel> conditionModels) {
        this.conditionModels = conditionModels;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
