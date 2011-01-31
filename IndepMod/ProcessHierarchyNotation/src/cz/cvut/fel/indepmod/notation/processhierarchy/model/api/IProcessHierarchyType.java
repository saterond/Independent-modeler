package cz.cvut.fel.indepmod.notation.processhierarchy.model.api;

import cz.cvut.fel.indepmod.independentmodeler.model.api.INote;
import cz.cvut.fel.indepmod.independentmodeler.model.api.IType;
import java.util.Collection;

/**
 *
 * @author Petr Vales
 */
public interface IProcessHierarchyType extends IType {
        public Collection<INote> getNotes();
}
