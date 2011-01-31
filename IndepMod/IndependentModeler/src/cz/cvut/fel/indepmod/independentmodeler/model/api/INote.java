package cz.cvut.fel.indepmod.independentmodeler.model.api;

/**
 *
 * @author Petr Vales
 */
public interface INote extends IType {

    public String getText();
    public IType getReferencedIType();

}
