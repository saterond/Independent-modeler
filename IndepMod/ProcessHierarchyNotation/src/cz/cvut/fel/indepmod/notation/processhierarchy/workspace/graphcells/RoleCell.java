package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class RoleCell extends Cell {

    public RoleCell() {
        this(null);
    }

    public RoleCell(Object o) {
        super(o);
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
}
