/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.component;

import cz.cvut.indepmod.sequencemodel.editor.cell.model.MessageModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public class MessageComponent extends JComponent {

    private static final Font CLASS_NAME_FONT = new Font("Serif", Font.CENTER_BASELINE, 12);

    private final MessageModel model;

    public MessageComponent(MessageModel model) {
        this.model = model;
    }

    @Override
    public void paint(Graphics g) {
        Dimension d = this.getSize();
        int namePartHeight = this.getNamePartHeight();
        //int attributePartHeight = this.getAttributePartHeight();
        //int methodPartHeight = this.getMethodPartHeight();

        this.paintClassPart(g.create(0, 0, d.width, namePartHeight));
        //this.paintAttributePart(g.create(0, namePartHeight, d.width, attributePartHeight));
        //this.paintMethodPart(g.create(0, namePartHeight + attributePartHeight, d.width, methodPartHeight));
    }

    @Override
    public Dimension getPreferredSize() {
        int width = this.model.toString().length()*7;
        int height = this.getNamePartHeight();// + this.getMethodPartHeight() + this.getAttributePartHeight();
        return new Dimension(width, height);
    }

    private void paintClassPart(Graphics g) {
        int width = getSize().width;
        int height = this.getNamePartHeight();
        String MessageName = this.model.toString();
        g.setColor(Color.BLACK);
        //g.drawRect(0, 0, width - 1, height - 1);

        g.setFont(CLASS_NAME_FONT);
        Rectangle2D rect = g.getFontMetrics().getStringBounds(MessageName, g);
        g.drawString(MessageName, (int) ((width - rect.getWidth()) / 2), height - 5);
    }

    /*
    private void paintAttributePart(Graphics g) {
        int width = getSize().width;
        int height = this.getAttributePartHeight();
        Set<AttributeModel> attrs = this.model.getAttributeModels();

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        g.setFont(CLASS_NAME_FONT);
        //Rectangle2D rect = g.getFontMetrics().getStringBounds(className, g);

        int stage = 1;
        for (AttributeModel attr : attrs ) {
            g.drawString(attr.toString(), 5, stage * 20 - 5);
            stage++;
        }
    }

    private void paintMethodPart(Graphics g) {
        int width = getSize().width;
        int height = this.getMethodPartHeight();
        Set<MethodModel> attrs = this.model.getMethodModels();

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        g.setFont(CLASS_NAME_FONT);

        int stage = 1;
        for (MethodModel method : attrs) {
            g.drawString(method.toString(), 5, stage * 20 - 5);
            stage++;
        }
    }
    */

    private int getNamePartHeight() {
        return 20;
    }

    /*
    private int getMethodPartHeight() {
        return 3 + this.model.getMethodModels().size() * 20;
    }

    private int getAttributePartHeight() {
        return 3 + this.model.getAttributeModels().size() * 20;
    }
     *
     */
}