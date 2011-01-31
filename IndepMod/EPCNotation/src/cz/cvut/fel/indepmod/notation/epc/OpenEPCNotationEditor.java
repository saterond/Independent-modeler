package cz.cvut.fel.indepmod.notation.epc;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Editor;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.EPCCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.palette.EPCCategoryChildrenFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class OpenEPCNotationEditor implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CellFactory cellFactory = new EPCCellFactory();
        Editor editor = new Editor(
                            "EPC Notation",
                            new EPCCategoryChildrenFactory(),
                            new IndependentModelerTransferHandler(cellFactory),
                            cellFactory);
        editor.open();
        editor.requestActive();
    }
}
