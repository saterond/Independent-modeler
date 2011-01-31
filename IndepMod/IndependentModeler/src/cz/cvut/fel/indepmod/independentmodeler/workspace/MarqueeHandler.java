package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import java.awt.Color;
import org.jgraph.graph.BasicMarqueeHandler;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.PortView;

public class MarqueeHandler extends BasicMarqueeHandler {

    private static final Logger LOG = Logger.getLogger(MarqueeHandler.class.
            getName());
    private final Graph graph;
    private final PaletteListener paletteListener;
    private final CellFactory cellFactory;
//    private final JPopupMenu popupMenu;
    private PortView actualPort;
    private Point2D actualPoint;
    private PortView startingPort;
    private Point2D startingPoint;

    public MarqueeHandler(final Graph _graph,
            final PaletteListener _paletteListener,
            final CellFactory _cellFactory,
            final JPopupMenu _popupMenu) {
        super();
        this.graph = _graph;
        this.paletteListener = _paletteListener;
        this.cellFactory = _cellFactory;
//        this.popupMenu = popupMenu;
        this.actualPort = null;
        this.actualPoint = null;
        this.startingPort = null;
        this.startingPoint = null;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && this.getPaletteListener().
                isElementSelected()) {
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
        this.resetPaletteTool(e);
    }

    private void handleLeftButtonPressed(final MouseEvent e) {
        LOG.fine("handleLeftButtonPressed");
        DefaultGraphCell[] cells = new DefaultGraphCell[1];
//        cells[0] = this.getCellFactory().getCell(this.getPaletteListener().
//                getSelectedToolEnum());
        cells[0] = this.getPaletteListener().getCell();
        if (cells[0] != null) {
            this.getGraph().createCell(e.getPoint(), cells);
        }
    }

    private void handleRightButtonPressed(final MouseEvent e) {
        LOG.fine("handleRightButtonPressed");
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
                    this.getGraph().addEdge(this.getStartingPort(), this.
                            getActualPort());
                }
            }
            this.setActualPort(null);
            this.setActualPoint(null);
            this.setStartingPort(null);
            this.setStartingPoint(null);
        } else {
            super.mouseReleased(mouseEvent);
        }
    }

    @Override
    public boolean isForceMarqueeEvent(final MouseEvent mouseEvent) {
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

    private void handleGraphPorts() {
        this.getGraph().setPortsVisible(this.getPaletteListener().
                isDependencySelected());
        this.getGraph().setJumpToDefaultPort(this.getPaletteListener().
                isDependencySelected());
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

    /**
     * @return the cellFactory
     */
    public CellFactory getCellFactory() {
        return cellFactory;
    }
}
