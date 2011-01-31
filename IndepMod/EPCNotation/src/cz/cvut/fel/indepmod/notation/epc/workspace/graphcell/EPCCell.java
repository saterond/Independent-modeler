package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;

/**
 *
 * @author Petr Vales
 */
abstract public class EPCCell extends Cell {

    public EPCCell() {
        this(null);
    }

    public EPCCell(Object o) {
        super(o);
    }

    abstract public boolean canBeEndCellOnEdgeWith(EventCell cell);

    abstract public boolean canBeEndCellOnEdgeWith(FunctionCell cell);

    abstract public boolean canBeEndCellOnEdgeWith(InputCell cell);

    abstract public boolean canBeEndCellOnEdgeWith(OutputCell cell);

    abstract public boolean canBeEndCellOnEdgeWith(OrganizationUnitCell cell);

    abstract public boolean canBeEndCellOnEdgeWith(SupportingSystemCell cell);

}
