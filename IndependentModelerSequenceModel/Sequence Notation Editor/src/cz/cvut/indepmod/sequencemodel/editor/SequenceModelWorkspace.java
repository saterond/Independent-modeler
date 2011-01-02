/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelAbstractAction;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelEditAction;
import cz.cvut.indepmod.sequencemodel.modelFactory.SequenceModelDiagramModelFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;


/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */

public class SequenceModelWorkspace extends TopComponent implements GraphModelListener {

    private GraphModel model;
    private GraphLayoutCache view;
    private SequenceModelGraph graph;
    private ToolChooserModel selectedTool;
    private JPopupMenu popupMenu;
    private InstanceContent lookupContent = new InstanceContent();
    private static SequenceModelWorkspace instance;
    private static final String PREFERRED_ID = "SequenceModelWorkspace";

    public SequenceModelWorkspace(){
        this.init(SequenceModelDiagramModelFactory.getInstance().createEmptyDiagramModel().getLayoutCache());
        setName(NbBundle.getMessage(SequenceModelWorkspace.class, "CTL_Editor"));
        setToolTipText(NbBundle.getMessage(SequenceModelWorkspace.class, "HINT_Editor"));
    }

    public static synchronized SequenceModelWorkspace getDefault() {
        if (instance == null) {
            instance = new SequenceModelWorkspace();
        }
        return instance;
    }

    public static synchronized SequenceModelWorkspace findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SequenceModelWorkspace.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SequenceModelWorkspace) {
            return (SequenceModelWorkspace) win;
        }
        Logger.getLogger(SequenceModelWorkspace.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

        @Override
    public void open() {
        Mode mode = WindowManager.getDefault().findMode("editor");
        if (mode != null) {
        mode.dockInto(this);
        }
        super.open();
        }

    private void init(GraphLayoutCache cache) {
        this.model = new DefaultGraphModel();
        this.selectedTool = new ToolChooserModel();
        this.popupMenu = new JPopupMenu();
        //this.view = new GraphLayoutCache(model, new DefaultCellViewFactory());

        this.graph = new SequenceModelGraph(selectedTool);
        this.graph.setMarqueeHandler(new SequenceModelMarqueeHandler(this.graph,selectedTool,this.popupMenu));
        this.graph.setGraphLayoutCache(cache);
        this.graph.getModel().addGraphModelListener(this);

        initLookup();
        initPopupMenu();
        this.setLayout(new GridLayout(1,1));
        this.add(new JScrollPane(this.graph));

    }

        private void initPopupMenu() {
        //SequenceModelAbstractAction deleteAction = this.actions.get(SequenceModelDeleteAction.ACTION_NAME);
        //deleteAction.setEnabled(true);

        SequenceModelAbstractAction editAction = new SequenceModelEditAction(this.graph);

        //this.popupMenu.add(deleteAction);
        this.popupMenu.add(editAction);
    }

    private void initLookup() {
        this.associateLookup(new AbstractLookup(this.lookupContent));
        this.lookupContent.add(this.selectedTool);
        this.lookupContent.add(this.model);
    }

    @Override
    public void graphChanged(GraphModelEvent gme) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
