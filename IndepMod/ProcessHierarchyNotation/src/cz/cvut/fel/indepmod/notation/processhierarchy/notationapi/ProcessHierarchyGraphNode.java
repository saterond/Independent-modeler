package cz.cvut.fel.indepmod.notation.processhierarchy.notationapi;

import cz.cvut.fel.indepmod.editorprovider.EditorProvider;
import cz.cvut.fel.indepmod.independentmodeler.workspace.Graph;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.IndependentModelerCellViewFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdge;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.IndependentModelerEdge;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdge;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.nodes.ArrowEdgeNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.nodes.LineEdgeNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import cz.cvut.fel.indepmod.processhierarchynotation.id.ProcessHierarchyNotationId;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.PortView;
import org.openide.nodes.Children;
import org.openide.util.actions.Presenter;

/**
 *
 * @author Petr Vales
 */
public final class ProcessHierarchyGraphNode extends GraphNode implements Externalizable {

    private Graph graph;

    public ProcessHierarchyGraphNode() {
        super(new Children.Array());
        this.setDisplayName("Process hierarchy graph");
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new OpenAction(), new RenameAction()};
    }

    public Graph getGraph() {
        if (this.graph == null) {
            try {
                this.loadGraph();
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
            }
        }
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getDisplayName());
        out.writeObject(this.getRootDir());
