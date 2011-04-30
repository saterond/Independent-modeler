package cz.cvut.indepmod.classmodel.frames.dialogs;

import cz.cvut.indepmod.classmodel.actions.ClassModelAbstractAction;
import cz.cvut.indepmod.classmodel.api.model.IAnnotation;
import cz.cvut.indepmod.classmodel.api.model.IAnotationValue;
import cz.cvut.indepmod.classmodel.frames.dialogs.validation.AbstractDialogValidation;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AnotationAttributeModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AnotationModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import org.openide.windows.WindowManager;

/**
 * Date: 25.11.2010
 * Time: 18:00:07
 * @author Lucky
 */
public class AnotationCreatorDialog extends AnotationCreatorDialogView {

    private static final Logger LOG = Logger.getLogger(AnotationCreatorDialog.class.getName());
    private IAnnotation returnValue;

    public AnotationCreatorDialog(Frame owner) {
        this(owner, null);
    }

    public AnotationCreatorDialog(Frame owner, IAnnotation returnValue) {
        super(owner);

        this.returnValue = returnValue;
        this.initValues();
        this.initAction();
        this.setSizes();
    }

    public IAnnotation getAnotation() {
        return this.returnValue;
    }

    private void initAction() {
        this.createButton.addActionListener(new CreateAction());
        this.addValueButton.addActionListener(new AddValueAction());
        this.removeValueButton.addActionListener(new RemoveValueAction());

        this.getRootPane().setDefaultButton(this.createButton);
    }

    private void initValues() {
        if (this.returnValue != null) {
            this.anotationName.setText(this.returnValue.getName());
            for (IAnotationValue anotVal : this.returnValue.getAttributes()) {
                this.valueListModel.addElement(anotVal);
            }
        }
    }

    //==========================================================================
    //======================== INNER CLASS =====================================
    //==========================================================================
    private class CreateAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractDialogValidation val = AbstractDialogValidation.getValidation();
            String name = anotationName.getText();

            if (val.validateAnnotationName(name)) {
                if (returnValue == null) {
                    returnValue = new AnotationModel(name);
                } else {
                    returnValue.setName(name);
                    for (IAnotationValue anotVal : returnValue.getAttributes()) {
                        returnValue.removeAttribute(anotVal);
                    }
                }

                Object[] atrList = valueListModel.toArray();
                for (int i = 0; i < atrList.length; i++) {
                    returnValue.addAttribute((AnotationAttributeModel) atrList[i]);
                }
                dispose();
            }
        }
    }

    private class AddValueAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame window = WindowManager.getDefault().getMainWindow();
            AnotationAttributeModel atr = new AnotationAttributeCreatorDialog(window).getReturnValue();

            if (atr != null) {
                valueListModel.addElement(atr);
                LOG.info("Added Anotation Attribute.");
            } else {
                LOG.info("Anotation Attribute was not added.");
            }
        }
    }

    private class RemoveValueAction extends ClassModelAbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = valueList.getSelectedIndex();
            if (index != -1) {
                valueListModel.remove(index);
            }
        }
    }
}
