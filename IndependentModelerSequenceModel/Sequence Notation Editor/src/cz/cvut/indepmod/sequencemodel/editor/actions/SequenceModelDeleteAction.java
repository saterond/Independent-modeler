/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.actions;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ReturnMessageModel;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphModel;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelDeleteAction extends SequenceModelAbstractAction{

    public static final String ACTION_NAME = "Delete";

    private static final Logger LOG = Logger.getLogger(SequenceModelDeleteAction.class.getName());

    private SequenceModelGraph graph;

    private GraphModel model;

    public SequenceModelDeleteAction(SequenceModelGraph graph) {
        super(ACTION_NAME, null);
        this.graph = graph;
        this.model = this.graph.getModel();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        LOG.fine("deleting cells");
        Object[] cells = this.graph.getSelectionCells();

        Set<Object> cellSet = new HashSet<Object>(Arrays.asList(cells)); //Transform cells into a colection


        Set<DefaultGraphCell> associatedRelations = this.getAssociatedRelations(cellSet); //get Edges and ports associated with vertices

        cellSet.addAll(associatedRelations); //add those edges (associatedRelations) into cellSet that will be deleted

        this.graph.getGraphLayoutCache().remove(cellSet.toArray());
    }

    /**
     * Returns a set of cells that are associated with the cell in param
     *
     * @param selected cells
     * @return set of cells, which are associated with an cell from cells set
     */
     private Set<DefaultGraphCell> getAssociatedRelations(Set<Object> cells) {
        Set<DefaultGraphCell> result = new HashSet<DefaultGraphCell>();

        for (Object cell : cells) {
            if(cell instanceof DefaultEdge){
                //add edges associate with source port
                Object port = model.getSource(cell);
                if(port != null){
                    result.addAll(getAssociatedCellsWithEdge((DefaultEdge)cell,(DefaultPort)port));
                }
                //add edges associate with source port
                port = model.getTarget(cell);
                if(port != null){
                    result.addAll(getAssociatedCellsWithEdge((DefaultEdge)cell,(DefaultPort)port));
                }
                //add associate edge
                DefaultEdge associateEdge = this.graph.getAssociateEdge((DefaultEdge)cell);
                if(associateEdge != null){
                    result.add(associateEdge);
                }

            }else if(cell instanceof DefaultGraphCell){
                int children = model.getChildCount(cell);
                for (int i = 0; i < children; i++) {
                    Object child = model.getChild(cell, i);
                    if (model.isPort(child)) {
                        result.add((DefaultPort)child);
                        Iterator it = model.edges(child);
                        while (it.hasNext()) {
                            result.addAll(getAssociatedCellsWithEdge((DefaultEdge)it.next(),(DefaultPort)child));
                        }
                    }else result.add((DefaultGraphCell)child);
                }
            }
        }

        return result;
    }

     private Set<DefaultGraphCell> getAssociatedCellsWithEdge(DefaultEdge edge, DefaultPort port){
        Set<DefaultGraphCell> result = new HashSet<DefaultGraphCell>();

        Iterator it = null;

        if(!edge.getTarget().equals(port)){
            DefaultPort targetPort = (DefaultPort) edge.getTarget();
            it = model.edges(targetPort);
            result.add(targetPort);
        }else{
            DefaultPort sourcePort = (DefaultPort) edge.getSource();
            it = model.edges(sourcePort);
            result.add(sourcePort);
        }

        while(it.hasNext()){
            result.add((DefaultEdge)it.next());
        }

        return result;
     }
   
}
