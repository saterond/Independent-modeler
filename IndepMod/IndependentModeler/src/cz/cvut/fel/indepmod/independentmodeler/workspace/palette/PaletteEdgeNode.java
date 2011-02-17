package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.IEdgeFactory;
import org.jgraph.graph.DefaultEdge;

/**
 *
 * @author Petr Vales
 */
public class PaletteEdgeNode implements IPaletteNode {

    private String name;
    private IEdgeFactory edgeFactory;

    public PaletteEdgeNode(String _name, IEdgeFactory _edgeFactory) {
        this.name = _name;
        this.edgeFactory = _edgeFactory;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public DefaultEdge getEdge() {
        return this.getEdgeFactory().createEdge();
    }

    public IEdgeFactory getEdgeFactory() {
        return edgeFactory;
    }

}
