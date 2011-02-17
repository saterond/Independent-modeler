package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Graph;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class GraphNode extends AbstractNode {

    private Graph graph;

    public GraphNode(Graph _graph) {
        super(new Children.Array());
        this.graph = _graph;
        this.setDisplayName("Graph");
    }

    @Override
    public String getHtmlDisplayName() {
        return "Graph";
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();

        try {

            Property nameProp = new PropertySupport.Reflection(this.graph,
                    Boolean.class, "gridVisibility");

            nameProp.setName("Grid visible");


            set.put(nameProp);

        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sheet.put(set);
        return sheet;
    }

    public void addChildren(Node node) {
        Node[] nodes = {node};
        this.getChildren().add(nodes);
    }
}
