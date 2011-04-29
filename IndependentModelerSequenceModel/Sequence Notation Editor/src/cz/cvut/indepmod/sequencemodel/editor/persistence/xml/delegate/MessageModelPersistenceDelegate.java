package cz.cvut.indepmod.sequencemodel.editor.persistence.xml.delegate;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.AbstractMessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.AttributeModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
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
public class MessageModelPersistenceDelegate extends DefaultPersistenceDelegate {

    /*
        @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
        //super.initialize(type, oldInstance, newInstance, out);

        MessageModel mess = (MessageModel)oldInstance;
        Collection<AttributeModel> attributes = mess.getAttributeModels();
        
        for (AttributeModel atr : attributes) {
            out.writeStatement(new Statement(oldInstance, "addAttributes", new Object[] {atr}));
        }
    }
     * 
     */


    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        MessageModel mm = (MessageModel) oldInstance;
        return new Expression(
                oldInstance,
                oldInstance.getClass(),
                "new",
                new Object[]{mm.getName(), mm.getType(), mm.getAttributeModels()});
    }

}
