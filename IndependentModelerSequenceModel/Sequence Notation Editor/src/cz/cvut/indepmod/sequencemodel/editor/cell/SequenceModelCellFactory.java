/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.cell;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.LifelineModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.PortModel;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditMessageDialog;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.tree.TreeNode;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;
import org.openide.windows.WindowManager;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelCellFactory {

    private static final Logger LOG = Logger.getLogger(SequenceModelCellFactory.class.getName());
    private static int counter = 0;
    private static int location = 50;

    public static DefaultGraphCell createLifelineCells(Point2D point, ToolChooserModel.Tool selectedTool) {
        DefaultGraphCell[] cells = null;
        DefaultEdge edge = new DefaultEdge();
        DefaultGraphCell parentCell;

        switch (selectedTool) {
            case TOOL_LIFELINE:
                cells = new DefaultGraphCell[3];
                cells[0] = new DefaultGraphCell();
                cells[0].setUserObject(new LifelineModel());
                cells[1] = new DefaultGraphCell(new String(""));
                cells[2] = edge;
                break;
            default:
                LOG.severe("Unknown selected tool");
        }

        cells[0].add(new DefaultPort());
        cells[1].add(new DefaultPort());

        int countPorts = 50;
        for (int i = 1; i < countPorts; i++) {
            DefaultPort dp = new DefaultPort();
            GraphConstants.setOffset(dp.getAttributes(), new Point2D.Double(0, (GraphConstants.PERMILLE / countPorts) * i));
            cells[2].add(dp);
        }


        GraphConstants.setBounds(
                cells[0].getAttributes(),
                new Rectangle2D.Double(
                location,
                20,
                //point.getX(),
                //point.getY(),
                60,
                30));

        GraphConstants.setBounds(
                cells[1].getAttributes(),
                new Rectangle2D.Double(
                location + 30,
                300,
                0,
                0));

        location += 150;

        edge.setSource(cells[0].getChildAt(0));
        edge.setTarget(cells[1].getChildAt(0));
        //GraphConstants.setLineEnd(cells[2].getAttributes(), GraphConstants.PERMILLE);
        //GraphConstants.setEndFill(cells[2].getAttributes(), true);
        float[] fl = {5, 5};
        GraphConstants.setDashPattern(edge.getAttributes(), fl);

        GraphConstants.setGradientColor(cells[0].getAttributes(), Color.YELLOW);
        GraphConstants.setOpaque(cells[0].getAttributes(), true);
        GraphConstants.setSizeable(cells[0].getAttributes(), false);
        //GraphConstants.setResize(cell.getAttributes(), true);
        parentCell = new DefaultGraphCell(new String("Lifeline"), null, cells);

        return parentCell;
    }

    public static DefaultGraphCell createMessageCells(PortView start, PortView end, ToolChooserModel.Tool selectedTool) {
        DefaultGraphCell[] cells = null;
        DefaultEdge edge = new DefaultEdge();
        DefaultEdge edgeTitle = new DefaultEdge();
        DefaultGraphCell parentCell;

        switch (selectedTool) {
            case TOOL_MESSAGE:
                cells = new DefaultGraphCell[3];
                cells[0] = new SequenceModelMessageCell();
                cells[0].setUserObject(new MessageModel());
                cells[1] = edge;
                cells[2] = edgeTitle;
                break;
            default:
                LOG.severe("Unknown selected tool");
                return null;
        }

        DefaultPort startPort = (DefaultPort)start.getCell();
        DefaultPort endPort = (DefaultPort)end.getCell();
        startPort.setUserObject(new PortModel("start_port"));
        endPort.setUserObject(new PortModel("end_port"));
        start.setCell(startPort);
        end.setCell(endPort);


        edge.setSource(start.getCell());
        edge.setTarget(end.getCell());
        

        //add port for titleEdge
        DefaultPort portTitle = new DefaultPort();
        GraphConstants.setOffset(portTitle.getAttributes(), new Point2D.Double(GraphConstants.PERMILLE / 2, 0));
        cells[1].add(portTitle);

        double cellX = ((end.getLocation().getX() + start.getLocation().getX()) / 2) - 30;
        double cellY = (end.getLocation().getY() - 20);
        GraphConstants.setBounds(cells[0].getAttributes(), new Rectangle2D.Double(cellX, cellY, 60, 20));
        GraphConstants.setAutoSize(cells[0].getAttributes(), true);

        GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
        GraphConstants.setEndFill(edge.getAttributes(), true);
        GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
        GraphConstants.setEditable(edge.getAttributes(), true);
        GraphConstants.setMoveable(edge.getAttributes(), true);
        GraphConstants.setDisconnectable(edge.getAttributes(), false);

        //set titleEdge from edge to message cell
        cells[0].add(new DefaultPort());
        edgeTitle.setSource(portTitle);
        edgeTitle.setTarget(cells[0].getChildAt(0));
        float[] fl = {5, 5};
        GraphConstants.setDashPattern(edgeTitle.getAttributes(), fl);

        parentCell = new DefaultGraphCell(new String("Message"), null, cells);
        return parentCell;
    }
    
    public static DefaultGraphCell createReturnMessageCell(PortView start, PortView end){
        DefaultGraphCell[] edges = new DefaultGraphCell[3];
        DefaultEdge returnEdge = new DefaultEdge();
        DefaultEdge liveOfMessage1 = new DefaultEdge();
        DefaultEdge liveOfMessage2 = new DefaultEdge();
        DefaultPort startPort = new DefaultPort();
        DefaultPort endPort = new DefaultPort();

        DefaultEdge startEdge = (DefaultEdge) start.getParentView().getCell();
        DefaultEdge endEdge = (DefaultEdge) end.getParentView().getCell();
        
        List<Object> startPorts = startEdge.getChildren();
        List<Object> endPorts = endEdge.getChildren();
        int indexOfStartPort = startPorts.indexOf(start.getCell());
        int i = indexOfStartPort;

        while(i != 0){
            Object startObjectPort = startPorts.get(i);
            Object endObjectPort = endPorts.get(i);

            if(startObjectPort.toString() != null && startObjectPort.toString().contains("end_port")){
                startPort = (DefaultPort)startObjectPort;
                endPort = (DefaultPort)endObjectPort;
                startPort.setUserObject(new PortModel("live_start"));
                endPort.setUserObject(new PortModel("live_end"));
                break;
            }
            i--;
            if(i == 0) return null;
        }
        /*
        for(Object port : edge.getChildren()){
        if(port.toString() != null){
            startPort = (DefaultPort)port;
            break;
        }
        }
         *
         */

        if(startPort != null && endPort != null){
            liveOfMessage1.setSource(startPort);
            liveOfMessage1.setTarget(start.getCell());
            GraphConstants.setLineWidth(liveOfMessage1.getAttributes(), 5);

            liveOfMessage2.setSource(endPort);
            liveOfMessage2.setTarget(end.getCell());
            GraphConstants.setLineWidth(liveOfMessage2.getAttributes(), 5);
        }


            if ((start != null) && (end != null)) {
                returnEdge.setSource(start.getCell());
                returnEdge.setTarget(end.getCell());
                float[] fl = {5, 5};
                GraphConstants.setDashPattern(returnEdge.getAttributes(), fl);
                GraphConstants.setLineEnd(returnEdge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
                GraphConstants.setEndFill(returnEdge.getAttributes(), false);
                GraphConstants.setLink(returnEdge.getAttributes(), "test");
                GraphConstants.setLabelEnabled(returnEdge.getAttributes(), true);
                GraphConstants.setLabelAlongEdge(returnEdge.getAttributes(), true);
            }else return null;
        edges[0] = returnEdge;
        edges[1] = liveOfMessage1;
        edges[2] = liveOfMessage2;

        return new DefaultGraphCell(new String("return"), null, edges);
    }


}
