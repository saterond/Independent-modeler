package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes.DataNode;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class DataCell extends Cell /*implements Externalizable */{

    private DataNode node;

    public DataCell() {
        super();
        node = new DataNode(this);
    }

    public DataCell(Object o) {
        super(o);
        node = new DataNode(this);
    }

    @Override
    public VertexView getVertexView() {
        return new DataView(this);
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
