package cz.cvut.indepmod.classmodel.workspace.cell;

import cz.cvut.indepmod.classmodel.api.ToolChooserModel;
import cz.cvut.indepmod.classmodel.api.model.Cardinality;
import cz.cvut.indepmod.classmodel.api.model.RelationType;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.ClassModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.GeneralizationModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.RelationModel;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.logging.Logger;
import org.jgraph.graph.DefaultEdge;

/**
 * Created by IntelliJ IDEA.
 * User: Lucky
 * Date: 30.9.2010
 * Time: 13:22:02
 * <p/>
 * This factory creates new cell types according to the chosen tool.
 */
public class ClassModelCellFactory {

    private static final Logger LOG = Logger.getLogger(ClassModelCellFactory.class.getName());

    public static DefaultGraphCell createCell(Point2D point, ToolChooserModel.Tool selectedTool) {
        DefaultGraphCell cell = null;

        switch (selectedTool) {
            case TOOL_ADD_CLASS:
                cell = new ClassModelClassCell();
                cell.setUserObject(new ClassModel());
                break;
            default:
                LOG.severe("Unknown selected tool");
        }

        GraphConstants.setBounds(
                cell.getAttributes(),
                new Rectangle.Double(
                point.getX(),
                point.getY(),
                0,
                0));

        GraphConstants.setResize(cell.getAttributes(), true);

        cell.add(new DefaultPort());

        return cell;
    }

    public static DefaultEdge createEdge(ToolChooserModel.Tool selectedTool) {
        DefaultEdge edge = null;

        switch (selectedTool) {
            case TOOL_ADD_RELATION:
                edge = new ClassModelRelation();
                edge.setUserObject(new RelationModel(RelationType.RELATION));
                Cardinality[] labels = {Cardinality.ONE, Cardinality.ONE};
                Point2D[] labPos = {new Point2D.Double(GraphConstants.PERMILLE / 8, 20), new Point2D.Double(GraphConstants.PERMILLE * 7/8, 20)};
                GraphConstants.setExtraLabelPositions(edge.getAttributes(), labPos);
                GraphConstants.setExtraLabels(edge.getAttributes(), labels);
                break;
            case TOOL_ADD_GENERALIZATION:
                edge = new ClassModelRelation();
                edge.setUserObject(new GeneralizationModel());
                GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
                GraphConstants.setEndFill(edge.getAttributes(), false);
                break;
            default:
                LOG.severe("Unknown selected tool");
        }

        //GraphConstants.setEndFill(edge.getAttributes(), true);
        GraphConstants.setLineStyle(edge.getAttributes(), GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setLabelAlongEdge(edge.getAttributes(), false);
        GraphConstants.setEditable(edge.getAttributes(), false);
        GraphConstants.setMoveable(edge.getAttributes(), true);
        GraphConstants.setDisconnectable(edge.getAttributes(), false);

        return edge;
    }
}
