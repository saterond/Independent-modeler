/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ModelListener;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelEditSequenceObjectDialog extends SequenceModelAbstractDialog implements ModelListener{

    private static final Logger LOG = Logger.getLogger(SequenceModelEditSequenceObjectDialog.class.getName());
    private SequenceObjectModel model;
    private SequenceModelGraph graph;
    private DefaultGraphCell cell;

    public static final String TITLE = "Object Edit";
    public static final String OBJ_NAME_LABEL = "Object name";
    public static final String CLASS_NAME_LABEL = "Class name";
    public static final String SAVE_BUTTON = "Ok";
    public static final String CANCEL_BUTTON = "Cancel";

    private JLabel objectNameLabel;
    private JLabel classNameLabel;
    private JTextField objectNameField;
    private JTextField classNameField;
    private JButton saveButton;
    private JButton cancelButton;


     public SequenceModelEditSequenceObjectDialog(Frame owner, SequenceModelGraph graph, DefaultGraphCell cell, SequenceObjectModel model) {
     super(owner,TITLE);
     this.model = model;
     this.graph = graph;
     this.cell = cell;

     this.initValue();
     this.initAction();
     this.initLayout();
     this.setSizes();
     }

    @Override
    public void modelChanged() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initValue() {
        this.objectNameLabel = new JLabel(OBJ_NAME_LABEL);
        this.objectNameField = new JTextField();
        this.classNameLabel = new JLabel(CLASS_NAME_LABEL);
        this.classNameField = new JTextField();
        this.saveButton = new JButton(SAVE_BUTTON);
        this.cancelButton = new JButton(CANCEL_BUTTON);

        this.objectNameField.setText(this.model.getObjectName());
        this.classNameField.setText(this.model.getTypeName());
    }

    private void initAction() {

        this.saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String newClassName = classNameField.getText();
                String newObjectName = objectNameField.getText();

                if (!newClassName.equals(model.getTypeName())) {
                    if (newClassName.matches("^[A-Za-z][0-9A-Za-z]*$")) {
                        LOG.info("Changing the name of the class");
                        model.setTypeName(newClassName);
                    } else {
                        LOG.warning("Bad name of the class!");
                    }
                }

                if (!newObjectName.equals(model.getObjectName())) {
                    if (newObjectName.matches("^[A-Za-z][0-9A-Za-z]*$")) {
                        LOG.info("Changing the name of the object");
                        model.setObjectName(newObjectName);
                    } else {
                        LOG.warning("Bad name of the object!");
                    }
                }

                updateCell();
                dispose();
            }
        });

        this.cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initLayout() {
        this.setLayout(new GridLayout(3, 2));

        this.add(this.classNameLabel);
        this.add(this.classNameField);

        this.add(this.objectNameLabel);
        this.add(this.objectNameField);

        this.add(this.saveButton);
        this.add(this.cancelButton);
    }

    public void updateCell() {
        this.graph.getGraphLayoutCache().editCell(this.cell,new HashMap());
    }

}
