package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.RoleNode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class RoleCell extends Cell /* implements Externalizable */{

    private RoleNode node;

    public RoleCell() {
        this(null);
    }

    public RoleCell(Object o) {
        super(o);
        this.node = new RoleNode(this);
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
    public boolean canConnectTo(Cell cell) {
        boolean ret = false;
        if (cell instanceof ProcessCell) {
            ret = true;
        }
        return ret;
    }

    @Override
    public CellNode getNavigatorNode() {
        return this.node;
    }

    @Override
    public Node getProjectNode() {
        return null;
    }

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeObject(this.getAttributes());
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        this.setAttributes((AttributeMap) in.readObject());
//    }
}
