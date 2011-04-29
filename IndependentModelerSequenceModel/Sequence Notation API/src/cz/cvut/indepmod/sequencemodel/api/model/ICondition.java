/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.api.model;

import java.util.Collection;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public interface ICondition {

    /**
     * Returns the name of this attribute
     *
     * @return the name of this attribute
     */
    public String getName();

    public Collection<? extends IAbstractMessage> getMessages();

}
