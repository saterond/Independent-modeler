package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.NoteNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdge;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class NoteCell extends Cell {

    private NoteNode node;

    public NoteCell() {
        this(null);
    }

    public NoteCell(Object userObject) {
        super(userObject);
        node = new NoteNode(this);
    }

    @Override
    public VertexView getVertexView() {
        return new NoteView(this);
    }

    @Override
    public boolean canConnectTo(Cell cell, DefaultEdge edge) {
        if(edge instanceof LineEdge) {
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
}
