/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.Common;
import java.util.Collection;
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
public class AnotationAttributeModelTest {

    private AnnotationAttributeModel model;

    @Before
    public void setUp() {
        this.model = new AnnotationAttributeModel(Common.ATTRIBUTE_NAME);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class AnnotationAttributeModel.
     */
    @Test
    public void testGetName() {
        assertNotNull(model.getName());
        assertEquals(Common.ATTRIBUTE_NAME, this.model.getName());
    }

    /**
     * Test of getValues method, of class AnnotationAttributeModel.
     */
    @Test
    public void testAddGetValues() {
        this.model.addValue(Common.VAL1);
        this.model.addValue(Common.VAL2);

        assertEquals(2, this.model.getValues().size());

        boolean isVal1 = false, isVal2 = false;
        for (String s : this.model.getValues()) {
            if (s.equals(Common.VAL1)) {
                isVal1 = true;
            } else if (s.equals(Common.VAL2)) {
                isVal2 = true;
            } else {
                fail("There is a value that I did not add");
            }
        }

        assertTrue(isVal1);
        assertTrue(isVal2);

        this.model.addValue(Common.VAL1);

        assertEquals(2, this.model.getValues().size());
    }

    @Test
    public void testEqualsHashCode() {
        AnnotationAttributeModel model2 = new AnnotationAttributeModel(Common.ATTRIBUTE_NAME);
        AnnotationAttributeModel model3 = new AnnotationAttributeModel(Common.ATTRIBUTE_NAME2);
        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));

        assertFalse(model.equals(null));
        assertFalse(model.equals("Ahoj"));

        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(this.model.toString());
        assertTrue(this.model.toString().length() > 0);
    }
}