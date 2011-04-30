package cz.cvut.indepmod.classmodel.frames.dialogs;

import cz.cvut.indepmod.classmodel.actions.ClassModelAbstractAction;
import cz.cvut.indepmod.classmodel.api.model.IMultidirectionalRelation;
import cz.cvut.indepmod.classmodel.api.model.ICardinality;
import cz.cvut.indepmod.classmodel.api.model.IRelation;
import cz.cvut.indepmod.classmodel.api.model.RelationDirection;
import cz.cvut.indepmod.classmodel.workspace.ClassModelGraph;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.Cardinality;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

/**
 * Date: 21.11.2010
 * Time: 17:41:36
 * @author Lucky
 *
 * Dialog that is used for relation editing
 */
public class EditRelationDialog extends EditRelationDialogView implements ItemListener {

    private static final Logger LOG = Logger.getLogger(EditRelationDialog.class.getName());
    private ClassModelGraph graph;
    private DefaultEdge edge;
    private IMultidirectionalRelation model;
    private boolean changed;

    public EditRelationDialog(Frame owner, ClassModelGraph graph, DefaultEdge edge, IMultidirectionalRelation model) {
        super(owner);

        this.graph = graph;
        this.edge = edge;
        this.model = model;
        this.changed = false;

        this.initValues();
        this.initHandlers();
        this.initActions();
        this.setSizes();
    }

    public boolean isChanged() {
        return this.changed;
    }

    public ICardinality getStartingCardinality() {
        Cardinality res = (Cardinality) this.sourceCardinality.getSelectedItem();
        return res;
    }

    public ICardinality getEndingCardinality() {
        Cardinality res = (Cardinality) this.targetCardinality.getSelectedItem();
        return res;
    }

    public String getRelationName() {
        String res = this.nameField.getText().trim();
        if (res.isEmpty()) {
            return null;
        } else {
            return res;
        }
    }

    public RelationDirection getRelationDirection() {
        if (this.unidirectional.isSelected()) {
            return RelationDirection.UNIDIRECTIONAL;
        }
        return RelationDirection.BIDIRECTIONAL;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        LOG.info("property changed");
        this.changed = true;
    }

    private void initValues() {
        this.nameField.setText(this.model.getRelationName());

        this.sourceCardinality.removeAllItems();
        this.targetCardinality.removeAllItems();

        this.sourceCardinality.addItem(Cardinality.ZERO);
        this.sourceCardinality.addItem(Cardinality.ONE);
        this.sourceCardinality.addItem(Cardinality.ZERO_INFINITY);
        this.sourceCardinality.addItem(Cardinality.ONE_INFINITY);

        this.targetCardinality.addItem(Cardinality.ZERO);
        this.targetCardinality.addItem(Cardinality.ONE);
        this.targetCardinality.addItem(Cardinality.ZERO_INFINITY);
        this.targetCardinality.addItem(Cardinality.ONE_INFINITY);

        this.sourceCardinality.setSelectedItem(this.model.getStartCardinality());
        this.targetCardinality.setSelectedItem(this.model.getEndCardinality());

        if (this.model.getDirection().equals(RelationDirection.UNIDIRECTIONAL)) {
            this.unidirectional.setSelected(true);
        } else {
            this.bidirectional.setSelected(true);
        }

        boolean nameAlongEdge = GraphConstants.isLabelAlongEdge(this.edge.getAttributes());
        this.nameAlongCheck.setSelected(nameAlongEdge);

        if (GraphConstants.getLineEnd(this.edge.getAttributes()) == GraphConstants.ARROW_SIMPLE) {
            this.arrowCheck.setSelected(true);
        }
    }

    private void initHandlers() {
        this.sourceCardinality.addItemListener(this);
        this.targetCardinality.addItemListener(this);
        this.nameField.getDocument().addDocumentListener(new NameFieldDocListener());

        ChangeListener editRelChange = new EditRelationChangeListener();
        this.arrowCheck.addChangeListener(editRelChange);
        this.unidirectional.addChangeListener(editRelChange);
        this.bidirectional.addChangeListener(editRelChange);
        this.nameAlongCheck.addChangeListener(editRelChange);
    }

    private void initActions() {
        this.saveButton.addActionListener(new SaveAction());
        this.cancelButton.addActionListener(new CancelAction());

        this.getRootPane().setDefaultButton(this.saveButton);
    }

    //==========================================================================
    //======================== INNER CLASS =====================================
    //==========================================================================
    private class EditRelationChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            changed = true;
        }

    }

    private class NameFieldDocListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            changed = true;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changed = true;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changed = true;
        }
    }

    private class SaveAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EditRelationDialog.this.isChanged()) {
                ICardinality startCard = EditRelationDialog.this.getStartingCardinality();
                ICardinality endCard = EditRelationDialog.this.getEndingCardinality();
                String relationName = EditRelationDialog.this.getRelationName();

                model.setRelationDirection(getRelationDirection());

                IRelation userObj = (IRelation) EditRelationDialog.this.edge.getUserObject();
                userObj.setRelationName(relationName);

                Map attrMap = new Hashtable();
                GraphConstants.setExtraLabels(attrMap, new ICardinality[]{startCard, endCard});
                GraphConstants.setLabelAlongEdge(attrMap, nameAlongCheck.isSelected());

                int lineBegin = GraphConstants.getLineBegin(edge.getAttributes());
                int newLineBegin = lineBegin;
                int newLineEnd;
                if (arrowCheck.isSelected()) {
                    newLineEnd = GraphConstants.ARROW_SIMPLE;
                    if (newLineBegin == GraphConstants.ARROW_NONE || newLineBegin == GraphConstants.ARROW_SIMPLE) {
                        newLineBegin = getRelationDirection() == RelationDirection.BIDIRECTIONAL ? GraphConstants.ARROW_SIMPLE : GraphConstants.ARROW_NONE;
                    }
                } else {
                    newLineEnd = GraphConstants.ARROW_NONE;
                    if (newLineBegin == GraphConstants.ARROW_NONE || newLineBegin == GraphConstants.ARROW_SIMPLE) {
                        newLineBegin = GraphConstants.ARROW_NONE;
                    }
                }
                GraphConstants.setLineBegin(attrMap, newLineBegin);
                GraphConstants.setLineEnd(attrMap, newLineEnd);

                EditRelationDialog.this.graph.getGraphLayoutCache().editCell(edge, attrMap);
            }
            EditRelationDialog.this.dispose();
        }
    }

    private class CancelAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }

    }
}
