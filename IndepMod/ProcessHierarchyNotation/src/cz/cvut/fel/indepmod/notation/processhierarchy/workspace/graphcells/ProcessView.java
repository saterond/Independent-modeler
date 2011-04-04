package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class ProcessView extends VertexView {

    private static transient CellViewRenderer renderer = new ProcessRenderer();

//    public ProcessView() {
//        super();
//    }

    public ProcessView(ProcessCell cell) {
        super(cell);
//        renderer = new ProcessRenderer(cell);
    }

    @Override
    public synchronized CellViewRenderer getRenderer() {
        return renderer;
    }
}
