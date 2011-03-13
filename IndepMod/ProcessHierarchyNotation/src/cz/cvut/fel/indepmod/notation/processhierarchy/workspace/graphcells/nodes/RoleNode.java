package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.RoleCell;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class RoleNode extends CellNode implements Externalizable {

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
        return "<b>" + this.getDisplayName() + "<b>";
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getDisplayName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setDisplayName((String) in.readObject());
    }
}
