package cz.cvut.indepmod.classmodel.util;

import java.awt.GridBagConstraints;

/**
 * Date: 11.2.2011
 * Time: 18:31:56
 * @author Lucky
 */
public class GridBagConstraintsUtils {

    public static GridBagConstraints createNewConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        return createNewConstraints(gridx, gridy, gridwidth, gridheight, GridBagConstraints.CENTER);
    }

    public static GridBagConstraints createNewConstraints(int gridx, int gridy, int gridwidth, int gridheight, int anchor) {
        GridBagConstraints res = new GridBagConstraints();

        res.gridx = gridx;
        res.gridy = gridy;
        res.gridwidth = gridwidth;
        res.gridheight = gridheight;
        res.anchor = anchor;

        return res;
    }
}
