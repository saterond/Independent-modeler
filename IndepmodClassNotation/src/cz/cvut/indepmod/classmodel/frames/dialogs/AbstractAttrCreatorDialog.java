package cz.cvut.indepmod.classmodel.frames.dialogs;

import cz.cvut.indepmod.classmodel.Globals;
import cz.cvut.indepmod.classmodel.actions.ClassModelAbstractAction;
import cz.cvut.indepmod.classmodel.api.model.IAnnotation;
import cz.cvut.indepmod.classmodel.api.model.IAttribute;
import cz.cvut.indepmod.classmodel.api.model.IType;
import cz.cvut.indepmod.classmodel.api.model.Visibility;
import cz.cvut.indepmod.classmodel.diagramdata.DiagramDataModel;
import cz.cvut.indepmod.classmodel.frames.dialogs.validation.AbstractDialogValidation;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AttributeModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.TypeModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultListModel;
import org.openide.windows.WindowManager;

/**
 * Date: 17.10.2010
 * Time: 14:32:19
 * @author Lucky
 */
public class AbstractAttrCreatorDialog extends AbstractAttrCreatorDialogView {

    private Collection<IType> availableTypes;
    private IAttribute returnValue;
    protected DefaultListModel anotationListModel;

    public AbstractAttrCreatorDialog(Frame owner) {
        this(owner, new ArrayList<IType>(0));
    }

    public AbstractAttrCreatorDialog(Frame owner, Collection<IType> types) {
        this(owner, types, null);
    }

    public AbstractAttrCreatorDialog(Frame owner, Collection<IType> types, IAttribute model) {
        super(owner);

        this.availableTypes = types;
        this.returnValue = model;

        this.initComponentBehavior();
        this.initValues();
        this.initAction();
        this.setSizes();
    }

    public IAttribute getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(IAttribute attribute) {
        this.returnValue = attribute;
    }

    public List<IAnnotation> getAnotationList() {
        List<IAnnotation> res = new ArrayList<IAnnotation>(this.anotationListModel.getSize());

        for (int i = 0; i < this.anotationListModel.getSize(); i++) {
            res.add((IAnnotation) this.anotationListModel.get(i));
        }

        return res;
    }

    public void addAnotation(IAnnotation anotation) {
        this.anotationListModel.addElement(anotation);
    }

    public void removeAnotationAt(int index) {
        this.anotationListModel.remove(index);
    }

    private void initAction() {
        this.createButton.addActionListener(new CreateAttributeAction());
        this.addAnotationButton.addActionListener(new AddAnnotationAction());
        this.editAnotationButton.addActionListener(new EditAnnotationAction());
        this.removeAnotationButton.addActionListener(new RemoveAnnotationAction());

        this.getRootPane().setDefaultButton(this.createButton);
    }

    private void initValues() {
        this.anotationListModel = new DefaultListModel();
        this.anotationList.setModel(this.anotationListModel);

        this.attributeType.removeAllItems();
        for (IType type : this.availableTypes) {
            this.attributeType.addItem(type);
        }

        this.attributeVisibility.removeAllItems();
        this.attributeVisibility.addItem(Visibility.PUBLIC);
        this.attributeVisibility.addItem(Visibility.PROTECTED);
        this.attributeVisibility.addItem(Visibility.PRIVATE);
        this.attributeVisibility.addItem(Visibility.NONE);

        if (this.returnValue != null) {
            this.attributeName.setText(this.returnValue.getName());
            this.attributeVisibility.setSelectedItem(this.returnValue.getVisibility());
            this.attributeType.setSelectedItem(this.returnValue.getType());
            for (IAnnotation anot : this.returnValue.getAnotations()) {
                this.addAnotation(anot);
            }
        }
    }

    private void initComponentBehavior() {
        this.attributeType.setEditable(true);
    }

    //==========================================================================
    //============ INNER CLASS =================================================
    //==========================================================================
    private class CreateAttributeAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            DiagramDataModel diagramData = Globals.getInstance().getActualDiagramData();
            AbstractDialogValidation val = AbstractDialogValidation.getValidation(diagramData.getDiagramType());

            String name = AbstractAttrCreatorDialog.this.getAttributeName();
            Object dataTypeObj = AbstractAttrCreatorDialog.this.getSelectedAttributeType();
            Visibility visibility = AbstractAttrCreatorDialog.this.getSelectedVisibility();

            if (val.validateAttributeName(name)) {
                TypeModel dataType;
                if (dataTypeObj instanceof String) {
                    dataType = new TypeModel((String) dataTypeObj);
                    diagramData.addDynamicDataType(dataType);
                } else {
                    dataType = (TypeModel) dataTypeObj;
                }

                IAttribute attribute = returnValue;
                if (attribute == null) {
                    attribute = new AttributeModel(dataType, name, visibility);
                } else {
                    attribute.setName(name);
                    attribute.setType(dataType);
                    attribute.setVisibility(visibility);
                }

                for (IAnnotation anot : AbstractAttrCreatorDialog.this.getAnotationList()) {
                    attribute.addAnotation(anot);
                }

                AbstractAttrCreatorDialog.this.setReturnValue(attribute);
                AbstractAttrCreatorDialog.this.dispose();
            }
        }
    }

    private class AddAnnotationAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame window = WindowManager.getDefault().getMainWindow();
            IAnnotation anot = new AnotationCreatorDialog(window).getAnotation();

            if (anot != null) {
                AbstractAttrCreatorDialog.this.addAnotation(anot);
            }
        }
    }

    private class EditAnnotationAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = AbstractAttrCreatorDialog.this.getSelectedAnotationIndex();
            if (index != -1) {
                IAnnotation anot = (IAnnotation) anotationListModel.getElementAt(index);
                Frame window = WindowManager.getDefault().getMainWindow();
                new AnotationCreatorDialog(window, anot).getAnotation();

                anotationListModel.set(index, anot);
            }
        }
    }

    private class RemoveAnnotationAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = AbstractAttrCreatorDialog.this.getSelectedAnotationIndex();
            if (index != -1) {
                AbstractAttrCreatorDialog.this.removeAnotationAt(index);
            }
        }
    }
}
