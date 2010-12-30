package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import java.awt.Color;
import org.jgraph.graph.BasicMarqueeHandler;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.logging.Logger;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
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

    public MarqueeHandler(Graph graph,
            PaletteListener paletteListener,
            CellFactory cellFactory,
            JPopupMenu popupMenu) {
        super();
        this.graph = graph;
        this.paletteListener = paletteListener;
        this.cellFactory = cellFactory;
//        this.popupMenu = popupMenu;
//
        this.actualPort = null;
        this.actualPoint = null;
        this.startingPort = null;
        this.startingPoint = null;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && this.paletteListener.
                isElementSelected()) {
            this.handleButton1Pressed(e);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("right button");
        } else if (this.actualPort != null && this.graph.isPortsVisible()) {
            this.startingPort = this.actualPort;
            this.startingPoint = this.startingPort.getLocation();
        } else {
            super.mousePressed(e);
        }
        this.resetPaletteTool(e);
//        if (SwingUtilities.isRightMouseButton(e)) {
//            this.popupMenu.show(this.graph, e.getX(), e.getY());
//        } else if (this.addAction()) {
////            this.graph.insertCell(e.getPoint());
//        } else if (this.actualPort != null && this.graph.isPortsVisible()) {
//            this.startingPort = this.actualPort;
//            this.startingPoint = this.startingPort.getLocation();
//        } else {
//            super.mousePressed(e);
//        }
    }

    private void handleButton1Pressed(final MouseEvent e) {
        DefaultGraphCell[] cells = new DefaultGraphCell[1];
        cells[0] = this.cellFactory.getCell(
                this.paletteListener.getSelectedTool());
        if (cells[0] != null) {
            this.getGraph().createCell(e.getPoint(), cells);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.startingPort != null && this.graph.isPortsVisible()) {
            LOG.fine("mouseDraged");
            this.printTempLine(Color.black, this.graph.getBackground());

            this.actualPort = this.graph.getPortViewAt(mouseEvent.getPoint().
                    getX(), mouseEvent.getPoint().getY());
            this.actualPoint = this.actualPort != null ? this.actualPort.
                    getLocation() : mouseEvent.getPoint();

            this.printTempLine(Color.black, this.graph.getBackground());
        } else {
            super.mouseDragged(mouseEvent);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.startingPort != null && this.graph.isPortsVisible()) {
            this.printTempLine(Color.black, this.graph.getBackground());

            if (this.actualPort != null && !this.actualPort.equals(
                    this.startingPort)) {
                DefaultEdge edge = new DefaultEdge(); //TODO move this into ClassModelGraph and improve it!
                edge.setSource(this.startingPort.getCell());
                edge.setTarget(this.actualPort.getCell());

                GraphConstants.setEndFill(edge.getAttributes(), true);
                GraphConstants.setLineStyle(edge.getAttributes(),
                        GraphConstants.STYLE_ORTHOGONAL);
                GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
                GraphConstants.setEditable(edge.getAttributes(), true);
                GraphConstants.setMoveable(edge.getAttributes(), true);
                GraphConstants.setDisconnectable(edge.getAttributes(), false);

                this.graph.getGraphLayoutCache().insert(edge);
            }

            this.actualPort = null;
            this.actualPoint = null;
            this.startingPort = null;
            this.startingPoint = null;
//            this.selectedToolModel.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROLL);
//            this.resetPaletteTool(mouseEvent);
        } else {
            super.mouseReleased(mouseEvent);
        }
    }

    @Override
    public boolean isForceMarqueeEvent(MouseEvent mouseEvent) {
        boolean dependency = this.paletteListener.isDependencySelected();
        this.graph.setPortsVisible(dependency);
        this.graph.setJumpToDefaultPort(dependency);

//        return super.isMarqueeTriggerEvent(mouseEvent, graph);
        if (SwingUtilities.isRightMouseButton(mouseEvent) && !mouseEvent.
                isShiftDown()) {
            return true;
        }
        this.actualPort = this.graph.getPortViewAt(mouseEvent.getPoint().getX(), mouseEvent.
                getPoint().getY());
        return (this.actualPort != null && this.graph.isPortsVisible()) || super.
                isForceMarqueeEvent(mouseEvent);
    }

    public boolean addAction() {
        return true;
//        IndependentModelerPaletteNodeModel tool =
//                    this.paletteListener.getSelectedTool();
////        try {
//////            tool = this.selectedToolModel.getSelectedTool();
////        } catch (ClassCastException ex) {
////            return false;
////        }
//        //TODO predelat
//        if (tool == IndependentModelerPaletteNodeModel.Note) {
//            return true;
//        } else {
//            return false;
//        }
//
//        switch (tool) {
//            case TOOL_ADD_CLASS:
//                return true;
//            default:
//        return false;
//        }
    }

    //TODO - newColor and oldColor are the same in all calls
    /**
     * This method prints temporary line when user want add an connection between vertices. Line is printed in XOR mode,
     * so if newColor pixel is printed on the pixel with the same color (newColor color), the oldColor pixel is
     * printed (like XOR operation)
     *
     * @param newColor color which will be used if repainted pixel has another color
     * @param oldColor color which will be used if repainted pixel has equal color to the newColor
     */
    private void printTempLine(Color newColor, Color oldColor) {
        graph.drawLine(newColor, oldColor, this.startingPoint, this.actualPoint);
//        if (this.startingPoint != null && this.actualPoint != null) {
//            Graphics2D g = (Graphics2D) this.graph.getGraphics();
//            g.setColor(newColor);
//            g.setXORMode(oldColor);
//
//            g.drawLine(
//                    (int) this.startingPoint.getX(),
//                    (int) this.startingPoint.getY(),
//                    (int) this.actualPoint.getX(),
//                    (int) this.actualPoint.getY());
//        }
    }

    private void resetPaletteTool(final MouseEvent e) {
        if (!e.isShiftDown()) {
            this.paletteListener.resetPaletteTool();
        }
    }
}
