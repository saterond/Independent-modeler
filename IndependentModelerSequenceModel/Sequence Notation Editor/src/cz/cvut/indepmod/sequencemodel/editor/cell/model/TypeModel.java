/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.IType;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class TypeModel extends AbstractModel implements IType{

    private String typeName;

    /**
     * Creates new Type
     *
     * @param name name of the type
     */
    public TypeModel(String name) {
        this.typeName = name;
    }

    /**
     * Returns the name of this data type
     *
     * @return the name
     */
    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Change the name of this data type
     * @param typeName new name of this data type
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
