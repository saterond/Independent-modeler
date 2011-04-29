/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelSaveEditFragmentDialog;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ConditionModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ModelListener;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import org.jgraph.graph.DefaultGraphCell;
import org.openide.windows.WindowManager;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelEditFragmentDialog extends SequenceModelAbstractDialog implements ModelListener{

    private static final Logger LOG = Logger.getLogger(SequenceModelEditFragmentDialog.class.getName());
    private FragmentModel model;
    private SequenceModelGraph graph;
    private DefaultGraphCell cell;
    private DefaultListModel conditionListModel;
    private List<ConditionModel> conditions;

    public static final String TITLE = "Fragment Edit";
    public static final String NAME_LABEL = "Name";
    public static final String SAVE_BUTTON = "Ok";
    public static final String CANCEL_BUTTON = "Cancel";
    public static final String CONDITION_LABEL = "Conditions";
    public static final String ADD_CON_BUTTON = "Add condition";
    public static final String REM_CON_BUTTON = "Remove condition";

    private JLabel fragmentNameLabel;
    private JTextField fragmentNameField;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel conditionLabel;
    private JList conditionsList;
    private JButton addConditionButton;
    private JButton remConditionButton;


     public SequenceModelEditFragmentDialog(Frame owner, SequenceModelGraph graph, DefaultGraphCell cell, FragmentModel model) {
     super(owner,TITLE);
     this.model = model;
     this.graph = graph;
     this.cell = cell;

     this.initValue();
     this.initAction();
     this.initLayout();
     this.setSizes();
     }

    private void initValue() {
        this.conditionListModel = new DefaultListModel();
        this.fragmentNameLabel = new JLabel(NAME_LABEL);
        this.fragmentNameField = new JTextField();
        
        this.conditionLabel = new JLabel(CONDITION_LABEL);
        this.saveButton = new JButton(SAVE_BUTTON);
        this.cancelButton = new JButton(CANCEL_BUTTON);
        this.conditionsList = new JList(this.conditionListModel);
        this.addConditionButton = new JButton(ADD_CON_BUTTON);
        this.remConditionButton = new JButton(REM_CON_BUTTON);
        this.fragmentNameField.setText(this.model.getName());

        this.saveButton = new JButton(SAVE_BUTTON);
        this.cancelButton = new JButton(CANCEL_BUTTON);
        
        this.conditions = this.model.getConditionModels();

        if(!conditions.isEmpty()){
            for(ConditionModel atr : conditions){
            this.conditionListModel.addElement(atr);
            }
        }
    }

    private void initAction() {
         this.saveButton.addActionListener(new SequenceModelSaveEditFragmentDialog(this.model,this.cell,this.graph,this));

         this.addConditionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Frame window = WindowManager.getDefault().getMainWindow();
                ConditionModel con = new SequenceModelConditionCreatorDialog(
                        window).getConditions();

                if (con != null) {
                    conditionListModel.addElement(con);
                }
            }
        });

        this.remConditionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ConditionModel con = (ConditionModel) conditionsList.getSelectedValue();
                if (con != null) {
                    conditionListModel.removeElement(con);
                }
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
        this.setLayout(new GridLayout(4, 2));

        this.add(this.fragmentNameLabel);
        this.add(this.fragmentNameField);

        this.add(this.addConditionButton);
        this.add(this.remConditionButton);

        this.add(this.conditionLabel);
        this.add(this.conditionsList);

        this.add(this.saveButton);
        this.add(this.cancelButton);
    }

    public void updateCell() {
        this.graph.getGraphLayoutCache().editCell(this.cell,new HashMap());
    }

    public String getFragmentName(){
        return this.fragmentNameField.getText();
    }

    public List<ConditionModel> getConditions(){
        List<ConditionModel> cons = new ArrayList<ConditionModel>();
        int size = conditionListModel.size();
        for (int i = 0; i < size; i++) {
            ConditionModel a = (ConditionModel) conditionListModel.get(i);
            cons.add(a);
        }
        return cons;
    }


        /**
     * Loads list of attributes into the attribute list which is situated in the
     * dialog
     */
    private void loadConditionListValues() {
        conditions = this.model.getConditionModels();
        this.conditionListModel.clear();
        for (ConditionModel con : conditions) {
            this.conditionListModel.addElement(con);
        }
    }

    @Override
    public void modelChanged() {
        loadConditionListValues();
    }

}
