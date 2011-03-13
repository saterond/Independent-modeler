package cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges;

import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public interface IndependentModelerEdge {
    public void setSourceNode(Node node);
    public void setTargetNode(Node node);
}
