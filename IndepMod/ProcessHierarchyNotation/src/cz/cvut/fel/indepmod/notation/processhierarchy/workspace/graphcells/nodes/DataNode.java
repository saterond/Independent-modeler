package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.DataCell;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class DataNode extends CellNode {

    private transient DataCell cell;

    public DataNode(DataCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Data");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.cell.getDataName() + "<b>";
    }

    @Override
    public String getDisplayName() {
        return this.cell.getDataName();
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = this.fillSetWithCommonProperties(Sheet.createPropertiesSet());
        Sheet.Set set2 = this.fillSetWithDataProperties(Sheet.createPropertiesSet());
        sheet.put(set);
        sheet.put(set2);
        return sheet;
    }

    protected Sheet.Set fillSetWithDataProperties(Sheet.Set set) {
        set.setDisplayName("Data");
        set.setName("Data");
        try {
            set.put(this.createNameProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    private Property createNameProperty() throws NoSuchMethodException {
        Property nameProp = new PropertySupport.Reflection(this.getCell(),
                String.class, "dataName");
        nameProp.setName("Name");
        return nameProp;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    public void setCell(DataCell cell) {
        this.cell = cell;
    }
}
