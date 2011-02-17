package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.InputCell;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class InputNode extends CellNode {

    private InputCell cell;

    public InputNode(InputCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Input");
    }

    @Override
    public Action[] getActions(boolean context) {
        return null;
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.getDisplayName() + "</b>";
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }
}
