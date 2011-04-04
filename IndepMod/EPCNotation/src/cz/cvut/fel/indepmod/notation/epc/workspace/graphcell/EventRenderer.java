package cz.cvut.fel.indepmod.notation.epc.workspace.graphcell;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.SampleUtils;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;

/**
 *
 * @author Petr Vales
 */
public class EventRenderer extends VertexRenderer {

    private EventCell cell;

    @Override
    public void paint(Graphics g) {
        this.cell = (EventCell) this.view.getCell();
        Graphics2D g2 = (Graphics2D) g;
        if (super.isOpaque()) {
            this.drawOpaque(g, g2);
        }
        if(this.cell.getTitle() != null) {
            this.printTitleText(g, g2, this.cell.getTitle());
        }
        if (bordercolor != null) {
            this.drawBorder(g, g2);
        }
        if (selected) {
            this.drawSelected(g, g2);
        }
    }

    private void printTitleText(Graphics g, Graphics2D g2, String infoText) {
        g.setColor(this.bordercolor);
        AttributedString attributedString = new AttributedString(infoText);
        AttributedCharacterIterator iterator = attributedString.getIterator();

        FontRenderContext frc = SampleUtils.getDefaultFontRenderContext();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(iterator, frc);

        float formatWidth = this.getSize().width * 5 / 7;

        float drawPosY = this.getSize().height / 10;

        lineMeasurer.setPosition(iterator.getBeginIndex());
        while (lineMeasurer.getPosition() < iterator.getEndIndex()) {

            TextLayout layout =
                    lineMeasurer.nextLayout(formatWidth);
            drawPosY += layout.getAscent();
            float drawPosX;
            if (layout.isLeftToRight()) {
                drawPosX = this.getSize().width / 7;
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
        g2.fillPolygon(this.getPolygon());
    }

    private void drawShape(Graphics2D g2) {
        g2.drawPolygon(this.getPolygon());
    }

    private Polygon getPolygon() {
        int[] x = this.getXCoordinates();
        int[] y = this.getYCoordinates();
        return new Polygon(x, y, x.length);
    }

    private int[] getXCoordinates() {
        int[] x = {
            (this.getSize().width + this.borderWidth) / 7,
            this.getSize().width - this.getSize().width / 7 - this.borderWidth,
            this.getSize().width - this.borderWidth,
            this.getSize().width - this.getSize().width / 7 - this.borderWidth,
            this.getSize().width / 7 + this.borderWidth,
            0 + this.borderWidth
        };
        return x;
    }

    private int[] getYCoordinates() {
        int[] y = {
            0 + this.borderWidth,
            0 + this.borderWidth,
            this.getSize().height / 2,
            this.getSize().height - this.borderWidth,
            this.getSize().height - this.borderWidth,
            this.getSize().height / 2
        };
        return y;
    }
}
