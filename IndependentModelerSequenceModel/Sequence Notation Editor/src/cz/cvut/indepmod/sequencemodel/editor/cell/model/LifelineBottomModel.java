/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class LifelineBottomModel extends TypeModel {

    private static int counter = 0;
    private DefaultGraphCell cell;

    public LifelineBottomModel() {
        this("LifelineBottom" + ++counter);
    }

    /**
     * Creates new ClassModel with no attributeModels and no methodModels
     *
     * @param name name of new class
     */
    public LifelineBottomModel(String name) {
        super(name);
        this.cell = null;
    }

    public LifelineBottomModel(LifelineBottomModel model) {
        super(model.toString());

        this.cell = model.cell;
    }

    public void setCell(DefaultGraphCell cell) {
        this.cell = cell; //TODO - shouldn't this throw an exception when the cell is already sat?
    }

    @Override
    public String toString() {
        return this.getTypeName();
    }

}

