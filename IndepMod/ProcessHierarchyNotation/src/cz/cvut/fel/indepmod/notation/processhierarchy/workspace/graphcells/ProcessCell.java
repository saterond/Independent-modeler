package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.epcnotation.id.EPCNotationId;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdge;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdge;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Data;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Process;
import cz.cvut.fel.indepmod.processhierarchynotation.api.Role;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.ProcessNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.NotationIdentifikator;
import java.beans.PropertyChangeEvent;
import java.io.File;
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
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class ProcessCell extends Cell implements Process {

    private static int counter = 0;
    private ProcessNode navigatorNode;
    private GraphNode projectNode;
    private String id;
    private String name;
    private String info;
    private UUID uuid;

    public ProcessCell(Object o) {
        super(o);
        this.init();
    }

    public ProcessCell() {
        super();
        this.init();
    }

    private void init() {
        counter++;
        this.createNodes();
        this.id = String.valueOf(counter);
        this.name = "Process";
        uuid = UUID.randomUUID();
    }

    private void createNodes() {
        this.navigatorNode = new ProcessNode(this);
//        this.projectNode = new ProcessProjectNode();
        for (NotationIdentifikator notation : Lookup.getDefault().lookupAll(
                NotationIdentifikator.class)) {
            if (notation.getName().equals(EPCNotationId.NAME)) {

                this.projectNode = notation.getGraphNode();
                this.projectNode.setName("Process");
                this.projectNode.setDisplayName("Process");


            }
        }
    }

    //nestaci mit instancni promenou, pak se nic nevykreslovalo,
    //nevim jak s tim JGraph zachazi, ze se tak deje
    @Override
    public synchronized VertexView getVertexView() {
        return new ProcessView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
        if (cell instanceof ProcessCell && edge instanceof ArrowEdge) {
            return true;
        } else if (cell instanceof DataCell && edge instanceof ArrowEdge) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CellNode getNavigatorNode() {
        return this.navigatorNode;
    }

    @Override
    public Node getProjectNode() {
        return this.projectNode;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(this.projectNode.getName());
        out.writeObject(this.projectNode.getDisplayName());
        out.writeObject(this.projectNode.getRootDir());
        out.writeObject(this.getProcessId());
        out.writeObject(this.getName());
        out.writeObject(this.getInfo());
        this.projectNode.saveGraph();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.projectNode.setName((String) in.readObject());
        this.projectNode.setDisplayName((String) in.readObject());
        this.projectNode.setRootDir((File) in.readObject());
        this.setProcessId((String) in.readObject());
        this.setName((String) in.readObject());
        this.setInfo((String) in.readObject());
    }

    @Override
    public String getProcessId() {
        return id;
    }

    public void setProcessId(String id) {
        String oldId = this.id;
        this.startEditing();
        this.id = id;
        this.stopEditing();
        this.navigatorNode.propertyChange(new PropertyChangeEvent(this, "name", oldId, this.id));
    }

    @Override
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.startEditing();
        this.info = info;
        this.stopEditing();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.startEditing();
        this.name = name;
        this.stopEditing();
        this.navigatorNode.propertyChange(new PropertyChangeEvent(this, "name", oldName, this.name));
        this.projectNode.setDisplayName(this.name);
        this.projectNode.propertyChange(new PropertyChangeEvent(this, "name", oldName, this.name));
    }

    @Override
    public Collection<Process> getAllPreviousProcesses() {
        return this.getAllProcesses(false, true);
    }

    @Override
    public Collection<Process> getAllFollowingProcesses() {
        return this.getAllProcesses(true, false);
    }

    @Override
    public Collection<Process> getAllProcesses() {
        return this.getAllProcesses(true, true);
    }

    private List<Process> getAllProcesses(boolean following, boolean previous) {
        List<Process> processList = new ArrayList<Process>();
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell sourceCell = sourceNode.getCell();
                Cell targetCell = targetNode.getCell();
                if (following == true && sourceCell instanceof ProcessCell && targetCell == this) {
                    processList.add((Process) sourceCell);
                }
                if (previous == true && targetCell instanceof ProcessCell && sourceCell == this) {
                    processList.add((Process) targetCell);
                }
            }
        }
        return processList;
    }

    @Override
    public Collection<Data> getAllData() {
        return this.getAllData(true, true);
    }

    @Override
    public Collection<Data> getAllInputData() {
        return this.getAllData(true, false);
    }

    @Override
    public Collection<Data> getAllOutputData() {
        return this.getAllData(false, true);
    }

    private List<Data> getAllData(boolean input, boolean output) {
        List<Data> dataList = new ArrayList<Data>();
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof ArrowEdge) {
                ArrowEdge arrowEdge = (ArrowEdge) edge;
                CellNode targetNode = (CellNode) arrowEdge.getTargetNode();
                Cell targetCell = targetNode.getCell();
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                Cell sourceCell = sourceNode.getCell();
                if (output == true && targetCell instanceof DataCell) {
                    dataList.add((Data) targetCell);
                }
                if (input == true && sourceCell instanceof DataCell) {
                    dataList.add((Data) sourceCell);
                }
            }
        }
        return dataList;
    }

    @Override
    public Collection<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<Role>();
        List<Edge> allEdges = this.getAllEdges();
        for (Edge edge : allEdges) {
            if (edge instanceof LineEdge) {
                LineEdge arrowEdge = (LineEdge) edge;
                CellNode sourceNode = (CellNode) arrowEdge.getSourceNode();
                Cell sourceCell = sourceNode.getCell();
                if (sourceCell instanceof RoleCell) {
                    roleList.add((Role) sourceCell);
                }
            }
        }
        return roleList;
    }

    @Override
    public String getTypeName() {
        return Process.TYPE_NAME;
    }

    @Override
    public String getId() {
        return this.uuid.toString();
    }
}
