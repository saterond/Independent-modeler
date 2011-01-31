package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class ProcessCellFactory implements ICellFactory<ProcessCell>{

    @Override
    public ProcessCell creta() {
        return new ProcessCell();
    }

}
