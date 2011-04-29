/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor;

import cz.cvut.indepmod.sequencemodel.api.ToolChooserModel;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelAbstractAction;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelDeleteAction;
import cz.cvut.indepmod.sequencemodel.editor.actions.SequenceModelEditAction;
import cz.cvut.indepmod.sequencemodel.editor.file.SequenceModelDataObject;
import cz.cvut.indepmod.sequencemodel.editor.file.SequenceModelSaveCookie;
import cz.cvut.indepmod.sequencemodel.editor.persistence.xml.SequenceModelXMLCoder;
import cz.cvut.indepmod.sequencemodel.modelFactory.SequenceModelDiagramModelFactory;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;


/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */

public class SequenceModelWorkspace extends CloneableTopComponent implements GraphModelListener {

    private GraphModel model;
    private GraphLayoutCache view;
    private SequenceModelGraph graph;
    private ToolChooserModel selectedTool;
    private Map<String, SequenceModelAbstractAction> actions;
    private JPopupMenu popupMenu;
    private InstanceContent lookupContent = new InstanceContent();
    private SequenceModelSaveCookie saveCookie;
    private boolean modified;
    private static SequenceModelWorkspace instance;
    private static final String PREFERRED_ID = "SequenceModelWorkspace";

    public SequenceModelWorkspace(){
        this.init(SequenceModelDiagramModelFactory.getInstance().createEmptyDiagramModel().getLayoutCache());
        //setName(NbBundle.getMessage(SequenceModelWorkspace.class, "CTL_Editor"));
        //setToolTipText(NbBundle.getMessage(SequenceModelWorkspace.class, "HINT_Editor"));
    }

    public SequenceModelWorkspace(SequenceModelDataObject dataObject) {
        String fileName = dataObject.getPrimaryFile().getPath();
        System.out.println(fileName);
        GraphLayoutCache cache = SequenceModelXMLCoder.getInstance().decode(fileName);

        if (cache != null) {
            this.init(cache);
        } else {
            this.init(SequenceModelDiagramModelFactory.getInstance().createEmptyDiagramModel().getLayoutCache());
        }
        this.lookupContent.add(dataObject);
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
        return TopComponent.PERSISTENCE_NEVER;
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
        this.actions = new HashMap<String, SequenceModelAbstractAction>();
        this.model = new DefaultGraphModel();
        this.selectedTool = new ToolChooserModel();
        this.popupMenu = new JPopupMenu();
        this.modified = false;
        //this.view = new GraphLayoutCache(model, new DefaultCellViewFactory());

        this.graph = new SequenceModelGraph(selectedTool);
        this.graph.setMarqueeHandler(new SequenceModelMarqueeHandler(this.graph,selectedTool,this.popupMenu));
        this.graph.setGraphLayoutCache(cache);
        this.graph.getModel().addGraphModelListener(this);
        this.saveCookie = new SequenceModelSaveCookie(this,this.graph);

        this.initLookup();
        this.initActions();
        this.initPopupMenu();
        this.setLayout(new GridLayout(1,1));
        this.add(new JScrollPane(this.graph));

    }

    /**
     * This method inititalizes actions
     */
    private void initActions() {
        this.actions.put(
                SequenceModelDeleteAction.ACTION_NAME,
                new SequenceModelDeleteAction(this.graph));

        this.actions.put(
                SequenceModelEditAction.ACTION_NAME,
                new SequenceModelEditAction(this.graph));
         /*
        this.actions.put(
                SequenceModelUndoAction.ACTION_NAME,
                new SequenceModelUndoAction());

        this.actions.put(
                SequenceModelRedoAction.ACTION_NAME,
                new SequenceModelRedoAction());
        */
    }

    private void initPopupMenu() {

        SequenceModelAbstractAction deleteAction = this.actions.get(SequenceModelDeleteAction.ACTION_NAME);
        deleteAction.setEnabled(true);

        SequenceModelAbstractAction editAction = this.actions.get(SequenceModelEditAction.ACTION_NAME);

        this.popupMenu.add(deleteAction);
        this.popupMenu.add(editAction);
    }

    private void initLookup() {
        this.associateLookup(new AbstractLookup(this.lookupContent));
        this.lookupContent.add(this.selectedTool);
        this.lookupContent.add(this.model);
    }

    @Override
    public void graphChanged(GraphModelEvent gme) {
        this.setModified(true);
        for (DefaultGraphCell fragment : this.graph.getAllFragmentCells()) {
            this.graph.setMessageToFragmentModel(fragment);
        }
    }


    public void setModified(boolean modified) {
        if (!this.modified && modified) {
            this.modified = modified;
            this.lookupContent.add(this.saveCookie);
        } else if (this.modified && !modified) {
            this.modified = modified;
            this.lookupContent.remove(this.saveCookie);
        }
    }


}