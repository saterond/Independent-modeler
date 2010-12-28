package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.IndependentModelerPaletteNodeModel;

/**
 *
 * @author Petr Vales
 */
public class CellFactory  {

        public CellFactory() {
        super();
    }


    public Cell getCell(String cellName) {
        if (cellName == null) {
            return null;
        }
        return this.getCell(this.findNodeModel(cellName));
//        Cell cell = null;
//        if (cellName.contains("Note")) {
//            cell = new NoteCell(cellName);
//        } else if (cellName.contains("Dependency")) {
//            cell = new RoundRectCell(cellName);
//        }
//        return cell;
    }

    public Cell getCell(Enum cellNode) {
        if (cellNode == null) {
            return null;
        }
        Cell cell = null;
        if (cellNode == IndependentModelerPaletteNodeModel.Note) {
            cell = new NoteCell(cellNode.name());
        } else if (cellNode == IndependentModelerPaletteNodeModel.Dependency) {
            cell = new RoundRectCell(cellNode.name());
        }
        return cell;
    }

    protected Enum findNodeModel(String cellName) {
        for (IndependentModelerPaletteNodeModel i :
                IndependentModelerPaletteNodeModel.values()) {
            if (i.name().contains(cellName)) {
                return i;
            }
        }
        return null;
    }
}
