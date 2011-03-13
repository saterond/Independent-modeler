package cz.cvut.fel.indepmod.independentmodeler.workspace.wizards.newproject;

import java.awt.Component;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NewProjectWizardPanel1 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private NewProjectVisualPanel1 component;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);
    private boolean isValid = true;

    



    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new NewProjectVisualPanel1();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
        // If it depends on some condition (form filled out...), then:
        // return someCondition();
        // and when this condition changes (last form field filled in...) then:
        // fireChangeEvent();
        // and uncomment the complicated stuff below.
    }
//    @Override
//    public final void addChangeListener(ChangeListener l) {
//    }
//
//    @Override
//    public final void removeChangeListener(ChangeListener l) {
//    }
    

    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    @Override
    public void readSettings(Object settings) {
    }

    @Override
    public void storeSettings(Object settings) {
        ((NewProjectVisualPanel1) this.component).storeSettings(settings);
    }

    @Override
    public void validate() throws WizardValidationException {
        String name = component.getNameField();
        String path = component.getPathField();
        if (name.isEmpty()) {
            throw new WizardValidationException(null, "Invalid Name", null);
        }
        if(!path.isEmpty()) {
            File file = new File(path);

            if (!file.exists() || !file.isDirectory() || !file.canRead()) {
                throw new WizardValidationException(null, "Invalid Path", null);
            }
        } else {
            throw new WizardValidationException(null, "Invalid Path", null);
        }
    }
}
