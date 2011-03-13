package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes.InputNode;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class InputCell extends EPCCell {

    private InputNode node;

    public InputCell() {
        super();
        this.node = new InputNode(this);
    }

    public InputCell(Object o) {
        super(o);
        this.node = new InputNode(this);
    }

    @Override
    public VertexView getVertexView() {
        return new InputView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell) {
        boolean ret = false;
        if (cell instanceof EPCCell) {
            ret = ((EPCCell) cell).canBeEndCellOnEdgeWith(this);
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

    @Override
    public CellNode getNavigatorNode() {
        return this.node;
    }

    @Override
    public Node getProjectNode() {
        return null;
    }
}
