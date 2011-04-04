package cz.cvut.fel.indepmod.notation.processhierarchy.workspace.graphcells;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.SampleUtils;
import java.awt.BasicStroke;
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

public class ProcessRenderer extends VertexRenderer {

    private ProcessCell cell;

    public ProcessRenderer() {
        super();
    }

    @Override
    public synchronized void paint(Graphics g) {
        cell = (ProcessCell) this.view.getCell();
        Graphics2D g2 = (Graphics2D) g;
        if (super.isOpaque()) {
            this.drawOpaque(g, g2);
        }
        if (this.cell.getId() != null && bordercolor != null) {
            g.setColor(bordercolor);
            g2.drawString("Id:   " + this.cell.getId(), 15, 15);
        }
        if (this.cell.getName() != null && bordercolor != null) {
            g.setColor(bordercolor);
            g2.drawString("Name: " + this.cell.getName(), 15, 30);
        }
        if (this.cell.getInfo() != null && bordercolor != null) {
            g.setColor(bordercolor);
            g2.drawString("Info: ", 15, 45);
            this.printInfoText(g, g2, this.cell.getInfo());
        }
        if (bordercolor != null) {
            this.drawBorder(g, g2);
        }
        if (selected) {
            this.drawSelected(g, g2);
        }
    }

    private void printInfoText(Graphics g, Graphics2D g2, String infoText) {
        g.setColor(this.bordercolor);
        AttributedString attributedString = new AttributedString(infoText);
        AttributedCharacterIterator iterator = attributedString.getIterator();

        FontRenderContext frc = SampleUtils.getDefaultFontRenderContext();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(iterator, frc);

        float formatWidth = (float) getSize().width * 1 / 2;

        float drawPosY = 33;

        lineMeasurer.setPosition(iterator.getBeginIndex());
        while (lineMeasurer.getPosition() < iterator.getEndIndex()) {

            TextLayout layout =
                    lineMeasurer.nextLayout(formatWidth);
            drawPosY += layout.getAscent();
            float drawPosX;
            if (layout.isLeftToRight()) {
                drawPosX = 45;
            } else {
                drawPosX = formatWidth - layout.getAdvance();
            }

            layout.draw(g2, drawPosX, drawPosY);
            drawPosY += layout.getDescent() + layout.getLeading();
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
