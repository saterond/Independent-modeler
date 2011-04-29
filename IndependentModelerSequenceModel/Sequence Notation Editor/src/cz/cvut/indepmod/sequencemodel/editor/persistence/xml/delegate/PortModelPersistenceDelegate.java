package cz.cvut.indepmod.sequencemodel.editor.persistence.xml.delegate;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.AttributeModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.PortModel;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;

/**
 * @author Hegladan
 *
 * This class is XML Persistence delegate for AttributeModel objects
 */
public class PortModelPersistenceDelegate extends DefaultPersistenceDelegate {

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        PortModel pm = (PortModel) oldInstance;
        return new Expression(
                oldInstance,
                oldInstance.getClass(),
                "new",
                new Object[]{pm.getName(), pm.getMessage()});
    }
}
