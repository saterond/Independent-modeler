/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.plaf.basic;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ReturnMessageModel;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.jgraph.graph.CellHandle;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.VertexView;
import org.jgraph.plaf.basic.BasicGraphUI;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelBasicGraphUI extends BasicGraphUI{

    private static final Logger LOG = Logger.getLogger(SequenceModelBasicGraphUI.class.getName());
    SequenceModelGraph sequenceGraph;

    public SequenceModelBasicGraphUI(){
        super();
        sequenceGraph = (SequenceModelGraph)super.graph;
    }

        /**
	 * Constructs the "root handle" for <code>context</code>.
	 *
	 * @param context
	 *            reference to the context of the current selection.
	 */
    @Override
    public CellHandle createHandle(GraphContext context) {
		if (context != null && !context.isEmpty() && super.graph.isEnabled()) {
			try {
				return new SequenceRootHandle(context);
			} catch (NullPointerException e) {
				// ignore for now...
			}
		}
		return null;
    }





    public class SequenceRootHandle extends BasicGraphUI.RootHandle{

        private List<DefaultEdge> edges;
        private boolean fragmentMoving;
        private DefaultGraphCell fragmentCell;
        private Rectangle2D fragmentBound;

        public SequenceRootHandle(GraphContext ctx){
            super(ctx);
            fragmentMoving = false;
        }

        @Override
        public void mousePressed(MouseEvent event) {
            super.mousePressed(event);
            try{
                DefaultGraphCell cell = (DefaultGraphCell) graph.getSelectionCell();
                if(cell.getUserObject() instanceof FragmentModel){
                    fragmentMoving = true;
                    fragmentCell = cell;
                    fragmentBound = graph.getCellBounds(cell);
                    Object [] cells = graph.getRoots(fragmentBound.getBounds());
                    edges = new ArrayList<DefaultEdge>();

                    for(Object obj : cells){
                        Rectangle2D tmpBound = graph.getCellBounds(obj);
                        if(fragmentBound.contains(tmpBound)){
                            if(obj instanceof DefaultEdge){
                                DefaultEdge edge = (DefaultEdge) obj;
                                Object model = edge.getUserObject();
                                if(model != null && (model instanceof MessageModel || model instanceof ReturnMessageModel)){
                                    edges.add(edge);
                                }
                            }
                        }
                    }
                }
            } catch (NullPointerException ex) {
            LOG.severe("NullPointerException!");
            } catch (ClassCastException ex) {
            LOG.severe("ClassCastException!");
            
          }
        }

        @Override
        public void mouseReleased(MouseEvent event){
            super.mouseReleased(event);

                if(fragmentMoving && edges.size() > 0){
                    Rectangle2D actualFragmentBound = graph.getCellBounds(fragmentCell);

                    if(fragmentBound.getY() != actualFragmentBound.getY()){
                        double shift = actualFragmentBound.getY() - fragmentBound.getY();

                        if(shift > 0) Collections.reverse(edges);
                        
                        for(DefaultEdge edge : edges){
                            Rectangle2D edgeBound = graph.getCellBounds(edge);
                            Rectangle2D edgeTargetBound = new Rectangle2D.Double(edgeBound.getX(), edgeBound.getY() + shift, edgeBound.getWidth(), edgeBound.getHeight());

                            if(actualFragmentBound.contains(edgeTargetBound)){
                                double sourceY = graph.getCellBounds(edge.getSource()).getCenterY();
                                double targetY = graph.getCellBounds(edge.getTarget()).getCenterY() + shift;
                                Point2D edgeTargetLocation = new Point2D.Double(edgeTargetBound.getCenterX(),targetY);

                                CellView cv = graph.getLeafViewAt(edgeBound.getCenterX(), sourceY);

                                if(cv != null && cv instanceof EdgeView){

                                    if(cv.getCell().equals(edge)){
                                        ((SequenceModelGraph) graph).moveWithEdge((EdgeView) cv, edgeTargetLocation);
                                    }
                                }
                            }
                        }
                    }
                    edges = new ArrayList<DefaultEdge>();
                    fragmentBound = null;
                    fragmentCell = null;
                    fragmentMoving = false;
                }
            }

    }

}
