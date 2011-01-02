/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.LifelineModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ModelListener;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
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
public class SequenceModelEditLifelineDialog extends SequenceModelAbstractDialog implements ModelListener{

    private static final Logger LOG = Logger.getLogger(SequenceModelEditLifelineDialog.class.getName());
    private LifelineModel model;
    private SequenceModelGraph graph;
    private DefaultGraphCell cell;

    public static final String TITLE = "Lifeline Edit";
    public static final String NAME_LABEL = "Name";
    public static final String SAVE_BUTTON = "Ok";
    public static final String CANCEL_BUTTON = "Cancel";

    private JLabel lifelineNameLabel;
    private JTextField lifelineNameField;
    private JButton saveButton;
    private JButton cancelButton;


     public SequenceModelEditLifelineDialog(Frame owner, SequenceModelGraph graph, DefaultGraphCell cell, LifelineModel model) {
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
        this.lifelineNameLabel = new JLabel(NAME_LABEL);
        this.lifelineNameField = new JTextField();
        this.saveButton = new JButton(SAVE_BUTTON);
        this.cancelButton = new JButton(CANCEL_BUTTON);

        this.lifelineNameField.setText(this.model.toString());
    }

    private void initAction() {

        this.saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               String newLifelineName = lifelineNameField.getText();
               if (!newLifelineName.equals(model.getTypeName())) {
               if (newLifelineName.matches("^[A-Za-z][0-9A-Za-z]*$")) {
                LOG.info("Changing the name of the class");
                model.setTypeName(newLifelineName);
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

        this.add(this.lifelineNameLabel);
        this.add(this.lifelineNameField);

        this.add(this.saveButton);
        this.add(this.cancelButton);
    }

    public void updateCell() {
        this.graph.getGraphLayoutCache().editCell(this.cell,new HashMap());
    }

}
