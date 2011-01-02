/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.VertexView;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelCellViewFactory extends DefaultCellViewFactory {

    @Override
    protected VertexView createVertexView(Object o) {
        return new SequenceModelVertexView(o);
    }
}
