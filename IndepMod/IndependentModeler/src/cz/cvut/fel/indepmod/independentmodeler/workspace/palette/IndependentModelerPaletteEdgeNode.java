package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import org.jgraph.graph.DefaultEdge;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class IndependentModelerPaletteEdgeNode extends AbstractNode {
    
    private PaletteEdgeNode paletteNode;

    public IndependentModelerPaletteEdgeNode(Children children, Lookup lookup,
            PaletteEdgeNode _paletteNode) {
        super(children, lookup);
        this.paletteNode = _paletteNode;
    }

    public IndependentModelerPaletteEdgeNode(Children children) {
        super(children);
    }

    public DefaultEdge getEdge() {
        return this.getPaletteNode().getEdge();
    }

    public PaletteEdgeNode getPaletteNode() {
        return this.paletteNode;
    }

    public void setPaletteNode(PaletteEdgeNode _paletteNode) {
        this.paletteNode = _paletteNode;
    }
}
