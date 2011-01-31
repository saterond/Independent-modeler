package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNodeChildrenFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.DataCellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.ProcessCellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.RoleCellFactory;
import java.util.List;

/**
 *
 * @author Petr Vales
 */
public class ProcessHierarchyPaletteNodeChildrenFactory
        extends PaletteNodeChildrenFactory {

    public ProcessHierarchyPaletteNodeChildrenFactory(String myNodeKey) {
        super(myNodeKey);
    }

    @Override
    protected boolean createKeys(final List<PaletteNode> toPopulate) {
        super.createKeys(toPopulate);
        if (super.getKey().equals("Process Hierarchy")) {

            toPopulate.add(new PaletteNode(
                    ProcessHierarchyPaletteNodeModel.Data.name(),
                    ProcessHierarchyPaletteNodeModel.Data,
                    new DataCellFactory()));
            toPopulate.add(new PaletteNode(
                    ProcessHierarchyPaletteNodeModel.Role.name(),
                    ProcessHierarchyPaletteNodeModel.Role,
                    new RoleCellFactory()));
            toPopulate.add(new PaletteNode(
                    ProcessHierarchyPaletteNodeModel.Process.name(),
                    ProcessHierarchyPaletteNodeModel.Process,
                    new ProcessCellFactory()));

        }
        return true;
    }
}
