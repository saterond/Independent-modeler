/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.api.ToolChooserModelListener;
import cz.cvut.indepmod.sequencemodel.api.model.ISequenceModelModel;
import cz.cvut.indepmod.sequencemodel.api.model.ISequenceObject;
import cz.cvut.indepmod.sequencemodel.editor.cell.SequenceModelCellFactory;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.AbstractMessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.LifelineBottomModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageItselfModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ReturnMessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditMessageDialog;
import cz.cvut.indepmod.sequencemodel.editor.plaf.basic.SequenceModelBasicGraphUI;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.AbstractCellView;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;
import org.openide.windows.WindowManager;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelGraph extends JGraph{

    private static final Logger LOG = Logger.getLogger(SequenceModelGraph.class.getName());

    private ToolChooserModel selectedTool;

    private Set<TypeModel> staticModels;

    //This value determined height of all lifelines
    private double lifelineHeight;

    public SequenceModelGraph(ToolChooserModel selectedTool){
        this.selectedTool = selectedTool;
        lifelineHeight = 500;

        this.initEventHandling();
        this.initStaticTypes();
        
    }

    //TODO - pridani objektu
    public Collection<TypeModel> getAllTypes() {
        return staticModels;
    }

    public double getLifelineHeight() {
        return lifelineHeight;
    }

    public void setLifelineHeight(double lifelineHeight) {
        this.lifelineHeight = lifelineHeight;
    }


     private void initStaticTypes() {
        this.staticModels = new HashSet<TypeModel>();
        this.staticModels.add(new TypeModel("void"));
        this.staticModels.add(new TypeModel("String"));
        this.staticModels.add(new TypeModel("int"));
        this.staticModels.add(new TypeModel("char"));
        this.staticModels.add(new TypeModel("boolean"));
        this.staticModels.add(new TypeModel("long"));
        this.staticModels.add(new TypeModel("double"));
        this.staticModels.add(new TypeModel("float"));
    }

    //TODO dodelat pridani portu jenom k urcitemu umisteni
   
    public void setPortToEdge(Point p) {
        DefaultGraphCell edge = new DefaultGraphCell();
        DefaultPort port = new DefaultPort();

        double x = p.x;
        double y = p.y;
        Point2D point = new Point2D.Double(x, y);
        //DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCellAt(new Point2D.Double(x, y));
        DefaultGraphCell cell = (DefaultGraphCell) this.getFirstCellForLocation(x, y);
        if (cell != null) {
            List<DefaultGraphCell> cells = new ArrayList<DefaultGraphCell>();
            cells = cell.getChildren();
            if(false && cells.size() == 3){
                System.out.println("X: " + (GraphConstants.getOffset(cells.get(0).getAttributes())).getX());
                System.out.println("Y: " + (GraphConstants.getOffset(cells.get(0).getAttributes())).getY());
            }
            edge = cells.get(2);
            if (edge != null) {
                DefaultPort dp = new DefaultPort();
                GraphConstants.setOffset(dp.getAttributes(), new Point2D.Double(0,GraphConstants.PERMILLE/2));
                edge.add(dp);
            }
        }
    }

    public void inserSequenceObjectCell(Point p) {
        LOG.fine("adding new lifeline cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        switch (this.selectedTool.getSelectedTool()) {
            case TOOL_LIFELINE:
                double lifelineX = calculateLifelineXCoordinate();
                
                DefaultGraphCell cells = SequenceModelCellFactory.createSequenceObjectCells(p, lifelineHeight, lifelineX);

                GraphConstants.setMoveableAxis(cells.getAttributes(), GraphConstants.X_AXIS);
                GraphConstants.setSizeableAxis(cells.getAttributes(), GraphConstants.Y_AXIS);

                this.getGraphLayoutCache().insert(cells);
                break;
            default:
                LOG.severe("Unknown selected tool");
        }

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
    }

    /*
     * Calculate X coordinate of new lifeline
     */
    private double calculateLifelineXCoordinate(){
        double lifelineX = 0;
        double tmpX = 0;
        Object [] roots = this.getRoots();

        for(Object obj : roots){
            if(obj instanceof DefaultGraphCell){
                DefaultGraphCell lifeline = (DefaultGraphCell) obj;
                if(lifeline.getUserObject() instanceof SequenceObjectModel){
                    tmpX = (int)this.getCellBounds(lifeline).getX();
                    lifelineX = (tmpX > lifelineX)?tmpX:lifelineX;
                }
            }
        }

        if(lifelineX > 0){
            lifelineX+=150;
        }else lifelineX = 50;

        return lifelineX;
    }

    /*
    public void insertMessageCell(PortView start, PortView end) {
        LOG.fine("adding new message cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        DefaultGraphCell cells = SequenceModelCellFactory.createMessageCells(start, end, tool);

        this.getGraphLayoutCache().insert(cells);


            if(cells.getUserObject() instanceof MessageModel){
            MessageModel model = (MessageModel) cells.getUserObject();

            SequenceModelEditMessageDialog dialog = new SequenceModelEditMessageDialog(
                    WindowManager.getDefault().getMainWindow(),
                    this,
                    cells,
                    model);

            if(dialog.isMessageReturn()){
            PortView startRet = this.getPortViewAt(end.getLocation().getX(), end.getLocation().getY() + 20);
            PortView endRet = this.getPortViewAt(start.getLocation().getX(), start.getLocation().getY() + 20);
            DefaultGraphCell returnEdge = SequenceModelCellFactory.createReturnMessageCell(startRet,endRet);
            this.getGraphLayoutCache().insert(returnEdge);
        }
        }
        
        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
    }
     *
     */

    public void insertMessageCell(Point2D source, Point2D target) {
        LOG.fine("adding new message cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();
        DefaultGraphCell edge;

        switch (tool) {
            case TOOL_MESSAGE:
                edge = SequenceModelCellFactory.createMessageCells(source, target, this);
                break;
            default:
                LOG.severe("Unknown selected tool");
                return;
        }

        //this.getGraphLayoutCache().insert(edge);
            if(edge != null){

            if(edge.getUserObject() instanceof MessageModel){
            MessageModel model = (MessageModel) edge.getUserObject();

            SequenceModelEditMessageDialog dialog = new SequenceModelEditMessageDialog(
                    WindowManager.getDefault().getMainWindow(),
                    this,
                    edge,
                    model);

            /*
            PortView port = this.getPortViewAt(source.getX(), source.getY());
            Rectangle2D bound = port.getBounds();
            if(port == null) return;
            if(port.getCell() instanceof DefaultPort){
                System.out.println("je to port");
                DefaultPort defPort = (DefaultPort) port.getCell();
                Point2D pp = GraphConstants.getOffset(defPort.getAttributes());
                if(pp == null) System.out.println("pp nic");
                else System.out.println(pp.getX() + " " + pp.getY());
                pp.setLocation(pp.getX(),pp.getY() + 300);
                GraphConstants.setOffset(defPort.getAttributes(), pp);

                CellView[] cellviews = AbstractCellView.getDescendantViews(new CellView[]{port});
                Map attr = GraphConstants.createAttributes(cellviews, null);

                this.getGraphLayoutCache().edit(attr, null, null, null);
            }
            */
            
            //DefaultPort port = (DefaultPort)edge.getChildAt(0);
            //Point2D point = GraphConstants.getOffset(port.getAttributes());
                //System.out.println(point.getX() + " " + point.getY());
                

                /*
            if(dialog.isMessageReturn()){
            PortView startRet = this.getPortViewAt(end.getLocation().getX(), end.getLocation().getY() + 20);
            PortView endRet = this.getPortViewAt(start.getLocation().getX(), start.getLocation().getY() + 20);
            DefaultGraphCell returnEdge = SequenceModelCellFactory.createReturnMessageCell(startRet,endRet);
            this.getGraphLayoutCache().insert(returnEdge);
            }
                 *
                 */
        }
        }

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
    }

    public void insertMessageForItselfCell(Point2D start) {
        LOG.fine("adding new message for itself cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();
        DefaultGraphCell edge;

        switch (tool) {
            case TOOL_MESSAGE:
                edge = SequenceModelCellFactory.createMessageForItselfCells(start, this);
                break;
            default:
                LOG.severe("Unknown selected tool");
                return;
        }


        //this.getGraphLayoutCache().insert(edge);
            if(edge != null){

            if(edge.getUserObject() instanceof MessageModel){
            MessageModel model = (MessageModel) edge.getUserObject();

            SequenceModelEditMessageDialog dialog = new SequenceModelEditMessageDialog(
                    WindowManager.getDefault().getMainWindow(),
                    this,
                    edge,
                    model);
        }
        }

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
    }

    public void insertReturnCell(Point2D source, Point2D target) {
        LOG.fine("adding new return cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        DefaultGraphCell returnEdge = SequenceModelCellFactory.createReturnMessageCell(source, target, this);
        
        if(returnEdge != null) sortEdges((DefaultEdge)returnEdge);

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
    }

    public void insertFragmentCell(Point p) {
        LOG.fine("adding new fragment cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        DefaultGraphCell fragmentCell = SequenceModelCellFactory.createFragmentCells(p, tool);
        if(fragmentCell != null){
        Object [] o = new Object[1];
        o[0] = fragmentCell;
        this.getGraphLayoutCache().insert(o);
        this.getGraphLayoutCache().toBack(o);
        }

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROL);
    }

    public void insertConditionCell(DefaultGraphCell fragment,FragmentModel model){
        List children = fragment.getChildren();
        List remObject = new ArrayList();

        for(Object obj : children){
            if(obj instanceof DefaultPort){
                remObject.add(obj);
            }
        }

        this.getGraphLayoutCache().remove(remObject.toArray(),false,true);

        List<ConditionModel> conditions = model.getConditionModels();
        int size = conditions.size();
        if(size > 0){
            int i = 0;
            DefaultPort sourcePort;
            DefaultPort targetPort;
            DefaultEdge edge;
            float[] fl = {5, 5};

            for(ConditionModel con : conditions){
                sourcePort = new DefaultPort();
                targetPort = new DefaultPort();
                edge = new DefaultEdge(con);
                GraphConstants.setDashPattern(edge.getAttributes(), fl);
                
                if(i == 0){
                    GraphConstants.setLineColor(edge.getAttributes(), Color.WHITE);
                    GraphConstants.setForeground(edge.getAttributes(), Color.BLACK);
                }


                GraphConstants.setLabelPosition(edge.getAttributes(), new Point2D.Double(GraphConstants.PERMILLE / 2, +10));
                GraphConstants.setOffset(sourcePort.getAttributes(), new Point2D.Double(0, (GraphConstants.PERMILLE / (size)) * (i)));
                GraphConstants.setOffset(targetPort.getAttributes(), new Point2D.Double(GraphConstants.PERMILLE, (GraphConstants.PERMILLE / (size)) * (i)));
                fragment.add(sourcePort);
                fragment.add(targetPort);
                edge.setSource(sourcePort);
                edge.setTarget(targetPort);
                con.setConditionCell(edge);

                //DefaultGraphCell condition = new DefaultGraphCell(i, AttributeMap.emptyAttributeMap, mtns);
                //this.getGraphLayoutCache().insertEdge(edge, sourcePort, targetPort);
                this.getGraphLayoutCache().insert(new Object[]{sourcePort,targetPort,edge});
                if(i == 0) this.getGraphLayoutCache().toBack(new Object[]{edge});
                i++;
            }

        }
    }

    public void moveWithEdge(EdgeView edge, Point2D to) {
        LOG.severe("Moving with edge");

        DefaultGraphCell cellOfEdge = (DefaultGraphCell)edge.getCell();

        PortView sourcePortView = null;
        PortView targetPortView = null;

        sourcePortView = (PortView) edge.getSource();
        targetPortView = (PortView) edge.getTarget();

        DefaultPort sourcePort = (DefaultPort) sourcePortView.getCell();
        DefaultPort targetPort = (DefaultPort) targetPortView.getCell();

        Point2D sourceLocation = GraphConstants.getOffset(sourcePort.getAttributes());
        Point2D targetLocation = GraphConstants.getOffset(targetPort.getAttributes());

        if (sourcePort.getParent() instanceof DefaultGraphCell) {

            //resize lifeline cells
            if(to.getY() > (lifelineHeight - 10)){
                resizeLifelines((int)(to.getY() + 30));
            }

            DefaultGraphCell cell = (DefaultGraphCell) sourcePort.getParent();
            Rectangle2D bound = this.getCellBounds(cell);

            double boundHeight = bound.getHeight();
            double sourceY = computeYCoordinate(cellOfEdge, to.getY()) - bound.getY();
            double percent = boundHeight / 100;


            if(cell.getUserObject() != null && cell.getUserObject() instanceof FragmentModel){
                if(sourceY >= boundHeight - 20){
                    sourceY = boundHeight - 20;
                }else if(sourceY <= 20){
                    sourceY = 20;
                }     
            }else{
                if (sourceY <= SequenceModelCellFactory.heightObjectLabel + 20) {
                    sourceY = SequenceModelCellFactory.heightObjectLabel + 20;
                }
            }

            setModelPosition(sourceY,cellOfEdge);

            double targetY = sourceY;

            sourceY = (sourceY / percent) * 10;
            if(cellOfEdge.getUserObject() != null && cellOfEdge.getUserObject() instanceof MessageItselfModel){
                List<Point> points = new ArrayList<Point>();
                double tmpY = targetY + bound.getY();
                points.add(new Point(0,0));
                points.add(new Point((int)to.getX(),(int)tmpY));
                points.add(new Point((int)to.getX(),(int)tmpY + 30));
                points.add(new Point(0,0));
                targetY = ((targetY + 30) / percent) * 10;
                GraphConstants.setPoints(cellOfEdge.getAttributes(), points);

            }else targetY = sourceY;

            sourceLocation.setLocation(sourceLocation.getX(), sourceY);
            targetLocation.setLocation(targetLocation.getX(), targetY);

            GraphConstants.setOffset(sourcePort.getAttributes(), sourceLocation);
            GraphConstants.setOffset(targetPort.getAttributes(), targetLocation);

            CellView[] cellviews = AbstractCellView.getDescendantViews(new CellView[]{sourcePortView});
            Map attr = GraphConstants.createAttributes(cellviews, null);

            this.getGraphLayoutCache().edit(attr, null, null, null);

            if(cellOfEdge.getUserObject() instanceof AbstractMessageModel) sortEdges((DefaultEdge) cellOfEdge);
        }

    }

    /*
     * Change y coordinate when message move behind return message or
     * return message move before message.
     *
     * @param
     * @return - Y coordinate.
     */
    private double computeYCoordinate(DefaultGraphCell edge, double y){
        double newY = y;

        Object model = edge.getUserObject();

        if(model != null){

            if(model instanceof AbstractMessageModel){
                DefaultEdge associateEdge = getAssociateEdge((DefaultEdge) edge);

                if(associateEdge != null){
                    Rectangle2D associateBound = this.getCellBounds(associateEdge);

                    double ycoordinate = associateBound.getY();// + returnBound.getHeight();
                    double associateHeight = associateBound.getHeight();

                    if(model instanceof MessageModel){
                        double barrierY = ycoordinate - 20;
                        if(y > barrierY) newY = barrierY;
                    }else if(model instanceof ReturnMessageModel){
                        double barrierY = ycoordinate + associateHeight + 20;
                        if(y < barrierY) newY = barrierY;
                    }
                }
            }

        }

        return newY;
    }

    /*
     * Resize lifeline
     * @param y - It`s coordinate where the lifeline resize to.
     */
    public void resizeLifelines(int y){
        Object[] roots = this.getRoots();
        for (Object ob : roots) {
            if (ob instanceof DefaultGraphCell) {
                DefaultGraphCell lifelines = (DefaultGraphCell) ob;
                if (lifelines.getUserObject() instanceof SequenceObjectModel) {

                    for (Object obj : lifelines.getChildren()) {
                        if (obj instanceof DefaultGraphCell) {
                            DefaultGraphCell df = (DefaultGraphCell) obj;
                            if (df.getUserObject() instanceof LifelineBottomModel) {

                                Rectangle2D bound = this.getCellBounds(obj);
                                double x = bound.getX();
                                bound.setFrame(x, y, 0, 0);

                                this.setLifelineHeight(y);

                                CellView cv = this.getGraphLayoutCache().getMapping(ob, false);

                                if (cv instanceof VertexView) {
                                    VertexView vert = (VertexView) cv;

                                    CellView[] cellviews = AbstractCellView.getDescendantViews(new CellView[]{vert});
                                    Map attr = GraphConstants.createAttributes(cellviews, null);

                                    this.getGraphLayoutCache().edit(attr, null, null, null);
                                }

                            }
                        }
                    }

                }
            }
        }
    }

        private void initEventHandling() {
        this.selectedTool.addListener(new ToolChooserModelListener() {

            @Override
            public void selectedToolChanged(ToolChooserModel.Tool newTool) {
                boolean showPorts = false;
                ToolChooserModel.Tool tool = newTool;

                boolean getModels = false;

                switch (tool) {
                    case TOOL_MESSAGE:
                    case TOOL_RETURN:
                        showPorts = true;
                        break;
                }

                if(getModels){
                    testModel();
                    getModels = false;
                }

                //setPortsVisible(showPorts);
                //setJumpToDefaultPort(showPorts);
            }

            private void testModel() {
                Collection<SequenceObjectModel> objects = getAllSequenceObjects();
                System.out.println("Sequence objects:");
                for (SequenceObjectModel sequenceObjectModel : objects) {
                    System.out.println(sequenceObjectModel.getTypeName());
                    Collection<MessageModel> messages = sequenceObjectModel.getSentMessages();
                    System.out.println("sent messages:");
                    for (MessageModel messageModel : messages) {
                        System.out.println(messageModel);
                    }
                    
                    messages = sequenceObjectModel.getIncomeMessages();
                    System.out.println("income messages:");
                    for (MessageModel messageModel : messages) {
                        System.out.println(messageModel);
                    }
                }
                
                System.out.println();
                System.out.println("Fragments:");
                Collection<FragmentModel> fragments = getAllFragmentModels();
                for (FragmentModel fragmentModel : fragments) {
                    System.out.println(fragmentModel.getName());
                    Collection<ConditionModel> conditions = fragmentModel.getConditionModels();
                    System.out.println("conditions:");
                    for (ConditionModel conditionModel : conditions) {
                        System.out.println(conditionModel);
                        Collection<AbstractMessageModel> messages = conditionModel.getMessages();
                        System.out.println("message:");
                        for (AbstractMessageModel abstractMessageModel : messages) {
                            System.out.println(abstractMessageModel);
                        }
                    }
                }
            }
        });
    }
/*
    private void sortEdges(DefaultEdge returnEdge) {
        DefaultEdge messageCell = getMessageFromReturn(returnEdge);
        DefaultPort messSource = (DefaultPort) messageCell.getSource();
        DefaultPort retTarget = (DefaultPort) returnEdge.getTarget();
        DefaultPort retSource = (DefaultPort) returnEdge.getSource();

        Rectangle2D boundMS = this.getCellBounds(messSource);
        Rectangle2D boundRT = this.getCellBounds(retTarget);
        Rectangle2D boundRS = this.getCellBounds(retSource);

        int x = (int)boundMS.getX();
        int y = (int)boundMS.getY();
        int width = 5;
        int height = (int)(boundRT.getY() - y);


        Object [] obj = this.getRoots(new Rectangle(x, y + 5, width, height - 10));
        for(Object ob : obj){
            
            if(ob instanceof DefaultEdge){
                DefaultEdge movingEdge = (DefaultEdge)ob;
            
                if(movingEdge.getUserObject() instanceof ReturnMessageModel){
                    Rectangle2D sourceBound = this.getCellBounds(movingEdge.getSource());

                    if((int)sourceBound.getX() == x){
                        Rectangle2D bound = this.getCellBounds(movingEdge);
                        Point2D to = new Point2D.Double(boundRT.getCenterX(), boundRT.getY() + 20);

                        CellView edgeView = this.getLeafViewAt(bound.getCenterX(), bound.getCenterY());
                        
                        if(edgeView instanceof EdgeView){
                            moveWithEdge((EdgeView)edgeView, to);
                            return;
                        }
                    }
                }
            }
        }

        obj = this.getRoots(new Rectangle((int)boundRS.getX(), y + 5, width, height - 10));
        for(Object ob : obj){

             if(ob instanceof DefaultEdge){
                DefaultEdge tmpEdge = (DefaultEdge)ob;
                 System.out.println(tmpEdge.toString());
                if(tmpEdge.getUserObject() instanceof MessageModel){
                    DefaultEdge tmpReturnEdge = getAssociateEdge(tmpEdge);
                    if(tmpReturnEdge != null){
                        Rectangle2D tmpRetTargetBound = this.getCellBounds(tmpReturnEdge.getTarget());

                        if((int)boundRS.getX() == tmpRetTargetBound.getX()){
                            if(boundRS.getY() < tmpRetTargetBound.getY()){
                                Rectangle2D bound = this.getCellBounds(tmpReturnEdge);
                                Point2D to = new Point2D.Double(tmpRetTargetBound.getCenterX(),boundRS.getY() - 20);

                                CellView edgeView = this.getLeafViewAt(bound.getCenterX(), bound.getCenterY());

                                if(edgeView instanceof EdgeView) moveWithEdge((EdgeView)edgeView, to);
                            }
                        }
                    }
                }
            }
        }
    }
 *
 */

    private void sortEdges(DefaultEdge movingEdge) {
        DefaultEdge associateEdge = getAssociateEdge(movingEdge);
        if (associateEdge == null) {
            return;
        }

        Rectangle2D boundMovingEdge = this.getCellBounds(movingEdge);
        Rectangle2D boundAssociateEdge = this.getCellBounds(associateEdge);

        DefaultEdge returnEdge = null;
        DefaultEdge messageEdge = null;

        //Recognizes if the movingEdge is message or return message
        if (boundMovingEdge.getY() < boundAssociateEdge.getY()) {
            messageEdge = movingEdge;
            returnEdge = associateEdge;
        } else {
            messageEdge = associateEdge;
            returnEdge = movingEdge;
        }

        DefaultPort messageTargetPort = (DefaultPort) messageEdge.getTarget();
        DefaultPort messageSourcePort = (DefaultPort) messageEdge.getSource();
        DefaultPort returnSourcePort = (DefaultPort) returnEdge.getSource();

        Rectangle2D boundMT = this.getCellBounds(messageTargetPort);
        Rectangle2D boundMS = this.getCellBounds(messageSourcePort);
        Rectangle2D boundRS = this.getCellBounds(returnSourcePort);

        //
        int x = (int) boundMS.getX();
        int y = (int) boundMS.getY();
        int width = 5;
        int height = (int) (boundRS.getY() - y);

        //filtering all edges between message source and return message target
        Object[] obj1 = this.getRoots(new Rectangle(x, y + 10, width, height - 20));
        for (Object ob : obj1) {

            if (ob.equals(messageEdge) || ob.equals(returnEdge)) {
                continue;
            }

            if (ob instanceof DefaultEdge) {
                DefaultEdge controlledEdge = (DefaultEdge) ob;

                if (controlledEdge.getUserObject() instanceof AbstractMessageModel) {
                    AbstractMessageModel model = (AbstractMessageModel) controlledEdge.getUserObject();

                    Rectangle2D controlPortBound = null;

                    if (model instanceof MessageModel) {
                        controlPortBound = this.getCellBounds(controlledEdge.getTarget());
                    } else if (model instanceof ReturnMessageModel) {
                        controlPortBound = this.getCellBounds(controlledEdge.getSource());
                    }

                    double lifelineX = boundMS.getX();

                    if (controlPortBound.getX() == lifelineX) {
                        Rectangle2D controlledEdgeBound = this.getCellBounds(controlledEdge);

                        Point2D to = null;
                        if (movingEdge.getUserObject() instanceof MessageModel) {
                            to = new Point2D.Double(lifelineX + 50, boundMS.getY() - 20);
                        } else {
                            to = new Point2D.Double(lifelineX + 50, boundRS.getY() + 20);
                        }


                        CellView edgeView = this.getLeafViewAt(controlledEdgeBound.getCenterX(), controlPortBound.getCenterY());

                        if (edgeView instanceof EdgeView) {
                            moveWithEdge((EdgeView) edgeView, to);
                            break;
                        }
                    }
                }
            }
        }

        //filtering all edges between message target and return message source
        Object[] obj2 = this.getRoots(new Rectangle((int) boundMT.getX(), y + 10, width, height - 20));
        for (Object ob : obj2) {

            if (ob.equals(messageEdge) || ob.equals(returnEdge)) {
                continue;
            }

            if (ob instanceof DefaultEdge) {
                DefaultEdge controlledEdge = (DefaultEdge) ob;

                if (controlledEdge.getUserObject() instanceof AbstractMessageModel) {
                    AbstractMessageModel model = (AbstractMessageModel) controlledEdge.getUserObject();

                    Rectangle2D controlPortBound = null;

                    if (model instanceof MessageModel) {
                        controlPortBound = this.getCellBounds(controlledEdge.getSource());
                    } else if (model instanceof ReturnMessageModel) {
                        controlPortBound = this.getCellBounds(controlledEdge.getTarget());
                    }

                    double lifelineX = boundMT.getX();

                    if (controlPortBound.getX() == lifelineX) {

                        DefaultEdge tmpAssociateEdge = getAssociateEdge(controlledEdge);
                        if (tmpAssociateEdge == null) {
                            continue;
                        }

                        if (containsObject(obj2, tmpAssociateEdge)) {
                            continue;
                        }

                        Rectangle2D associateBound = this.getCellBounds(tmpAssociateEdge);

                        double associateY = associateBound.getY();
                        Point2D to = null;

                        if (associateY < y) {
                            to = new Point2D.Double(lifelineX, boundMT.getY() + 20);
                        } else if (associateY > boundRS.getY()) {
                            to = new Point2D.Double(lifelineX, boundRS.getY() - 20);
                        }

                        Rectangle2D associatePortBound = this.getCellBounds(tmpAssociateEdge.getSource());
                        CellView edgeView = this.getLeafViewAt(associateBound.getCenterX(), associatePortBound.getCenterY());

                        if (edgeView != null && edgeView instanceof EdgeView) {
                            moveWithEdge((EdgeView) edgeView, to);
                            break;
                        }
                    }
                }
            }
        }
    }

    /*
     * Return edge(return message or message), which is associate with edge in parameter.
     * If the edge in parametr dont have any associate edge, then return null.
     */
    public DefaultEdge getAssociateEdge(DefaultEdge edge){
        DefaultEdge associateEdge = null;
        try{
            AbstractMessageModel model = ((AbstractMessageModel)edge.getUserObject()).getAssociateMessage();
            associateEdge = (DefaultEdge)model.getCell();
        }catch(NullPointerException ex){
            LOG.severe("Retrun message dont have any message cell");
        }catch(ClassCastException ex){
            LOG.severe("Edge has different type of user object.");
        }

        return associateEdge;
    }

    	/**
	 * Notification from the <code>UIManager</code> that the L&F has changed.
	 * Replaces the current UI object with the latest version from the
	 * <code>UIManager</code>. Subclassers can override this to support
	 * different GraphUIs.
	 *
	 * @see JComponent#updateUI
	 *
	 */
    @Override
	public void updateUI() {
		setUI(new SequenceModelBasicGraphUI());
		super.invalidate();
	}

        private boolean containsObject(Object [] objects, Object searchObject){
            boolean returnValue = false;
            for(Object object : objects){
                if(object.equals(searchObject)){
                    returnValue = true;
                    break;
                }
            }

            return returnValue;
        }

    /**
     * Returns collection of all sequence objects that are in the Graph
     * @return Colection of all objects
     */
    Collection<SequenceObjectModel> getAllSequenceObjects() {
        Collection<SequenceObjectModel> res = new ArrayList<SequenceObjectModel>();
        CellView[] cw = this.getGraphLayoutCache().getRoots();
        for (int i = 0; i < cw.length; i++) {
            DefaultGraphCell cell = (DefaultGraphCell)cw[i].getCell();
            Object userObject = cell.getUserObject();
            if (userObject instanceof SequenceObjectModel) {
                res.add((SequenceObjectModel) userObject);
            }
        }
        return res;
    }

    /**
     * Returns collection of all fragment models that are in the Graph
     * @return Colection of all fragment models
     */
    public Collection<FragmentModel> getAllFragmentModels() {
        Collection<FragmentModel> res = new ArrayList<FragmentModel>();
        CellView[] cw = this.getGraphLayoutCache().getRoots();
        for (int i = 0; i < cw.length; i++) {
            DefaultGraphCell cell = (DefaultGraphCell)cw[i].getCell();
            Object userObject = cell.getUserObject();
            if (userObject instanceof FragmentModel) {
                res.add((FragmentModel) userObject);
            }
        }
        return res;
    }

    /**
     * Returns collection of all fragment cells that are in the Graph
     * @return Colection of all fragment cells
     */
    public Collection<DefaultGraphCell> getAllFragmentCells() {
        Collection<DefaultGraphCell> res = new ArrayList<DefaultGraphCell>();
        for (Object object : this.getRoots()) {
            if(object instanceof DefaultGraphCell){
                DefaultGraphCell cell = (DefaultGraphCell) object;
                if(cell.getUserObject() != null && cell.getUserObject() instanceof FragmentModel){
                    res.add(cell);
                }
            }
        }
        return res;
    }

    /*
     * Set a position of message to model of message.
     * @param cellOfEdge - The message edge.
     */
    private void setModelPosition(double position, DefaultGraphCell cellOfEdge) {
        try {
            AbstractMessageModel messageModel = (AbstractMessageModel)cellOfEdge.getUserObject();
            messageModel.setPosition(position);

        } catch (NullPointerException ex) {
            LOG.severe("Null object for message.");
        } catch (ClassCastException ex) {
            LOG.severe("Different edge.");
        }
    }

    public void setMessageToFragmentModel(DefaultGraphCell fragmentCell) {
        try{
            FragmentModel fragmentModel = (FragmentModel)fragmentCell.getUserObject();
            List<ConditionModel> conditions = fragmentModel.getConditionModels();
            Rectangle2D fragmentBound = this.getCellBounds(fragmentCell);
            List<AbstractMessageModel> messages;
            
            int i = 0;
            for (Iterator<ConditionModel> it = conditions.iterator(); it.hasNext(); i++) {
                messages = new ArrayList<AbstractMessageModel>();
                ConditionModel conditionModel = it.next();
                DefaultEdge conditionCell = conditionModel.getConditionCell();
                Rectangle2D sourceBound = this.getCellBounds(conditionCell.getSource());
                double x = sourceBound.getX();
                double y = sourceBound.getY();
                double width = fragmentBound.getWidth();
                double height;

                if(it.hasNext()){
                    ConditionModel nextConditionModel = conditions.get(i+1);
                    DefaultEdge nextConditionCell = nextConditionModel.getConditionCell();
                    Rectangle2D targetBound = this.getCellBounds(nextConditionCell.getTarget());

                    height = targetBound.getY() - y;
                }else{
                    height = fragmentBound.getMaxY() - y;
                }

                Rectangle conditionBound = new Rectangle((int)x, (int)y, (int)width, (int)height);
                Object [] conditionCells = this.getRoots(conditionBound);
                for (Object object : conditionCells) {
                    DefaultGraphCell cell = (DefaultGraphCell)object;
                    if(cell.getUserObject() instanceof AbstractMessageModel){
                        messages.add((AbstractMessageModel)cell.getUserObject());
                    }
                }

                conditionModel.setMessageModels(messages);

            }

            messages = new ArrayList<AbstractMessageModel>();
            for (Object object : this.getRoots(fragmentBound.getBounds())) {
                DefaultGraphCell cell = (DefaultGraphCell)object;
                    if(cell.getUserObject() instanceof AbstractMessageModel){
                        messages.add((AbstractMessageModel)cell.getUserObject());
                    }
            }

            fragmentModel.setMessageModels(messages);


        } catch (NullPointerException ex) {
            LOG.severe("Null object for fragment.");
        } catch (ClassCastException ex) {
            LOG.severe("Different class.");
        }
    }

}
