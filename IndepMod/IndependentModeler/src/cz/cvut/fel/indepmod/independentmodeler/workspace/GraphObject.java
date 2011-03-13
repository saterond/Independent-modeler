package cz.cvut.fel.indepmod.independentmodeler.workspace;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes.CellNode;
import javax.swing.JPopupMenu;
import org.jgraph.JGraph;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public interface GraphObject {

    public Node getNavigatorNode();
    public Node getProjectNode();
    public void setGraph(JGraph graph);
    public JPopupMenu getPopupMenu();
    
}
