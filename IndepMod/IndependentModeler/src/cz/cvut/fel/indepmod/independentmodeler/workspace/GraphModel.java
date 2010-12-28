package cz.cvut.fel.indepmod.independentmodeler.workspace;

import java.util.List;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphModel;

/**
 * Created by IntelliJ IDEA.
 * User: Lucky
 * Date: 30.9.2010
 * Time: 15:28:58
 */
public class GraphModel extends DefaultGraphModel {

    public GraphModel() {
        super();
    }

    public GraphModel(final List roots, final AttributeMap attributes){
        super(roots, attributes);
    }

}
