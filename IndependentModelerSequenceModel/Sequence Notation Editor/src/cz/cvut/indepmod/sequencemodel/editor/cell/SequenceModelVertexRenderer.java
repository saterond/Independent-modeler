/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell;

import cz.cvut.indepmod.sequencemodel.editor.cell.component.MessageComponent;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import java.awt.Component;
import java.util.logging.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexRenderer;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelVertexRenderer  extends VertexRenderer {

    private static final Logger LOG = Logger.getLogger(SequenceModelVertexRenderer.class.getName());


    @Override
    public Component getRendererComponent(JGraph jGraph, CellView cellView, boolean b, boolean b1, boolean b2) {
        DefaultGraphCell cell = (DefaultGraphCell) cellView.getCell();
        Object userObject = cell.getUserObject();

        if (userObject instanceof MessageModel) {
            //return new MessageComponent((MessageModel) userObject);
        } else {
            //LOG.severe("unknown cell type! User Object of the cell is of " + userObject.getClass().getName() + " type");
            //return super.getRendererComponent(jGraph,cellView,b,b1,b2);
        }
        return super.getRendererComponent(jGraph,cellView,b,b1,b2);
    }
}