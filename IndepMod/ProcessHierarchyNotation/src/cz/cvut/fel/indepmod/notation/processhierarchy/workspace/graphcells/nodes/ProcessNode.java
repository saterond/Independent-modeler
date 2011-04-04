package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.ProcessCell;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class ProcessNode extends CellNode {

    private transient ProcessCell cell;

    public ProcessNode() {
        super(Children.LEAF);
        this.setDisplayName("Process");
    }

    public ProcessNode(ProcessCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Process");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.cell.getProcessId() +
                "<b>:  <font color='!controlShadow'><i>" +
                this.cell.getName() + "</i></font>";
    }

    @Override
    public String getDisplayName() {
        return this.cell.getName();
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    public void setCell(ProcessCell cell) {
        this.cell = cell;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = this.fillSetWithCommonProperties(Sheet.createPropertiesSet());
        Sheet.Set set2 = this.fillSetWithProcessProperties(Sheet.createPropertiesSet());
        sheet.put(set);
        sheet.put(set2);
        return sheet;
    }

    protected Sheet.Set fillSetWithProcessProperties(Sheet.Set set) {
        set.setDisplayName("Process");
        set.setName("Process");
        try {
            set.put(this.createIdProperty());
            set.put(this.createNameProperty());
            set.put(this.createInfoProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    private Property createIdProperty() throws NoSuchMethodException {
        Property idProp = new PropertySupport.Reflection(this.getCell(),
                String.class, "processId");
        idProp.setName("Id");
        return idProp;
    }

    private Property createNameProperty() throws NoSuchMethodException {
        Property nameProp = new PropertySupport.Reflection(this.getCell(),
                String.class, "name");
        nameProp.setName("Name");
        return nameProp;
    }

    private Property createInfoProperty() throws NoSuchMethodException {
        Property infoProp = new PropertySupport.Reflection(this.getCell(),
                String.class, "info");
        infoProp.setName("Info");
        return infoProp;
    }
}
