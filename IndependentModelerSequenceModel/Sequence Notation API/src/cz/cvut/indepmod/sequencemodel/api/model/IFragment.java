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
public interface IFragment {

    public Collection<? extends IAbstractMessage> getMessages();
}
