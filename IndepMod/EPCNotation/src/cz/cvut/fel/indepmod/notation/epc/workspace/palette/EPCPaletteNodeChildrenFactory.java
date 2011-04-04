package cz.cvut.fel.indepmod.notation.epc.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.ArrowEdgeFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphedges.LineEdgeFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.IPaletteNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteCellNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteEdgeNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNodeChildrenFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.EventCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.EventCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.FunctionCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.FunctionCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.InputCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.InputCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OrganizationUnitCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OrganizationUnitCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OutputCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OutputCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.SupportingSystemCell;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.SupportingSystemCellFactory;
import java.util.List;

/**
 *
 * @author Petr Vales
 */
public class EPCPaletteNodeChildrenFactory
        extends PaletteNodeChildrenFactory {

    public EPCPaletteNodeChildrenFactory(String myNodeKey) {
        super(myNodeKey);
    }

    @Override
    protected boolean createKeys(final List<IPaletteNode> toPopulate) {
        if (super.getKey().equals("EPC Notation")) {
            toPopulate.add(new PaletteCellNode<EventCell>(
                    EPCPaletteNodeModel.Event.name(),
                    new EventCellFactory()));
            toPopulate.add(new PaletteCellNode<InputCell>(
                    EPCPaletteNodeModel.Input.name(),
                    new InputCellFactory()));
            toPopulate.add(new PaletteCellNode<FunctionCell>(
                    EPCPaletteNodeModel.Function.name(),
                    new FunctionCellFactory()));
            toPopulate.add(new PaletteCellNode<OrganizationUnitCell>(
                    "Organization Unit",
                    new OrganizationUnitCellFactory()));
            toPopulate.add(new PaletteCellNode<OutputCell>(
                    EPCPaletteNodeModel.Output.name(),
                    new OutputCellFactory()));
            toPopulate.add(new PaletteCellNode<SupportingSystemCell>(
                    "Supporting System",
                    new SupportingSystemCellFactory()));
            toPopulate.add(new PaletteEdgeNode(
                    "Arrow edge",
                    new ArrowEdgeFactory()));
            toPopulate.add(new PaletteEdgeNode(
                    "Line edge",
                    new LineEdgeFactory()));
        }
        return true;
    }
}
