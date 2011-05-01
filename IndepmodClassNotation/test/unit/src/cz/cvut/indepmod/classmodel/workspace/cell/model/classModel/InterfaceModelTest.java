
package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.api.model.ElementType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucky
 */
public class InterfaceModelTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getElementType method, of class InterfaceModel.
     */
    @Test
    public void testGetElementType() {
        InterfaceModel im = new InterfaceModel(new ClassModel("xxx"));
        assertEquals(ElementType.INTERFACE, im.getElementType());
    }

}