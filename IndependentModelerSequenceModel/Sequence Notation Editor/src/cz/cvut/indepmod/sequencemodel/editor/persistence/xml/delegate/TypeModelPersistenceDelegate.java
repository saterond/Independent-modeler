package cz.cvut.indepmod.sequencemodel.editor.persistence.xml.delegate;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;

/**
 * @author Hegladan
 *
 * This class is XML Persistence delegate for TypeModel objects
 */
public class TypeModelPersistenceDelegate extends DefaultPersistenceDelegate {

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        TypeModel m = (TypeModel) oldInstance;
        return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{m.getTypeName()});
    }



}
