package cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Petr Vales
 */
public class ArrowEdge extends DefaultEdge{

    public ArrowEdge() {
        super();
        GraphConstants.setEndFill(getAttributes(), true);
        GraphConstants.setLineStyle(getAttributes(),
                GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setLabelAlongEdge(getAttributes(), true);
        GraphConstants.setEditable(getAttributes(), true);
        GraphConstants.setMoveable(getAttributes(), true);
        GraphConstants.setDisconnectable(getAttributes(), false);
        GraphConstants.setRouting(getAttributes(),
                GraphConstants.ROUTING_SIMPLE);
        GraphConstants.setBendable(getAttributes(), true);
        GraphConstants.setLineEnd(getAttributes(),
                GraphConstants.ARROW_SIMPLE);
    }
}
