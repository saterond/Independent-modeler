package cz.cvut.fel.indepmod.notation.processhierarchy.notationapi;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.palette.ProcessHierarchyCategoryChildrenFactory;
import cz.cvut.fel.indepmod.notationapi.Notation;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import cz.cvut.fel.indepmod.processhierarchynotation.id.ProcessHierarchyNotationId;
import java.io.IOException;
import java.io.ObjectInput;

/**
 *
 * @author Petr Vales
 */
public class ProcessHierarchyNotation implements Notation {

    @Override
    public CategoryChildrenFactory getCathegoryChildrenFactory() {
        return new ProcessHierarchyCategoryChildrenFactory();
    }

    @Override
    public IndependentModelerTransferHandler getTransferHandler() {
        return new IndependentModelerTransferHandler();
    }

    @Override
    public String getName() {
        return ProcessHierarchyNotationId.NAME;
    }

    @Override
    public GraphNode getGraphNode() {
        return new ProcessHierarchyGraphNode();
    }

    @Override
    public GraphNode loadGraphNode(ObjectInput in) throws ClassNotFoundException, IOException {
        return (GraphNode) in.readObject();
    }
}
