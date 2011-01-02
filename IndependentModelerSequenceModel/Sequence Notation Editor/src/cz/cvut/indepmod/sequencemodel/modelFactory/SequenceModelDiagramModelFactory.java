/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.modelFactory;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceModelDiagramModelFactory {

    private static SequenceModelDiagramModelFactory singleton = new SequenceModelDiagramModelFactory();

    public static SequenceModelDiagramModelFactory getInstance() {
        return singleton;
    }


    public SequenceModelDiagramModel createEmptyDiagramModel() {
        return new SequenceModelDiagramModel();
    }
}

