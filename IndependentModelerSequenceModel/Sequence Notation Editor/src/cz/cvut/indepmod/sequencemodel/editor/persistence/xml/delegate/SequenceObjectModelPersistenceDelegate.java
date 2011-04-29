package cz.cvut.indepmod.sequencemodel.editor.persistence.xml.delegate;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.Statement;
import java.util.Collection;

/**
 * @author Hegladan
 *
 * This class is XML Persistence delegate for SequenceObjectModel objects
 */
public class SequenceObjectModelPersistenceDelegate extends DefaultPersistenceDelegate {

    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
        //super.initialize(type, oldInstance, newInstance, out);

        SequenceObjectModel som = (SequenceObjectModel)oldInstance;
        Collection<MessageModel> income = som.getIncomeMessages();
        Collection<MessageModel> sent = som.getSentMessages();
        //Collection<MessageModel> all = som.getAllMessages();

        for (MessageModel m : income) {
            out.writeStatement(new Statement(oldInstance, "addIncomeMessage", new Object[] {m}));
        }

        for (MessageModel m : sent) {
            out.writeStatement(new Statement(oldInstance, "addSentMessage", new Object[] {m}));
        }

        /*
        for (MessageModel m : all) {
            out.writeStatement(new Statement(oldInstance, "addAllMessage", new Object[] {m}));
        }
         *
         */
    }

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        SequenceObjectModel cm = (SequenceObjectModel)oldInstance;
        return new Expression(
                oldInstance,
                oldInstance.getClass(),
                "new",
                new Object[]{cm.getTypeName()});
    }


}
