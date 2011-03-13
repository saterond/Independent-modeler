package cz.cvut.fel.indepmod.independentmodeler.workspace.wizards.newproject;

import cz.cvut.fel.indepmod.independentmodeler.workspace.ProjectTopComponent;
import cz.cvut.fel.indepmod.independentmodeler.workspace.projectnodes.ProjectNode;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

public final class NewProjectWizardAction implements ActionListener {

    private WizardDescriptor.Panel[] panels;

    @Override
    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("New project wizard");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(
                wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue()
                != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            try {
                ProjectNode node =
                        new ProjectNode((String) wizardDescriptor.getProperty(
                        "name"),
                        new File((String) wizardDescriptor.getProperty("path")));
                ProjectTopComponent.findInstance().setProject(node);
                ProjectTopComponent.findInstance().saveProject();
            } catch (FileNotFoundException ex) {
                this.errorMessageDialog();
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                this.errorMessageDialog();
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private void errorMessageDialog() {
        JOptionPane.showMessageDialog(
                null,
                "Unable to create new project.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[]{
                        new NewProjectWizardPanel1()
                    };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(
                            i));
                    // Sets steps names for a panel
                    jc.putClientProperty("WizardPanel_contentData", steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty("WizardPanel_autoWizardStyle",
                            Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty("WizardPanel_contentDisplayed",
                            Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered",
                            Boolean.TRUE);
                }
            }
        }
        return panels;
    }

    public String getName() {
        return "New project wizard";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
}
