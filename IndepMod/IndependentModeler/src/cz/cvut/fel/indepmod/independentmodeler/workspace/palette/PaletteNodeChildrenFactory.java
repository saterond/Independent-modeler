package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCellFactory;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Petr Vales
 */
public class PaletteNodeChildrenFactory extends ChildFactory<PaletteNode> {

    private final String key;

    public PaletteNodeChildrenFactory(final String myNodeKey) {
        this.key = myNodeKey;
    }

    public String getKey() {
        return key;
    }

    @Override
    protected boolean createKeys(final List<PaletteNode> toPopulate) {
        if (this.getKey().equals("Common")) {
            toPopulate.add(new PaletteNode(
                    IndependentModelerPaletteNodeModel.Note.name(),
                    IndependentModelerPaletteNodeModel.Note,
                    new NoteCellFactory()));
            toPopulate.add(new PaletteNode(
                    IndependentModelerPaletteNodeModel.Dependency.name(),
                    IndependentModelerPaletteNodeModel.Dependency,
                    null));
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Node createNodeForKey(final PaletteNode myNodeKey) {
        IndependentModelerPaletteNode n = new IndependentModelerPaletteNode(
                Children.LEAF) {

            @Override
            public Transferable drag() throws IOException {
                return myNodeKey;
            }
        };
        n.setName(myNodeKey.getName());
        n.setDisplayName(myNodeKey.getName());
        n.setType(myNodeKey.getPaletteNode());
        n.setPaletteNode(myNodeKey);
        return n;
    }
}
