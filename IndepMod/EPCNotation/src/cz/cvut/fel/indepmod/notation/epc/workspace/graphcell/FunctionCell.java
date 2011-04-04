package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.epcnotation.api.Event;
import cz.cvut.fel.indepmod.epcnotation.api.Function;
import cz.cvut.fel.indepmod.epcnotation.api.Input;
import cz.cvut.fel.indepmod.epcnotation.api.OrganizationUnit;
import cz.cvut.fel.indepmod.epcnotation.api.Output;
import cz.cvut.fel.indepmod.epcnotation.api.SupportingSystem;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdge;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdge;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes.FunctionNode;
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
public class FunctionCell extends Cell implements Function {

    private FunctionNode node;
    private FunctionType type;
    private UUID uuid;

    public FunctionCell() {
        super();
        this.init();
    }

    public FunctionCell(Object o) {
        super(o);
        this.init();
    }

    private void init() {
        this.node = new FunctionNode(this);
        this.type = FunctionType.AND;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public VertexView getVertexView() {
        return new FunctionView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
        if (cell instanceof EventCell && edge instanceof ArrowEdge) {
            return true;
        } else if (cell instanceof OutputCell && edge instanceof ArrowEdge) {
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

    public FunctionType getType() {
        return type;
    }

    public void setType(FunctionType type) {
        FunctionType oldType = this.type;
        this.startEditing();
        this.type = type;
        this.stopEditing();
        this.node.propertyChange(new PropertyChangeEvent(this, "name", oldType.name(), this.type.name()));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.setType((FunctionType) in.readObject());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(this.getType());
    }

    @Override
    public String getFunction() {
        return this.type.name();
    }

    @Override
    public Event getInputEvent() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (sourceCell instanceof Event && targetCell == this) {
                    return (Event) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public Event getOutputEvent() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (targetCell instanceof Event && sourceCell == this) {
                    return (Event) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public Input getInput() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (sourceCell instanceof Input && targetCell == this) {
                    return (Input) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public Output getOutput() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (targetCell instanceof Output && sourceCell == this) {
                    return (Output) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public OrganizationUnit getOrganizationUnit() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof LineEdge) {
                LineEdge lineEdge = (LineEdge) edge;
                CellNode sourceNode = (CellNode) lineEdge.getSourceNode();
                CellNode targetNode = (CellNode) lineEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (sourceCell instanceof OrganizationUnit && targetCell == this) {
                    return (OrganizationUnit) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public SupportingSystem getSupportingSystem() {
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof LineEdge) {
                LineEdge lineEdge = (LineEdge) edge;
                CellNode sourceNode = (CellNode) lineEdge.getSourceNode();
                CellNode targetNode = (CellNode) lineEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (sourceCell instanceof SupportingSystem && targetCell == this) {
                    return (SupportingSystem) sourceCell;
                }
            }
        }
        return null;
    }

    @Override
    public String getTypeName() {
        return Function.TYPE;
    }

    @Override
    public String getId() {
        return this.uuid.toString();
    }
}
