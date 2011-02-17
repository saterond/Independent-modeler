package cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Petr Vales
 */
public class ArrowEdgeFactory implements IEdgeFactory {

    @Override
    public DefaultEdge createEdge() {
        DefaultEdge edge = new DefaultEdge();
        GraphConstants.setEndFill(edge.getAttributes(), true);
        GraphConstants.setLineStyle(edge.getAttributes(),
                GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setLabelAlongEdge(edge.getAttributes(), true);
        GraphConstants.setEditable(edge.getAttributes(), true);
        GraphConstants.setMoveable(edge.getAttributes(), true);
        GraphConstants.setDisconnectable(edge.getAttributes(), false);
        GraphConstants.setRouting(edge.getAttributes(),
                GraphConstants.ROUTING_SIMPLE);
        GraphConstants.setBendable(edge.getAttributes(), true);
        GraphConstants.setLineEnd(edge.getAttributes(),
                GraphConstants.ARROW_SIMPLE);
        return edge;
    }
}
