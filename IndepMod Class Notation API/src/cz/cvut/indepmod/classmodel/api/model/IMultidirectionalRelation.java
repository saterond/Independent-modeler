package cz.cvut.indepmod.classmodel.api.model;

/**
 *
 * @author Lucky
 */
public interface IMultidirectionalRelation extends IRelation {

    /**
     * Returns whether is this relation bidirectional or unidirectional
     * @return
     */
    public RelationDirection getDirection();

    /**
     * sets whether is this relation bidirectional or unidirectional
     * @param direction
     */
    public void setRelationDirection(RelationDirection direction);

}
