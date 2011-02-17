package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.CellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.palette.EPCPaletteNodeModel;

/**
 *
 * @author Petr Vales
 * @deprecated 
 */
public class EPCCellFactory extends CellFactory {

    @Override
    public Cell getCell(Enum cellNode) {
        if (cellNode == null) {
            return null;
        }
        Cell cell = null;
        if (cellNode == EPCPaletteNodeModel.Event) {
            cell = new EventCell(cellNode.name());
        } else if (cellNode == EPCPaletteNodeModel.Function) {
            cell = new FunctionCell(cellNode.name());
        } else if (cellNode == EPCPaletteNodeModel.Oraganization_Unit) {
            cell = new OrganizationUnitCell(cellNode.name());
        } else if (cellNode == EPCPaletteNodeModel.Supporting_System) {
            cell = new SupportingSystemCell(cellNode.name());
        } else if (cellNode == EPCPaletteNodeModel.Input) {
            cell = new InputCell(cellNode.name());
        } else if (cellNode == EPCPaletteNodeModel.Output) {
            cell = new OutputCell(cellNode.name());
        }
        return cell != null ? cell : super.getCell(cellNode);
    }

//    @Override
//    protected Enum findNodeModel(String cellName) {
//        for (EPCPaletteNodeModel i :
//                EPCPaletteNodeModel.values()) {
//            if (i.name().contains(cellName)) {
//                return i;
//            }
//        }
//        return super.findNodeModel(cellName);
//    }
}
