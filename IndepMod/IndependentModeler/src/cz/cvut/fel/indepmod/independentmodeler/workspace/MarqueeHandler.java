package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.IndependentModelerEdge;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import java.awt.Color;
import java.beans.PropertyVetoException;
import org.jgraph.graph.BasicMarqueeHandler;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class MarqueeHandler extends BasicMarqueeHandler implements Serializable {

    private static final transient Logger LOG = Logger.getLogger(MarqueeHandler.class.
            getName());
    private transient final Graph graph;
    private final transient PaletteListener paletteListener;
//    private final JPopupMenu popupMenu;
    private transient PortView actualPort;
    private transient Point2D actualPoint;
    private transient PortView startingPort;
    private transient Point2D startingPoint;

    public MarqueeHandler() {
        super();
        this.graph = null;
        this.paletteListener = null;
    }

    public MarqueeHandler(final Graph _graph,
            final PaletteListener _paletteListener,
            final JPopupMenu _popupMenu) {
        super();
        this.graph = _graph;
        this.paletteListener = _paletteListener;
//        this.popupMenu = popupMenu;
        this.actualPort = null;
        this.actualPoint = null;
        this.startingPort = null;
        this.startingPoint = null;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && this.getPaletteListener().
                isCellSelected()) {
            this.handleLeftButtonPressed(e);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            this.handleRightButtonPressed(e);
        } else if (this.getActualPort() != null
                && this.getGraph().isPortsVisible()) {
            this.setStartingPort(this.getActualPort());
            this.setStartingPoint(this.getStartingPort().getLocation());
        } else {
            super.mousePressed(e);
        }

    }

    private void handleLeftButtonPressed(final MouseEvent e) {
        LOG.fine("handleLeftButtonPressed");
        Cell[] cells = new Cell[1];
        cells[0] = this.getPaletteListener().getCell();
        if (cells[0] != null) {
            GraphConstants.setBounds(cells[0].getAttributes(),
                    new Rectangle2D.Double(e.getX(), e.getY(), 200, 100));
            GraphConstants.setOpaque(cells[0].getAttributes(), true);
            this.getGraph().createCell(cells);
        }
        this.resetPaletteTool(e);
    }

    private void handleRightButtonPressed(final MouseEvent e) {
        LOG.fine("handleRightButtonPressed");
        Object firstCellForLocation =
                this.getGraph().getFirstCellForLocation(e.getX(), e.getY());
        JPopupMenu popupMenu = null;
        if (firstCellForLocation != null
                && firstCellForLocation instanceof GraphObject) {
            popupMenu =
                    ((GraphObject) firstCellForLocation).getPopupMenu();

        } else {
        }
        if (popupMenu != null) {
            popupMenu.show(graph, e.getX(), e.getY());
        }
        this.resetPaletteTool(e);
    }

    @Override
    public void mouseDragged(final MouseEvent mouseEvent) {
        if (this.getStartingPort() != null && this.getGraph().isPortsVisible()) {
            LOG.fine("mouseDraged");
            this.drawTempLine(Color.black, this.getGraph().getBackground());

            this.setActualPort(this.getGraph().getPortViewAt(mouseEvent.getPoint().
                    getX(), mouseEvent.getPoint().getY()));
            if (this.getActualPort() != null) {
                this.setActualPoint(this.getActualPort().getLocation());
            } else {
                this.setActualPoint(mouseEvent.getPoint());
            }
            this.drawTempLine(Color.black, this.getGraph().getBackground());
        } else {
            super.mouseDragged(mouseEvent);
        }
    }

    //TODO refactorovat
    @Override
    public void mouseReleased(final MouseEvent mouseEvent) {
        if (this.getStartingPort() != null && this.getGraph().isPortsVisible()) {
            LOG.fine("mouseReleased");
            this.drawTempLine(Color.black, this.getGraph().getBackground());
            if (this.getActualPort() != null && !this.getActualPort().equals(
                    this.getStartingPort())) {

                if (((Cell) this.getStartingPort().getParentView().getCell()).
                        canConnectTo((Cell) this.getActualPort().getParentView().
                        getCell())) {
                    DefaultEdge edge = this.getPaletteListener().getEdge();
                    edge.setSource(this.getStartingPort().getCell());
                    edge.setTarget(this.getActualPort().getCell());
                    if (edge instanceof IndependentModelerEdge) {
                        ((IndependentModelerEdge) edge).setSourceNode(
                                ((Cell) this.getStartingPort().getParentView().
                                getCell()).getNavigatorNode());
                        ((IndependentModelerEdge) edge).setTargetNode(
                                ((Cell) this.getActualPort().getParentView().
                                getCell()).getNavigatorNode());
                    }
                    this.getGraph().addEdge(edge);
                }
            }
            this.setActualPort(null);
            this.setActualPoint(null);
            this.setStartingPort(null);
            this.setStartingPoint(null);
        } else {
            super.mouseReleased(mouseEvent);
            Object[] objects = this.getGraph().getSelectionCells();
            if (objects != null && objects.length != 0) {
                try {
                    List<Node> nodes = new ArrayList<Node>();
                    for (Object object : objects) {
                        if (object instanceof Cell) {
                            Cell cell =
                                    (Cell) object;
                            nodes.add(cell.getNavigatorNode());
                        }
                    }
                    Navigator.findInstance().
                            setSelectedNodes(nodes.toArray(new Node[0]));
                } catch (PropertyVetoException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        this.resetPaletteTool(mouseEvent);
    }
    //TODO calling selectNodeNavigator should be called elsewhere?

    @Override
    public boolean isForceMarqueeEvent(final MouseEvent mouseEvent) {
//        this.bobbleEventToCell(mouseEvent);
        this.selectNodeInNavigator(mouseEvent);
        this.handleGraphPorts();
        if (SwingUtilities.isRightMouseButton(mouseEvent) && !mouseEvent.
                isShiftDown()) {
            return true;
        }
        this.setActualPort(this.getGraph().getPortViewAt(
                mouseEvent.getPoint().getX(), mouseEvent.getPoint().getY()));
        return (this.getActualPort() != null && this.graph.isPortsVisible()) || super.
                isForceMarqueeEvent(mouseEvent);
    }

    private boolean bobbleEventToCell(final MouseEvent e) {
        Cell cell = (Cell) this.getGraph().getFirstCellForLocation(e.getX(), e.
                getY());
        if (cell != null) {
//            cell.mouseClicked(e);
            return true;
        } else {
            return false;
        }
    }

    //TODO refactor
    //TODO select edge
    private void selectNodeInNavigator(final MouseEvent e) {
        Object clickedObject =
                this.getGraph().getFirstCellForLocation(e.getX(), e.getY());
        try {
            if (clickedObject != null) {
                if (clickedObject instanceof GraphObject) {
                    GraphObject graphObject = ((GraphObject) clickedObject);
                    Node[] node = {graphObject.getNavigatorNode()};
                    Navigator.findInstance().setSelectedNodes(node);
                }
            } else {
                Node[] node = {this.getGraph().getNavigatorNode()};
                Navigator.findInstance().setSelectedNodes(node);
            }
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }

    private void handleGraphPorts() {
        this.getGraph().setPortsVisible(this.getPaletteListener().
                isEdgeSelected());
        this.getGraph().setJumpToDefaultPort(this.getPaletteListener().
                isEdgeSelected());
    }

//    public boolean addAction() {
//        return true;
////        IndependentModelerPaletteNodeModel tool =
////                    this.paletteListener.getSelectedTool();
//////        try {
////////            tool = this.selectedToolModel.getSelectedTool();
//////        } catch (ClassCastException ex) {
//////            return false;
//////        }
////        //TODO predelat
////        if (tool == IndependentModelerPaletteNodeModel.Note) {
////            return true;
////        } else {
////            return false;
////        }
////
////        switch (tool) {
////            case TOOL_ADD_CLASS:
////                return true;
////            default:
////        return false;
////        }
//    }
    private void drawTempLine(final Color newColor, final Color oldColor) {
        graph.drawLine(newColor, oldColor, this.getStartingPoint(), this.
                getActualPoint());
    }

    private void resetPaletteTool(final MouseEvent e) {
        if (!e.isShiftDown()) {
            this.getPaletteListener().resetPaletteTool();
        }
    }

    /**
     * @return PaletteListener
     */
    public PaletteListener getPaletteListener() {
        return paletteListener;
    }

    /**
     *
     * @return Graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return the actualPort
     */
    public PortView getActualPort() {
        return actualPort;
    }

    /**
     * @param actualPort the actualPort to set
     */
    private void setActualPort(PortView actualPort) {
        this.actualPort = actualPort;
    }

    /**
     * @return the actualPoint
     */
    public Point2D getActualPoint() {
        return actualPoint;
    }

    /**
     * @param actualPoint the actualPoint to set
     */
    private void setActualPoint(Point2D actualPoint) {
        this.actualPoint = actualPoint;
    }

    /**
     * @return the startingPort
     */
    public PortView getStartingPort() {
        return startingPort;
    }

    /**
     * @param startingPort the startingPort to set
     */
    private void setStartingPort(PortView startingPort) {
        this.startingPort = startingPort;
    }

    /**
     * @return the startingPoint
     */
    public Point2D getStartingPoint() {
        return startingPoint;
    }

    /**
     * @param startingPoint the startingPoint to set
     */
    private void setStartingPoint(Point2D startingPoint) {
        this.startingPoint = startingPoint;
    }
}
