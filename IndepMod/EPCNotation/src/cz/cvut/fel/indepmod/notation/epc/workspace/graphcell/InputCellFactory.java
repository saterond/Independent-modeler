package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class InputCellFactory implements ICellFactory<InputCell> {

    @Override
    public InputCell creta() {
        return new InputCell();
    }

}
