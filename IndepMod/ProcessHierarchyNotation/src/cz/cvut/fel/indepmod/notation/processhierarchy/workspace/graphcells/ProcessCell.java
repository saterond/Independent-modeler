package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class ProcessCell extends Cell {

    public ProcessCell(Object o) {
        super(o);
    }

    public ProcessCell() {
        this(null);
    }

    @Override
    public VertexView getVertexView() {
        return new ProcessView(this);
    }

    public boolean canConnectTo(ProcessCell processCell) {
        return true;
    }

    public boolean canConnectTo(RoleCell roleCell) {
        return false;
    }

    public boolean canConnectTo(DataCell dataCell) {
        return true;
    }

    @Override
    public boolean canConnectTo(Cell cell) {
        boolean ret = false;
        if (cell instanceof ProcessCell) {
            ret = true;
        } else if (cell instanceof DataCell) {
            ret = true;
        }
        return ret;
    }
}
