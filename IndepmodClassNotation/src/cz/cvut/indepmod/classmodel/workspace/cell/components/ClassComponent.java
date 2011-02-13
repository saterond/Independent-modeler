package cz.cvut.indepmod.classmodel.workspace.cell.components;

import cz.cvut.indepmod.classmodel.util.GridBagConstraintsUtils;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AnotationModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AttributeModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.ClassModel;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.MethodModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Created by IntelliJ IDEA.
 * User: Lucky
 * Date: 25.9.2010
 * Time: 10:22:36
 * <p/>
 * The purpose of this class is to paint a Class representation according to its model
 */

//TODO - complete painting of the class component
public class ClassComponent extends JComponent {

    private static final Font CLASS_NAME_FONT = new Font("Serif", Font.BOLD, 15);

    private final ClassModel model;

    public ClassComponent(ClassModel model) {
        this.model = model;

        this.initLayout();
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 0;
        int height = 0;
        for (Component child : this.getComponents()) {
            Dimension prefSize = child.getPreferredSize();
            if (prefSize.width > width) {
                width = prefSize.width;
            }

            height += prefSize.height;
        }
        return new Dimension(width, height);
    }



    private void initLayout() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;

        this.add(this.getClassNamePanel(), c);
        this.add(this.getAnotationPanel(), c);
        this.add(this.getAttributePanel(), c);

        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;
        this.add(this.getMethodPanel(), c);
    }

    private JPanel getClassNamePanel() {
        JPanel res = new JPanel(new GridBagLayout());
        res.setBorder(new LineBorder(Color.BLACK));

        GridBagConstraints c = new GridBagConstraints();
        JLabel className = new JLabel(this.model.getTypeName());
        res.add(className, c);

        return res;
    }

    private JPanel getAnotationPanel() {
        JPanel res = new JPanel(new GridBagLayout());
        res.setBorder(new LineBorder(Color.BLACK));

        Set<AnotationModel> anots = this.model.getAnotations();
        for (AnotationModel anot : anots ) {
            JLabel anotLabel = new JLabel(anot.toString());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE;
            c.anchor = GridBagConstraints.LINE_START;
            res.add(anotLabel, c);
        }

        return res;
    }

    private JPanel getAttributePanel() {
        JPanel res = new JPanel(new GridBagLayout());
        res.setBorder(new LineBorder(Color.BLACK));

        Set<AttributeModel> attrs = this.model.getAttributeModels();
        for (AttributeModel attr : attrs ) {
            JLabel anotLabel = new JLabel(attr.toString());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE;
            c.anchor = GridBagConstraints.LINE_START;
            res.add(anotLabel, c);
        }

        return res;
    }

    private JPanel getMethodPanel() {
        JPanel res = new JPanel(new GridBagLayout());
        res.setBorder(new LineBorder(Color.BLACK));

        Set<MethodModel> attrs = this.model.getMethodModels();
        for (MethodModel method : attrs) {
            JLabel anotLabel = new JLabel(method.toString());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE;
            c.anchor = GridBagConstraints.LINE_START;
            res.add(anotLabel, c);
        }

        return res;
    }
}
