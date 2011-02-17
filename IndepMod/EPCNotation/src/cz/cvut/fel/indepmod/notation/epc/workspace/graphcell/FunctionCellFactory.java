package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.ICellFactory;
import java.awt.Color;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Petr Vales
 */
public class FunctionCellFactory implements ICellFactory<FunctionCell> {

    @Override
    public FunctionCell creta() {
        FunctionCell cell = new FunctionCell();
        GraphConstants.setBackground(cell.getAttributes(), Color.WHITE);
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        return cell;
    }

}
