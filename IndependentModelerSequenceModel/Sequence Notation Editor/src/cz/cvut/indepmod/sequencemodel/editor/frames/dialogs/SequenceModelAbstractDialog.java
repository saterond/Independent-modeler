/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import javax.swing.JDialog;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public abstract class SequenceModelAbstractDialog extends JDialog{

    public SequenceModelAbstractDialog(Frame owner, String title) {
        super(owner, title, true);
    }


    public void setSizes() {
        this.pack();

        Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        setLocation((dim.width - abounds.width) / 2,
                (dim.height - abounds.height) / 2);
        this.setVisible(true);
        this.requestFocus();
    }
}
