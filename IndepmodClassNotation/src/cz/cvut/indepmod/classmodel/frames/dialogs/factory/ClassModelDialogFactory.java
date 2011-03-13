package cz.cvut.indepmod.classmodel.frames.dialogs.factory;

import cz.cvut.indepmod.classmodel.frames.dialogs.AbstractAttrCreatorDialog;
import cz.cvut.indepmod.classmodel.frames.dialogs.AbstractEditClassDialog;
import cz.cvut.indepmod.classmodel.frames.dialogs.ClassModelAttrCreatorDialog;
import cz.cvut.indepmod.classmodel.frames.dialogs.ClassModelEditClassDialog;
import cz.cvut.indepmod.classmodel.workspace.ClassModelGraph;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.ClassModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.TypeModel;
import java.util.Collection;
import org.jgraph.graph.DefaultGraphCell;
import org.openide.windows.WindowManager;

/**
 * Date: 5.3.2011
 * Time: 10:38:44
 * @author Lucky
 */
public class ClassModelDialogFactory extends AbstractDialogFactory {

    @Override
    public AbstractEditClassDialog createEditClassDialog(ClassModelGraph graph, DefaultGraphCell cell, ClassModel model) {
        AbstractEditClassDialog dialog = new ClassModelEditClassDialog(
                WindowManager.getDefault().getMainWindow(),
                graph, 
                cell, 
                model);
        return dialog;
    }

    @Override
    public AbstractAttrCreatorDialog createAttributeCreatorDialog(Collection<TypeModel> types) {
        AbstractAttrCreatorDialog dialog = new ClassModelAttrCreatorDialog(
                WindowManager.getDefault().getMainWindow(),
                types);
        return dialog;
    }

}
