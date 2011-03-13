package cz.cvut.indepmod.classmodel.frames.dialogs;

import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.TypeModel;
import java.awt.Frame;
import java.util.Collection;
import javax.swing.JPanel;

/**
 * Date: 13.3.2011
 * Time: 15:12:13
 * @author Lucky
 */
public class BusinessModelAttrCreatorDialog extends AbstractAttrCreatorDialog  {

    public BusinessModelAttrCreatorDialog(Frame owner, Collection<TypeModel> types) {
        super(owner, types);
    }

    public BusinessModelAttrCreatorDialog(Frame owner) {
        super(owner);
    }

    @Override
    protected JPanel initAnotationPanel() {
        return null;
    }



}
