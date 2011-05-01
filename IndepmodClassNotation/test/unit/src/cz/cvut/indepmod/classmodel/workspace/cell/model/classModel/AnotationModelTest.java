/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

import cz.cvut.indepmod.classmodel.Common;
import cz.cvut.indepmod.classmodel.api.model.IAnotationValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucky
 */
public class AnotationModelTest {

    AnotationModel model;

    @Before
    public void setUp() {
        this.model = new AnotationModel(Common.ANOT1);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class AnotationModel.
     */
    @Test
    public void testGetName() {
        assertNotNull(this.model.getName());
        assertEquals(Common.ANOT1, this.model.getName());

        this.model.setName(Common.ANOT2);
        assertEquals(Common.ANOT2, this.model.getName());
    }

    /**
     * Test of getAttributes method, of class AnotationModel.
     */
    @Test
    public void testAddGetAttributes() {
        AnnotationAttributeModel atr1 = new AnnotationAttributeModel(Common.ATTRIBUTE_NAME);
        AnnotationAttributeModel atr2 = new AnnotationAttributeModel(Common.ATTRIBUTE_NAME2);
        AnnotationAttributeModel atr1X = new AnnotationAttributeModel(Common.ATTRIBUTE_NAME);

        assertEquals(0, this.model.getAttributes().size());

        this.model.addAttribute(atr1);
        this.model.addAttribute(atr2);

        assertEquals(2, this.model.getAttributes().size());

        boolean isAtr1 = true, isAtr2 = false;
        for (IAnotationValue val : this.model.getAttributes()) {
            if (val.getName().equals(Common.ATTRIBUTE_NAME)) {
                isAtr1 = true;
            } else if (val.getName().equals(Common.ATTRIBUTE_NAME2)) {
                isAtr2 = true;
            } else {
                fail("There is another Anotation Value!");
            }
        }

        assertTrue(isAtr1);
        assertTrue(isAtr2);

        this.model.addAttribute(atr1X);
        assertEquals(2, this.model.getAttributes().size());

        this.model.addAttribute(null);
        assertEquals(2, this.model.getAttributes().size());

        this.model.removeAttribute(null);
        assertEquals(2, this.model.getAttributes().size());

        this.model.removeAttribute(atr1);
        assertEquals(1, this.model.getAttributes().size());

        this.model.removeAttribute(atr1X);
        assertEquals(1, this.model.getAttributes().size());

        this.model.removeAttribute(atr2);
        assertEquals(0, this.model.getAttributes().size());
    }

    @Test
    public void testEqualsHashCode() {
        AnotationModel model2 = new AnotationModel(Common.ANOT1);
        AnotationModel model3 = new AnotationModel(Common.ANOT3);

        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));
        assertFalse(model.equals(null));
        assertFalse(model.equals(123));
        assertFalse(model.equals("xxx"));

        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(model.toString());
        assertTrue(model.toString().length() > 0);
    }
}