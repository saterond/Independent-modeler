/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.cell;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.LifelineBottomModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageItselfModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.PortModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ReturnMessageModel;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelCellFactory {

    private static final Logger LOG = Logger.getLogger(SequenceModelCellFactory.class.getName());
    private static int counter = 0;
    private static int locationX = 50;
    private static int locationY = 20;
    public static int heightObjectLabel = 30;
    private static int widthObjectLabel = 100;

    public static DefaultGraphCell createSequenceObjectCells(Point2D point, double lifelineHeight, double lifelineX) {
        //vytvoření cellů a nastavení jejich uživatelských objektů
        DefaultGraphCell[] cells = new DefaultGraphCell[3];
        DefaultEdge edge = new DefaultEdge();
        DefaultGraphCell parentCell;
        SequenceObjectModel objectModel = new SequenceObjectModel();

        cells[0] = new DefaultGraphCell();
        cells[0].setUserObject(objectModel);
        cells[1] = new DefaultGraphCell(new LifelineBottomModel());
        cells[2] = edge;

        //vytvoření portů pro propojení částí lifeline, která je reprezentována čárkovanou čárou
        cells[0].add(new DefaultPort());
        cells[1].add(new DefaultPort());

        //setBounds() nastavuje pozici a velikost(tedy rámec ve kterém je objekt umístěn) komponenty
        GraphConstants.setBounds(
                cells[0].getAttributes(),
                new Rectangle2D.Double(
                lifelineX,
                locationY,
                widthObjectLabel,
                heightObjectLabel));

        GraphConstants.setBounds(
                cells[1].getAttributes(),
                new Rectangle2D.Double(
                lifelineX + (widthObjectLabel / 2),
                lifelineHeight,
                0,
                0));

        //locationX += 150;

        //nastavení zdrojového a cílového portu lifeline
        edge.setSource(cells[0].getChildAt(0));
        edge.setTarget(cells[1].getChildAt(0));

        //nastavení atributu pro čárkovanou čáru
        float[] fl = {5, 5};
        GraphConstants.setDashPattern(edge.getAttributes(), fl);

        //nastavení atributů pro rámování, vykreslení a zamezení změny velikosti
        GraphConstants.setBorder(cells[0].getAttributes(), BorderFactory.createLineBorder(Color.black));
        //GraphConstants.setGradientColor(cells[0].getAttributes(), Color.YELLOW);
        GraphConstants.setOpaque(cells[0].getAttributes(), true);
        GraphConstants.setSizeable(cells[0].getAttributes(), false);

        //všechny komponenty jsou vloženy pod jednu návratovou komponentu
        parentCell = new DefaultGraphCell(objectModel, null, cells);

        //tento atribut zamezuje zvolení a manipulaci s komponenty, které jsou ukryty pod parentCell
        GraphConstants.setChildrenSelectable(parentCell.getAttributes(), false);

        return parentCell;
    }

    /**
     * Create message and ports from source to target point
     * @param source - point of message source
     * @param target - point of message target
     * @param selectedTool - selected tool
     * @param graph - sequence model graph
     * @return created graph cell of message
     */
    public static DefaultGraphCell createMessageCells(Point2D source, Point2D target, SequenceModelGraph graph) {
        DefaultGraphCell [] cells = new DefaultGraphCell [3];
        DefaultEdge edge = new DefaultEdge();
        Object startObj = graph.getFirstCellForLocation(source.getX(), source.getY());
        Object endObj = graph.getFirstCellForLocation(target.getX(), target.getY());
        DefaultGraphCell startSeqObject = null;
        DefaultGraphCell endSeqObject = null;
        MessageModel edgeModel = new MessageModel("Message", edge);

        //set model to edge
        edge.setUserObject(edgeModel);

        try {

            if (startObj instanceof DefaultGraphCell && endObj instanceof DefaultGraphCell) {
                startSeqObject = (DefaultGraphCell) startObj;
                endSeqObject = (DefaultGraphCell) endObj;

                SequenceObjectModel startSeqObjectModel = (SequenceObjectModel) startSeqObject.getUserObject();
                SequenceObjectModel endSeqObjectModel = (SequenceObjectModel) endSeqObject.getUserObject();

                if (startSeqObjectModel == null || endSeqObjectModel == null) {
                    return null;
                }

                if (startSeqObjectModel instanceof SequenceObjectModel && endSeqObjectModel instanceof SequenceObjectModel) {
                    DefaultPort startPort = new DefaultPort();
                    DefaultPort endPort = new DefaultPort();
                    startPort.setUserObject(new PortModel("start_port"));
                    endPort.setUserObject(new PortModel("end_port"));


                    Rectangle2D startBound = graph.getCellBounds(startSeqObject);

                    double y = source.getY() - startBound.getY();
                    double percent = startBound.getHeight() / 100;

                    if (y <= heightObjectLabel + 10) {
                        y = heightObjectLabel + 10;
                    }
                    y = (y / percent) * 10;

                    GraphConstants.setOffset(startPort.getAttributes(), new Point2D.Double((GraphConstants.PERMILLE / 2), y));
                    GraphConstants.setOffset(endPort.getAttributes(), new Point2D.Double((GraphConstants.PERMILLE / 2), y));

                    startSeqObject.add(startPort);
                    endSeqObject.add(endPort);

                    edge.setSource(startPort);
                    edge.setTarget(endPort);

                    GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
                    GraphConstants.setEndFill(edge.getAttributes(), true);
                    GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_ORTHOGONAL);
                    //GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
                    GraphConstants.setEditable(edge.getAttributes(), true);
                    //GraphConstants.setMoveable(edge.getAttributes(), true);
                    GraphConstants.setDisconnectable(edge.getAttributes(), false);

                    GraphConstants.setLabelPosition(edge.getAttributes(), new Point2D.Double(GraphConstants.PERMILLE / 2, -10));

                    //set message to SequenceObjectModel
                    startSeqObjectModel.addSentMessage(edgeModel);
                    endSeqObjectModel.addIncomeMessage(edgeModel);

                    //set sequence object model to message model
                    edgeModel.setSourceSeqObject(startSeqObjectModel);
                    edgeModel.setTargetSeqObject(endSeqObjectModel);

                    //set position of edge to model
                    edgeModel.setPosition(source.getY());

                    cells[0] = startPort;
                    cells[1] = endPort;
                    cells[2] = edge;

                    graph.getGraphLayoutCache().insert(cells);
                    //graph.getGraphLayoutCache().insertEdge(edge, startPort, endPort);
                } else {
                    return null;
                }

            } else {
                return null;
            }

        } catch (NullPointerException ex) {
            LOG.severe("Null object for message.");
            return null;
        } catch (ClassCastException ex) {
            LOG.severe("Wrong object for message.");
            return null;
        }

        return edge;
    }


    public static DefaultGraphCell createReturnMessageCell(Point2D source, Point2D target, SequenceModelGraph graph){

        DefaultGraphCell [] edges = new DefaultGraphCell[5];
        DefaultEdge returnEdge = new DefaultEdge();
        DefaultEdge liveOfObject1 = new DefaultEdge();
        DefaultEdge liveOfObject2 = new DefaultEdge();
        Object startObj = graph.getFirstCellForLocation(source.getX(), source.getY());
        Object endObj = graph.getFirstCellForLocation(target.getX(), target.getY());
        DefaultGraphCell startLifeline = null;
        DefaultGraphCell endLifeline = null;
        DefaultPort messageStartPort = null;
        DefaultPort messageEndPort = null;

        if(startObj instanceof DefaultGraphCell && endObj instanceof DefaultGraphCell){
            startLifeline = (DefaultGraphCell) startObj;
            endLifeline = (DefaultGraphCell) endObj;

            if(startLifeline.getUserObject() == null || endLifeline.getUserObject() == null) return null;
            if(startLifeline.getUserObject() instanceof SequenceObjectModel && endLifeline.getUserObject() instanceof SequenceObjectModel){
                //List<Object> startChild = startLifeline.getChildren();
                //List<Object> endChild = endLifeline.getChildren();

                Rectangle2D bound = graph.getCellBounds(startLifeline);

                for(double i = source.getY(); i > bound.getY() + heightObjectLabel; i-=5){
                    PortView pv = graph.getPortViewAt(bound.getCenterX(), i);
                    if(pv != null){
                        if(pv.getCell() instanceof DefaultPort){
                            DefaultPort port = (DefaultPort) pv.getCell();
                            if(port.getUserObject() != null){
                                if(port.getUserObject().toString().contains("end_port")){
                                    messageStartPort = port;
                                    messageEndPort = (DefaultPort) graph.getPortForLocation(target.getX(), i);
                                    break;
                                }
                            }
                        }
                    }
                }

                if(messageStartPort != null && messageEndPort != null){
                    messageStartPort.setUserObject(new PortModel("live_start"));
                    messageEndPort.setUserObject(new PortModel("live_end"));

                    //TODO - set return to message

                    DefaultPort startPort = new DefaultPort();
                    DefaultPort endPort = new DefaultPort();
                    startPort.setUserObject(new PortModel("start_return_port"));
                    endPort.setUserObject(new PortModel("end_return_port"));

                    double y = source.getY() - bound.getY();
                    double percent = bound.getHeight() / 100;

                    if(y <= heightObjectLabel + 10) y = heightObjectLabel + 10;
                    y = (y / percent) * 10;

                    GraphConstants.setOffset(startPort.getAttributes(),new Point2D.Double((GraphConstants.PERMILLE / 2), y));
                    GraphConstants.setOffset(endPort.getAttributes(), new Point2D.Double((GraphConstants.PERMILLE / 2), y));

                    startLifeline.add(startPort);
                    endLifeline.add(endPort);

                    returnEdge.setSource(startPort);
                    returnEdge.setTarget(endPort);
                    float[] fl = {5, 5};
                    GraphConstants.setDashPattern(returnEdge.getAttributes(), fl);
                    GraphConstants.setLineEnd(returnEdge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
                    GraphConstants.setEndFill(returnEdge.getAttributes(), false);
                    GraphConstants.setLabelPosition(returnEdge.getAttributes(), new Point2D.Double(GraphConstants.PERMILLE / 4.5, -10));
                    //GraphConstants.setLabelEnabled(returnEdge.getAttributes(), true);
                    //GraphConstants.setLabelAlongEdge(returnEdge.getAttributes(), true);

                    //Add live of object
                    liveOfObject1.setSource(messageStartPort);
                    liveOfObject1.setTarget(startPort);
                    GraphConstants.setLineWidth(liveOfObject1.getAttributes(), 7);

                    liveOfObject2.setSource(messageEndPort);
                    liveOfObject2.setTarget(endPort);
                    GraphConstants.setLineWidth(liveOfObject2.getAttributes(), 7);

                    /*
                    //Insert into the graph
                    graph.getGraphLayoutCache().insertEdge(returnEdge, startPort, endPort);
                    graph.getGraphLayoutCache().insertEdge(liveOfMessage1,messageStartPort,startPort);
                    graph.getGraphLayoutCache().insertEdge(liveOfMessage2, messageEndPort, endPort);
                     * 
                     */

                    /*
                     * setting the model of cells
                     */
                    Object [] tmp = messageStartPort.getEdges().toArray();
                    if(tmp.length > 0){
                        DefaultGraphCell tmpCell = (DefaultGraphCell)tmp[0];
                        if(tmpCell.getUserObject() instanceof MessageModel){
                            ReturnMessageModel returnModel = new ReturnMessageModel("",returnEdge,(MessageModel)tmpCell.getUserObject());
                            returnEdge.setUserObject(returnModel);
                            MessageModel messageModel = (MessageModel) tmpCell.getUserObject();
                            messageModel.setAssociateMessage(returnModel);
                            tmpCell.setUserObject(messageModel);
                        }
                    }else returnEdge.setUserObject(new ReturnMessageModel(""));


                    edges[0] = startPort;
                    edges[1] = endPort;
                    edges[2] = returnEdge;
                    edges[3] = liveOfObject1;
                    edges[4] = liveOfObject2;

                    graph.getGraphLayoutCache().insert(edges);
                    graph.getGraphLayoutCache().toBack(new Object [] {liveOfObject1,liveOfObject2});
                    return returnEdge;

                }else return null;

            }
        }
        /*
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
  
        for(Object port : edge.getChildren()){
        if(port.toString() != null){
            startPort = (DefaultPort)port;
            break;
        }
        }
        

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
                GraphConstants.setLabelEnabled(returnEdge.getAttributes(), true);
                GraphConstants.setLabelAlongEdge(returnEdge.getAttributes(), true);
            }else return null;
        edges[0] = returnEdge;
        edges[1] = liveOfMessage1;
        edges[2] = liveOfMessage2;

        return new DefaultGraphCell(new String("return"), null, edges);
         *
         */
        return null;
    }

    public static DefaultGraphCell createMessageForItselfCells(Point2D start, SequenceModelGraph graph) {

        DefaultEdge edge = new DefaultEdge();
        DefaultGraphCell [] messageForItself = new DefaultGraphCell[4];
        Object lifelineObj = graph.getFirstCellForLocation(start.getX(), start.getY());
        DefaultGraphCell lifeline = null;
        MessageItselfModel edgeModel = new MessageItselfModel("Message", edge);

        try{


        if(lifelineObj instanceof DefaultGraphCell){
            lifeline = (DefaultGraphCell) lifelineObj;

            SequenceObjectModel lifelineModel = (SequenceObjectModel)lifeline.getUserObject();

        if(lifeline.getUserObject() == null) return null;
        if(lifeline.getUserObject() instanceof SequenceObjectModel){
            DefaultPort startPort = new DefaultPort();
            DefaultPort endPort = new DefaultPort();
            DefaultEdge liveOfObject = new DefaultEdge();

            startPort.setUserObject(new PortModel("start_port"));
            endPort.setUserObject(new PortModel("end_port"));

            Rectangle2D lifelineBound = graph.getCellBounds(lifeline);

            double startY = start.getY() - lifelineBound.getY();
            double targetY = startY + 30;
            double percent = lifelineBound.getHeight() / 100;

            if(targetY <= heightObjectLabel + 10) targetY = heightObjectLabel + 10;
            startY = (startY / percent) * 10;
            targetY = (targetY / percent) * 10;

            GraphConstants.setOffset(startPort.getAttributes(),new Point2D.Double((GraphConstants.PERMILLE / 2), startY));
            GraphConstants.setOffset(endPort.getAttributes(), new Point2D.Double((GraphConstants.PERMILLE / 2), targetY));

            lifeline.add(startPort);
            lifeline.add(endPort);

            edge.setSource(startPort);
            edge.setTarget(endPort);

            GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
            GraphConstants.setEndFill(edge.getAttributes(), true);
            GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_ORTHOGONAL);
            //GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
            GraphConstants.setEditable(edge.getAttributes(), true);
            //GraphConstants.setMoveable(edge.getAttributes(), true);
            GraphConstants.setDisconnectable(edge.getAttributes(), false);

            GraphConstants.setLabelPosition(edge.getAttributes(), new Point2D.Double(GraphConstants.PERMILLE / 2, -10));

            //set additive points and orthogonal style of edge
            List<Point> points = new ArrayList<Point>();
            points.add(new Point(0,0));
            points.add(new Point((int)start.getX() + 50,(int)start.getY()));
            points.add(new Point((int)start.getX() + 50,(int)start.getY() + 30));
            points.add(new Point(0,0));
            GraphConstants.setPoints(edge.getAttributes(), points);
            GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_ORTHOGONAL);

            //Add the live of object
            liveOfObject.setSource(startPort);
            liveOfObject.setTarget(endPort);
            GraphConstants.setLineWidth(liveOfObject.getAttributes(), 7);

            //Add message to sequence object model
            lifelineModel.addIncomeMessage(edgeModel);
            lifelineModel.addSentMessage(edgeModel);

            //set sequence object model to message model
            edgeModel.setSourceSeqObject(lifelineModel);
            edgeModel.setTargetSeqObject(lifelineModel);

            //set position of edge to model
            edgeModel.setPosition(start.getY());

            //set model to edge
            edge.setUserObject(edgeModel);

            messageForItself[0] = startPort;
            messageForItself[1] = endPort;
            messageForItself[2] = edge;
            messageForItself[3] = liveOfObject;

            graph.getGraphLayoutCache().insert(messageForItself);
            graph.getGraphLayoutCache().toBack(new Object [] {liveOfObject});

        }else return null;

        }else return null;

        } catch (NullPointerException ex) {
            LOG.severe("Null object for message.");
            return null;
        } catch (ClassCastException ex) {
            LOG.severe("Wrong object for message.");
            return null;
        }

        return edge;
    }

    public static DefaultGraphCell createFragmentCells(Point2D point, ToolChooserModel.Tool selectedTool) {
        DefaultGraphCell[] cells = null;

        switch (selectedTool) {
            case TOOL_FRAGMENT:
                cells = new DefaultGraphCell[2];
                cells[0] = new DefaultGraphCell();
                cells[1] = new DefaultGraphCell("Fragment");
                break;
            default:
                LOG.severe("Unknown selected tool");
        }

       GraphConstants.setBounds(
                cells[0].getAttributes(),
                new Rectangle2D.Double(
                point.getX(),
                point.getY(),
                300,
                150));

       GraphConstants.setBounds(
                cells[1].getAttributes(),
                new Rectangle2D.Double(
                point.getX(),
                point.getY(),
                60,
                20));
       

       //GraphConstants.setOpaque(cells[0].getAttributes(), true);
       GraphConstants.setBorder(cells[0].getAttributes(), BorderFactory.createLineBorder(Color.black));
       GraphConstants.setBorder(cells[1].getAttributes(), BorderFactory.createLineBorder(Color.black));
       GraphConstants.setSizeable(cells[1].getAttributes(), false);
       GraphConstants.setOpaque(cells[1].getAttributes(), true);
       GraphConstants.setAutoSize(cells[1].getAttributes(), true);
       GraphConstants.setBackground(cells[1].getAttributes(), Color.white);

       DefaultGraphCell returnCells = new DefaultGraphCell(new FragmentModel("Fragment"), null, cells);
       GraphConstants.setChildrenSelectable(returnCells.getAttributes(), false);
       
       return returnCells;
    }

    public static int getLocationY() {
        return locationY;
    }


}
