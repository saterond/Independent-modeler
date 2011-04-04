package cz.cvut.fel.indepmod.independentmodeler;

import cz.cvut.fel.indepmod.independentmodeler.workspace.ProjectTopComponent;
import cz.cvut.fel.indepmod.independentmodeler.workspace.projectnodes.ProjectNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.util.Exceptions;

public final class OpenProjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select project file");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "Independent Modeler Project File",
                "imp"));
        int fileChooserStatus = fileChooser.showOpenDialog(null);
        if (fileChooserStatus == JFileChooser.APPROVE_OPTION) {
            this.loadProject(fileChooser.getSelectedFile());
        }
    }

    public void loadProject(File file) {
        if (file != null) {
            try {
                FileInputStream fin = null;
                ObjectInputStream in = null;
                fin = new FileInputStream(file);
                in = new ObjectInputStream(fin);
                ProjectNode node = (ProjectNode) in.readObject();
                ProjectTopComponent.findInstance().setProject(node);
                in.close();
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
