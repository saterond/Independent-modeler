/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ReturnMessageModel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.EdgeView;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelMarqueeHandler extends BasicMarqueeHandler{

    private static final Logger LOG = Logger.getLogger(SequenceModelMarqueeHandler.class.getName());
    
    private final SequenceModelGraph graph;
    private final ToolChooserModel selectedTool;
    private final JPopupMenu popupMenu;

    private Point2D actualPoint;
    private Point2D initialPoint;

    private double sourceX;
    private double targetX;

    private boolean movingWithEdge;
    
    public SequenceModelMarqueeHandler(SequenceModelGraph graph, ToolChooserModel selectedTool,JPopupMenu popupMenu){
        this.graph = graph;
        this.selectedTool = selectedTool;
        this.popupMenu = popupMenu;
        this.movingWithEdge = false;
        this.initialPoint = null;
        this.actualPoint = null;
    }

    //TODO - try refractor this method
    @Override
    public void mousePressed(final MouseEvent e){
        if(SwingUtilities.isRightMouseButton(e)){
            this.popupMenu.show(this.graph, e.getX(), e.getY());
        }else if (SwingUtilities.isLeftMouseButton(e) && isSelectedLifeline()){
            this.graph.inserSequenceObjectCell(e.getPoint());

            /*
        }else if(isSelectedCondition() && isSelectedFragmentCell(e.getPoint())){
            DefaultGraphCell selectedCell = (DefaultGraphCell)this.graph.getFirstCellForLocation(e.getX(), e.getY());
            DefaultGraphCell cell = (DefaultGraphCell)selectedCell.getChildAt(0);
             *
             */
        }else if (SwingUtilities.isLeftMouseButton(e) && isSelectedFragment()){
            this.graph.insertFragmentCell(e.getPoint());
        }else if(SwingUtilities.isLeftMouseButton(e) && (isSelectedMessage() || isSelectedReturn()) && isLifeline(e)){
            this.initialPoint = e.getPoint();
            this.actualPoint = e.getPoint();
        }else if (SwingUtilities.isLeftMouseButton(e) && isEdge(e.getPoint())){
            this.initialPoint = e.getPoint();
            this.actualPoint = e.getPoint();

            initEdgeValue(e);

            this.movingWithEdge = true;
        }else{
            super.mousePressed(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(this.initialPoint != null){
            if(movingWithEdge) this.printEdgeTempLine(Color.black);
            else this.printTempLine(Color.black);

            //this.actualPort = this.graph.getPortViewAt(e.getPoint().getX(), startPort.getLocation().getY()-6);
            //this.actualPoint = this.actualPort != null ? this.actualPort.getLocation() : e.getPoint();

            if(isLifeline(e)){
                this.actualPoint.setLocation(e.getX(), initialPoint.getY());
            }else{
                this.actualPoint = e.getPoint();
            }

            if(movingWithEdge) this.printEdgeTempLine(Color.black);
            else this.printTempLine(Color.black);
        }else{
            super.mouseDragged(e);
        }
        /*
        if(this.initialPoint != null && this.graph.isPortsVisible()){
            this.printTempLine(Color.black);

            this.actualPort = this.graph.getPortViewAt(e.getPoint().getX(), startPort.getLocation().getY()-6);
            this.actualPoint = this.actualPort != null ? this.actualPort.getLocation() : e.getPoint();

            this.printTempLine(Color.black);
        }else{
            super.mouseDragged(e);
        }
         *
         */
    }

        @Override
    public void mouseReleased(MouseEvent mouseEvent) {
            if (this.initialPoint != null && this.actualPoint != null) {
                if(movingWithEdge) this.printEdgeTempLine(Color.black);
                else this.printTempLine(Color.black);

            if (this.initialPoint.getX() != this.actualPoint.getX() && isLifeline(mouseEvent)) {
                if(isSelectedMessage()){
                    this.graph.insertMessageCell(this.initialPoint, this.actualPoint);
                }else if(isSelectedReturn()){
                    this.graph.insertReturnCell(this.initialPoint, this.actualPoint);
                }
            } else if(this.initialPoint.getX() == this.actualPoint.getX() && isLifeline(mouseEvent)){
                if(isSelectedMessage()){
                    this.graph.insertMessageForItselfCell(this.initialPoint);
                }
            } else if (movingWithEdge) {
                CellView edge = this.graph.getLeafViewAt(initialPoint.getX(), initialPoint.getY());
                if(edge instanceof EdgeView) this.graph.moveWithEdge((EdgeView)edge, actualPoint);
            }

            //this.actualPort = null;
            this.actualPoint = null;
            //this.startPort = null;
            this.initialPoint = null;
            this.movingWithEdge = false;
            this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
        } else {
            super.mouseReleased(mouseEvent);
        }
            /*
        if (this.startPort != null && this.graph.isPortsVisible()) {
            this.printTempLine(Color.black);

            if (this.actualPort != null && !this.actualPort.equals(this.startPort)) {
                if(isSelectedMessage()){
                this.graph.insertMessageCell(this.startPort, this.actualPort);
                }else if(isSelectedReturn()){
                this.graph.insertReturnCell(this.startPort, this.actualPort);
                }
            }

            this.actualPort = null;
            this.actualPoint = null;
            this.startPort = null;
            this.initialPoint = null;
            this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
        } else {
            super.mouseReleased(mouseEvent);
        }
             *
             */
    }

    
    @Override
    public boolean isForceMarqueeEvent(MouseEvent mouseEvent) {
        if (SwingUtilities.isRightMouseButton(mouseEvent) && !mouseEvent.isShiftDown()) {
            return true;
        }

        if (SwingUtilities.isLeftMouseButton(mouseEvent) && (isSelectedMessage() || isSelectedReturn())){
            return true;
        }

        if(SwingUtilities.isLeftMouseButton(mouseEvent) && isEdge(mouseEvent.getPoint())) return true;


        return super.isForceMarqueeEvent(mouseEvent);
    }

    public boolean isLifeline(MouseEvent e){
        Object cell = this.graph.getFirstCellForLocation(e.getX(), e.getY());
        if(cell instanceof DefaultGraphCell && ((DefaultGraphCell) cell).getUserObject() instanceof SequenceObjectModel)
            return true;
        else return false;
    }


    public boolean isSelectedLifeline(){
        ToolChooserModel.Tool tool;
        try {
            tool = this.selectedTool.getSelectedTool();
        } catch (ClassCastException ex) {
            return false;
        }

        switch (tool) {
            case TOOL_LIFELINE:
                return true;
            default:
                return false;
        }
    }

   public boolean isSelectedReturn(){
        ToolChooserModel.Tool tool;
        try {
            tool = this.selectedTool.getSelectedTool();
        } catch (ClassCastException ex) {
            return false;
        }

        switch (tool) {
            case TOOL_RETURN:
                return true;
            default:
                return false;
        }
    }

        public boolean isSelectedMessage(){
        ToolChooserModel.Tool tool;
        try {
            tool = this.selectedTool.getSelectedTool();
        } catch (ClassCastException ex) {
            return false;
        }

        switch (tool) {
            case TOOL_MESSAGE:
                return true;
            default:
                return false;
        }
    }

    public boolean isSelectedFragment(){
        ToolChooserModel.Tool tool;
        try {
            tool = this.selectedTool.getSelectedTool();
        } catch (ClassCastException ex) {
            return false;
        }

        switch (tool) {
            case TOOL_FRAGMENT:
                return true;
            default:
                return false;
        }
    }

    public boolean isSelectedFragmentCell(Point2D p){
        try {
            DefaultGraphCell cell = (DefaultGraphCell) this.graph.getFirstCellForLocation(p.getX(), p.getY());
            if(cell.toString().contains("fragment")){
            return true;
            }else{
                return false;
            }
        } catch (NullPointerException ex) {
                ex.printStackTrace();
        } catch (ClassCastException ex) {
                ex.printStackTrace();
        }
        return false;
    }

    private void printTempLine(Color color) {
            if (this.initialPoint != null && this.actualPoint != null) {
            Graphics2D g = (Graphics2D) this.graph.getGraphics();
            g.setColor(color);
            g.setXORMode(this.graph.getBackground());

            g.drawLine(
                    (int) this.initialPoint.getX(),
                    (int) this.initialPoint.getY(),
                    (int) this.actualPoint.getX(),
                    (int) this.actualPoint.getY()
            );
        }
    }

    private void printEdgeTempLine(Color color) {
            if (this.initialPoint != null && this.actualPoint != null) {
            Graphics2D g = (Graphics2D) this.graph.getGraphics();
            g.setColor(color);
            g.setXORMode(this.graph.getBackground());

            g.drawLine(
                    (int) this.sourceX,
                    (int) this.actualPoint.getY(),
                    (int) this.targetX,
                    (int) this.actualPoint.getY()
            );
        }
    }

    private boolean isEdge(Point p) {
        CellView cv = this.graph.getLeafViewAt(p.getX(), p.getY());
        if(cv == null) return false;
        if(cv.getCell() instanceof DefaultEdge){
            DefaultEdge de = (DefaultEdge) cv.getCell();
            if(de.getUserObject() instanceof MessageModel
                    || de.getUserObject() instanceof ConditionModel
                    || de.getUserObject() instanceof ReturnMessageModel){
                return true;
            }
        }

        return false;
    }

    private void initEdgeValue(MouseEvent e) {
        CellView cv = this.graph.getLeafViewAt(e.getX(), e.getY());
        if(cv instanceof EdgeView){
            EdgeView ev = (EdgeView)cv;
            sourceX = ev.getSource().getBounds().getX();
            targetX = ev.getTarget().getBounds().getX();
        }
    }
}
