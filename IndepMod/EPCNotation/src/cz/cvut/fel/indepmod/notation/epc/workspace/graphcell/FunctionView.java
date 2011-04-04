package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class FunctionView extends VertexView {

    private static transient CellViewRenderer renderer = new FunctionRenderer();

    public FunctionView(FunctionCell cell) {
        super(cell);
    }
//
//    public FunctionView() {
//    }

    protected static void setRenderer(EventRenderer aRenderer) {
        renderer = aRenderer;
    }

    @Override
    public CellViewRenderer getRenderer() {
        return FunctionView.renderer;
    }
}
