package cz.cvut.fel.indepmod.notation.processhierarchy.model.api;

import java.util.Collection;

/**
 *
 * @author Petr Vales
 */
public interface IProcess extends IProcessHierarchyType {

    public Collection<IProcess> getParentProcess();
    public Collection<IProcess> getChildrenProcess();
    public Collection<IData> getInData();
    public Collection<IData> getOutData();
    public Collection<IRole> getRoles();
    public Integer getId();
    public String getProcessName();
    public String getText();

}
