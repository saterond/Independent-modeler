package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import java.awt.Color;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Petr Vales
 */
public class NoteCellFactory implements ICellFactory<NoteCell> {

    @Override
    public NoteCell creta() {
        NoteCell cell = new NoteCell();
        GraphConstants.setBackground(cell.getAttributes(), Color.WHITE);
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        return cell;
    }
}
