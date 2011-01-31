package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class OutputCellFactory implements ICellFactory<OutputCell>{

    @Override
    public OutputCell creta() {
        return new OutputCell();
    }

}
