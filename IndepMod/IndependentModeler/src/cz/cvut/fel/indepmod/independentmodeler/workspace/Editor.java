package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import cz.cvut.fel.indepmod.independentmodeler.workspace.actions.IndependentModelerPaletteActions;
import cz.cvut.fel.indepmod.independentmodeler.workspace.cookie.SaveCookieImpl;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 *
 * @author Petr Vales
 */
public class Editor extends TopComponent {

    private PaletteController palette;
    private JScrollPane scenePane = new JScrollPane();
    private Graph graph;
    private IndependentModelerTransferHandler transferHandler;
    private PaletteListener paletteListener;
    private InstanceContent ic = new InstanceContent();
    private SaveCookieImpl impl;

//    InstanceContent ic;
//    SaveCookieImpl impl;
    public Editor(String title,
            CategoryChildrenFactory childrenfactory,
            IndependentModelerTransferHandler transferHandler,
            GraphNode node) {
        super();
        this.transferHandler = transferHandler;
        this.setDisplayName(title);
        this.initPalette(childrenfactory);
        this.initComponents(node);
        this.initSaveCookie();
//        impl = new SaveCookieImpl();
//        ic = new InstanceContent();
////        ic.add(ExplorerUtils.createLookup(mgr, getActionMap()));
//        ic.add(impl);
//        associateLookup(new AbstractLookup(ic));
        associateLookup(new AbstractLookup(ic));
    }

    public Editor(String title, CategoryChildrenFactory factory,
            IndependentModelerTransferHandler handler, GraphNode node,
            Graph graph) {
        super();
        this.transferHandler = handler;
        this.setDisplayName(title);
        this.initPalette(factory);
        this.initComponents(node, graph);
        this.initSaveCookie();
        associateLookup(new AbstractLookup(ic));
    }

    private void initSaveCookie() {
        impl = new SaveCookieImpl();
////        ic.add(ExplorerUtils.createLookup(mgr, getActionMap()));
        ic.add(impl);
    }

    private void initComponents(GraphNode node, Graph graph) {
        this.setLayout(new BorderLayout());
        this.graph = graph;
        this.add(this.graph);
        this.add(this.scenePane, BorderLayout.CENTER);
        this.scenePane.setViewportView(this.graph);
        initTransferHandler();
        this.graph.setMarqueeHandler(
                new MarqueeHandler(graph,
                this.paletteListener,
                null));
        this.graph.setProjectNode(node);

    }

    private void initComponents(GraphNode node) {
        this.setLayout(new BorderLayout());
        this.initJGraph(node);
        this.add(this.graph);
        this.add(this.scenePane, BorderLayout.CENTER);
        this.scenePane.setViewportView(this.graph);
    }

    private void initJGraph(GraphNode node) {
        GraphModel model = new DefaultGraphModel();
        GraphLayoutCache view = new GraphLayoutCache(model,
                new DefaultCellViewFactory());
        this.graph = new Graph(model, view);
        initTransferHandler();
        this.graph.setMarqueeHandler(
                new MarqueeHandler(graph,
                this.paletteListener,
                null));
        this.graph.setProjectNode(node);
    }

    private void initTransferHandler() {
        this.transferHandler.setGraph(graph);
        this.graph.setTransferHandler(this.transferHandler);
        this.transferHandler.setPaletteListener(this.paletteListener);
    }

    private void initPalette(CategoryChildrenFactory factory) {
        this.palette = PaletteFactory.createPalette(
                new AbstractNode(Children.create(
                factory, true)),
                new IndependentModelerPaletteActions());
        ic.add(this.palette);
//        this.associateLookup(Lookups.fixed(this.palette));
        this.paletteListener = new PaletteListener(palette);
        this.palette.addPropertyChangeListener(this.paletteListener);
    }

    @Override
    protected void componentActivated() {
        Navigator.findInstance().setRoot(this.graph.getNavigatorNode());
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    protected void componentOpened() {
        super.componentOpened();
    }

    @Override
    protected void componentClosed() {
        super.componentClosed();
    }
}
