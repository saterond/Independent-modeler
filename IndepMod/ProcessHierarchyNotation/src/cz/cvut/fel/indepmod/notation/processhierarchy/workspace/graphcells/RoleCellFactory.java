package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;
import java.awt.Color;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Petr Vales
 */
public class RoleCellFactory implements ICellFactory<RoleCell>{

    @Override
    public RoleCell creta() {
        RoleCell cell = new RoleCell();
        GraphConstants.setBackground(cell.getAttributes(), Color.WHITE);
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        return cell;
    }

}
