package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class EventCellFactory implements ICellFactory<EventCell> {

    @Override
    public EventCell creta() {
        return new EventCell();
    }

}
