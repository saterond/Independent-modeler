package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.DataCell;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class DataNode extends CellNode {

    private transient DataCell cell;

    public DataNode(DataCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Data");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.getDisplayName() + "<b>";
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    public void setCell(DataCell cell) {
        this.cell = cell;
    }
}
