
package cz.cvut.indepmod.classmodel.util;

import java.awt.GridBagConstraints;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucky
 */
public class GridBagConstraintsUtilsTest {


    /**
     * Test of createNewConstraints method, of class GridBagConstraintsUtils.
     */
    @Test
    public void testCreateNewConstraints_4args() {
        int gridx = 0;
        int gridy = 1;
        int gridwidth = 2;
        int gridheight = 3;
        GridBagConstraints result = GridBagConstraintsUtils.createNewConstraints(gridx, gridy, gridwidth, gridheight);

        assertEquals(0, result.gridx);
        assertEquals(1, result.gridy);
        assertEquals(2, result.gridwidth);
        assertEquals(3, result.gridheight);
    }

    /**
     * Test of createNewConstraints method, of class GridBagConstraintsUtils.
     */
    @Test
    public void testCreateNewConstraints_5args() {
        int gridx = 0;
        int gridy = 1;
        int gridwidth = 2;
        int gridheight = 3;
        int anchor = GridBagConstraints.LINE_END;
        GridBagConstraints result = GridBagConstraintsUtils.createNewConstraints(gridx, gridy, gridwidth, gridheight, anchor);

        assertEquals(0, result.gridx);
        assertEquals(1, result.gridy);
        assertEquals(2, result.gridwidth);
        assertEquals(3, result.gridheight);
        assertEquals(GridBagConstraints.LINE_END, result.anchor);
    }

}