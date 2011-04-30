/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.classmodel.api.model;

import java.util.Collection;

/**
 *
 * @author Lucky
 */
public interface IAttribute {

    /**
     * Returns the Visibility of this Attribute
     * @return the Visibility of this Attribute
     */
    public Visibility getVisibility();

    /**
     * Sets the Visibility of this Attribute
     * @param visibility
     */
    public void setVisibility(Visibility visibility);

    /**
     * Returns the name of this attribute
     *
     * @return the name of this attribute
     */
    public String getName();

    /**
     * Sets the name of the attribute
     * @param name
     */
    public void setName(String name);

    /**
     * Returns Type instantion represeting the type of this attribute
     *
     * @return Type instantion
     */
    public IType getType();

    /**
     * Sets the Type of this attribut
     * @param type
     */
    public void setType(IType type);

    /**
     * Returns a collection of annotations that are related to this attribute
     * @return A collection of annotations
     */
    public Collection<IAnnotation> getAnotations();

    /**
     * Adds new anotation into this attribute
     * @param anot new annotation to be added
     */
    public void addAnotation(IAnnotation anot);

    /**
     * Removes the anotation from this attribute
     * @param anot an anotation to be removed
     */
    public void removeAnotation(IAnnotation anot);

}
