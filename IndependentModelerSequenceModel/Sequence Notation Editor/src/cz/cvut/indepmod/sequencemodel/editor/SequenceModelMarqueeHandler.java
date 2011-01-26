/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.component.LifelineComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelMarqueeHandler extends BasicMarqueeHandler{

    private static final Logger LOG = Logger.getLogger(SequenceModelMarqueeHandler.class.getName());
    
    private final SequenceModelGraph graph;
    private final ToolChooserModel selectedTool;
    private final JPopupMenu popupMenu;

    private PortView actualPort;
    private PortView startPort;
    private Point2D actualPoint;
    private Point2D startPoint;
    
    public SequenceModelMarqueeHandler(SequenceModelGraph graph, ToolChooserModel selectedTool,JPopupMenu popupMenu){
        this.graph = graph;
        this.selectedTool = selectedTool;
        this.popupMenu = popupMenu;
    }

    //TODO - try refractor this method
    @Override
    public void mousePressed(final MouseEvent e){
        if(SwingUtilities.isRightMouseButton(e)){
        this.popupMenu.show(this.graph, e.getX(), e.getY());
        }else if (SwingUtilities.isLeftMouseButton(e) && isSelectedLifeline()){
            this.graph.insertLifelineCell(e.getPoint());
        }else if (SwingUtilities.isLeftMouseButton(e) && isSelectedFragment()){
            this.graph.insertFragmentCell(e.getPoint());
        }else if(this.actualPort != null && this.graph.isPortsVisible()){
            this.startPort = this.actualPort;
            this.startPoint = this.startPort.getLocation();

        }else{
            super.mousePressed(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(this.startPoint != null && this.graph.isPortsVisible()){
            this.printTempLine(Color.black);

            this.actualPort = this.graph.getPortViewAt(e.getPoint().getX(), startPort.getLocation().getY()-6);
            this.actualPoint = this.actualPort != null ? this.actualPort.getLocation() : e.getPoint();

            this.printTempLine(Color.black);
        }else{
            super.mouseDragged(e);
        }
    }

        @Override
    public void mouseReleased(MouseEvent mouseEvent) {
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
            this.startPoint = null;
            this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_INTERACTION);
        } else {
            super.mouseReleased(mouseEvent);
        }
    }

    
    @Override
    public boolean isForceMarqueeEvent(MouseEvent mouseEvent) {
        if (SwingUtilities.isRightMouseButton(mouseEvent) && !mouseEvent.isShiftDown()) {
            return true;
        }

        //this.graph.setPortToEdge(mouseEvent.getPoint());
        this.actualPort = this.graph.getPortViewAt(mouseEvent.getPoint().getX(), mouseEvent.getPoint().getY());
        return (this.actualPort != null && this.graph.isPortsVisible()) || super.isForceMarqueeEvent(mouseEvent);
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

    private void printTempLine(Color color) {
            if (this.startPoint != null && this.actualPoint != null) {
            Graphics2D g = (Graphics2D) this.graph.getGraphics();
            g.setColor(color);
            g.setXORMode(this.graph.getBackground());

            g.drawLine(
                    (int) this.startPoint.getX(),
                    (int) this.startPoint.getY(),
                    (int) this.actualPoint.getX(),
                    (int) this.actualPoint.getY()
            );
        }
    }
}
