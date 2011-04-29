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
public interface ISequenceObject {

    public Collection<? extends IMessage> getSentMessages();

    public Collection<? extends IMessage> getIncomeMessages();

    public Collection<? extends IMessage> getAllMessages();

    public IMessage getNextMessage(IMessage message);

    public IMessage getPreiviousMessage(IMessage message);
}
