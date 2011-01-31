package cz.cvut.fel.indepmod.notation.processhierarchy.model.api;

/**
 *
 * @author Petr Vales
 */
public interface IData extends IProcessHierarchyType {

    public IProcess getProcess();
    public String getDataName();

}
