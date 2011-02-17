package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.NoteNode;
import java.awt.event.MouseEvent;
import org.jgraph.graph.VertexView;

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
    public boolean canConnectTo(Cell cell) {
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
    }

    @Override
    public CellNode getNode() {
        return this.node;
    }
}
