package cz.cvut.indepmod.classmodel.persistence.xml.delegate;

import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AnnotationAttributeModel;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.Statement;
import java.util.Collection;

/**
 * Date: 26.2.2011
 * Time: 19:20:29
 * @author LuckyV
 */
public class AnotationAtributeModelPersistenceDelegate extends DefaultPersistenceDelegate {

    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
        AnnotationAttributeModel aam = (AnnotationAttributeModel)oldInstance;

        Collection<String> values = aam.getValues();
        for (String value : values) {
            out.writeStatement(new Statement(oldInstance, "addValue", new Object[] {value}));
        }
    }

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        AnnotationAttributeModel am = (AnnotationAttributeModel) oldInstance;
        return new Expression(
                oldInstance,
                oldInstance.getClass(),
                "new",
                new Object[]{am.getName()});
    }

}
