package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OutputCell;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class OutputNode extends CellNode {

    private transient OutputCell cell;

    public OutputNode(OutputCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    @Override
    public Action[] getActions(boolean context) {
        return null;
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.cell.getTitle() + "</b>";
    }

    @Override
    public String getDisplayName() {
        return this.cell.getTitle();
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = this.fillSetWithCommonProperties(Sheet.createPropertiesSet());
        Sheet.Set set2 = this.fillSetWithOutputProperties(Sheet.createPropertiesSet());
        sheet.put(set);
        sheet.put(set2);
        return sheet;
    }

    protected Sheet.Set fillSetWithOutputProperties(Sheet.Set set) {
        set.setDisplayName("Output");
        set.setName("Output");
        try {
            set.put(this.createTitleProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    private Property createTitleProperty() throws NoSuchMethodException {
        Property functionTypeProp = new PropertySupport.Reflection(this.getCell(),
                String.class, "title");
        functionTypeProp.setName("Title");
        return functionTypeProp;
    }
}
