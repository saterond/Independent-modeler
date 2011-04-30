package cz.cvut.indepmod.classmodel.persistence.xml.delegate;

import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.RelationModel;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.Statement;

/**
 * Date: 21.11.2010
 * Time: 13:20:13
 * @author Lucky
 */
public class RelationModelPersistenceDelegate extends DefaultPersistenceDelegate {

    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
        RelationModel mm = (RelationModel) oldInstance;
        out.writeStatement(new Statement(oldInstance, "setRelationDirection", new Object []{mm.getDirection()}));
    }



    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        RelationModel model = (RelationModel) oldInstance;
        return new Expression(
                oldInstance,
                oldInstance.getClass(),
                "new",
                new Object[]{model.getRelationType()});
    }
}
