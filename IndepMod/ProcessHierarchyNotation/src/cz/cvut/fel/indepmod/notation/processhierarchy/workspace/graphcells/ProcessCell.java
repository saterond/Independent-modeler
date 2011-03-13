package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.epcnotationid.EPCNotationId;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.projectnodes.ProcessProjectNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.ProcessNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.NotationIdentifikator;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class ProcessCell extends Cell /*implements Externalizable */ {

    private ProcessNode navigatorNode;
//    private ProcessProjectNode projectNode;
    private GraphNode projectNode;

    public ProcessCell(Object o) {
        super(o);
        this.createNodes();
    }

    public ProcessCell() {
        super();
        this.createNodes();
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

    @Override
    public VertexView getVertexView() {
        return new ProcessView(this);
    }

    public boolean canConnectTo(ProcessCell processCell) {
        return true;
    }

    public boolean canConnectTo(RoleCell roleCell) {
        return false;
    }

    public boolean canConnectTo(DataCell dataCell) {
        return true;
    }

    @Override
    public boolean canConnectTo(Cell cell) {
        boolean ret = false;
        if (cell instanceof ProcessCell) {
            ret = true;
        } else if (cell instanceof DataCell) {
            ret = true;
        }
        return ret;
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
        this.projectNode.saveGraph();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.projectNode.setName((String) in.readObject());
        this.projectNode.setDisplayName((String) in.readObject());
        this.projectNode.setRootDir((File) in.readObject());
    }
}
