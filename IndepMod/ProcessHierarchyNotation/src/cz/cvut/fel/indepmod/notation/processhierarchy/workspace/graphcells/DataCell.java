package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdge;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Data;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Process;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.DataNode;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
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
public class DataCell extends Cell implements Data {

    private DataNode node;
    private String name;
    private UUID uuid;

    public DataCell() {
        super();
        this.init();
    }

    public DataCell(Object o) {
        super(o);
        this.init();
    }

    private void init() {
        node = new DataNode(this);
        this.name = "Data";
        uuid = UUID.randomUUID();
    }

    @Override
    public synchronized VertexView getVertexView() {
        return new DataView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
        if (cell instanceof ProcessCell && edge instanceof ArrowEdge) {
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

    public void setDataName(String name) {
        String oldName = this.name;
        this.startEditing();
        this.name = name;
        this.stopEditing();
        this.node.propertyChange(new PropertyChangeEvent(this, "name", oldName, this.name));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(this.getDataName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.setDataName((String) in.readObject());
    }

    //-------------------------API-------------------------------------
    @Override
    public String getDataName() {
        return this.name;
    }

    private List<Process> getProcesses(boolean inDate, boolean outDate) {
        List<Process> processList = new ArrayList<Process>();
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                if (sourceCell instanceof ProcessCell && outDate == true) {
                    processList.add((Process) sourceCell);
                }
                Cell targetCell = targetNode.getCell();
                if (targetCell instanceof ProcessCell && inDate == true) {
                    processList.add((Process) targetCell);
                }
            }

        }
        return processList;
    }

    @Override
    public List<Process> getAllProcesses() {
        return this.getProcesses(true, true);
    }

    @Override
    public Collection<Process> getAllProcessesForInputData() {
        return this.getProcesses(true, false);
    }

    @Override
    public Collection<Process> getAllProcessesForOutputData() {
        return this.getProcesses(false, true);
    }

    @Override
    public String getTypeName() {
        return Data.TYPE_NAME;
    }

    @Override
    public String getId() {
        return this.uuid.toString();
    }
}
