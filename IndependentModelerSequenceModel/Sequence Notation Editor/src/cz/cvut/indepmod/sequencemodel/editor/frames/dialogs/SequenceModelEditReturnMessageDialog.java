/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ModelListener;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ReturnMessageModel;
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
public class SequenceModelEditReturnMessageDialog extends SequenceModelAbstractDialog implements ModelListener{

    private static final Logger LOG = Logger.getLogger(SequenceModelEditReturnMessageDialog.class.getName());
    private ReturnMessageModel model;
    private SequenceModelGraph graph;
    private DefaultGraphCell cell;

    public static final String TITLE = "Return Message Edit";
    public static final String NAME_LABEL = "Name";
    public static final String SAVE_BUTTON = "Ok";
    public static final String CANCEL_BUTTON = "Cancel";

    private JLabel NameLabel;
    private JTextField NameField;
    private JButton saveButton;
    private JButton cancelButton;


     public SequenceModelEditReturnMessageDialog(Frame owner, SequenceModelGraph graph, DefaultGraphCell cell, ReturnMessageModel model) {
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
        this.NameLabel = new JLabel(NAME_LABEL);
        this.NameField = new JTextField();
        this.saveButton = new JButton(SAVE_BUTTON);
        this.cancelButton = new JButton(CANCEL_BUTTON);

        this.NameField.setText(this.model.toString());
    }

    private void initAction() {

        this.saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               String newReturnName = NameField.getText();
               if (!newReturnName.equals(model.getName())) {
               if (newReturnName.matches("^[A-Za-z][0-9A-Za-z]*$")) {
                LOG.info("Changing the name of the class");
                model.setName(newReturnName);
            } else {
                LOG.warning("Bad name of the class!");
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
        this.setLayout(new GridLayout(2, 2));

        this.add(this.NameLabel);
        this.add(this.NameField);

        this.add(this.saveButton);
        this.add(this.cancelButton);
    }

    public void updateCell() {
        this.graph.getGraphLayoutCache().editCell(this.cell,new HashMap());
    }

}
