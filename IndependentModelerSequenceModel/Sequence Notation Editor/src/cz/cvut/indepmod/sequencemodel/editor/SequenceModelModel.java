/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.model.IFragment;
import cz.cvut.indepmod.sequencemodel.api.model.ISequenceModelModel;
import cz.cvut.indepmod.sequencemodel.api.model.ISequenceObject;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.FragmentModel;
import cz.cvut.indepmod.sequencemodel.editor.cell.model.SequenceObjectModel;
import java.util.Collection;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
/**
 * Date: 7.11.2010
 * Time: 11:59:45
 * @author Lucky
 */
public class SequenceModelModel implements ISequenceModelModel {

    private SequenceModelGraph graph;

    public SequenceModelModel(SequenceModelGraph graph) {
        this.graph = graph;
    }

    @Override
    public Collection<SequenceObjectModel> getSequenceObjects() {
        return this.graph.getAllSequenceObjects();
    }

    @Override
    public Collection<FragmentModel> getFragments() {
        return this.graph.getAllFragmentModels();
    }

}