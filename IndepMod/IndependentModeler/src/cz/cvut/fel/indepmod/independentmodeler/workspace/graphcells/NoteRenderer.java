package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;

public class NoteRenderer extends VertexRenderer {

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (super.isOpaque()) {
            this.drawOpaque(g, g2);
        }
        if (this.getText() != null) {
            this.printLableText(g, g2);
        }
        if (bordercolor != null) {
            this.drawBorder(g, g2);
        }
        if (selected) {
            this.drawSelected(g, g2);
        }
    }

    private void printLableText(Graphics g, Graphics2D g2) {
        g.setColor(this.bordercolor);
        AttributedString attributedString = new AttributedString(this.getText());
        AttributedCharacterIterator iterator = attributedString.getIterator();

        FontRenderContext frc = SampleUtils.getDefaultFontRenderContext();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(iterator, frc);

        Dimension size = getSize();
        float formatWidth = (float) size.width - 10;

        float drawPosY = 10;

        lineMeasurer.setPosition(iterator.getBeginIndex());
        while (lineMeasurer.getPosition() < iterator.getEndIndex()) {

            TextLayout layout =
                    lineMeasurer.nextLayout(formatWidth);
            drawPosY += layout.getAscent();
            float drawPosX;
            if (layout.isLeftToRight()) {
                drawPosX = 10;
            } else {
                drawPosX = formatWidth - layout.getAdvance();
            }

            layout.draw(g2, drawPosX, drawPosY);
            drawPosY += layout.getDescent() + layout.getLeading();
        }

//        AttributedString attributedString = new AttributedString(this.getText());
//        g.drawString(attributedString.getIterator(), this.getSize().width / 2, this.
//                getSize().height / 2);
//        boolean tmp = selected;
//        try {
//            setBorder(null);
//            setOpaque(false);
//            selected = false;
//            super.paint(g);
//        } finally {
//            selected = tmp;
//        }
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

    private void fillShape(Graphics2D g2) {
        int[] x = this.getXCoordinates();
        int[] y = this.getYCoordinates();
        g2.fillPolygon(x, y, x.length);
    }

    private void drawShape(Graphics2D g2) {
        int[] x = this.getXCoordinates();
        int[] y = this.getYCoordinates();
        g2.drawPolygon(x, y, x.length);
        g2.drawLine(this.getSize().width - this.borderWidth - 10, 0
                + this.borderWidth,
                this.getSize().width - this.borderWidth - 10, 10
                + this.borderWidth);
        g2.drawLine(this.getSize().width - this.borderWidth - 10, 10
                + this.borderWidth,
                this.getSize().width - this.borderWidth, 10 + this.borderWidth);
    }

    private int[] getXCoordinates() {
        int[] x = {0 + this.borderWidth,
            this.getSize().width - 10 - this.borderWidth,
            this.getSize().width - this.borderWidth,
            this.getSize().width - this.borderWidth,
            0 + this.borderWidth,
            0 + this.borderWidth};
        return x;
    }

    private int[] getYCoordinates() {
        int[] y = {0 + this.borderWidth,
            0 + this.borderWidth,
            10 + this.borderWidth,
            this.getSize().height - this.borderWidth,
            this.getSize().height - this.borderWidth,
            0 + this.borderWidth};
        return y;
    }
}
