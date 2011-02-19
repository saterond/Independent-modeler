package cz.cvut.indepmod.classmodel.modelFactory.diagramModel;

import cz.cvut.indepmod.classmodel.api.model.DiagramType;
import cz.cvut.indepmod.classmodel.workspace.ClassModelGraphModel;
import cz.cvut.indepmod.classmodel.workspace.cell.ClassModelCellViewFactory;
import org.jgraph.graph.GraphLayoutCache;


public class ClassModelDiagramModel {

    private GraphLayoutCache layoutCache;
    private DiagramType diagramType;
//    private UndoManager undoManager;

    public ClassModelDiagramModel() {
        this.layoutCache = new GraphLayoutCache(
                new ClassModelGraphModel(),
                new ClassModelCellViewFactory()
        );
        this.diagramType = DiagramType.CLASS;

//        this.undoManager = new UndoManager();
//        this.layoutCache.getModel().addUndoableEditListener(this.undoManager);
    }

    public ClassModelDiagramModel(GraphLayoutCache layoutCache) {
        this.layoutCache = layoutCache;
        this.diagramType = DiagramType.CLASS;
    }

    public GraphLayoutCache getLayoutCache() {
        return layoutCache;
    }

    public DiagramType getDiagramType() {
        return diagramType;
    }

//    public UndoManager getUndoManager() {
//        return undoManager;
//    }

}
