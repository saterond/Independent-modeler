package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.jgraph.graph.DefaultEdge;

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

    abstract public boolean canBeEndCellOnEdgeWith(EventCell cell, DefaultEdge edge);

    abstract public boolean canBeEndCellOnEdgeWith(FunctionCell cell, DefaultEdge edge);

    abstract public boolean canBeEndCellOnEdgeWith(InputCell cell, DefaultEdge edge);

    abstract public boolean canBeEndCellOnEdgeWith(OutputCell cell, DefaultEdge edge);

    abstract public boolean canBeEndCellOnEdgeWith(OrganizationUnitCell cell, DefaultEdge edge);

    abstract public boolean canBeEndCellOnEdgeWith(SupportingSystemCell cell, DefaultEdge edge);

}
