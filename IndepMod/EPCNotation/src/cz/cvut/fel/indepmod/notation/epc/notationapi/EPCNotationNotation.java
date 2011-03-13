package cz.cvut.fel.indepmod.notation.epc.notationapi;

import cz.cvut.fel.indepmod.epcnotationid.EPCNotationId;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import cz.cvut.fel.indepmod.notation.epc.workspace.palette.EPCCategoryChildrenFactory;
import cz.cvut.fel.indepmod.notationapi.Notation;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import java.io.IOException;
import java.io.ObjectInput;

/**
 *
 * @author Petr Vales
 */
public class EPCNotationNotation implements Notation {

    

    @Override
    public String getName() {
        return EPCNotationId.NAME;
    }

    @Override
    public CategoryChildrenFactory getCathegoryChildrenFactory() {
        return new EPCCategoryChildrenFactory();
    }

    @Override
    public IndependentModelerTransferHandler getTransferHandler() {
        return new IndependentModelerTransferHandler();
    }

    @Override
    public GraphNode getGraphNode() {
        return new EPCNotationGraphNode();
    }

    @Override
    public GraphNode loadGraphNode(ObjectInput in) throws ClassNotFoundException, IOException {
        return (GraphNode) in.readObject();
    }

}
