package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.api.model.IAnnotation;
import cz.cvut.indepmod.classmodel.api.model.IAttribute;
import cz.cvut.indepmod.classmodel.api.model.IType;
import cz.cvut.indepmod.classmodel.api.model.Visibility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lucky
 * Date: 3.10.2010
 * Time: 9:52:29
 */
public class AttributeModel extends AbstractModel implements IAttribute {

    private IType type;
    private String name;
    private List<IAnnotation> anotations;
    private Visibility visibility;

    public AttributeModel(TypeModel typeModel, String name) {
        this(typeModel, name, Visibility.PUBLIC);
    }

    public AttributeModel(TypeModel typeModel, String name, Visibility visibility) {
        this.visibility = visibility;
        this.type = typeModel;
        this.name = name;

        this.anotations = new ArrayList<IAnnotation>();
    }

    /**
     * Returns Type instantion represeting the type of this attribute
     *
     * @return Type instantion
     */
    @Override
    public IType getType() {
        return type;
    }

    /**
     * Returns the name of this attribute
     *
     * @return the name of this attribute
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(30);
        res.append(this.visibility.toString());
        res.append(" ");
        res.append(this.name);
        res.append(" : ");
        res.append(this.type.getTypeName());

        if (!this.anotations.isEmpty()) {
            res.append(" [");
            for (IAnnotation anot : this.anotations) {
                res.append(anot.toString());
            }
            res.append("]");
        }
        return res.toString();
    }

    @Override
    public Collection<IAnnotation> getAnotations() {
        return new ArrayList<IAnnotation>(this.anotations);
    }

    @Override
    public void addAnotation(IAnnotation anot) {
        if (anot != null && !this.anotations.contains(anot)) {
            this.anotations.add(anot);
        }
    }

    @Override
    public void removeAnotation(IAnnotation anot) {
        this.anotations.remove(anot);
    }

    @Override
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void setType(IType type) {
        this.type = type;
    }
}
