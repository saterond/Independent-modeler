package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexView;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
abstract public class Cell extends DefaultGraphCell implements MouseListener {

    private JGraph graph;

    public Cell(Object o) {
        super(o);
    }

    public Cell() {
    }

    abstract public VertexView getVertexView();

    abstract public boolean canConnectTo(Cell cell);

    abstract public Node getNode();

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void setGraph(JGraph _graph) {
        this.graph = _graph;
    }

    public JGraph getGraph() {
        return this.graph;
    }

    public void startEditing() {
        this.getGraph().startEditingAtCell(this);
    }

    public boolean stopEditing() {
        return this.getGraph().stopEditing();
    }

    public void cancleEditing() {
        this.getGraph().cancelEditing();
    }

    protected Rectangle getBounds() {
        return GraphConstants.getBounds(this.getAttributes()).getBounds();
    }

    protected void setBounds(Rectangle2D bounds) {
        this.startEditing();
        GraphConstants.setBounds(this.getAttributes(), bounds);
        this.stopEditing();
    }

    public Integer getHeight() {
        return this.getBounds().height;
    }

    public void setHeight(Integer _height) {
        Rectangle b = this.getBounds();
        this.setBounds(new Rectangle2D.Double(b.getX(), b.getY(), b.width,
                _height));
    }

    public Integer getWidth() {
        return this.getBounds().width;
    }

    public void setWidth(Integer width) {
        Rectangle2D b = GraphConstants.getBounds(this.getAttributes());
        this.setBounds(new Rectangle2D.Double(b.getX(), b.getY(), width, b.
                getBounds().height));
    }

    public Integer getX() {
        return this.getBounds().x;
    }

    public void setX(Integer _x) {
        Rectangle2D b = GraphConstants.getBounds(this.getAttributes());
        this.setBounds(new Rectangle2D.Double(_x, b.getY(), b.getBounds().width,
                b.getBounds().height));
    }

    public Integer getY() {
        return this.getBounds().y;
    }

    public void setY(Integer _y) {
        Rectangle2D b = GraphConstants.getBounds(this.getAttributes());
        this.setBounds(new Rectangle2D.Double(b.getX(), _y, b.getBounds().width,
                b.getBounds().height));
    }

    public Color getBackgroundColor() {
        return GraphConstants.getBackground(this.getAttributes());
    }

    public void setBackgroundColor(Color _color) {
        this.startEditing();
        GraphConstants.setBackground(this.getAttributes(), _color);
        this.stopEditing();
    }

    public Color getGradientColor() {
        return GraphConstants.getGradientColor(this.getAttributes());
    }

    public void setGradientColor(Color _color) {
        this.startEditing();
        GraphConstants.setGradientColor(this.getAttributes(), _color);
        this.stopEditing();
    }

    public Float getLineWidth() {
        return GraphConstants.getLineWidth(this.getAttributes());
    }

    public void setLineWidth(Float _width) {
        this.startEditing();
        GraphConstants.setLineWidth(this.getAttributes(), _width);
        this.stopEditing();
    }

    public Color getLineColor() {
        return GraphConstants.getBorderColor(this.getAttributes());
    }

    public void setLineColor(Color _color) {
        this.startEditing();
        GraphConstants.setBorderColor(this.getAttributes(), _color);
        this.stopEditing();
    }
}
