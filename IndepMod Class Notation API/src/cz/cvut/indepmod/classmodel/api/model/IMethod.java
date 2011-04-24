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
public interface IMethod {

    /**
     * Returns the Visibility of this Method
     * @return the Visibility of this Method
     */
    public Visibility getVisibility();

    /**
     * Returns an unmodifiable view of the attributes set
     *
     * @return an unmodifiable view of the attributes set
     */
    public Set<IAttribute> getAttributeModels();

    /**
     * Returns the name of the method
     *
     * @return name of the method
     */
    public String getName();

    /**
     * Returns Type instantion represeting the return type of this method
     *
     * @return Type instantion
     */
    public IType getType();

    /**
     * Returns true if the method is static
     * @return
     */
    public boolean isStatic();

    /**
     * Sets whether is this method static or not
     * @param isStatic
     * @return
     */
    public void setStatic(boolean isStatic);

    /**
     * Returns true if the method is abstract
     * @return
     */
    public boolean isAbstract();

    /**
     * Sets whether is this method abstract or not
     * @param isAbstract
     */
    public void setAbstract(boolean isAbstract);

}
