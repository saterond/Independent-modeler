package cz.cvut.indepmod.sequencemodel.editor.file;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelGraph;
import cz.cvut.indepmod.sequencemodel.editor.SequenceModelWorkspace;
import cz.cvut.indepmod.sequencemodel.editor.persistence.xml.SequenceModelXMLCoder;
import java.io.File;
import java.io.IOException;
import org.jgraph.graph.GraphLayoutCache;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;

/**
 * Date: 6.11.2010
 * Time: 9:24:57
 * @author Lucky
 */
public class SequenceModelSaveCookie implements SaveCookie {

    private SequenceModelWorkspace workspace;
    private SequenceModelGraph graph;

    public SequenceModelSaveCookie(SequenceModelWorkspace workspace, SequenceModelGraph graph) {
        this.workspace = workspace;
        this.graph = graph;
    }

    @Override
    public void save() throws IOException {
        DataObject dataObj = this.workspace.getLookup().lookup(DataObject.class);
        if (dataObj != null) {
            File f = FileUtil.toFile(dataObj.getPrimaryFile());
            SequenceModelXMLCoder.getInstance().encode(this.graph.getGraphLayoutCache(), f.getPath());
            
            this.workspace.setModified(false);
            
        } else {
            
        }
    }

    private void save(File f) throws IOException {
        
    }

}
