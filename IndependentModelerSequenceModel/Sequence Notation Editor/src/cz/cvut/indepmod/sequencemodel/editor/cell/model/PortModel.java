/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class PortModel extends AbstractModel{

    private static int counter = 0;
    private MessageModel message;
    private String name;

    public PortModel(){
        this(null,"port");
    }

    public PortModel(MessageModel message) {
        this(message,"port");
    }

    public PortModel(String name){
        this(null,name);
    }

    public PortModel(MessageModel message,String name) {
        this.message = message;
        this.name = name + ++counter;
    }

    /**
     * Returns Type instantion represeting the type of this attribute
     *
     * @return Type instantion
     */
    //@Override
    public MessageModel getMessage() {
        return message;
    }

    /**
     * Returns the name of this attribute
     *
     * @return the name of this attribute
     */
    //@Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}