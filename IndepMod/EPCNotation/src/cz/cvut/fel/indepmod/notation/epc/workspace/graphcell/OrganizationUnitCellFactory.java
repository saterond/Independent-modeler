package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;
import java.awt.Color;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Petr Vales
 */
public class OrganizationUnitCellFactory implements ICellFactory<OrganizationUnitCell> {

    @Override
    public OrganizationUnitCell creta() {
        OrganizationUnitCell cell = new OrganizationUnitCell();
        GraphConstants.setBackground(cell.getAttributes(), Color.WHITE);
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        return cell;
    }
}
