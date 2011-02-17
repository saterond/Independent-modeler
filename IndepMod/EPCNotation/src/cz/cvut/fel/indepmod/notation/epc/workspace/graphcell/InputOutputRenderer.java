package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;

/**
 *
 * @author Petr Vales
 */
public class InputOutputRenderer extends VertexRenderer {


    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        boolean tmp = selected;
        if (super.isOpaque()) {
            this.drawOpaque(g, g2);
        }
        try {
            setBorder(null);
            setOpaque(false);
            selected = false;
            super.paint(g);
        } finally {
            selected = tmp;
        }
        if (bordercolor != null) {
            this.drawBorder(g, g2);
        }
        if (selected) {
            this.drawSelected(g, g2);
        }
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
        g.fillRect(
                0 + this.borderWidth,
                0 + this.borderWidth,
                this.getSize().width - 2*this.borderWidth,
                this.getSize().height - 2*this.borderWidth
                );
    }

    private void drawShape(Graphics g) {
        g.drawRect(
                0 + this.borderWidth,
                0 + this.borderWidth,
                this.getSize().width - 2*this.borderWidth,
                this.getSize().height - 2*this.borderWidth
                );
    }

}
