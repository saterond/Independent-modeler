package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.epcnotation.api.Function;
import cz.cvut.fel.indepmod.epcnotation.api.Output;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdge;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes.OutputNode;
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
public class OutputCell extends Cell implements Output {

    private OutputNode node;
    private String title;
    private UUID uuid;

    public OutputCell() {
        super();
        this.init();
    }

    public OutputCell(Object o) {
        super(o);
        this.init();
    }

    private void init() {
        this.node = new OutputNode(this);
        this.title = "Output";
        this.uuid = UUID.randomUUID();
    }

    @Override
    public VertexView getVertexView() {
        return new OutputView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
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
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (sourceCell instanceof Function && targetCell == this) {
                    return (Function) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public String getTypeName() {
        return Output.TYPE;
    }

    @Override
    public String getId() {
        return this.uuid.toString();
    }
}
