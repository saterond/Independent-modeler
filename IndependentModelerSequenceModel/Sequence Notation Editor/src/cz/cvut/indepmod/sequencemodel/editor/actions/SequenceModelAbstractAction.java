/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public abstract class SequenceModelAbstractAction extends AbstractAction {

    public SequenceModelAbstractAction(String name, Icon icon) {
        super(name, icon);
    }

}
