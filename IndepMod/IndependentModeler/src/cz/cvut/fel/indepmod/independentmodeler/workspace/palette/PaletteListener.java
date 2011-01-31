package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.spi.palette.PaletteController;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class PaletteListener implements PropertyChangeListener {

    private PaletteController paletteController;
    private String selectedTool;
    private Enum selectedToolEnum;
    private PaletteNode<? extends Cell> paletteNode;

    public PaletteListener(final PaletteController palette) {
        this.paletteController = palette;
        this.selectedTool = null;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (PaletteController.PROP_SELECTED_ITEM.equals(
                evt.getPropertyName())) {
            Lookup selItem = getPaletteController().getSelectedItem();
            if (null != selItem) {
                IndependentModelerPaletteNode selNode = selItem.lookup(
                        IndependentModelerPaletteNode.class);
                if (null != selNode) {

                    this.setSelectedTool(selNode.getName());
                    this.setSelectedToolEnum(selNode.getType());
                    this.setPaletteNode(selNode.getPaletteNode());
                } else {
                    this.setSelectedTool(null);
                }
            } else {
                this.setSelectedTool(null);
            }
        }
    }

    public String getSelectedTool() {
        return this.selectedTool;
    }

    /**
     * @param selected the selectedTool to set
     */
    protected void setSelectedTool(final String selected) {
        this.selectedTool = selected;
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
        this.setSelectedTool(null);
        this.setSelectedToolEnum(null);
    }

    public boolean isElementSelected() {
        String tool = this.getSelectedTool();
        boolean ret = true;
        if (tool == null || this.getSelectedTool().contains(
                IndependentModelerPaletteNodeModel.Dependency.name())) {
            ret = false;
        }
        return ret;
    }

    public boolean isDependencySelected() {
        String tool = this.getSelectedTool();
        boolean ret = false;
        if (tool != null && this.getSelectedTool().contains(
                IndependentModelerPaletteNodeModel.Dependency.name())) {
            ret = true;
        }
        return ret;
    }

    public Enum getSelectedToolEnum() {
        return selectedToolEnum;
    }

    private void setSelectedToolEnum(Enum selectedToolEnum) {
        this.selectedToolEnum = selectedToolEnum;
    }

    public PaletteNode<? extends Cell> getPaletteNode() {
        return paletteNode;
    }

    public void setPaletteNode(PaletteNode<? extends Cell> paletteNode) {
        this.paletteNode = paletteNode;
    }

    public Cell getCell() {
        return this.getPaletteNode().getCell();
    }
}
