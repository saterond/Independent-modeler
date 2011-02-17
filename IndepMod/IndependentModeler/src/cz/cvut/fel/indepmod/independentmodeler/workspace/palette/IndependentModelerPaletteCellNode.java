package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Petr Vales
 */
public class IndependentModelerPaletteCellNode extends AbstractNode {

    private PaletteCellNode<? extends Cell> paletteNode;

    public IndependentModelerPaletteCellNode(
            PaletteCellNode<? extends Cell> _paletteNode) {
        super(Children.LEAF);
        this.paletteNode = _paletteNode;
        this.setName(_paletteNode.getName());
        this.setDisplayName(_paletteNode.getName());
    }

    public IndependentModelerPaletteCellNode() {
        super(Children.LEAF);
    }

    public Cell getCell() {
        return this.getPaletteNode().getCell();
    }

    public PaletteCellNode<? extends Cell> getPaletteNode() {
        return this.paletteNode;
    }

    public void setPaletteNode(PaletteCellNode<? extends Cell> _paletteNode) {
        this.paletteNode = _paletteNode;
    }

}
