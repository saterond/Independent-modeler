package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.SupportingSystemCell;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class SupportingSystemNode extends CellNode {

    private transient SupportingSystemCell cell;

    public SupportingSystemNode(SupportingSystemCell cell) {
        super(Children.LEAF);
        this.cell = cell;
        this.setDisplayName("Supporting System");
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    @Override
    public Action[] getActions(boolean context) {
        return null;
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.getDisplayName() + "<b>";
    }
}
