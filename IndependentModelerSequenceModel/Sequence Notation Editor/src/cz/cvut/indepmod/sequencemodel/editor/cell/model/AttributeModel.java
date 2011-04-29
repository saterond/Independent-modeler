/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.IAttribute;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class AttributeModel extends AbstractModel implements IAttribute{

    private TypeModel type;
    private String name;

    public AttributeModel(TypeModel typeModel, String name) {
        this.type = typeModel;
        this.name = name;
    }

    /**
     * Returns Type instantion represeting the type of this attribute
     *
     * @return Type instantion
     */
    @Override
    public TypeModel getType() {
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
        return this.name + " : " + this.type.getTypeName();
    }

}