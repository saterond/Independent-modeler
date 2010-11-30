package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.api.model.Cardinality;
import cz.cvut.indepmod.classmodel.api.model.IClass;
import cz.cvut.indepmod.classmodel.api.model.IRelation;
import cz.cvut.indepmod.classmodel.api.model.RelationType;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

/**
 * Date: 30.11.2010
 * Time: 15:43:51
 * @author Lucky
 */
public abstract class AbstractRelationModel implements IRelation {

    private RelationType type;
    protected DefaultEdge cell;

    public AbstractRelationModel() {
        this.cell = null;
    }

    public void setCell(DefaultEdge cell) {
        this.cell = cell;
    }

    @Override
    public IClass getStartingClass() {
        this.verifyCell();

        DefaultPort p = (DefaultPort) this.cell.getSource();
        DefaultGraphCell c = (DefaultGraphCell) p.getParent();
        IClass clazz = (IClass) c.getUserObject();

        return clazz;
    }

    @Override
    public IClass getEndingClass() {
        this.verifyCell();

        DefaultPort p = (DefaultPort) this.cell.getTarget();
        DefaultGraphCell c = (DefaultGraphCell) p.getParent();
        IClass clazz = (IClass) c.getUserObject();

        return clazz;
    }

    @Override
    public String toString() {
        return "";
    }

    private void verifyCell() {
        if (this.cell == null) {
            throw new NullPointerException("Cell of RelationModel was not sat.");
        }
    }
}
