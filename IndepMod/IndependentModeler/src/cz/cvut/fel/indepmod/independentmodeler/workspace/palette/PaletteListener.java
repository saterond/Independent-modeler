package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.jgraph.graph.DefaultEdge;
import org.netbeans.spi.palette.PaletteController;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class PaletteListener implements PropertyChangeListener {

    private PaletteController paletteController;
    private PaletteCellNode<? extends Cell> paletteCellNode;
    private PaletteEdgeNode paletteEdgeNode;

    public PaletteListener(final PaletteController palette) {
        this.paletteController = palette;
        this.paletteCellNode = null;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (PaletteController.PROP_SELECTED_ITEM.equals(
                evt.getPropertyName())) {
            Lookup selItem = getPaletteController().getSelectedItem();
            if (null != selItem) {
                this.lookupCellNode(selItem);
                this.lookupEdgeNode(selItem);
            }
        }
    }

    private void lookupCellNode(Lookup selItem) {
        IndependentModelerPaletteCellNode selNode = selItem.lookup(
                IndependentModelerPaletteCellNode.class);
        if (null != selNode) {
            this.setPaletteEdgeNode(null);
            this.setPaletteCellNode(selNode.getPaletteNode());
        }
    }

    private void lookupEdgeNode(Lookup selItem) {
        IndependentModelerPaletteEdgeNode selNode = selItem.lookup(
                IndependentModelerPaletteEdgeNode.class);
        if (null != selNode) {
            this.setPaletteCellNode(null);
            this.setPaletteEdgeNode(selNode.getPaletteNode());
        }
    }

    /**
     * @return the paletteController
     */
    public PaletteController getPaletteController() {
        return paletteController;
    }

    public void resetPaletteTool() {
        this.paletteController.setSelectedItem(this.paletteController.
                getSelectedCategory(), null);
        this.setPaletteCellNode(null);
        this.setPaletteEdgeNode(null);
    }

    public boolean isCellSelected() {
        return (this.getPaletteCellNode() != null)
                && (this.getPaletteCellNode().isCell()) ? true : false;
    }

    public boolean isEdgeSelected() {
        return this.getPaletteEdgeNode() != null ? true : false;
    }

    protected PaletteCellNode<? extends Cell> getPaletteCellNode() {
        return paletteCellNode;
    }

    protected void setPaletteCellNode(
            PaletteCellNode<? extends Cell> paletteNode) {
        this.paletteCellNode = paletteNode;
    }

    public Cell getCell() {
        return this.getPaletteCellNode().getCell();
    }

    protected void setPaletteEdgeNode(PaletteEdgeNode paletteNode) {
        this.paletteEdgeNode = paletteNode;
    }

    protected PaletteEdgeNode getPaletteEdgeNode() {
        return this.paletteEdgeNode;
    }

    public DefaultEdge getEdge() {
        return this.getPaletteEdgeNode().getEdge();
    }
}
