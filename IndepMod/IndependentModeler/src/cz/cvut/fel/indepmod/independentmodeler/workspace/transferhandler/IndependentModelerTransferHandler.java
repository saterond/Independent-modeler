package cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Graph;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.IndependentModelerCellViewFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNode;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.TransferHandler;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.openide.util.Exceptions;

/**
 *
 * @author Petr Vales
 */
public class IndependentModelerTransferHandler extends TransferHandler {

    private Graph graph;
    private DefaultCellViewFactory viewFacotry = new IndependentModelerCellViewFactory();
    private CellFactory cellFactory;
    private PaletteListener paletteListener;

    public IndependentModelerTransferHandler(CellFactory cellFactory) {
        this.viewFacotry = new IndependentModelerCellViewFactory();
        this.cellFactory = cellFactory;
    }

    public CellFactory getCellFactory() {
        return cellFactory;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        graph.getGraphLayoutCache().setFactory(this.viewFacotry);
    }

    public Graph getGraph() {
        return graph;
    }

    public DefaultCellViewFactory getViewFacotry() {
        return viewFacotry;
    }

    @Override
    public boolean canImport(final TransferSupport support) {
        return support.isDataFlavorSupported(PaletteNode.DATA_FLAVOR);
    }

    @Override
    public boolean importData(final TransferSupport support) {
        this.paletteListener.resetPaletteTool();
        try {
            DefaultGraphCell[] cells = new DefaultGraphCell[1];
            cells[0] = this.handleData(support);
            this.getGraph().createCell(support.getDropLocation().getDropPoint(), cells);
            return true;
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return false;
    }

    protected DefaultGraphCell handleData(final TransferSupport support) throws UnsupportedFlavorException, IOException {
        DefaultGraphCell[] cells = new DefaultGraphCell[1];
        PaletteNode myNode = (PaletteNode) support.getTransferable().getTransferData(PaletteNode.DATA_FLAVOR);
        cells[0] = this.getCellFactory().getCell(myNode.getName());
        return cells[0];
    }

    public void setPaletteListener(PaletteListener paletteListener) {
        this.paletteListener = paletteListener;
    }
}
