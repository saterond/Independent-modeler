/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import cz.cvut.indepmod.sequencemodel.api.model.IMessage;
import cz.cvut.indepmod.sequencemodel.api.model.ISequenceObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class SequenceObjectModel extends TypeModel implements ISequenceObject{

    private static int counter = 0;
    private DefaultGraphCell cell;
    private List<MessageModel> sentMessages;
    private List<MessageModel> incomeMessages;
    private List<MessageModel> allMessages;
    private String objectName;

    public SequenceObjectModel() {
        this("","Class" + ++counter);
    }

    /**
     * Creates new ClassModel with no attributeModels and no methodModels
     *
     * @param className name of new class
     */
    public SequenceObjectModel(String objectName, String className) {
        super(className);
        this.cell = null;
        this.objectName = objectName;
        initValue();
    }

    public SequenceObjectModel(SequenceObjectModel model) {
        super(model.toString());
        this.cell = model.cell;
        initValue();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /*
     * Init some collection of model
     */
    private void initValue() {
        sentMessages = new ArrayList<MessageModel>();
        incomeMessages = new ArrayList<MessageModel>();
        allMessages = new ArrayList<MessageModel>();
    }

    public void setCell(DefaultGraphCell cell) {
        this.cell = cell; //TODO - shouldn't this throw an exception when the cell is already sat?
    }

    public void addSentMessage(MessageModel message){
        allMessages.add(message);
        sentMessages.add(message);
    }

    public void addIncomeMessage(MessageModel message){
        allMessages.add(message);
        incomeMessages.add(message);
    }

    public void removeIncomeMessage(MessageModel message){
        allMessages.remove(message);
        incomeMessages.remove(message);
    }

    public void removeSentMessage(MessageModel message){
        allMessages.remove(message);
        sentMessages.remove(message);
    }

    @Override
    public String toString() {
        return this.getObjectName() + ":" + this.getTypeName();
    }

    @Override
    public Collection<MessageModel> getSentMessages() {
        Collections.sort(sentMessages);
        return sentMessages;
    }

    @Override
    public Collection<MessageModel> getIncomeMessages() {
        Collections.sort(incomeMessages);
        return incomeMessages;
    }

    @Override
    public Collection<MessageModel> getAllMessages() {
        Collections.sort(allMessages);
        return allMessages;
    }

    @Override
    public IMessage getNextMessage(IMessage message) {
        Collections.sort(allMessages);
        int index = allMessages.indexOf(message);

        return allMessages.get(index + 1);
    }

    @Override
    public IMessage getPreiviousMessage(IMessage message) {
        Collections.sort(allMessages);
        int index = allMessages.indexOf(message);

        return allMessages.get(index - 1);
    }

}
