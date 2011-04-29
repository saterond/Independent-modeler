/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelConditionCreatorDialog extends SequenceModelAbstractDialog {

    public static final String TITLE = "Create Condition Dialog";

    public static final String CREATE_BUTTON_TITLE = "Create";

    private ConditionModel returnValue;

    private JTextField conditionName;
    private JButton createButton;

    public SequenceModelConditionCreatorDialog(Frame owner) {
        super(owner, TITLE);

        this.initLayout();
        this.initAction();
        this.setSizes();
    }

    public ConditionModel getConditions() {
        return this.returnValue;
    }

    private void initLayout() {
        this.setLayout(new GridLayout(2, 1));

        this.conditionName = new JTextField();
        this.createButton = new JButton(CREATE_BUTTON_TITLE);

        this.add(this.conditionName);
        this.add(this.createButton);
    }

    private void initAction() {
        this.createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = conditionName.getText();

                //TODO - verify filled data!
                returnValue = new ConditionModel(name);
                dispose();
            }
        });
    }
}
