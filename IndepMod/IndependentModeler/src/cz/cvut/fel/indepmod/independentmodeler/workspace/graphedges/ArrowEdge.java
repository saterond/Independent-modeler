package cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges;

import cz.cvut.fel.indepmod.independentmodeler.workspace.GraphObject;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.nodes.ArrowEdgeNode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class ArrowEdge extends DefaultEdge implements GraphObject,
        IndependentModelerEdge, Externalizable {

    private ArrowEdgeNode node;
    private JGraph graph;

    public ArrowEdge() {
        super();
        GraphConstants.setEndFill(getAttributes(), true);
        GraphConstants.setLineStyle(getAttributes(),
                GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setLabelAlongEdge(getAttributes(), false);
        GraphConstants.setEditable(getAttributes(), true);
        GraphConstants.setMoveable(getAttributes(), true);
        GraphConstants.setDisconnectable(getAttributes(), false);
        GraphConstants.setRouting(getAttributes(),
                GraphConstants.ROUTING_DEFAULT);
        GraphConstants.setBendable(getAttributes(), true);
        GraphConstants.setLineEnd(getAttributes(),
                GraphConstants.ARROW_SIMPLE);
        this.node = new ArrowEdgeNode(this);
    }

    @Override
    public Node getNavigatorNode() {
        return this.node;
    }

    @Override
    public Node getProjectNode() {
        return null;
    }

    public JGraph getGraph() {
        return graph;
    }

    @Override
    public void setGraph(JGraph graph) {
        this.graph = graph;
    }

    public Float getLineWidth() {
        return GraphConstants.getLineWidth(this.getAttributes());
    }

    public void setLineWidth(Float lineWidth) {
        this.getGraph().startEditingAtCell(this);
        GraphConstants.setLineWidth(this.getAttributes(), lineWidth);
        this.getGraph().stopEditing();
    }

    @Override
    public JPopupMenu getPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        if (this.getNavigatorNode() != null) {
            Action[] actions = this.getNavigatorNode().getActions(true);
            if (actions != null) {
                for (Action action : actions) {
                    menu.add(action);
                }
            }
        }
        if (this.getProjectNode() != null) {
            Action[] actions = this.getProjectNode().getActions(true);
            if (actions != null) {
                for (Action action : actions) {
                    menu.add(action);
                }
            }
        }
        return menu;
    }

    @Override
    public void setSourceNode(Node node) {
        this.node.setSourceNode(node);
    }

    @Override
    public void setTargetNode(Node node) {
        this.node.setTargetNode(node);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getAttributes());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        this.setAttributes((AttributeMap) in.readObject());
    }
}
