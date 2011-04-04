package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class DataView extends VertexView {

    private static transient CellViewRenderer renderer = new DataRenderer();

    public DataView(DataCell o) {
        super(o);
//        renderer = new DataRenderer(o);
    }

//    public DataView() {
//        super();
//    }
//
//    protected static void setRenderer(ProcessRenderer aRenderer) {
//        renderer = aRenderer;
//    }

    @Override
    public synchronized CellViewRenderer getRenderer() {
        return renderer;
    }
}
