package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.IndependentModelerPaletteNodeModel;

/**
 *
 * @author Petr Vales
 */
public class CellFactory {

    public CellFactory() {
        super();
    }

//    public Cell getCell(String cellNode) {
//        if (cellNode == null) {
//            return null;
//        }
//        return this.getCell(this.findNodeModel(cellNode));
//    }

    public Cell getCell(Enum cellNode) {
        if (cellNode == null) {
            return null;
        }
        Cell cell = null;
        if (cellNode == IndependentModelerPaletteNodeModel.Note) {
            cell = new NoteCell(cellNode.name());
        }
        return cell;
    }

//    protected Enum findNodeModel(String cellName) {
//        for (IndependentModelerPaletteNodeModel i :
//                IndependentModelerPaletteNodeModel.values()) {
//            if (i.name().contains(cellName)) {
//                return i;
//            }
//        }
//        return null;
//    }
}
