package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

/**
 *
 * @author Petr Vales
 */
public class NoteCellFactory implements ICellFactory<NoteCell> {

    @Override
    public NoteCell creta() {
        return new NoteCell();
    }

}
