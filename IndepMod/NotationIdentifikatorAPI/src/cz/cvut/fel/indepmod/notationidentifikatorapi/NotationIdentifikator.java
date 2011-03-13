package cz.cvut.fel.indepmod.notationidentifikatorapi;

import java.io.IOException;
import java.io.ObjectInput;

/**
 *
 * @author Petr Vales
 */
public interface NotationIdentifikator {

    public String getName();
    public GraphNode getGraphNode();
    public GraphNode loadGraphNode(ObjectInput in)  throws ClassNotFoundException, IOException;

}
