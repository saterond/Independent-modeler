package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;

/**
 *
 * @author Petr Vales
 */
public class RoleCellFactory implements ICellFactory<RoleCell>{

    @Override
    public RoleCell creta() {
        return new RoleCell();
    }

}
