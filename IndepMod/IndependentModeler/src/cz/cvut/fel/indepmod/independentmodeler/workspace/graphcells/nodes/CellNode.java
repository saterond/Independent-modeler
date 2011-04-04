package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.nodes;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import sun.beans.editors.ColorEditor;

/**
 *
 * @author Petr Vales
 */
abstract public class CellNode extends AbstractNode implements
        PropertyChangeListener {

    public CellNode(Children children) {
        super(children);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = this.fillSetWithCommonProperties(Sheet.
                createPropertiesSet());
        sheet.put(set);
        return sheet;
    }

    protected Sheet.Set fillSetWithCommonProperties(Sheet.Set set) {
        set.setDisplayName("Common");
        set.setName("Common");
        try {
            set.put(this.createHeightProperty());
            set.put(this.createWidthProperty());
            set.put(this.createXProperty());
            set.put(this.createYProperty());
            set.put(this.createBackgroundColorProperty());
            set.put(this.createGradientColorProperty());
            set.put(this.createLineWidthProperty());
            set.put(this.createLineColorProperty());
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    private Property createHeightProperty() throws NoSuchMethodException {
        Property heightProp = new PropertySupport.Reflection(this.getCell(),
                Integer.class, "height");
        heightProp.setName("Height");
        return heightProp;
    }

    private Property createWidthProperty() throws NoSuchMethodException {
        Property widthProp = new PropertySupport.Reflection(this.getCell(),
                Integer.class, "width");
        widthProp.setName("Width");
        return widthProp;
    }

    private Property createXProperty() throws NoSuchMethodException {
        Property xProp = new PropertySupport.Reflection(this.getCell(),
                Integer.class, "x");
        xProp.setName("X");
        return xProp;
    }

    private Property createYProperty() throws NoSuchMethodException {
        Property yProp = new PropertySupport.Reflection(this.getCell(),
                Integer.class, "y");
        yProp.setName("Y");
        return yProp;
    }

    private Property createBackgroundColorProperty() throws
            NoSuchMethodException {
        PropertySupport.Reflection backgroundColorProp =
                new PropertySupport.Reflection(this.getCell(), Color.class,
                "backgroundColor");
        backgroundColorProp.setName("Background color");
        backgroundColorProp.setPropertyEditorClass(ColorEditor.class);
        return backgroundColorProp;
    }

    private Property createGradientColorProperty() throws NoSuchMethodException {
        PropertySupport.Reflection gradientColorProp =
                new PropertySupport.Reflection(this.getCell(), Color.class,
                "gradientColor");
        gradientColorProp.setName("Gradient color");
        gradientColorProp.setPropertyEditorClass(ColorEditor.class);
        return gradientColorProp;
    }

    private Property createLineWidthProperty() throws NoSuchMethodException {
        Property lineWidthProp = new PropertySupport.Reflection(
                this.getCell(),
                Float.class, "lineWidth");
        lineWidthProp.setName("Line width");
        return lineWidthProp;
    }

    private Property createLineColorProperty() throws NoSuchMethodException {
        PropertySupport.Reflection lineColorProp =
                new PropertySupport.Reflection(this.getCell(), Color.class,
                "lineColor");
        lineColorProp.setName("Line color");
        lineColorProp.setPropertyEditorClass(ColorEditor.class);
        return lineColorProp;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            this.fireDisplayNameChange((String) evt.getOldValue(), (String) evt.
                    getNewValue());
        }
    }

    abstract public Cell getCell();
}
