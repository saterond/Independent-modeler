package cz.cvut.fel.indepmod.independentmodeler.workspace;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

public final class Navigator extends TopComponent implements
        ExplorerManager.Provider {

    private static Navigator instance;
    private static final String PREFERRED_ID = "Navigator";
    static final String ICON_PATH =
            "cz/cvut/fel/indepmod/independentmodeler/navigator.png";
    private ExplorerManager mgr = new ExplorerManager();

    private Navigator() {
        initTopComponent();
        initComponents();
        associateLookup(ExplorerUtils.createLookup(mgr, getActionMap()));
        this.setDisplayName("Navigator");
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        ((BeanTreeView) jScrollPane1).setRootVisible(false);

//        Mode mode = WindowManager.getDefault().findMode("navigator");
//        if (mode != null) {
//            mode.dockInto(this);
//        }
    }

    public static synchronized Navigator getDefault() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    /**
     * Obtain the Navigator instance. Never call {@link #getDefault} directly!
     */
    public static synchronized Navigator findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(
                PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(Navigator.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID
                    + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof Navigator) {
            return (Navigator) win;
        }
        Logger.getLogger(Navigator.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    private void initTopComponent() {
        setName(NbBundle.getMessage(ProjectTopComponent.class,
                "CTL_NavigatorTopComponent"));
        setToolTipText(NbBundle.getMessage(ProjectTopComponent.class,
                "HINT_NavigatorTopComponent"));
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN,
                Boolean.TRUE);
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new BeanTreeView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public ExplorerManager getExplorerManager() {
        return mgr;
    }

    public void setRoot(Node node) {
        this.mgr.setRootContext(node);
        ((BeanTreeView) jScrollPane1).setRootVisible(true);
    }

    public void setSelectedNodes(Node[] nodes) throws PropertyVetoException {
        this.mgr.setSelectedNodes(nodes);
        this.requestActive();
    }

    public Node[] getSelectedNodes() {
        return this.mgr.getSelectedNodes();
    }
}
