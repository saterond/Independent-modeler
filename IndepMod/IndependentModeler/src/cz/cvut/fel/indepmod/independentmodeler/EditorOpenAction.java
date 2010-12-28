/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.indepmod.independentmodeler;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Editor;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class EditorOpenAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CellFactory cellFactory = new CellFactory();
        Editor editor = new Editor("Test Editor", 
                                    new CategoryChildrenFactory(),
                                    new IndependentModelerTransferHandler(
                                        cellFactory),
                                    cellFactory);
        editor.open();
        editor.requestActive();
    }
}
