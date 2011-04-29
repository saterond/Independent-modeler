package cz.cvut.indepmod.sequencemodel.editor.file;

import cz.cvut.indepmod.sequencemodel.editor.SequenceModelWorkspace;
import org.openide.cookies.CloseCookie;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.MultiDataObject.Entry;
import org.openide.loaders.OpenSupport;
import org.openide.windows.CloneableTopComponent;

/**
 * Date: 6.11.2010
 * Time: 12:36:00
 * @author Lucky
 */
class SequenceModelOpenSupport extends OpenSupport implements OpenCookie, CloseCookie {

    public SequenceModelOpenSupport(Entry primaryEntry) {
        super(primaryEntry);
    }

    @Override
    protected CloneableTopComponent createCloneableTopComponent() {
        SequenceModelDataObject dobj = (SequenceModelDataObject) entry.getDataObject();
        SequenceModelWorkspace workspace = new SequenceModelWorkspace(dobj);
        workspace.setName(dobj.getName());
        return workspace;
    }

}
