package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.epcnotation.api.Function;
import cz.cvut.fel.indepmod.epcnotation.api.OrganizationUnit;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdge;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes.OrganizationUnitNode;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.UUID;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.Edge;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class OrganizationUnitCell extends Cell implements OrganizationUnit {

    private OrganizationUnitNode node;
    private String title;
    private UUID uuid;

    public OrganizationUnitCell() {
        super();
        this.init();
    }

    public OrganizationUnitCell(Object o) {
        super(o);
        this.init();
    }

    private void init() {
        this.node = new OrganizationUnitNode(this);
        this.title = "Organization unit";
        this.uuid = UUID.randomUUID();
    }

    @Override
    public VertexView getVertexView() {
        return new OrganizationUnitView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
        if (cell instanceof FunctionCell && edge instanceof LineEdge) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CellNode getNavigatorNode() {
        return this.node;
    }

    @Override
    public Node getProjectNode() {
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldTitle = this.title;
        this.startEditing();
        this.title = title;
        this.stopEditing();
        this.node.propertyChange(new PropertyChangeEvent(this, "name", oldTitle, this.title));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.setTitle((String) in.readObject());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(this.getTitle());
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public Function getFunction() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof LineEdge) {
                LineEdge lineEdge = (LineEdge) edge;
                CellNode sourceNode = (CellNode) lineEdge.getSourceNode();
                CellNode targetNode = (CellNode) lineEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (targetCell instanceof Function && sourceCell == this) {
                    return (Function) targetCell;
                }
            }
        }
        return null;
    }

    @Override
    public String getTypeName() {
        return OrganizationUnit.TYPE;
    }

    @Override
    public String getId() {
        return this.uuid.toString();
    }
}
