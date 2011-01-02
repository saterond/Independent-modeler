/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.frames.dialogs;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelSaveEditMessageDialog;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.AttributeModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.ModelListener;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.TypeModel;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.openide.windows.WindowManager;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelEditMessageDialog extends SequenceModelAbstractDialog implements ModelListener{

    private static final Logger LOG = Logger.getLogger(SequenceModelEditMessageDialog.class.getName());
    private MessageModel model;
    private SequenceModelGraph graph;
    private DefaultGraphCell cell;
    private Collection<TypeModel> availableTypes;
    private DefaultListModel attributeListModel;
    private Set<AttributeModel> attributes;
    private TypeModel type;

    public static final String TITLE = "Message Edit";
    public static final String NAME_LABEL = "Name";
    public static final String PARAMETERS_LABEL = "Parameters";
    public static final String RETURN_LABEL = "Is Return";
    public static final String SAVE_BUTTON = "Ok";
    public static final String CANCEL_BUTTON = "Cancel";
    public static final String ADD_ATR_BUTTON = "Add Attribute";
    public static final String REM_ATR_BUTTON = "Remove Attribute";
    public static final String TYPE_LABEL = "Type";

    private JLabel messageNameLabel;
    private JLabel parametersLabel;
    private JLabel returnLabel;
    private JLabel typeLabel;
    private JComboBox typeBox;
    private JTextField messageNameField;
    private JTextField parametersField;
    private JButton saveButton;
    private JButton cancelButton;
    private JCheckBox returnCheckBox;
    private JList attributesList;
    private JButton addAttributeButton;
    private JButton remAttributeButton;

    public SequenceModelEditMessageDialog(Frame owner, SequenceModelGraph graph, DefaultGraphCell cell, MessageModel model) {
        super(owner, TITLE);
        this.model = model;
        this.graph = graph;
        this.cell = cell;
        this.availableTypes = this.graph.getAllTypes();
        this.type = this.model.getType();

        this.initValues();
        this.initAction();
        this.initLayout();
        this.setSizes();
    }

    private void initValues() {
        this.attributeListModel = new DefaultListModel();
        this.messageNameLabel = new JLabel(NAME_LABEL);
        this.parametersLabel = new JLabel(PARAMETERS_LABEL);
        this.returnLabel = new JLabel(RETURN_LABEL);
        this.typeLabel = new JLabel(TYPE_LABEL);
        this.typeBox = new JComboBox(this.availableTypes.toArray());
        this.messageNameField = new JTextField();
        this.parametersField = new JTextField();
        this.saveButton = new JButton(SAVE_BUTTON);
        this.cancelButton = new JButton(CANCEL_BUTTON);
        this.returnCheckBox = new JCheckBox();
        this.attributesList = new JList(this.attributeListModel);
        this.addAttributeButton = new JButton(ADD_ATR_BUTTON);
        this.remAttributeButton = new JButton(REM_ATR_BUTTON);
        //String typeName = cell.toString();
        //this.messageNameField.setText(typeName);
        this.messageNameField.setText(this.model.getName());
        this.attributes = this.model.getAttributeModels();

            for(TypeModel tp : availableTypes){
            if(tp.toString().contains(type.toString())) this.typeBox.setSelectedItem(tp);
            }
       

        if(!attributes.isEmpty()){
            for(AttributeModel atr : attributes){
            this.attributeListModel.addElement(atr);
            }
        }
        //TODO - dodelat defaultni nastaveni parametru
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
        this.add(this.attributesList);

        this.add(this.returnLabel);
        this.add(this.returnCheckBox);

        this.add(this.saveButton);
        this.add(this.cancelButton);
    }


    @Override
    public void dispose() {
        this.model.removeListener(this);
        super.dispose();
    }

    private void initAction() {
         this.saveButton.addActionListener(new SequenceModelSaveEditMessageDialog(this.model,this));      
   
         this.addAttributeButton.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame window = WindowManager.getDefault().getMainWindow();
                AttributeModel attr = new SequenceModelAttributeCreatorDialog(
                        window,
                        availableTypes).getAttribute();

                if (attr != null) {
                    attributeListModel.addElement(attr);
                }
            }
        });
        
        this.remAttributeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AttributeModel attr = (AttributeModel) attributesList.getSelectedValue();
                if (attr != null) {
                    attributeListModel.removeElement(attr);
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

    public void updateCell() {
        this.graph.getGraphLayoutCache().editCell(this.cell,new HashMap());
    }

    /**
     * Initializes event handlers
     */
    private void initHandlers() {
        this.model.addListener(this);
    }
    
    //Metody pro ziskani informaci o tvorene message.
    public String getMessageName(){
        return this.messageNameField.getText();
    }
    
    public boolean isMessageReturn(){
        return this.returnCheckBox.isSelected();
    }

    public Set<AttributeModel> getAttributes(){
        Set<AttributeModel> attrs = new HashSet<AttributeModel>();
        int size = attributeListModel.size();
        for (int i = 0; i < size; i++) {
            AttributeModel a = (AttributeModel) attributeListModel.get(i);
            attrs.add(a);
        }
        return attrs;
    }

    public TypeModel getType(){
        return (TypeModel) typeBox.getSelectedItem();
    }

        /**
     * Loads list of attributes into the attribute list which is situated in the
     * dialog
     */
    private void loadAttributeListValues() {
        Set<AttributeModel> attributes = this.model.getAttributeModels();
        this.attributeListModel.clear();
        for (AttributeModel attr : attributes) {
            this.attributeListModel.addElement(attr);
        }
    }

    @Override
    public void modelChanged() {
        loadAttributeListValues();
    }

}
