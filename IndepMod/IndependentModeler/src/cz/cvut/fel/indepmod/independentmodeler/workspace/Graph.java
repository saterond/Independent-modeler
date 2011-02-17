package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.IndependentModelerCellViewFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.GraphNode;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

/**
 *
 * @author Petr Vales
 */
public class Graph extends JGraph {

    private GraphNode node;

    public Graph(GraphModel gm, GraphLayoutCache glc) {
        super(gm, glc);
        this.getGraphLayoutCache().setFactory(
                new IndependentModelerCellViewFactory());
        this.node = new GraphNode(this);
    }

    public void createCell(Point point, DefaultGraphCell[] cells) {
        GraphConstants.setBounds(cells[0].getAttributes(), new Rectangle2D.Double(point.
                getX(), point.getY(), 200, 100));
        GraphConstants.setOpaque(cells[0].getAttributes(), true);
        DefaultPort port0 = new DefaultPort();
        cells[0].add(port0);
        this.getGraphLayoutCache().insert(cells);
        this.repaint();
        Cell cell = (Cell) cells[0];
        this.node.addChildren(cell.getNode());
        cell.setGraph(this);
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

//    public void addEdge(PortView beginPort, PortView endPort) {
//        DefaultEdge edge = new DefaultEdge();
//
//
//        GraphConstants.setEndFill(edge.getAttributes(), true);
//        GraphConstants.setLineStyle(edge.getAttributes(),
//                GraphConstants.STYLE_ORTHOGONAL);
//        GraphConstants.setLabelAlongEdge(edge.getAttributes(), true);
//        GraphConstants.setEditable(edge.getAttributes(), true);
//        GraphConstants.setMoveable(edge.getAttributes(), true);
//        GraphConstants.setDisconnectable(edge.getAttributes(), false);
//        GraphConstants.setRouting(edge.getAttributes(),
//                GraphConstants.ROUTING_SIMPLE);
//        GraphConstants.setBendable(edge.getAttributes(), true);
//        GraphConstants.setLineEnd(edge.getAttributes(),
//                GraphConstants.ARROW_SIMPLE);
//
//        edge.setSource(beginPort.getCell());
//        edge.setTarget(endPort.getCell());
//
//
//        this.getGraphLayoutCache().insert(edge);
//        this.repaint();
//    }

    public void addEdge(DefaultEdge edge) {
        this.getGraphLayoutCache().insert(edge);
        this.repaint();
    }

    public void setGraphNode(GraphNode _node) {
        this.node = _node;
    }

    public GraphNode getGraphNode() {
        return this.node;
    }

    public Boolean getGridVisibility() {
        return this.gridVisible;
    }

    public void setGridVisibility(Boolean _gridVisible) {
        this.setGridVisible(_gridVisible);
    }

}
