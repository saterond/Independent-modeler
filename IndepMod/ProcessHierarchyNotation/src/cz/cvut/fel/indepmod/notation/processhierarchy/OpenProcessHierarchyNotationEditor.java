package cz.cvut.fel.indepmod.notation.processhierarchy;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Editor;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.ProcessHierarchyCellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.palette.ProcessHierarchyCategoryChildrenFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.transferhandler.ProcessHierarchyTransferHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Petr Vales
 */
public final class OpenProcessHierarchyNotationEditor
                        implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent e) {
        CellFactory cellFactory = new ProcessHierarchyCellFactory();
        Editor editor = new Editor(
                            "Process Hierarchy",
                            new ProcessHierarchyCategoryChildrenFactory(),
                            new ProcessHierarchyTransferHandler(
                                cellFactory),
                            cellFactory);
        editor.open();
        editor.requestActive();
    }
}
