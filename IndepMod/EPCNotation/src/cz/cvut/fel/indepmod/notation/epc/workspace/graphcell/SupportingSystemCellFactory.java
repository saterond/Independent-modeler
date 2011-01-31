package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class SupportingSystemCellFactory implements ICellFactory<SupportingSystemCell>{

    @Override
    public SupportingSystemCell creta() {
        return new SupportingSystemCell();
    }

}
