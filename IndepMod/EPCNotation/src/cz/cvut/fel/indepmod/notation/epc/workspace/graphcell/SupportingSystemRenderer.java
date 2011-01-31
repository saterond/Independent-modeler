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
public class SupportingSystemRenderer extends VertexRenderer {

    @Override
    public void paint(Graphics g) {
        int b = borderWidth;
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();
        boolean tmp = selected;
        if (super.isOpaque()) {
            g.setColor(super.getBackground());
            if (gradientColor != null && !preview) {
                setOpaque(false);
                g2.setPaint(new GradientPaint(0, 0, getBackground(), getWidth(), getHeight(), gradientColor, true));
            }
            g.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            this.drawSupportingSystem(g, d, b);
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
            g.setColor(bordercolor);
            g2.setStroke(new BasicStroke(b));
            this.drawRectangle(g, d, b);
        }
        if (selected) {
            g2.setStroke(GraphConstants.SELECTION_STROKE);
            g.setColor(highlightColor);
            this.drawRectangle(g, d, b);
        }
    }

    private void drawRectangle(Graphics g, Dimension d, int b) {
        g.drawRect(0, 0, d.width, d.height);
    }

    private void drawSupportingSystem(Graphics g, Dimension d, int b) {
        this.drawRectangle(g, d, b);
        g.drawLine(d.width / 10, 0, d.width / 10, d.height);
        g.drawLine(d.width - d.width / 10, 0, d.width - d.width / 10, d.height);
        g.drawLine(d.width / 20, 0, d.width / 20, d.height);
        g.drawLine(d.width - d.width / 20, 0, d.width - d.width / 20, d.height);
    }
}
