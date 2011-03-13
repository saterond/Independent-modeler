package cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdge;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Petr Vales
 */
public class ArrowEdgeNode extends AbstractNode implements Externalizable {

    private transient ArrowEdge edge;
    private Node source;
    private Node target;

    public ArrowEdgeNode() {
        super(Children.LEAF);
        this.setDisplayName(" -> ");
    }

    public ArrowEdgeNode(ArrowEdge _edge) {
        super(Children.LEAF);
        this.edge = _edge;
        this.setDisplayName(" -> ");
    }

    @Override
    public String getHtmlDisplayName() {
        if (this.source != null && this.target != null) {
            return "<b>" + this.source.getDisplayName() + this.getDisplayName() + this.target.
                    getDisplayName() + "<b>";
        } else {
            return "<b>" + this.getDisplayName() + "<b>";
        }
    }

    public ArrowEdge getEdge() {
        return edge;
    }

    @Override
    public Action[] getActions(boolean popup) {
        return null;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getDisplayName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSourceNode(Node node) {
        this.source = node;
    }

    public void setTargetNode(Node node) {
        this.target = node;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName("Common");
        try {
            set.put(this.createLineWidthProperty());
//            set.put(this.createLineColorProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sheet.put(set);
        return sheet;
    }

    private Property createLineWidthProperty() throws NoSuchMethodException {
        Property lineWidthProp = new PropertySupport.Reflection(
                this.getEdge(),
                Float.class, "lineWidth");
        lineWidthProp.setName("Line width");
        return lineWidthProp;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }
}
