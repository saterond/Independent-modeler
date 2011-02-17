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
//TODO find better name for this class
public class PaletteCellNode<T extends Cell> implements IPaletteNode, Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(
            PaletteCellNode.class, "PaletteCellNode");
    private String name;
//    private Enum paletteNode;
    private ICellFactory<T> cellFactory;

    /**
     *
     * @param _name
     * @param _cellFactory
     */
    public PaletteCellNode(String _name, /*Enum _paletteNode,*/
            ICellFactory<T> _cellFactory) {
        this(_name);
//        this.name = _name;
//        this.paletteNode = _paletteNode;
        this.cellFactory = _cellFactory;
    }

    public PaletteCellNode(String _name /*Enum _paletteNode,*/) {
        this.name = _name;
//        this.paletteNode = _paletteNode;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Enum getPaletteNode() {
//        return paletteNode;
//    }
//
//    public void setPaletteNode(Enum paletteNode) {
//        this.paletteNode = paletteNode;
//    }
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

    public boolean isCell() {
        return this.getCellFactory() != null ? true : false;
    }

    public boolean isEdge() {
        return !this.isCell();
    }
}
