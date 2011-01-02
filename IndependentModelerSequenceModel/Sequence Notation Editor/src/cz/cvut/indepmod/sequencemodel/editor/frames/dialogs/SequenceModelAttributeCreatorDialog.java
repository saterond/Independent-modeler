/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.AttributeModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelAttributeCreatorDialog extends SequenceModelAbstractDialog {

    public static final String TITLE = "Create Attribute Dialog";

    public static final String CREATE_BUTTON_TITLE = "Create";

    private Collection<TypeModel> availableTypes;

    private AttributeModel returnValue;

    private JComboBox attributeType;
    private JTextField attributeName;
    private JButton createButton;

    public SequenceModelAttributeCreatorDialog(Frame owner) {
        this(owner, new ArrayList<TypeModel>(0));
    }

    public SequenceModelAttributeCreatorDialog(Frame owner, Collection<TypeModel> types) {
        super(owner, TITLE);

        this.availableTypes = types;

        this.initLayout();
        this.initAction();
        this.setSizes();
    }

    public AttributeModel getAttribute() {
        return this.returnValue;
    }

    private void initLayout() {
        this.setLayout(new GridLayout(3, 1));

        this.attributeType = new JComboBox(this.availableTypes.toArray());
        this.attributeName = new JTextField();
        this.createButton = new JButton(CREATE_BUTTON_TITLE);

        this.add(this.attributeType);
        this.add(this.attributeName);
        this.add(this.createButton);
    }

    private void initAction() {
        this.createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TypeModel dateType = (TypeModel)attributeType.getSelectedItem();
                String name = attributeName.getText();

                //TODO - verify filled data!
                returnValue = new AttributeModel(dateType, name);
                dispose();
            }
        });
    }
}
