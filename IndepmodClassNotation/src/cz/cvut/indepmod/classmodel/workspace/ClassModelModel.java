package cz.cvut.indepmod.classmodel.workspace;

import cz.cvut.indepmod.classmodel.api.model.DiagramType;
import cz.cvut.indepmod.classmodel.api.model.IClass;
import cz.cvut.indepmod.classmodel.api.model.IClassModelModel;
import cz.cvut.indepmod.classmodel.diagramdata.DiagramDataModel;
import java.util.Collection;

/**
 * Date: 7.11.2010
 * Time: 11:59:45
 * @author Lucky
 */
public class ClassModelModel implements IClassModelModel {

    private ClassModelGraph graph;
    private DiagramDataModel dataModel;

    public ClassModelModel(ClassModelGraph graph, DiagramDataModel dataModel) {
        this.graph = graph;
        this.dataModel = dataModel;
    }

    @Override
    public Collection<IClass> getClasses() {
        return this.graph.getAllClasses();
    }

    @Override
    public DiagramType getDiagramType() {
        return this.dataModel.getDiagramType();
    }
}