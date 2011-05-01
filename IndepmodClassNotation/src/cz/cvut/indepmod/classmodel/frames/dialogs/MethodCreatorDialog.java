package cz.cvut.indepmod.classmodel.frames.dialogs;

import cz.cvut.indepmod.classmodel.Globals;
import cz.cvut.indepmod.classmodel.actions.ClassModelAbstractAction;
import cz.cvut.indepmod.classmodel.api.model.DiagramType;
import cz.cvut.indepmod.classmodel.api.model.IAttribute;
import cz.cvut.indepmod.classmodel.api.model.IType;
import cz.cvut.indepmod.classmodel.api.model.Visibility;
import cz.cvut.indepmod.classmodel.frames.dialogs.factory.AbstractDialogFactory;
import cz.cvut.indepmod.classmodel.frames.dialogs.validation.AbstractDialogValidation;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AttributeModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.MethodModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.TypeModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 23.10.2010
 * Time: 13:23:01
 * @author Lucky
 */
public class MethodCreatorDialog extends MethodCreatorDialogView {

    private MethodModel returnValue = null;
    private Collection<IType> availableTypes;

    public MethodCreatorDialog(Frame owner, Collection<IType> types) {
        this(owner, types, null);
    }

    public MethodCreatorDialog(Frame owner, Collection<IType> types, MethodModel model) {
        super(owner);

        this.availableTypes = types;
        this.returnValue = model;

        this.initValues();
        this.initAction();
        this.setSizes();
    }

    public MethodModel getReturnValue() {
        return this.returnValue;
    }

    private void initAction() {
        this.saveButton.addActionListener(new SaveAction());
        this.addAttrButton.addActionListener(new AddAttributeAction());
        this.editAttrButton.addActionListener(new EditAttributeAction());
        this.remAttrButton.addActionListener(new RemoveAttributeAction());
        this.cancelButton.addActionListener(new CancelAction());

        this.getRootPane().setDefaultButton(this.saveButton);
    }

    private void initValues() {
        this.typeBox.removeAllItems();
        for (IType type : this.availableTypes) {
            this.typeBox.addItem(type);
        }

        this.visibilityBox.removeAllItems();
        this.visibilityBox.addItem(Visibility.PUBLIC);
        this.visibilityBox.addItem(Visibility.PROTECTED);
        this.visibilityBox.addItem(Visibility.PRIVATE);
        this.visibilityBox.addItem(Visibility.NONE);

        if (this.returnValue != null) {
            this.nameField.setText(this.returnValue.getName());
            this.typeBox.setSelectedItem(this.returnValue.getType());
            this.visibilityBox.setSelectedItem(this.returnValue.getVisibility());
            this.isStaticBox.setSelected(this.returnValue.isStatic());
            this.isAbstractBox.setSelected(this.returnValue.isAbstract());
            for (IAttribute attr : this.returnValue.getAttributeModels()) {
                this.attributeListModel.addElement(attr);
            }
        }
    }

    //================== INNER CLASS WITH ACTION ===============================
    private class SaveAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            TypeModel returnType = (TypeModel) typeBox.getSelectedItem();
            Visibility vis = (Visibility) visibilityBox.getSelectedItem();
            boolean isAbstract = isAbstractBox.isSelected();
            boolean isStatic = isStaticBox.isSelected();

            AbstractDialogValidation val = AbstractDialogValidation.getValidation();
            if (val.validateMethod(vis, isAbstract, isStatic, name)) {
                Set<IAttribute> attrs = new HashSet<IAttribute>();
                int size = attributeListModel.size();
                for (int i = 0; i < size; i++) {
                    AttributeModel a = (AttributeModel) attributeListModel.get(i);
                    attrs.add(a);
                }

                if (returnValue == null) {
                    returnValue = new MethodModel(returnType, name, attrs, vis);
                } else {
                    returnValue.setAttributeModels(attrs);
                    returnValue.setType(returnType);
                    returnValue.setName(name);
                    returnValue.setVisibility(vis);
                }
                returnValue.setAbstract(isAbstract);
                returnValue.setStatic(isStatic);
                dispose();
            }
        }
    }

    private class AddAttributeAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            DiagramType diagramType = Globals.getInstance().getActualDiagramData().getDiagramType();
            AbstractDialogFactory factory = AbstractDialogFactory.getFactory(diagramType);

            IAttribute attr = factory.createAttributeCreatorDialog(
                    availableTypes).getReturnValue();

            if (attr != null) {
                attributeListModel.addElement(attr);
            }
        }
    }

    private class EditAttributeAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            AttributeModel attr = (AttributeModel) attrList.getSelectedValue();
            if (attr != null) {
                DiagramType diagramType = Globals.getInstance().getActualDiagramData().getDiagramType();
                AbstractDialogFactory factory = AbstractDialogFactory.getFactory(diagramType);
                
                IAttribute updatedAttr = factory.createAttributeCreatorDialog(
                    availableTypes, attr).getReturnValue();
                attributeListModel.set(attributeListModel.indexOf(updatedAttr), updatedAttr);
            }
        }
    }

    private class RemoveAttributeAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            AttributeModel attr = (AttributeModel) attrList.getSelectedValue();
            if (attr != null) {
                attributeListModel.removeElement(attr);
            }
        }
    }

    private class CancelAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
