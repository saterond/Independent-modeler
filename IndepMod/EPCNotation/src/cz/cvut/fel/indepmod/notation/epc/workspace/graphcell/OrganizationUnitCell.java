package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class OrganizationUnitCell extends EPCCell {

    public OrganizationUnitCell() {
        this(null);
    }

    public OrganizationUnitCell(Object o) {
        super(o);
    }

    @Override
    public VertexView getVertexView() {
        return new OrganizationUnitView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell) {
        boolean ret = false;
        if (cell instanceof EPCCell) {
            ret = ((EPCCell)cell).canBeEndCellOnEdgeWith(this);
        }
        return ret;
    }

    @Override
    public boolean canBeEndCellOnEdgeWith(EventCell cell) {
        return false;
    }

    @Override
    public boolean canBeEndCellOnEdgeWith(FunctionCell cell) {
        return false;
    }

    @Override
    public boolean canBeEndCellOnEdgeWith(InputCell cell) {
        return false;
    }

    @Override
    public boolean canBeEndCellOnEdgeWith(OutputCell cell) {
        return false;
    }

    @Override
    public boolean canBeEndCellOnEdgeWith(OrganizationUnitCell cell) {
        return false;
    }

    @Override
    public boolean canBeEndCellOnEdgeWith(SupportingSystemCell cell) {
        return false;
    }
}
