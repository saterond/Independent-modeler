package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.api.model.IAnnotation;
import cz.cvut.indepmod.classmodel.api.model.IAttribute;
import cz.cvut.indepmod.classmodel.api.model.IMethod;
import cz.cvut.indepmod.classmodel.api.model.IType;
import cz.cvut.indepmod.classmodel.api.model.Visibility;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Lucky
 * Date: 3.10.2010
 * Time: 9:52:44
 */
public class MethodModel extends AbstractModel implements IMethod {

    private IType type;
    private String name;
    private Set<IAttribute> attributeModels;
    private Set<IAnnotation> annotations;
    private Visibility visibility;
    private boolean isAbstract;
    private boolean isStatic;

    public MethodModel(TypeModel typeModel, String name) {
        this(typeModel, name, null);
    }
    
    public MethodModel(TypeModel typeModel, String name, Set<IAttribute> attributeModels) {
        this(typeModel, name, attributeModels, null);
    }

    public MethodModel(TypeModel typeModel, String name, Set<IAttribute> attributeModels, Set<IAnnotation> annotations) {
        this(typeModel, name, attributeModels, annotations, Visibility.PUBLIC);
    }

    public MethodModel(TypeModel typeModel, String name, Set<IAttribute> attributeModels, Set<IAnnotation> annotations, Visibility v) {
        this.visibility = v;
        this.type = typeModel;
        this.name = name;

        if (attributeModels != null) {
            this.attributeModels = new HashSet<IAttribute>(attributeModels);
        } else {
            this.attributeModels = new HashSet<IAttribute>();
        }

        if (annotations != null) {
            this.annotations = new HashSet<IAnnotation>(annotations);
        } else {
            this.annotations = new HashSet<IAnnotation>();
        }
    }

    @Override
    public IType getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<IAttribute> getAttributeModels() {
        return new HashSet<IAttribute>(this.attributeModels);
    }

    @Override
    public String toString() {
        StringBuilder bfr = new StringBuilder(30);
        bfr.append(this.visibility.toString());
        bfr.append(" ");

        if (this.isAbstract) {
            bfr.append("abstract ");
        }
        if (this.isStatic) {
            bfr.append("static ");
        }

        bfr.append(this.name);
        bfr.append("(");

        boolean comma = false;
        for (IAttribute attr : this.getAttributeModels()) {
            if (comma) {
                bfr.append(", ");
            } else {
                comma = true;
            }
            
            bfr.append(attr.getType());
        }

        bfr.append(") : ");
        bfr.append(this.type.toString());

        if (!this.annotations.isEmpty()) {
            bfr.append(" [");
            for (IAnnotation anot : this.annotations) {
                bfr.append(anot.toString());
            }
            bfr.append("]");
        }

        return bfr.toString();
    }

    @Override
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    @Override
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    @Override
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    @Override
    public void addAttribute(IAttribute attribute) {
        this.attributeModels.add(attribute);
    }

    @Override
    public void removeAttribute(IAttribute attribute) {
        this.attributeModels.remove(attribute);
    }

    @Override
    public void setAttributeModels(Set<IAttribute> attributes) {
        this.attributeModels = new HashSet<IAttribute>(attributes);
    }

    @Override
    public void setVisibility(Visibility vis) {
        this.visibility = vis;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setType(IType type) {
        this.type = type;
    }

    @Override
    public Set<IAnnotation> getAnotations() {
        return new HashSet<IAnnotation>(this.annotations);
    }

    @Override
    public void addAnotation(IAnnotation anot) {
        this.annotations.add(anot);
    }

    @Override
    public void removeAnotation(IAnnotation anot) {
        this.annotations.remove(anot);
    }

    @Override
    public void setAnnotationModels(Set<IAnnotation> anots) {
        this.annotations = new HashSet<IAnnotation>(anots);
    }
}
