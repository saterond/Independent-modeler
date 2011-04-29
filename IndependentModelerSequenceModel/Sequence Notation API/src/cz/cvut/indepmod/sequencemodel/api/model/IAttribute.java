/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.api.model;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public interface IAttribute {

    /**
     * Returns the name of this attribute
     *
     * @return the name of this attribute
     */
    public String getName();

    /**
     * Returns Type instantion represeting the type of this attribute
     *
     * @return Type instantion
     */
    public IType getType();

}
