package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @param <T> 
 * @author Petr Vales
 */
public class PaletteNode<T extends Cell> implements Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(
            PaletteNode.class, "PaletteNode");
    private String name;
    private Enum paletteNode;
    private ICellFactory<T> cellFactory;

    public PaletteNode(String _name, Enum _paletteNode,
            ICellFactory<T> _cellFactory) {
        this.name = _name;
        this.paletteNode = _paletteNode;
        this.cellFactory = _cellFactory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum getPaletteNode() {
        return paletteNode;
    }

    public void setPaletteNode(Enum paletteNode) {
        this.paletteNode = paletteNode;
    }

    public T getCell() {
        return this.getCellFactory().creta();
    }

    public ICellFactory<T> getCellFactory() {
        return cellFactory;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor == DATA_FLAVOR;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws
            UnsupportedFlavorException, IOException {
        if (flavor == DATA_FLAVOR) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
