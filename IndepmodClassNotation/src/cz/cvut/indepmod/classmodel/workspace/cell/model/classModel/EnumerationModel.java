package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.api.model.ElementType;
import cz.cvut.indepmod.classmodel.api.model.IAnnotation;
import cz.cvut.indepmod.classmodel.api.model.IAttribute;
import cz.cvut.indepmod.classmodel.api.model.IMethod;
import java.util.Set;

/**
 * Date: 24.3.2011
 * Time: 17:07:28
 * @author Lucky
 */
public class EnumerationModel extends AbstractElementModel {

    private static final String DEFAULT_STEREOTYPE = "enumeration";

    public EnumerationModel(String name) {
        super(name);
        this.setStereotype(DEFAULT_STEREOTYPE);
    }

    public EnumerationModel(String name, Set<IMethod> methodModels, Set<IAttribute> attributeModels, Set<IAnnotation> anotationModels) {
        super(name, methodModels, attributeModels, anotationModels);
        this.setStereotype(DEFAULT_STEREOTYPE);
    }

    public EnumerationModel(AbstractElementModel model) {
        super(model);
        this.setStereotype("enumeration");
    }


    @Override
    public ElementType getElementType() {
        return ElementType.ENUMERATION;
    }

}
