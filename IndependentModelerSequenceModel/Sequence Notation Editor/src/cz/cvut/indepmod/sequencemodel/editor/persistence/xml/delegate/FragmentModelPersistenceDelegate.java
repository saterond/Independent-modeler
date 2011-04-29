package cz.cvut.indepmod.sequencemodel.editor.persistence.xml.delegate;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.AbstractMessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.Statement;
import java.util.Collection;

/**
 *
 * @author Hegladan
 *
 * This class is XML Persistence delegate for FragmentModel objects
 */
public class FragmentModelPersistenceDelegate extends DefaultPersistenceDelegate {

        @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
        //super.initialize(type, oldInstance, newInstance, out);

        FragmentModel fm = (FragmentModel)oldInstance;
        //Collection<ConditionModel> conditions = fm.getConditionModels();
        Collection<AbstractMessageModel> messages = fm.getMessages();

        /*
        for (ConditionModel con : conditions) {
            out.writeStatement(new Statement(oldInstance, "addCondition", new Object[] {con}));
        }
         *
         */

        for (AbstractMessageModel m : messages) {
            out.writeStatement(new Statement(oldInstance, "addMessages", new Object[] {m}));
        }
    }


    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        FragmentModel mm = (FragmentModel) oldInstance;
        return new Expression(
                oldInstance,
                oldInstance.getClass(),
                "new",
                new Object[]{mm.getName(), mm.getConditionModels()});
    }

}
