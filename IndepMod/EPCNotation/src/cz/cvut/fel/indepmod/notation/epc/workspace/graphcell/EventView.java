package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class EventView extends VertexView {

    private static transient CellViewRenderer renderer = new EventRenderer();

    public EventView(EventCell o) {
        super(o);
    }

    public EventView() {
    }

    protected static void setRenderer(EventRenderer aRenderer) {
        renderer = aRenderer;
    }

    @Override
    public CellViewRenderer getRenderer() {
        return EventView.renderer;
    }
}
