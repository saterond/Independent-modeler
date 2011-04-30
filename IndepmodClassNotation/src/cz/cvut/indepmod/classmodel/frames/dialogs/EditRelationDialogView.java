package cz.cvut.indepmod.classmodel.frames.dialogs;

import cz.cvut.indepmod.classmodel.resources.Resources;
import cz.cvut.indepmod.classmodel.util.GridBagConstraintsUtils;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Date: 21.11.2010
 * Time: 17:44:45
 * @author Lucky
 */
public class EditRelationDialogView extends AbstractClassModelDialog {
    
    public static final String TITLE = Resources.getString("dialog_edit_relation_title");

    public static final String NAME_LABEL_TEXT = Resources.getString("dialog_edit_relation_name");
    public static final String SOURCE_CARD_LABEL_TEXT = Resources.getString("dialog_edit_relation_source_card");
    public static final String TARGET_CARD_LABEL_TEXT = Resources.getString("dialog_edit_relation_target_card");
    public static final String ARROW_LABEL = Resources.getString("dialog_edit_relation_arrow");
    public static final String RELATION_TYPE_LABEL = Resources.getString("dialog_edit_relation_reltype");
    public static final String UNIDIRECTIONAL_LABEL = Resources.getString("dialog_edit_relation_unidirectional");
    public static final String BIDIRECTIONAL_LABEL = Resources.getString("dialog_edit_relation_bidirectional");
    public static final String NAME_ALONG_EDGE_LABEL = Resources.getString("dialog_edit_relation_name_along");
    public static final String SAVE_TEXT = Resources.getString("dialog_edit_relation_save");
    public static final String CANCEL_TEXT = Resources.getString("dialog_edit_relation_cancel");

    protected JLabel nameLabel = new JLabel(NAME_LABEL_TEXT);
    protected JTextField nameField = new JTextField();
    protected JLabel sourceCardinLab = new JLabel(SOURCE_CARD_LABEL_TEXT);
    protected JLabel targetCardinLab = new JLabel(TARGET_CARD_LABEL_TEXT);
    protected JComboBox sourceCardinality = new JComboBox();
    protected JComboBox targetCardinality = new JComboBox();
    protected JLabel arrowLabel = new JLabel(ARROW_LABEL);
    protected JCheckBox arrowCheck = new JCheckBox();
    protected JLabel relationTypeLabel = new JLabel(RELATION_TYPE_LABEL);
    protected JRadioButton unidirectional = new JRadioButton(UNIDIRECTIONAL_LABEL);
    protected JRadioButton bidirectional = new JRadioButton(BIDIRECTIONAL_LABEL);
    protected JLabel nameAlongLabel = new JLabel(NAME_ALONG_EDGE_LABEL);
    protected JCheckBox nameAlongCheck = new JCheckBox();
    protected JButton saveButton = new JButton(SAVE_TEXT);
    protected JButton cancelButton = new JButton(CANCEL_TEXT);

    public EditRelationDialogView(Frame owner) {
        super(owner, TITLE);

        this.initLayout();
    }

    private void initLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = null;

        ButtonGroup grp = new ButtonGroup();
        grp.add(this.unidirectional);
        grp.add(this.bidirectional);

        c = GridBagConstraintsUtils.createNewConstraints(0, 0, 1, 1, GridBagConstraints.LINE_START);
        this.add(this.nameLabel, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 0, 2, 1);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.nameField, c);

        c = GridBagConstraintsUtils.createNewConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START);
        this.add(this.sourceCardinLab, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 1, 2, 1);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.sourceCardinality, c);

        c = GridBagConstraintsUtils.createNewConstraints(0, 2, 1, 1, GridBagConstraints.LINE_START);
        this.add(this.targetCardinLab, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 2, 2, 1);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.targetCardinality, c);

        c = GridBagConstraintsUtils.createNewConstraints(0, 3, 1, 1, GridBagConstraints.LINE_START);
        this.add(this.relationTypeLabel, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 3, 1, 1);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.unidirectional, c);

        c = GridBagConstraintsUtils.createNewConstraints(2, 3, 1, 1);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.bidirectional, c);

        c = GridBagConstraintsUtils.createNewConstraints(0, 4, 1, 1, GridBagConstraints.LINE_START);
        this.add(this.arrowLabel, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 4, 2, 1);
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.LINE_START;
        this.add(this.arrowCheck, c);

        c = GridBagConstraintsUtils.createNewConstraints(0, 5, 1, 1, GridBagConstraints.LINE_START);
        this.add(this.nameAlongLabel, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 5, 2, 1);
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.LINE_START;
        this.add(this.nameAlongCheck, c);

        c = GridBagConstraintsUtils.createNewConstraints(1, 6, 1, 1);
        this.add(this.saveButton, c);

        c = GridBagConstraintsUtils.createNewConstraints(2, 6, 1, 1);
        this.add(this.cancelButton, c);
    }



}
