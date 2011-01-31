package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.IndependentModelerCellViewFactory;
import org.jgraph.JGraph;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.DefaultGraphCell;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.PortView;

/**
 *
 * @author Petr Vales
 */
public class Graph extends JGraph {

    public Graph(GraphModel gm, GraphLayoutCache glc) {
        super(gm, glc);
        this.getGraphLayoutCache().setFactory(
                new IndependentModelerCellViewFactory());
    }

    public void createCell(Point point, DefaultGraphCell[] cells) {
//        DefaultGraphCell[] cells = new DefaultGraphCell[1];
//        cells[0] = new NoteCell(); //TODO
        GraphConstants.setBounds(cells[0].getAttributes(), new Rectangle2D.Double(point.
                getX(), point.getY(), 200, 100));
        GraphConstants.setOpaque(cells[0].getAttributes(), true);
        DefaultPort port0 = new DefaultPort();
        cells[0].add(port0);
        this.getGraphLayoutCache().insert(cells);
        this.repaint();
    }

    public void drawLine(Color newColor, Color oldColor, Point2D startingPoint,
            Point2D actualPoint) {
        if (startingPoint != null && actualPoint != null) {
            Graphics2D g = (Graphics2D) this.getGraphics();
            g.setColor(newColor);
            g.setXORMode(oldColor);
            g.drawLine(
                    (int) startingPoint.getX(),
                    (int) startingPoint.getY(),
                    (int) actualPoint.getX(),
                    (int) actualPoint.getY());
        }
    }

    public void addEdge(PortView beginPort, PortView endPort) {
        DefaultEdge edge = new DefaultEdge();


        GraphConstants.setEndFill(edge.getAttributes(), true);
        GraphConstants.setLineStyle(edge.getAttributes(),
                GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setLabelAlongEdge(edge.getAttributes(), true);
        GraphConstants.setEditable(edge.getAttributes(), true);
        GraphConstants.setMoveable(edge.getAttributes(), true);
        GraphConstants.setDisconnectable(edge.getAttributes(), false);
        GraphConstants.setRouting(edge.getAttributes(),
                GraphConstants.ROUTING_SIMPLE);
        GraphConstants.setBendable(edge.getAttributes(), true);
        GraphConstants.setLineEnd(edge.getAttributes(),
                GraphConstants.ARROW_SIMPLE);

        edge.setSource(beginPort.getCell());
        edge.setTarget(endPort.getCell());


        this.getGraphLayoutCache().insert(edge);
        this.repaint();
    }
//
//    private static final Logger LOG = Logger.getLogger(Graph.class.getName());
////    private Map<String, ClassModelAbstractAction> actions;
//    private PaletteListener paletteListener;
//    private Set<TypeModel> staticModels;
//
//    public Graph(/*Map<String, ClassModelAbstractAction> actions,*/PaletteListener paletteListener) {
////        this.actions = actions;
//        this.paletteListener = paletteListener;
//
//        this.initActions();
//        this.initEventHandling();
////        this.initStaticTypes();
//    }
//
//    //TODO - this could be saved (and updated when model id changed)
//    public Collection<TypeModel> getAllTypes() {
//        Collection<TypeModel> res = new LinkedList<TypeModel>(this.getAllClasses());
////        CellView[] cw = this.getGraphLayoutCache().getCellViews();
////        for (int i = 0; i < cw.length; i++) {
////            DefaultGraphCell cell = (DefaultGraphCell)cw[i].getCell();
////            Object userObject = cell.getUserObject();
////            if (userObject instanceof ClassModel) {
////                res.add((TypeModel) userObject);
////            }
////        }
//
//        res.addAll(this.staticModels);
//        return res;
//    }
//
//    /**
//     * Returns collection of all classes that are in the Graph
//     * @return Colection of all classes
//     */
//    public Collection<ClassModel> getAllClasses() {
//        Collection<ClassModel> res = new LinkedList<ClassModel>();
//        CellView[] cw = this.getGraphLayoutCache().getCellViews();
//        for (int i = 0; i < cw.length; i++) {
//            DefaultGraphCell cell = (DefaultGraphCell) cw[i].getCell();
//            Object userObject = cell.getUserObject();
//            if (userObject instanceof ClassModel) {
//                res.add((ClassModel) userObject);
//            }
//        }
//        return res;
//    }
//
//    public void insertCell(Point p) {
//        LOG.fine("adding new cell");
//        ToolChooserModel.Tool tool = this.paletteListener.getSelectedTool();
//        DefaultGraphCell cell = IndependentModelerCellViewFactory.createCell(p, tool);
//
//        this.getGraphLayoutCache().insert(cell);
//        this.selectedTool.setSelectedTool(ToolChooserModel.Tool.TOOL_CONTROLL);
//    }
//
////    /**
////     * TODO - this will be loaded from a XML. Only temporary
////     */
////    private void initStaticTypes() {
////        this.staticModels = new HashSet<TypeModel>();
////        this.staticModels.add(new TypeModel("String"));
////        this.staticModels.add(new TypeModel("int"));
////        this.staticModels.add(new TypeModel("char"));
////        this.staticModels.add(new TypeModel("boolean"));
////        this.staticModels.add(new TypeModel("long"));
////        this.staticModels.add(new TypeModel("double"));
////        this.staticModels.add(new TypeModel("float"));
////    }
//    private void initActions() {
////        this.actions.put(
////                ClassModelEditAction.ACTION_NAME,
////                new ClassModelEditAction(this)
////        );
//    }
//
//    private void initEventHandling() {
//        this.selectedTool.addListener(new ToolChooserModelListener() {
//
//            @Override
//            public void selectedToolChanged(ToolChooserModel.Tool newTool) {
//                boolean showPorts = false;
//                ToolChooserModel.Tool tool = newTool;
//
////                switch (tool) {
////                    case TOOL_ADD_RELATION:
////                        showPorts = true;
////                }
//
//                String tool = this.paletteListener.getSelectedTool();
//                //TODO predelat
//                if (tool.contains("Dependenci")) {
//                    showPorts = true;
//                }
//
//                setPortsVisible(showPorts);
//                setJumpToDefaultPort(showPorts);
//            }
//        });
//
//        this.addGraphSelectionListener(new GraphSelectionListener() {
//
//            @Override
//            public void valueChanged(GraphSelectionEvent graphSelectionEvent) {
//                actions.get(ClassModelEditAction.ACTION_NAME).setEnabled(getSelectionCell() != null);
//            }
//        });
//    }
}
