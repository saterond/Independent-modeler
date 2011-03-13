package cz.cvut.fel.indepmod.editorprovider;

import cz.cvut.fel.indepmod.independentmodeler.workspace.Editor;
import cz.cvut.fel.indepmod.independentmodeler.workspace.Graph;
import cz.cvut.fel.indepmod.independentmodeler.workspace.palette.CategoryChildrenFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.transferhandler.IndependentModelerTransferHandler;
import cz.cvut.fel.indepmod.notationapi.Notation;
import cz.cvut.fel.indepmod.notationidentifikatorapi.GraphNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.util.Lookup;

/**
 *
 * @author Petr Vales
 */
public class EditorProvider {

    private static EditorProvider instance;
    private Map<String, Editor> map;

    private EditorProvider() {
        super();
        this.map = new HashMap<String, Editor>();
    }

    public synchronized static EditorProvider getInstance() {
        if (instance == null) {
            instance = new EditorProvider();
        }
        return instance;
    }

    public Graph requestEditor(
            String notationID,
            String graphID,
            String title,
            GraphNode projectNode) {
        Graph graph = null;
        for (Notation notation : Lookup.getDefault().lookupAll(Notation.class)) {
            if (notation.getName().equals(notationID)) {
                CategoryChildrenFactory factory = notation.getCathegoryChildrenFactory();
                IndependentModelerTransferHandler handler = notation.getTransferHandler();
                Editor editor = new Editor(title, factory, handler, projectNode);
                this.map.put(graphID, editor);
                graph = editor.getGraph();
                editor.open();
                editor.requestActive();
            }
        }
        return graph;
    }

    public Graph requestEditor(
            String notationID,
            String graphID,
            String title,
            GraphNode projectNode,
            Graph graph) {
        Graph _graph = null;
        if( this.map.containsKey(graphID) ) {
            _graph = reopen(graphID);
        } else {
            _graph = createEditor(notationID, graphID, title, projectNode, graph);
        }
        return _graph;
    }

    private Graph reopen(String graphID) {
        Editor editor = this.map.get(graphID);
        editor.open();
        editor.requestActive();
        return editor.getGraph();
    }

    private Graph createEditor(
            String notationID,
            String graphID,
            String title,
            GraphNode projectNode,
            Graph graph) {
        Graph _graph = null;
        for (Notation notation : Lookup.getDefault().lookupAll(Notation.class)) {
            if (notation.getName().equals(notationID)) {
                CategoryChildrenFactory factory = notation.getCathegoryChildrenFactory();
                IndependentModelerTransferHandler handler = notation.getTransferHandler();
                Editor editor = new Editor(title, factory, handler, projectNode, graph);
                _graph = editor.getGraph();
                editor.open();
                editor.requestActive();
            }
        }
        return _graph;
    }

    public String[] findAvaiableNotations() {
        List<String> ret = new ArrayList<String>();
        for (Notation notation : Lookup.getDefault().lookupAll(Notation.class)) {
            ret.add(notation.getName());
        }
        return ret.toArray(new String[0]);
    }
}
