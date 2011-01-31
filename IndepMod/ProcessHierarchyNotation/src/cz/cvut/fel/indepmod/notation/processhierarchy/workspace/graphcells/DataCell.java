package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class DataCell extends Cell {

    public DataCell() {
        this(null);
    }

    public DataCell(Object o) {
        super(o);
    }

    @Override
    public VertexView getVertexView() {
        return new DataView(this);
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
