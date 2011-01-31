package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class IndependentModelerPaletteNode extends AbstractNode {

    private Enum type;
    private PaletteNode<? extends Cell> paletteNode;

    public IndependentModelerPaletteNode(Children children, Lookup lookup,
            PaletteNode<? extends Cell> _paletteNode) {
        super(children, lookup);
        this.paletteNode = _paletteNode;
    }

    public IndependentModelerPaletteNode(Children children) {
        super(children);
    }

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }

    public Cell getCell() {
        return this.getPaletteNode().getCell();
    }

    public PaletteNode<? extends Cell> getPaletteNode() {
        return this.paletteNode;
    }

    public void setPaletteNode(PaletteNode<? extends Cell> _paletteNode) {
        this.paletteNode = _paletteNode;
    }
}
