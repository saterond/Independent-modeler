package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.RoleNode;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class RoleCell extends Cell {

    private RoleNode node;

    public RoleCell() {
        this(null);
    }

    public RoleCell(Object o) {
        super(o);
        this.node = new RoleNode(this);
    }

    @Override
    public VertexView getVertexView() {
        return new RoleView(this);
    }

    public boolean canConnectTo(ProcessCell processCell) {
        return true;
    }

    public boolean canConnectTo(RoleCell roleCell) {
        return false;
    }

    public boolean canConnectTo(DataCell dataCell) {
        return false;
    }

    @Override
    public boolean canConnectTo(Cell cell) {
        boolean ret = false;
        if (cell instanceof ProcessCell) {
            ret = true;
        }
        return ret;
    }

    @Override
    public CellNode getNode() {
        return this.node;
    }
}
