package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCellFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdgeFactory;
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
public class PaletteNodeChildrenFactory extends ChildFactory<IPaletteNode> {

    private final String key;

    public PaletteNodeChildrenFactory(final String myNodeKey) {
        this.key = myNodeKey;
    }

    public String getKey() {
        return key;
    }

    @Override
    protected boolean createKeys(final List<IPaletteNode> toPopulate) {
        if (this.getKey().equals("Common")) {
            toPopulate.add(new PaletteCellNode<NoteCell>(
                    IndependentModelerPaletteNodeModel.Note.name(),
                    //                    IndependentModelerPaletteNodeModel.Note,
                    new NoteCellFactory()));
            toPopulate.add(new PaletteEdgeNode(
                    "Edge",
                    new ArrowEdgeFactory()));
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(final IPaletteNode myNodeKey) {
        Node ret = null;
        if (myNodeKey instanceof PaletteCellNode) {
            PaletteCellNode<? extends Cell> paletteCellNode =
                    (PaletteCellNode<? extends Cell>) myNodeKey;
            ret = this.createNodeForCellKey(paletteCellNode);
        } else if (myNodeKey instanceof PaletteEdgeNode) {
            PaletteEdgeNode paletteEdgeNode = (PaletteEdgeNode) myNodeKey;
            ret = this.createNodeForEdgeKey(paletteEdgeNode);
        }
        return ret;
    }

    private Node createNodeForCellKey(
            final PaletteCellNode<? extends Cell> myNodeKey) {
        IndependentModelerPaletteCellNode n = new IndependentModelerPaletteCellNode(
                myNodeKey) {

            @Override
            public Transferable drag() throws IOException {
                return myNodeKey;
            }
        };
        return n;
    }

    private Node createNodeForEdgeKey(final PaletteEdgeNode myNodeKey) {
        IndependentModelerPaletteEdgeNode n =
                new IndependentModelerPaletteEdgeNode(Children.LEAF);
        n.setName(myNodeKey.getName());
        n.setDisplayName(myNodeKey.getName());
        n.setPaletteNode(myNodeKey);
        return n;
    }
}
