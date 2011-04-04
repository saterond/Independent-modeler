package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;

/**
 *
 * @author Petr Vales
 */
public class FunctionRenderer extends VertexRenderer {

    private FunctionCell cell;

    @Override
    public void paint(Graphics g) {
        this.cell = (FunctionCell) this.view.getCell();
        Graphics2D g2 = (Graphics2D) g;
        boolean tmp = selected;
        if (super.isOpaque()) {
            this.drawOpaque(g, g2);
        }
//        try {
//            setBorder(null);
//            setOpaque(false);
//            selected = false;
//            super.paint(g);
//        } finally {
//            selected = tmp;
//        }
        if (cell.getType() == FunctionType.AND) {
            this.drawAnd(g, g2);
        } else if (cell.getType() == FunctionType.OR) {
            this.drawOr(g, g2);
        } else if (cell.getType() == FunctionType.XOR) {
            this.drawXor(g, g2);
        }
        if (bordercolor != null) {
            this.drawBorder(g, g2);
        }
        if (selected) {
            this.drawSelected(g, g2);
        }
    }

    private void drawAnd(Graphics g, Graphics2D g2) {
        g.setColor(this.bordercolor);
        g2.setStroke(new BasicStroke(this.borderWidth));
        g.drawLine(this.getSize().width / 2, this.getSize().height / 10, this.getSize().width / 4, this.getSize().height * 9 / 10);
        g.drawLine(this.getSize().width / 2, this.getSize().height / 10, this.getSize().width * 3 / 4, this.getSize().height * 9 / 10);
    }

    private void drawOr(Graphics g, Graphics2D g2) {
        g.setColor(this.bordercolor);
        g2.setStroke(new BasicStroke(this.borderWidth));
        g.drawLine(this.getSize().width / 2, this.getSize().height * 9 / 10, this.getSize().width / 4, this.getSize().height / 10);
        g.drawLine(this.getSize().width / 2, this.getSize().height * 9 / 10, this.getSize().width * 3 / 4, this.getSize().height / 10);
    }

    private void drawXor(Graphics g, Graphics2D g2) {
        g.setColor(this.bordercolor);
        g2.setStroke(new BasicStroke(this.borderWidth));
        g.drawLine(this.getSize().width * 3 / 4, this.getSize().height * 9 / 10, this.getSize().width / 4, this.getSize().height / 10);
        g.drawLine(this.getSize().width / 4, this.getSize().height * 9 / 10, this.getSize().width * 3 / 4, this.getSize().height / 10);
    }

    private void drawOpaque(Graphics g, Graphics2D g2) {
        g.setColor(super.getBackground());
        if (gradientColor != null && !preview) {
            setOpaque(false);
            g2.setPaint(new GradientPaint(0, 0, super.getBackground(),
                    getWidth(), getHeight(), gradientColor, true));
        }
        g2.setStroke(new BasicStroke(2));
        this.fillShape(g2);
    }

    private void drawBorder(Graphics g, Graphics2D g2) {
        g.setColor(bordercolor);
        g2.setStroke(new BasicStroke(this.borderWidth));
        this.drawShape(g2);
    }

    private void drawSelected(Graphics g, Graphics2D g2) {
        g2.setStroke(GraphConstants.SELECTION_STROKE);
        g.setColor(highlightColor);
        this.drawShape(g2);
    }

    private void fillShape(Graphics g) {
        g.fillRoundRect(
                0 + this.borderWidth,
                0 + this.borderWidth,
                this.getSize().width - 2 * this.borderWidth,
                this.getSize().height - 2 * this.borderWidth,
                (this.getSize().width - this.borderWidth) / 3,
                (this.getSize().height - this.borderWidth) / 3);
    }

    private void drawShape(Graphics g) {
        g.drawRoundRect(
                0 + this.borderWidth,
                0 + this.borderWidth,
                this.getSize().width - 2 * this.borderWidth,
                this.getSize().height - 2 * this.borderWidth,
                (this.getSize().width - this.borderWidth) / 3,
                (this.getSize().height - this.borderWidth) / 3);
    }
}
