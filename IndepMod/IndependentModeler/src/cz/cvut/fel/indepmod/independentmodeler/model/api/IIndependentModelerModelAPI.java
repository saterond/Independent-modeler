package cz.cvut.fel.indepmod.independentmodeler.model.api;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCell;
import java.util.Collection;

/**
 *
 * @author Petr Vales
 */
public interface IIndependentModelerModelAPI {
    public Collection<Cell> getCells();
    public Collection<NoteCell> getNotes();
}
