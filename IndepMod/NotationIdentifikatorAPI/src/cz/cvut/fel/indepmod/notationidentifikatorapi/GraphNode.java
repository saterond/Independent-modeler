package cz.cvut.fel.indepmod.notationidentifikatorapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
abstract public class GraphNode extends AbstractNode {

    private File rootDir;

    public GraphNode(Children children) {
        super(children);
        rootDir = null;
    }

    public File getRootDir() {
        return rootDir;
    }

    public void setRootDir(File _rootDir) {
        rootDir = _rootDir;
    }

    abstract public String getNotationId();

    public void addChildren(GraphNode node) {
        if (this.getChildren() == Children.LEAF) {
            this.setChildren(new Children.Array());
        }
        if(!this.containChildren(node)) {
            this.getChildren().add(new Node[]{node});
            File _rootDir = new File(this.getRootDir() + File.separator
                    + node.getName() + File.separator);
            _rootDir.mkdir();
            node.setRootDir(_rootDir);
        }
    }

    public boolean containChildren(GraphNode graphNode) {
        Node[] nodes = this.getChildren().getNodes();
        for (Node node : nodes) {
            if(graphNode == node) {
                return true;
            }
        }
        return false;
    }

    abstract public void saveGraph() throws FileNotFoundException, IOException;
    abstract public void loadGraph() throws FileNotFoundException, IOException, ClassNotFoundException ;
}
