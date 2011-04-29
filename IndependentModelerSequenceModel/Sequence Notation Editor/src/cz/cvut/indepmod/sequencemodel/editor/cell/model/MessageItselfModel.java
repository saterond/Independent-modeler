/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import java.util.Set;
import org.jgraph.graph.DefaultEdge;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class MessageItselfModel extends MessageModel{

    public MessageItselfModel(String name, DefaultEdge edge) {
        super(name,edge);
   }

   public MessageItselfModel(String name, TypeModel type, Set<AttributeModel> attributeModels) {
        super(name,type,attributeModels);
   }

}
