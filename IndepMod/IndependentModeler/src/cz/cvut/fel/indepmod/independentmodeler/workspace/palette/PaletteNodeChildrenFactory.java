package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class PaletteNodeChildrenFactory extends ChildFactory < PaletteNode > {

    private final String key;

    public PaletteNodeChildrenFactory(final String myNodeKey) {
        this.key = myNodeKey;
    }

    public String getKey() {
        return key;
    }

    @Override
    protected boolean createKeys(final List < PaletteNode > toPopulate) {
        if (this.getKey().equals("Common")) {
            for (IndependentModelerPaletteNodeModel i :
                                    IndependentModelerPaletteNodeModel.values()) {
                PaletteNode node = new PaletteNode();
                node.setName(i.name());
                toPopulate.add(node);
            }
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(final PaletteNode myNodeKey) {
        Node n = new AbstractNode(Children.LEAF) {

            @Override
            public Transferable drag() throws IOException {
                return myNodeKey;
            }

        };
        n.setName(myNodeKey.getName());
        n.setDisplayName(myNodeKey.getName());
        return n;
    }
}

