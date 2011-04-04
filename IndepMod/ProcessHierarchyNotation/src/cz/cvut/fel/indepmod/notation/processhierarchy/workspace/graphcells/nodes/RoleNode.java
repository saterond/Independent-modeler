package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.RoleCell;
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
public class RoleNode extends CellNode {

    private transient RoleCell cell;

    public RoleNode() {
        super(Children.LEAF);
        this.setDisplayName("Role");
    }

    public RoleNode(RoleCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Role");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.cell.getRoleName() + "<b>";
    }

    @Override
    public String getDisplayName() {
        return this.cell.getRoleName();
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    public void setCell(RoleCell cell) {
        this.cell = cell;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = this.fillSetWithCommonProperties(Sheet.createPropertiesSet());
        Sheet.Set set2 = this.fillSetWithRoleProperties(Sheet.createPropertiesSet());
        sheet.put(set);
        sheet.put(set2);
        return sheet;
    }

    protected Sheet.Set fillSetWithRoleProperties(Sheet.Set set) {
        set.setDisplayName("Role");
        set.setName("Role");
        try {
            set.put(this.createNameProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    private Property createNameProperty() throws NoSuchMethodException {
        Property nameProp = new PropertySupport.Reflection(this.getCell(),
                String.class, "roleName");
        nameProp.setName("Name");
        return nameProp;
    }
}
