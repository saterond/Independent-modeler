package cz.cvut.indepmod.classmodel.frames.dialogs.factory;

import cz.cvut.indepmod.classmodel.frames.dialogs.ClassModelEditClassDialog;
import cz.cvut.indepmod.classmodel.workspace.ClassModelGraph;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.ClassModel;
import org.jgraph.graph.DefaultGraphCell;
import org.openide.windows.WindowManager;

/**
 * Date: 5.3.2011
 * Time: 10:38:44
 * @author Lucky
 */
public class ClassModelDialogFactory extends AbstractDialogFactory {

    @Override
    public ClassModelEditClassDialog createEditClassDialog(ClassModelGraph graph, DefaultGraphCell cell, ClassModel model) {
        return new ClassModelEditClassDialog(WindowManager.getDefault().getMainWindow(), graph, cell, model);
    }

}
