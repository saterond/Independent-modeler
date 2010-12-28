package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.spi.palette.PaletteController;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class PaletteListener implements PropertyChangeListener {

    private PaletteController paletteController;
    private String selectedTool;

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
                Node selNode = selItem.lookup(Node.class);
                if (null != selNode) {
//                    this.findIndependentModelerPaletteNodeModel(selNode);
                    setSelectedTool(selNode.getName());
                } else {
                    this.setSelectedTool(null);
                }
            } else {
                this.setSelectedTool(null);
            }
        }
    }

//    private void findIndependentModelerPaletteNodeModel(Node selNode) {
//        for (IndependentModelerPaletteNodeModel i :
//                        IndependentModelerPaletteNodeModel.values()) {
//            if (i.name().contains(selNode.getName())) {
//                this.setSelectedTool(i);
//            }
//        }
//    }

    public String getSelectedTool() {
        return this.selectedTool;
    }

    /**
     * @param selected the selectedTool to set
     */
    private void setSelectedTool(final String selected) {
        this.selectedTool = selected;
    }

    /**
     * @return the paletteController
     */
    public PaletteController getPaletteController() {
        return paletteController;
    }

    public void resetPaletteTool() {
        this.paletteController.setSelectedItem(this.paletteController.getSelectedCategory(), null);
    }

    public boolean isSelectedTool() {
        return this.getSelectedTool() == null? false: true;
    }
}
