package cz.cvut.fel.indepmod.independentmodeler;

import cz.cvut.fel.indepmod.independentmodeler.workspace.wizards.newproject.NewProjectWizardAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class NewProjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        new NewProjectWizardAction().actionPerformed(e);
    }


}
