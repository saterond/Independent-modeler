package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import cz.cvut.fel.indepmod.independentmodeler.workspace.actions.IndependentModelerPaletteActions;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
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
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;

/**
 *
 * @author Petr Vales
 */
public class Editor extends TopComponent {

    private PaletteController palette;
    private JScrollPane scenePane = new JScrollPane();
    private Graph graph = new Graph();
    private IndependentModelerTransferHandler transferHandler;
    private PaletteListener paletteListener;
    private CellFactory cellFactory;

    public Editor(String title,
                    CategoryChildrenFactory childrenfactory,
                    IndependentModelerTransferHandler transferHandler,
                    CellFactory cellFactory) {
        super();
        this.transferHandler = transferHandler;
        this.cellFactory = cellFactory;
        this.setDisplayName(title);
        this.initPalette(childrenfactory);
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.initJGraph();
        this.add(this.graph);
        this.add(this.scenePane, BorderLayout.CENTER);
        this.scenePane.setViewportView(this.graph);
    }

    private void initJGraph() {
        GraphModel model = new DefaultGraphModel();
        GraphLayoutCache view = new GraphLayoutCache(model,
                new DefaultCellViewFactory());
        this.graph = new Graph(model, view);
        initTransferHandler();
        this.graph.setMarqueeHandler(
                new MarqueeHandler(graph,
                    this.paletteListener,
                    this.cellFactory,
                    null));
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
        this.associateLookup(Lookups.fixed(this.palette));
        this.paletteListener = new PaletteListener(palette);
        this.palette.addPropertyChangeListener(this.paletteListener);
    }
}
