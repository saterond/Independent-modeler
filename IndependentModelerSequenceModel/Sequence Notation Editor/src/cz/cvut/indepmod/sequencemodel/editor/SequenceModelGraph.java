/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.api.ToolChooserModelListener;
import cz.cvut.indepmod.sequencemodel.editor.cell.SequenceModelCellFactory;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.PortModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import cz.cvut.indepmod.sequencemodel.editor.frames.dialogs.SequenceModelEditMessageDialog;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.tree.TreeNode;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
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
public class SequenceModelGraph extends JGraph{

    private static final Logger LOG = Logger.getLogger(SequenceModelGraph.class.getName());

    private ToolChooserModel selectedTool;

    private Set<TypeModel> staticModels;

    public SequenceModelGraph(ToolChooserModel selectedTool){
        this.selectedTool = selectedTool;

        this.initEventHandling();
        this.initStaticTypes();
    }

    //TODO - pridani objektu
    public Collection<TypeModel> getAllTypes() {
        return staticModels;
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

    public void insertLifelineCell(Point p) {
        LOG.fine("adding new lifeline cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();
        DefaultGraphCell cells = SequenceModelCellFactory.createLifelineCells(p, tool);

        GraphConstants.setMoveableAxis(cells.getAttributes(), GraphConstants.X_AXIS);
        GraphConstants.setSizeableAxis(cells.getAttributes(), GraphConstants.Y_AXIS);
        
        this.getGraphLayoutCache().insert(cells);
        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_INTERACTION);
    }

    public void insertMessageCell(PortView start, PortView end) {
        LOG.fine("adding new message cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        DefaultGraphCell cells = SequenceModelCellFactory.createMessageCells(start, end, tool);

        this.getGraphLayoutCache().insert(cells);


            DefaultGraphCell cell = (DefaultGraphCell) cells.getChildAt(0);

            if(cell.getUserObject() instanceof MessageModel){
            MessageModel model = (MessageModel) cell.getUserObject();

            SequenceModelEditMessageDialog dialog = new SequenceModelEditMessageDialog(
                    WindowManager.getDefault().getMainWindow(),
                    this,
                    cell,
                    model);

            if(dialog.isMessageReturn()){
            PortView startRet = this.getPortViewAt(end.getLocation().getX(), end.getLocation().getY() + 20);
            PortView endRet = this.getPortViewAt(start.getLocation().getX(), start.getLocation().getY() + 20);
            DefaultGraphCell returnEdge = SequenceModelCellFactory.createReturnMessageCell(startRet,endRet);
            this.getGraphLayoutCache().insert(returnEdge);
        }
        }
        
        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_INTERACTION);
    }

    public void insertReturnCell(PortView start, PortView end) {
        LOG.fine("adding new return cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        DefaultGraphCell returnEdge = SequenceModelCellFactory.createReturnMessageCell(start,end);
        if(returnEdge != null){
        this.getGraphLayoutCache().insert(returnEdge);
        }

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_INTERACTION);
    }

    public void insertFragmentCell(Point p) {
        LOG.fine("adding new fragment cell");
        ToolChooserModel.Tool tool = this.selectedTool.getSelectedTool();

        DefaultGraphCell fragmentCell = SequenceModelCellFactory.createFragmentCells(p, tool);
        if(fragmentCell != null){
        this.getGraphLayoutCache().insert(fragmentCell);
        }

        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_INTERACTION);
    }

        private void initEventHandling() {
        this.selectedTool.addListener(new ToolChooserModelListener() {

            @Override
            public void selectedToolChanged(ToolChooserModel.Tool newTool) {
                boolean showPorts = false;
                ToolChooserModel.Tool tool = newTool;

                switch (tool) {
                    case TOOL_MESSAGE:
                    case TOOL_RETURN:
                        showPorts = true;
                }

                setPortsVisible(showPorts);
                setJumpToDefaultPort(showPorts);
            }
        });
    }

}
