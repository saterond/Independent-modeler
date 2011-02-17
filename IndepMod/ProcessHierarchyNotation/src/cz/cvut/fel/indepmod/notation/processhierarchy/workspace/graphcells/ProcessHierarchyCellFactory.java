package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.notation.processhierarchy.workspace.palette.ProcessHierarchyPaletteNodeModel;

/**
 *
 * @author Petr Vales
 * @deprecated 
 */
public class ProcessHierarchyCellFactory extends CellFactory {

    public ProcessHierarchyCellFactory() {
        super();
    }


    @Override
    public Cell getCell(Enum cellNode) {
        if (cellNode == null) {
            return null;
        }
        Cell cell = null;
        if (cellNode == ProcessHierarchyPaletteNodeModel.Process) {
            cell = new ProcessCell(cellNode.name());
        } else if (cellNode == ProcessHierarchyPaletteNodeModel.Data) {
            cell = new DataCell(cellNode.name());
        } else if (cellNode == ProcessHierarchyPaletteNodeModel.Role) {
            cell = new RoleCell(cellNode.name());
        }
        return cell != null ? cell: super.getCell(cellNode);
    }

    
//    @Override
//    protected Enum findNodeModel(String cellName) {
//        for (ProcessHierarchyPaletteNodeModel i :
//                ProcessHierarchyPaletteNodeModel.values()) {
//            if (i.name().contains(cellName)) {
//                return i;
//            }
//        }
//        return super.findNodeModel(cellName);
//    }

}
