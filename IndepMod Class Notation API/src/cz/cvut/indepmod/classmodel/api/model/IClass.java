/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.classmodel.api.model;

import java.util.Set;

/**
 *
 * @author Lucky
 */
public interface IClass extends IType {

    /**
     * Returns an attribute set
     *
     * @return an attribute set
     */
    public Set<? extends IAttribute> getAttributeModels();

    /**
     * Returns a method set
     *
     * @return a method set
     */
    public Set<? extends IMethod> getMethodModels();

    /**
     * Returns set of related methods
     * @return related method set
     */
    public Set<? extends IRelation> getRelatedClass();
}
