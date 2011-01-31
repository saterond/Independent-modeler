package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Petr Vales
 */
public class OrganizationUnitView extends VertexView {

    private static transient CellViewRenderer renderer = new OrganizationUnitRenderer();

    public OrganizationUnitView(Object o) {
        super(o);
    }

    public OrganizationUnitView() {
    }

    protected static void setRenderer(EventRenderer aRenderer) {
        renderer = aRenderer;
    }

    @Override
    public CellViewRenderer getRenderer() {
        return OrganizationUnitView.renderer;
    }
}
