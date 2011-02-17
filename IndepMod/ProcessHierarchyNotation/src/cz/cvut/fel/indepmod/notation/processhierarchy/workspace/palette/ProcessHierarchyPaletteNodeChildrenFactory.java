package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.IPaletteNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteCellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNodeChildrenFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.DataCell;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.DataCellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.ProcessCell;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.ProcessCellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells.RoleCell;
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
    protected boolean createKeys(final List<IPaletteNode> toPopulate) {
        super.createKeys(toPopulate);
        if (super.getKey().equals("Process Hierarchy")) {

            toPopulate.add(new PaletteCellNode<DataCell>(
                    ProcessHierarchyPaletteNodeModel.Data.name(),
                    new DataCellFactory()));
            toPopulate.add(new PaletteCellNode<RoleCell>(
                    ProcessHierarchyPaletteNodeModel.Role.name(),
                    new RoleCellFactory()));
            toPopulate.add(new PaletteCellNode<ProcessCell>(
                    ProcessHierarchyPaletteNodeModel.Process.name(),
                    new ProcessCellFactory()));

        }
        return true;
    }
}
