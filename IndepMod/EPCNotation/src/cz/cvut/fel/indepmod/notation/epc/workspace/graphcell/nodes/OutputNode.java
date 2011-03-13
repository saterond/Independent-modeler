package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OutputCell;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class OutputNode extends CellNode {

    private transient OutputCell cell;

    public OutputNode(OutputCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Output");
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
        return "<b>" + this.getDisplayName() + "</b>";
    }
}
