package cz.cvut.fel.indepmod.independentmodeler.workspace;

//import cz.cvut.indepmod.classmodel.actions.ClassModelEditAction;
//import cz.cvut.indepmod.classmodel.api.ToolChooserModelListener;
//import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.ClassModel;
//import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.TypeModel;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCell;
import org.jgraph.JGraph;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.DefaultGraphCell;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

public class Graph extends JGraph {

    public Graph(GraphModel gm, GraphLayoutCache glc, BasicMarqueeHandler bmh) {
        super(gm, glc, bmh);
    }

    public Graph(GraphModel gm, BasicMarqueeHandler bmh) {
        super(gm, bmh);
    }

    public Graph(GraphModel gm, GraphLayoutCache glc) {
        super(gm, glc);
    }

    public Graph(GraphLayoutCache glc) {
        super(glc);
    }

    public Graph(GraphModel gm) {
        super(gm);
    }

    public Graph() {
        super();
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
