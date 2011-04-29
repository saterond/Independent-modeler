/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.api.model;

import java.util.Set;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public interface IMessage {

    public ISequenceObject getSourceSequenceObject();

    public ISequenceObject getTargetSequenceObject();

    public Set<? extends IAttribute> getAttributeModels();

    public IType getType();

}
