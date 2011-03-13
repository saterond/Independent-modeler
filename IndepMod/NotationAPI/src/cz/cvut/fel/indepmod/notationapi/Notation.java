package cz.cvut.fel.indepmod.notationapi;

import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import cz.cvut.fel.indepmod.notationidentifikatorapi.NotationIdentifikator;

/**
 *
 * @author Petr Vales
 */
public interface Notation extends NotationIdentifikator {
    public CategoryChildrenFactory getCathegoryChildrenFactory();
    public IndependentModelerTransferHandler getTransferHandler();
}
