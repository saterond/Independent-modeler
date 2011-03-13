package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.EventCell;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class EventNode extends CellNode {

    private transient EventCell cell;

    public EventNode(EventCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Event");
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.getDisplayName() + "<b>";
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }
}
