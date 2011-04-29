/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.LifelineBottomModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.logging.Logger;
import org.jgraph.graph.AbstractCellView;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellHandle;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.VertexView;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelVertexView extends VertexView {

    private static final Logger LOG = Logger.getLogger(SequenceModelVertexView.class.getName());
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


    @Override
    public CellHandle getHandle(GraphContext context) {
		if (GraphConstants.isSizeable(getAllAttributes())
				&& !GraphConstants.isAutoSize(getAllAttributes())
				&& context.getGraph().isSizeable())
			return new SequenceSizeHandle(this, context);
		return null;
	}
    
    public static class SequenceSizeHandle extends VertexView.SizeHandle{
    
        
        public SequenceSizeHandle(VertexView vertexview, GraphContext ctx) {
            super(vertexview,ctx);
        }


            public GraphContext getGraphContext(){
                return super.context;
            }


        /*
         * compute size of new bound
         *
           public Rectangle2D resizeBounds(MouseEvent e,Rectangle2D bound){
                double left = bound.getX();
		double right = bound.getX() + bound.getWidth() - 1;
		double top = bound.getY();
		double bottom = bound.getY() + bound.getHeight() - 1;

                Point2D p = super.graph.fromScreen(super.graph.snap((Point2D) e.getPoint()
					.clone()));

                p.setLocation(Math.max(0, p.getX()), Math.max(0, p.getY()));

                if(super.index > 4){
                    bottom = p.getY();
                }

                double width = right - left;
		double height = bottom - top;

                return new Rectangle2D.Double(left, top, width + 1, height + 1);
        }
           */

        @Override
        public void mouseReleased(MouseEvent e) {
            try {
                //resize Lifeline cells
                Object cell = super.vertex.getCell();
                if (cell instanceof DefaultGraphCell) {
                    DefaultGraphCell tmp = (DefaultGraphCell) cell;
                    if (tmp.getUserObject() instanceof SequenceObjectModel) {
                        if(super.graph instanceof SequenceModelGraph){
                            SequenceModelGraph seqGraph = (SequenceModelGraph) super.graph;
                            seqGraph.resizeLifelines(e.getY());
                        }
                    }
                }
            } catch (NullPointerException ex) {
                LOG.severe("Resize was performed on wrong vertex");
            } catch (ClassCastException ex) {
                LOG.severe("Resize was performed on different vertex than class");
            }
            super.mouseReleased(e);
        }
    }
}
