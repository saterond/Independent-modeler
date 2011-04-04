package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.FunctionCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.FunctionType;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class FunctionNode extends CellNode {

    private transient FunctionCell cell;

    public FunctionNode(FunctionCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.cell.getType().name() + "<b>";
    }

    @Override
    public String getDisplayName() {
        return this.cell.getFunction();
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = this.fillSetWithCommonProperties(Sheet.createPropertiesSet());
        Sheet.Set set2 = this.fillSetWithFunctionProperties(Sheet.createPropertiesSet());
        sheet.put(set);
        sheet.put(set2);
        return sheet;
    }

    protected Sheet.Set fillSetWithFunctionProperties(Sheet.Set set) {
        set.setDisplayName("Function");
        set.setName("Function");
        try {
            set.put(this.createFunctionTypeProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    private Property createFunctionTypeProperty() throws NoSuchMethodException {
        Property functionTypeProp = new PropertySupport.Reflection(this.getCell(),
                FunctionType.class, "type");
        functionTypeProp.setName("Function");
        return functionTypeProp;
    }
}
