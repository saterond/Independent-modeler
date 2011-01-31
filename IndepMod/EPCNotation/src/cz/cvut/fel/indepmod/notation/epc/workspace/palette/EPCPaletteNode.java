package cz.cvut.fel.indepmod.notation.epc.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNode;

/**
 *
 * @author Petr Vales
 * @deprecated 
 */
public final class EPCPaletteNode extends PaletteNode {

    private EPCPaletteNodeModel paletteNode;

    public EPCPaletteNode() {
        this(null, null);
    }

    public EPCPaletteNode(String _name, Enum _paletteNode) {
        super(_name, null,null);
        this.setPaletteNode(_paletteNode);
    }

    @Override
    public Enum getPaletteNode() {
        return paletteNode;
    }

    public void setPaletteNode(EPCPaletteNodeModel paletteNode) {
        this.paletteNode = paletteNode;
    }
}
