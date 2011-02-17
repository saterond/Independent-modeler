package cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Graph;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteListener;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteCellNode;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.TransferHandler;
import org.jgraph.graph.DefaultGraphCell;
import org.openide.util.Exceptions;

/**
 *
 * @author Petr Vales
 */
public class IndependentModelerTransferHandler extends TransferHandler {

    private Graph graph;
    private PaletteListener paletteListener;

    public IndependentModelerTransferHandler() {
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public boolean canImport(final TransferSupport support) {
        return support.isDataFlavorSupported(PaletteCellNode.DATA_FLAVOR);
    }

    @Override
    public boolean importData(final TransferSupport support) {
        this.resetPalletListener();
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
        PaletteCellNode myNode = (PaletteCellNode) support.getTransferable().getTransferData(PaletteCellNode.DATA_FLAVOR);
        cells[0] = myNode.getCell();
        return cells[0];
    }

    private void resetPalletListener() {
        if(this.getPaletteListener() != null) {
            this.getPaletteListener().resetPaletteTool();
        }
    }

    public PaletteListener getPaletteListener() {
        return this.paletteListener;
    }

    public void setPaletteListener(PaletteListener paletteListener) {
        this.paletteListener = paletteListener;
    }
}
