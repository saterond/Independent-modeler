package cz.cvut.indepmod.classmodel.api.model;

import java.util.Collection;

/**
 * Date: 25.11.2010
 * Time: 16:02:04
 * @author Lucky
 *
 * Represents Anotatin of the class
 */
public interface IAnnotation {

    /**
     * Returns the name of the annotation. Name is only the name (e.g. in Java
     * if there is anotation @Anotation, getName will return only Anotation -
     * without sign '@'.). This is beacuse of the sign @ is sign that is used in
     * java language to indicate that this is an anotation. In an other language
     * it can be a different sign...
     * @return The name of the anotation
     */
    public String getName();

    /**
     * Sets the name of the annotation.
     * @param name
     */
    public void setName(String name);

    /**
     * Returns a collection of IAnotationValues
     * @return A collection of IAnotationValues
     */
    public Collection<IAnotationValue> getAttributes();

    /**
     * Adds an atribute to this annotation
     * @param attr new attribute to be added
     */
    public void addAttribute(IAnotationValue attr);

    /**
     * Returns an atribute from this annotation
     */
    public void removeAttribute(IAnotationValue attr);

}
