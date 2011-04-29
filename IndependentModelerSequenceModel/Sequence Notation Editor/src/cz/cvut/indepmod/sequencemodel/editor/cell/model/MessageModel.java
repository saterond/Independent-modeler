/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.IMessage;
import cz.cvut.indepmod.sequencemodel.api.model.ISequenceObject;
import java.util.HashSet;
import java.util.Set;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class MessageModel extends AbstractMessageModel implements IMessage{

    private static int counter = 0;
    private TypeModel type;
    private String name;
    private Set<AttributeModel> attributeModels;
    private ReturnMessageModel returnModel;
    private SequenceObjectModel sourceSeqObject;
    private SequenceObjectModel targetSeqObject;

    public MessageModel() {
        this("Message" + ++counter);
    }

    public MessageModel(String name) {
        this.name = name;
        this.type = new TypeModel("void");
        this.attributeModels = new HashSet<AttributeModel>();
    }

    public MessageModel(String name,DefaultGraphCell cell) {
        this.name = name;
        this.type = new TypeModel("void");
        this.attributeModels = new HashSet<AttributeModel>();
        super.cell = cell;
    }

    public void setSourceSeqObject(SequenceObjectModel sourceSeqObject) {
        this.sourceSeqObject = sourceSeqObject;
    }

    public void setTargetSeqObject(SequenceObjectModel targetSeqObject) {
        this.targetSeqObject = targetSeqObject;
    }

    /**
     * Creates new MessageModel with no attributeModels and no methodModels
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
        super.cell = null;
    }

    public MessageModel(MessageModel model) {
        this.name = model.toString();
        this.associateMessage = model.associateMessage;
        this.attributeModels = model.getAttributeModels();
        super.cell = model.cell;
    }

    @Override
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


    @Override
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

    @Override
    public ISequenceObject getSourceSequenceObject() {
        return sourceSeqObject;
    }

    @Override
    public ISequenceObject getTargetSequenceObject() {
        return targetSeqObject;
    }

}
