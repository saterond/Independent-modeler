package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;

public class ProcessRenderer extends VertexRenderer {


    @Override
    public void paint(Graphics g) {
//        int b = borderWidth;
        Graphics2D g2 = (Graphics2D) g;
//        Dimension d = getSize();
//        boolean tmp = selected;
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
            g2.setPaint(new GradientPaint(0, 0, getBackground(), getWidth(),
                    getHeight(), gradientColor, true));
        }
        g2.setStroke(new BasicStroke(2));
        this.fillProcess(g2);
    }

    private void drawBorder(Graphics g, Graphics2D g2) {
        g.setColor(bordercolor);
        g2.setStroke(new BasicStroke(this.borderWidth));
        this.drawProcess(g2);
    }

    private void drawSelected(Graphics g, Graphics2D g2) {
        g2.setStroke(GraphConstants.SELECTION_STROKE);
        g.setColor(highlightColor);
        this.drawProcess(g2);
    }

    private void fillProcess(Graphics2D g2) {
        int[] x = this.getXCoordinates();
        int[] y = this.getYCoordinates();
        g2.fillPolygon(x, y, x.length);
    }

    private void drawProcess(Graphics2D g2) {
        int[] x = this.getXCoordinates();
        int[] y = this.getYCoordinates();
        g2.drawPolygon(x, y, x.length);
    }

    private int[] getXCoordinates() {
        int[] x = {
            0 + this.borderWidth,
            (this.getSize().width - this.borderWidth) * 5 / 6,
            this.getSize().width - this.borderWidth,
            (this.getSize().width - this.borderWidth) * 5 / 6,
            0 + this.borderWidth,
            0 + this.borderWidth
        };
        return x;
    }

    private int[] getYCoordinates() {
        int[] y = {
            0 + this.borderWidth,
            0 + this.borderWidth,
            (this.getSize().height - this.borderWidth) / 2,
            this.getSize().height - this.borderWidth,
            this.getSize().height - this.borderWidth,
            0 + this.borderWidth
        };
        return y;
    }
}
