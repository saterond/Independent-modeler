/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexView;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelVertexView extends VertexView {

    private static final SequenceModelVertexRenderer RENDERER = new SequenceModelVertexRenderer();
    private static final GraphCellEditor EDITOR = new SequenceModelVertexEditor();

    public SequenceModelVertexView() {
    }

    public SequenceModelVertexView(Object o) {
        super(o);
    }

    @Override
    public CellViewRenderer getRenderer() {
        return RENDERER;
    }

    @Override
    public GraphCellEditor getEditor() {
        return EDITOR;
    }
}