
package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.Common;
import cz.cvut.indepmod.classmodel.api.model.ElementType;
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
public class EnumerationModelTest {

    private EnumerationModel en;

    @Before
    public void setUp() {
        this.en = new EnumerationModel(Common.CLASS_NAME2);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getElementType method, of class EnumerationModel.
     */
    @Test
    public void testGetElementType() {
        assertEquals(ElementType.ENUMERATION, en.getElementType());
    }

}