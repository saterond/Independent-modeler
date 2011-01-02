/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.sequencemodel.editor.cell.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hegladan <hegladan@fel.cvut.cz>
 */
public abstract class AbstractModel {

    Set<ModelListener> lsnrs = new HashSet<ModelListener>();

    public void addListener(ModelListener lsnr) {
        this.lsnrs.add(lsnr);
    }

    public void removeListener(ModelListener lsnr) {
        this.lsnrs.remove(lsnr);
    }

    protected void fireModelChanged() {
        for (ModelListener lsnr: this.lsnrs) {
            lsnr.modelChanged();
        }
    }
}
