/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelEditMessageDialogView extends SequenceModelAbstractDialog{

    public static final String TITLE = "Message Edit";
    public static final String NAME_LABEL = "Name";
    public static final String PARAMETERS_LABEL = "Parameters";
    public static final String RETURN_LABEL = "Is Return";
    public static final String SAVE_BUTTON = "Ok";
    public static final String CANCEL_BUTTON = "Cancel";
    public static final String ADD_ATR_BUTTON = "Add Attribute";
    public static final String REM_ATR_BUTTON = "Remove Attribute";
    public static final String TYPE_LABEL = "Type";

    protected JLabel messageNameLabel = new JLabel(NAME_LABEL);
    protected JLabel parametersLabel = new JLabel(PARAMETERS_LABEL);
    protected JLabel returnLabel = new JLabel(RETURN_LABEL);
    protected JLabel typeLabel = new JLabel(TYPE_LABEL);
    protected JComboBox typeBox = new JComboBox();
    protected JTextField messageNameField = new JTextField();
    protected JTextField parametersField = new JTextField();
    protected JButton saveButton = new JButton(SAVE_BUTTON);
    protected JButton cancelButton = new JButton(CANCEL_BUTTON);
    protected JCheckBox returnCheckBox = new JCheckBox();
    protected JList parametersList = new JList();
    protected JButton addAttributeButton = new JButton(ADD_ATR_BUTTON);
    protected JButton remAttributeButton = new JButton(REM_ATR_BUTTON);

    public SequenceModelEditMessageDialogView(Frame owner) {
        super(owner, TITLE);

        this.initLayout();
    }

    private void initLayout() {
        this.setLayout(new GridLayout(6, 2));

        this.add(this.messageNameLabel);
        this.add(this.messageNameField);
        
        this.add(this.typeLabel);
        this.add(this.typeBox);

        this.add(this.addAttributeButton);
        this.add(this.remAttributeButton);

        this.add(this.parametersLabel);
        this.add(this.parametersList);

        this.add(this.returnLabel);
        this.add(this.returnCheckBox);

        this.add(this.saveButton);
        this.add(this.cancelButton);
    }


}
