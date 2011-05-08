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
     * Sets the visibility of this method
     * @param vis
     */
    public void setVisibility(Visibility vis);

    /**
     * Returns a collection of anotations
     * @return a collection of anotations
     */
    public Set<IAnnotation> getAnotations();

    /**
     * Adds an anotation
     * @param anot the anotation to be added
     */
    public void addAnotation(IAnnotation anot);

    /**
     * Removes an anotation
     * @param anot The anotation to be removed
     */
    public void removeAnotation(IAnnotation anot);

    /**
     * Sets a new collection of annotations
     * @param anots
     */
    public void setAnnotationModels(Set<IAnnotation> anots);

    /**
     * Returns an unmodifiable view of the attributes set
     *
     * @return an unmodifiable view of the attributes set
     */
    public Set<IAttribute> getAttributeModels();

    /**
     * Adds an attribute
     * @param attribute
     */
    public void addAttribute(IAttribute attribute);

    /**
     * Removes an attribute
     * @param attribute
     */
    public void removeAttribute(IAttribute attribute);

    /**
     * Sets a new collection of attributes
     * @param attributes
     */
    public void setAttributeModels(Set<IAttribute> attributes);

    /**
     * Returns the name of the method
     *
     * @return name of the method
     */
    public String getName();

    /**
     * Sets the name of the method
     * @param name
     */
    public void setName(String name);

    /**
     * Returns Type instantion represeting the return type of this method
     *
     * @return Type instantion
     */
    public IType getType();

    /**
     * Sets the new type of this method
     * @param type
     */
    public void setType(IType type);

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
