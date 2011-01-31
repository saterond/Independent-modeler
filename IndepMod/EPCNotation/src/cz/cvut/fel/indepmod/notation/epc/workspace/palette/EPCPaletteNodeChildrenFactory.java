package cz.cvut.fel.indepmod.notation.epc.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNode;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.PaletteNodeChildrenFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.EventCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.FunctionCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.InputCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OrganizationUnitCellFactory;
import cz.cvut.fel.indepmod.notation.epc.workspace.graphcell.OutputCellFactory;
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
    protected boolean createKeys(final List<PaletteNode> toPopulate) {
        if (super.getKey().equals("EPC Notation")) {
            toPopulate.add(new PaletteNode(
                    EPCPaletteNodeModel.Event.name(),
                    EPCPaletteNodeModel.Event,
                    new EventCellFactory()));
            toPopulate.add(new PaletteNode(
                    EPCPaletteNodeModel.Input.name(),
                    EPCPaletteNodeModel.Input,
                    new InputCellFactory()));
            toPopulate.add(new PaletteNode(
                    EPCPaletteNodeModel.Function.name(),
                    EPCPaletteNodeModel.Function,
                    new FunctionCellFactory()));
            toPopulate.add(new PaletteNode(
                    "Organization Unit",
                    EPCPaletteNodeModel.Oraganization_Unit,
                    new OrganizationUnitCellFactory()));
            toPopulate.add(new PaletteNode(
                    EPCPaletteNodeModel.Output.name(),
                    EPCPaletteNodeModel.Output,
                    new OutputCellFactory()));
            toPopulate.add(new PaletteNode(
                    "Supporting System",
                    EPCPaletteNodeModel.Supporting_System,
                    new SupportingSystemCellFactory()));
            toPopulate.add(new PaletteNode(
                    EPCPaletteNodeModel.Dependency.name(),
                    EPCPaletteNodeModel.Dependency,
                    null));
        }
        return true;
    }
}
