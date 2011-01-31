package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class DataCellFactory implements ICellFactory<DataCell>{

    @Override
    public DataCell creta() {
        return new DataCell();
    }

}
