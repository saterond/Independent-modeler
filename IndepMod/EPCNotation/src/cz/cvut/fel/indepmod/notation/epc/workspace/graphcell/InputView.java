package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class InputView extends VertexView {

    private static transient CellViewRenderer renderer = new InputOutputRenderer();

    public InputView(InputCell o) {
        super(o);
    }

    public InputView() {
    }

    protected static void setRenderer(EventRenderer aRenderer) {
        renderer = aRenderer;
    }

    @Override
    public CellViewRenderer getRenderer() {
        return InputView.renderer;
    }
}
