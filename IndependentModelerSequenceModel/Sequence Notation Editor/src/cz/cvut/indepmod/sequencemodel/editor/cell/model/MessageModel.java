/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import java.util.HashSet;
import java.util.Set;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class MessageModel extends AbstractModel {

    private static int counter = 0;
    private DefaultGraphCell cell;
    private TypeModel type;
    private String name;
    private Set<AttributeModel> attributeModels;

    public MessageModel() {
        this("Message" + ++counter);
    }

    public MessageModel(String name) {
        this.name = name;
        this.type = new TypeModel("void");
        this.attributeModels = new HashSet<AttributeModel>();
    }

    /**
     * Creates new ClassModel with no attributeModels and no methodModels
     *
     * @param name name of new class
     */
    public MessageModel(String name, TypeModel type, Set<AttributeModel> attributeModels) {
        this.name = name;
        this.type = type;

        if (attributeModels != null) {
            this.attributeModels = new HashSet<AttributeModel>(attributeModels);
        } else {
            this.attributeModels = new HashSet<AttributeModel>();
        }
        this.cell = null;
    }

    public MessageModel(MessageModel model) {
        this.name = model.toString();
        this.cell = model.cell;
    }

    public void setCell(DefaultGraphCell cell) {
        this.cell = cell; //TODO - shouldn't this throw an exception when the cell is already sat?
    }

    public TypeModel getType() {
        return this.type;
    }

    public void setType(TypeModel type){
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Set<AttributeModel> getAttributeModels() {
        return new HashSet<AttributeModel>(this.attributeModels);
    }

    public void setAttributeModels(Set<AttributeModel> attributeModels) {
        this.attributeModels = attributeModels;
    }

    @Override
    public String toString() {
        StringBuilder bfr = new StringBuilder(30);
        bfr.append(this.type.toString());
        bfr.append(" ");
        bfr.append(this.name);
        bfr.append("(");

        boolean comma = false;
        for (AttributeModel attr : this.getAttributeModels()) {
            if (comma) {
                bfr.append(", ");
            } else {
                comma = true;
            }

            bfr.append(attr.toString());
        }

        bfr.append(")");

        return bfr.toString();
    }
}
