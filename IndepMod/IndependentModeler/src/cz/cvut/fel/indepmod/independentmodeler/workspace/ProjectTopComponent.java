/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.indepmod.independentmodeler.workspace;

import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.explorer.view.BeanTreeView;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd =
"-//cz.cvut.fel.indepmod.independentmodeler.workspace//Project//EN",
autostore = false)
public final class ProjectTopComponent extends TopComponent {

    private static ProjectTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "ProjectTopComponent";

    public ProjectTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ProjectTopComponent.class,
                "CTL_ProjectTopComponent"));
        setToolTipText(NbBundle.getMessage(ProjectTopComponent.class,
                "HINT_ProjectTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN,
                Boolean.TRUE);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new BeanTreeView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized ProjectTopComponent getDefault() {
        if (instance == null) {
            instance = new ProjectTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the ProjectTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized ProjectTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(
                PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ProjectTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID
                    + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ProjectTopComponent) {
            return (ProjectTopComponent) win;
        }
        Logger.getLogger(ProjectTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
}
