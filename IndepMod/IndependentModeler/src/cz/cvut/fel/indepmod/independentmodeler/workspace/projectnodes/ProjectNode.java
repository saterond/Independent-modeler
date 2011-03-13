package cz.cvut.fel.indepmod.independentmodeler.workspace.projectnodes;

import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import cz.cvut.fel.indepmod.notationidentifikatorapi.NotationIdentifikator;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.actions.Presenter;

/**
 *
 * @author Petr Vales
 */
public class ProjectNode extends AbstractNode implements Externalizable {

    private File rootDir;
    private String projectFileName;
    private static final long serialVersionUID = 1000000001;

    public ProjectNode() {
        super(new Children.Array());
    }

    public ProjectNode(String displayName, File path) {
        super(new Children.Array());
        this.setDisplayName(displayName);
        this.rootDir = path;
        this.projectFileName = displayName;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new NewGraphAction(), new RenameAction()};
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + this.getDisplayName() + "</b>";
    }

    public void addGraph(Node node) {
        if (node != null) {
            Node[] nodes = {node};
            this.getChildren().add(nodes);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getDisplayName());
        out.writeObject(this.getRootDir());
        out.writeObject(this.projectFileName);
        List<String> notationIds = new ArrayList<String>();
        List<GraphNode> nodes = new ArrayList<GraphNode>();
        for (Node node : this.getChildren().getNodes()) {
            if (node instanceof GraphNode) {
                notationIds.add(((GraphNode) node).getNotationId());
            }
        }
        out.writeObject(notationIds);
        for (Node node : this.getChildren().getNodes()) {
            if (node instanceof GraphNode) {
                out.writeObject(node);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        this.setDisplayName((String) in.readObject());
        this.rootDir = (File) in.readObject();
        this.projectFileName = (String) in.readObject();
        List<String> notationIds = (List<String>) in.readObject();
        List<Node> nodes = new ArrayList<Node>();
        for (Iterator<String> it = notationIds.iterator(); it.hasNext();) {
            String notationId = it.next();
            for (NotationIdentifikator notation : Lookup.getDefault().lookupAll(
                    NotationIdentifikator.class)) {
                if (notation.getName().equals(notationId)) {
                    GraphNode loadGraphNode = notation.loadGraphNode(in);
                    nodes.add(loadGraphNode);
                }
            }
        }
        this.getChildren().add(nodes.toArray(new Node[]{}));
        
    }

    public File getFile() {
        return new File(rootDir, this.projectFileName + ".imp");
    }

    public File getRootDir() {
        return this.rootDir;
    }

    protected void addChildrens(Node[] nodes) {
        if (nodes != null) {
            this.getChildren().add(nodes);
        }
    }

    private class NewGraphAction extends AbstractAction implements
            Presenter.Popup {

        public NewGraphAction() {
            putValue(NAME, "New graph");
        }

        //TODO refactor
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> list = new ArrayList<String>();
            for (NotationIdentifikator notation : Lookup.getDefault().lookupAll(
                    NotationIdentifikator.class)) {
                list.add(notation.getName());
            }
            Object select = JOptionPane.showInputDialog(
                    null,
                    "Choos diagram: ",
                    "Choos diagram",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    list.toArray(new String[0]),
                    0);
            for (NotationIdentifikator notation : Lookup.getDefault().lookupAll(
                    NotationIdentifikator.class)) {
                if (notation.getName().equals(select)) {
                    String name = JOptionPane.showInputDialog(
                            null,
                            "Graph name: ",
                            "Graph name: ",
                            JOptionPane.PLAIN_MESSAGE);
                    if (name != null) {
                        GraphNode node = notation.getGraphNode();
                        node.setName(name);
                        node.setDisplayName(name);
                        File rootDir = new File(getRootDir() + File.separator
                                + name + File.separator);
                        rootDir.mkdir();
                        node.setRootDir(rootDir);
                        addGraph(node);
                    }
                }
            }

        }

        @Override
        public JMenuItem getPopupPresenter() {
            return new JMenuItem(this);
        }
    }

    private class RenameAction extends AbstractAction implements Presenter.Popup {

        public RenameAction() {
            putValue(NAME, "Rename");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String newName = JOptionPane.showInputDialog("Rename:",
                    getDisplayName());
            setDisplayName(newName);
        }

        @Override
        public JMenuItem getPopupPresenter() {
            JMenuItem item = new JMenuItem(this);
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                    InputEvent.CTRL_MASK));
            return item;
        }
    }
}
