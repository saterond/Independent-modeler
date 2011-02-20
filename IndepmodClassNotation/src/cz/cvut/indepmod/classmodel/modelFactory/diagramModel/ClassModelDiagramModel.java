package cz.cvut.indepmod.classmodel.modelFactory.diagramModel;

import cz.cvut.indepmod.classmodel.api.model.DiagramType;
import cz.cvut.indepmod.classmodel.workspace.ClassModelGraphModel;
import cz.cvut.indepmod.classmodel.workspace.cell.ClassModelCellViewFactory;
import org.jgraph.graph.GraphLayoutCache;

/**
 * This class stores information which have to be saved. It is information
 * like which type of diagram is used (business, class), used GraphLayoutCache,
 * static data types, ...
 * @author Lucky
 */
public class ClassModelDiagramModel {

    private GraphLayoutCache layoutCache;
    private DiagramType diagramType;

    public ClassModelDiagramModel() {
        this.layoutCache = new GraphLayoutCache(
                new ClassModelGraphModel(),
                new ClassModelCellViewFactory());
        this.diagramType = DiagramType.CLASS;
    }

    public ClassModelDiagramModel(GraphLayoutCache layoutCache, DiagramType diagramType) {
        this.layoutCache = layoutCache;
        this.diagramType = diagramType;

        if (this.layoutCache == null) {
            this.layoutCache = new GraphLayoutCache(
                    new ClassModelGraphModel(),
                    new ClassModelCellViewFactory());
        }
    }

    public GraphLayoutCache getLayoutCache() {
        return layoutCache;
    }

    public DiagramType getDiagramType() {
        return diagramType;
    }
}