package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class SupportingSystemView extends VertexView {

    private static transient CellViewRenderer renderer = new SupportingSystemRenderer();

    public SupportingSystemView(SupportingSystemCell o) {
        super(o);
    }

    public SupportingSystemView() {
    }

    protected static void setRenderer(EventRenderer aRenderer) {
        renderer = aRenderer;
    }

    @Override
    public CellViewRenderer getRenderer() {
        return SupportingSystemView.renderer;
    }
}
