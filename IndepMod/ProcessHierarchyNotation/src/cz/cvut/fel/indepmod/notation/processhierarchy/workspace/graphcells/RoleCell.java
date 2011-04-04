package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdge;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Process;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Role;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.RoleNode;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
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
public class RoleCell extends Cell implements Role /* implements Externalizable */ {

    private RoleNode node;
    private String name;
    private UUID uuid;

    public RoleCell() {
        super();
        this.init();
    }

    public RoleCell(Object o) {
        super(o);
        this.init();
    }

    private void init() {
        this.node = new RoleNode(this);
        this.name = "Role";
        uuid = UUID.randomUUID();
    }

    @Override
    public VertexView getVertexView() {
        return new RoleView(this);
    }

    public boolean canConnectTo(ProcessCell processCell) {
        return true;
    }

    public boolean canConnectTo(RoleCell roleCell) {
        return false;
    }

    public boolean canConnectTo(DataCell dataCell) {
        return false;
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
        if (cell instanceof ProcessCell && edge instanceof LineEdge) {
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

    @Override
    public String getRoleName() {
        return this.name;
    }

    public void setRoleName(String name) {
        String oldName = this.name;
        this.startEditing();
        this.name = name;
        this.stopEditing();
        this.node.propertyChange(new PropertyChangeEvent(this, "name", oldName, this.name));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(this.getRoleName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.setRoleName((String) in.readObject());
    }

    @Override
    public List<Process> getAllProcesses() {
        List<Process> processList = new ArrayList<Process>();
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof LineEdge) {
                LineEdge arrowEdge = (LineEdge) edge;
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell targetCell = targetNode.getCell();
                if (targetCell instanceof ProcessCell) {
                    processList.add((Process) targetCell);
                }
            }
        }
        return processList;
    }

    @Override
    public String getTypeName() {
        return Role.TYPE_NAME;
    }

    @Override
    public String getId() {
        return this.uuid.toString();
    }
}
