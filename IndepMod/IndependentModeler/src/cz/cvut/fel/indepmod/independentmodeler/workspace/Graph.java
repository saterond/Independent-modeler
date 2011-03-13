package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.IndependentModelerCellViewFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.NavigatorGraphNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import java.awt.Color;
import java.awt.Graphics2D;
import org.jgraph.JGraph;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class Graph extends JGraph implements Serializable {

    private NavigatorGraphNode navigatorNode;
    private transient GraphNode projectNode;
    private static final long serialVersionUID = 769541961;

    public Graph() {
        super();
    }

    public Graph(GraphModel gm, GraphLayoutCache glc) {
        super(gm, glc);
        this.getGraphLayoutCache().setFactory(
                new IndependentModelerCellViewFactory());
        this.navigatorNode = new NavigatorGraphNode(this);
    }

    public void createCell(Cell[] cells) {
        DefaultPort port0 = new DefaultPort();
        cells[0].setPort(port0);
        this.getGraphLayoutCache().insert(cells);
        this.repaint();
        Cell cell = cells[0];
        if (this.navigatorNode != null) {
            this.navigatorNode.addChildren(cell.getNavigatorNode());
            cell.setGraph(this);
        }
        if (this.projectNode != null) {
            Node cellProjectNode = cell.getProjectNode();
            if (cellProjectNode != null) {
                if (cellProjectNode instanceof GraphNode) {
                        this.projectNode.addChildren((GraphNode) cellProjectNode);
                }
//                Node[] nodes = {cellProjectNode};
//                this.projectNode.getChildren().add(nodes);
            }
        }
    }

    public void drawLine(Color newColor, Color oldColor, Point2D startingPoint,
            Point2D actualPoint) {
        if (startingPoint != null && actualPoint != null) {
            Graphics2D g = (Graphics2D) this.getGraphics();
            g.setColor(newColor);
            g.setXORMode(oldColor);
            g.drawLine(
                    (int) startingPoint.getX(),
                    (int) startingPoint.getY(),
                    (int) actualPoint.getX(),
                    (int) actualPoint.getY());
        }
    }

    public void addEdge(DefaultEdge edge) {
        this.getGraphLayoutCache().insert(edge);
        this.repaint();
        if (edge instanceof GraphObject && this.navigatorNode != null) {
            Node edgeNode = ((GraphObject) edge).getNavigatorNode();
            this.navigatorNode.addChildren(edgeNode);
            ((GraphObject) edge).setGraph(this);
        }
    }

    public void setNavigatorNode(NavigatorGraphNode _node) {
        this.navigatorNode = _node;
    }

    public NavigatorGraphNode getNavigatorNode() {
        return this.navigatorNode;
    }

    public Boolean getGridVisibility() {
        return this.gridVisible;
    }

    public void setGridVisibility(Boolean _gridVisible) {
        this.setGridVisible(_gridVisible);
    }

    public Node getProjectNode() {
        return projectNode;
    }

    public void setProjectNode(GraphNode projectNode) {
        this.projectNode = projectNode;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