//        out.writeObject(this.getChildren().getNodes());
        this.saveGraph();
    }

    @Override
    public void saveGraph() throws FileNotFoundException, IOException {
        File file = new File(this.getRootDir().getPath() + File.separator + "graph.imj");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        this.saveCells(out);
        this.saveEdges(out);
        out.close();
    }

    //TODO
    private void saveCells(ObjectOutputStream out) throws IOException {
        Object[] cells = this.getGraph().getGraphLayoutCache().getCells(false, true, false, false);
        out.writeObject(cells);

//        ------------------------test----------------------
//        for (Object object : cells) {
//            if (object instanceof RoleCell) {
//                Collection<cz.cvut.fel.indepmod.notation.processhierarchy.model.api.Process> data = ((RoleCell) object).getAllProcesses();
//                for (cz.cvut.fel.indepmod.notation.processhierarchy.model.api.Process iData : data) {
//                    System.out.println(iData.getName());
//                }
//            }
//        }
//        ------------------------test----------------------
    }

    private void saveEdges(ObjectOutputStream out) throws IOException {
        Object[] edges = this.getGraph().getGraphLayoutCache().getCells(false, false, false, true);
        List<String> edgeClasses = new ArrayList<String>();
        List<Rectangle2D> sources = new ArrayList<Rectangle2D>();
        List<Rectangle2D> targets = new ArrayList<Rectangle2D>();
        for (Object object : edges) {
            this.parseEdgeInfo(object, edgeClasses, sources, targets);
        }
        out.writeObject(edgeClasses);
        out.writeObject(sources);
        out.writeObject(targets);
    }

    private void parseEdgeInfo(Object object,
            List<String> edgeClasses,
            List<Rectangle2D> sources,
            List<Rectangle2D> targets) {
        edgeClasses.add(object.getClass().getName());
        if (object instanceof ArrowEdge) {
            this.parseArrowEdgeInfo((ArrowEdge) object, sources, targets);
        } else if (object instanceof LineEdge) {
            this.parseLineEdgeInfo((LineEdge) object, sources, targets);
        }
//        ArrowEdge edge = (ArrowEdge) object;
//        ArrowEdgeNode navigatorNode = (ArrowEdgeNode) edge.getNavigatorNode();
//        sources.add(this.getBoundsForCellNode((CellNode) navigatorNode.getSource()));
//        targets.add(this.getBoundsForCellNode((CellNode) navigatorNode.getTarget()));
    }

    private void parseLineEdgeInfo(LineEdge edge, List<Rectangle2D> sources,
            List<Rectangle2D> targets) {
        LineEdgeNode navigatorNode = (LineEdgeNode) edge.getNavigatorNode();
        sources.add(this.getBoundsForCellNode((CellNode) navigatorNode.getSource()));
        targets.add(this.getBoundsForCellNode((CellNode) navigatorNode.getTarget()));
    }

    private void parseArrowEdgeInfo(ArrowEdge edge, List<Rectangle2D> sources,
            List<Rectangle2D> targets) {
        ArrowEdgeNode navigatorNode = (ArrowEdgeNode) edge.getNavigatorNode();
        sources.add(this.getBoundsForCellNode((CellNode) navigatorNode.getSource()));
        targets.add(this.getBoundsForCellNode((CellNode) navigatorNode.getTarget()));
    }

    private Rectangle2D getBoundsForCellNode(CellNode node) {
        AttributeMap attributes = node.getCell().getAttributes();
        return GraphConstants.getBounds(attributes);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setDisplayName((String) in.readObject());
        this.setRootDir((File) in.readObject());
//        this.getChildren().add((Node[]) in.readObject());
//        this.loadGraph();
    }

    @Override
    public void loadGraph() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File(this.getRootDir().getPath() + File.separator + "graph.imj");
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fin);
        this.initGraph();
        this.readCells(in);
        this.readEdges(in);
        in.close();
    }

    private void initGraph() {
        GraphModel model = new DefaultGraphModel();
        GraphLayoutCache view = new GraphLayoutCache(model,
                new DefaultCellViewFactory());
        this.setGraph(new Graph(model, view));
        this.getGraph().setProjectNode(this);
        this.getGraph().getGraphLayoutCache().setFactory(
                new IndependentModelerCellViewFactory());
    }

    private void readCells(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Object[] cells = (Object[]) in.readObject();
        for (Object object : cells) {
            if (object instanceof Cell) {
                Cell[] cellArray = new Cell[1];
                cellArray[0] = (Cell) object;
                if (cells[0] != null) {
                    this.getGraph().createCell(cellArray);
                }

            }
        }
    }

    private void readEdges(ObjectInputStream in) throws IOException, ClassNotFoundException {
        List<String> edgeClasses = (List<String>) in.readObject();
        List<Rectangle2D> sources = (List<Rectangle2D>) in.readObject();
        List<Rectangle2D> targets = (List<Rectangle2D>) in.readObject();
        Iterator<Rectangle2D> sourceIterator = sources.iterator();
        Iterator<Rectangle2D> targetIterator = targets.iterator();
        this.getGraph().setPortsVisible(true);
        this.getGraph().setJumpToDefaultPort(true);
        for (String edgeClass : edgeClasses) {
            this.createEdgeByClassName(edgeClass, sourceIterator, targetIterator);
        }
        this.getGraph().setPortsVisible(false);
        this.getGraph().setJumpToDefaultPort(false);
    }

    private void createEdgeByClassName(String edgeClass, Iterator<Rectangle2D> sourceIterator, Iterator<Rectangle2D> targetIterator) {
        if (edgeClass.equals(ArrowEdge.class.getName())) {
            ArrowEdge edge = new ArrowEdge();
            this.findSourceAndTarget(edge, sourceIterator.next(), targetIterator.next());
            this.getGraph().addEdge(edge);
        } else if (edgeClass.equals(LineEdge.class.getName())) {
            LineEdge edge = new LineEdge();
            this.findSourceAndTarget(edge, sourceIterator.next(), targetIterator.next());
            this.getGraph().addEdge(edge);
        }
    }

    private void findSourceAndTarget(DefaultEdge edge, Rectangle2D source, Rectangle2D target) {
        PortView portViewAtSource = this.getGraph().getPortViewAt(source.getX(), source.getY());
        PortView portViewAtTarget = this.getGraph().getPortViewAt(target.getX(), target.getY());
        edge.setSource(portViewAtSource.getCell());
        edge.setTarget(portViewAtTarget.getCell());
        this.setSourceAndTargetNodes(edge, portViewAtSource, portViewAtTarget);
    }

    private void setSourceAndTargetNodes(DefaultEdge edge, PortView portViewAtSource, PortView portViewAtTarget) {
        if (edge instanceof IndependentModelerEdge) {
            ((IndependentModelerEdge) edge).setSourceNode(
                    ((Cell) portViewAtSource.getParentView().getCell()).getNavigatorNode());
            ((IndependentModelerEdge) edge).setTargetNode(
                    ((Cell) portViewAtTarget.getParentView().getCell()).getNavigatorNode());
        }
    }

    @Override
    public String getNotationId() {
        return ProcessHierarchyNotationId.NAME;
    }

    private class OpenAction extends AbstractAction implements Presenter.Popup {

        public OpenAction() {
            putValue(NAME, "Open");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getGraph() == null) {
                Graph graph = EditorProvider.getInstance().requestEditor(
                        ProcessHierarchyNotationId.NAME,
                        getDisplayName(),
                        getDisplayName(),
                        ProcessHierarchyGraphNode.this);
                setGraph(graph);
            } else {
                EditorProvider.getInstance().requestEditor(
                        ProcessHierarchyNotationId.NAME,
                        getDisplayName(),
                        getDisplayName(),
                        ProcessHierarchyGraphNode.this,
                        getGraph());
            }
        }

        @Override
        public JMenuItem getPopupPresenter() {
            JMenuItem item = new JMenuItem(this);
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    InputEvent.CTRL_MASK));
            return item;
        }
    }

    private class RenameAction extends AbstractAction implements Presenter.Popup {

        public RenameAction() {
            putValue(NAME, "Rename");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String newName = JOptionPane.showInputDialog("Rename:",
                    getDisplayName());
            setDisplayName(newName);
        }

        @Override
        public JMenuItem getPopupPresenter() {
            JMenuItem item = new JMenuItem(this);
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                    InputEvent.CTRL_MASK));
            return item;
        }
    }
}
