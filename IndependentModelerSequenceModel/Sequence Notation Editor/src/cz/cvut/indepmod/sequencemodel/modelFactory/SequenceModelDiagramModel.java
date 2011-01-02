/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.modelFactory;

import cz.cvut.indepmod.sequencemodel.editor.cell.SequenceModelCellViewFactory;
import javax.swing.undo.UndoManager;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelDiagramModel {

    private GraphLayoutCache layoutCache;
    private UndoManager undoManager;

    public SequenceModelDiagramModel() {
        this.layoutCache = new GraphLayoutCache(
                new DefaultGraphModel(),
                new SequenceModelCellViewFactory()
        );

        this.undoManager = new UndoManager();
        this.layoutCache.getModel().addUndoableEditListener(this.undoManager);
    }

    public GraphLayoutCache getLayoutCache() {
        return layoutCache;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

}