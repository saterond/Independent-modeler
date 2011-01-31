package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class FunctionCellFactory implements ICellFactory<FunctionCell> {

    @Override
    public FunctionCell creta() {
        return new FunctionCell();
    }

}
