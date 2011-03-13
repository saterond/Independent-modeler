package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCell;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.Action;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class NoteNode extends CellNode implements Externalizable {

    private transient NoteCell cell;

    public NoteNode(NoteCell _cell) {
        super(Children.LEAF);
        this.cell = _cell;
        this.setDisplayName("Note");
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>Note<b>";
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        return sheet;
    }

    @Override
    public Cell getCell() {
        return this.cell;
    }

    public void setCell(NoteCell _cell) {
        this.cell = _cell;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getDisplayName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
