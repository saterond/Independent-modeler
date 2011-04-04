package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class OutputView extends VertexView {

    private static transient CellViewRenderer renderer = new InputOutputRenderer();

    public OutputView(OutputCell o) {
        super(o);
    }

    public OutputView() {
    }

    protected static void setRenderer(EventRenderer aRenderer) {
        renderer = aRenderer;
    }

    @Override
    public CellViewRenderer getRenderer() {
        return OutputView.renderer;
    }
}
