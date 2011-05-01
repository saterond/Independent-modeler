
package cz.cvut.indepmod.classmodel.frames.dialogs.validation;

import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.AbstractElementModel;
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
public class ClassModelValidationTest {

    /**
     * Test of getInstance method, of class ClassModelValidation.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(ClassModelValidation.getInstance());
    }

    /**
     * Test of validateClassName method, of class ClassModelValidation.
     */
    @Test
    public void testValidateClassName() {
        ClassModelValidation val = ClassModelValidation.getInstance();

        
    }

    /**
     * Test of validateAttributeName method, of class ClassModelValidation.
     */
    @Test
    public void testValidateAttributeName() {
        ClassModelValidation val = ClassModelValidation.getInstance();

        assertTrue(val.validateAttributeName("atr1"));
        assertTrue(val.validateAttributeName("Atr1"));
        assertFalse(val.validateAttributeName(" Atr1"));
        assertFalse(val.validateAttributeName(" "));
        assertFalse(val.validateAttributeName(""));
        assertFalse(val.validateAttributeName("1atr"));
        assertFalse(val.validateAttributeName("12"));
        assertFalse(val.validateAttributeName("1"));
        assertFalse(val.validateAttributeName("*"));
        assertFalse(val.validateAttributeName("."));
        assertFalse(val.validateAttributeName("/"));
        assertFalse(val.validateAttributeName(":"));
    }

    /**
     * Test of validateAnnotationAttributeName method, of class ClassModelValidation.
     */
    @Test
    public void testValidateAnnotationAttributeName() {
        ClassModelValidation val = ClassModelValidation.getInstance();

        assertTrue(val.validateAnnotationAttributeName("atr1"));
        assertTrue(val.validateAnnotationAttributeName("Atr1"));
        assertFalse(val.validateAnnotationAttributeName(" Atr1"));
        assertFalse(val.validateAnnotationAttributeName(" "));
        assertFalse(val.validateAnnotationAttributeName(""));
        assertFalse(val.validateAnnotationAttributeName("1atr"));
        assertFalse(val.validateAnnotationAttributeName("12"));
        assertFalse(val.validateAnnotationAttributeName("1"));
        assertFalse(val.validateAnnotationAttributeName("*"));
        assertFalse(val.validateAnnotationAttributeName("."));
        assertFalse(val.validateAnnotationAttributeName("/"));
        assertFalse(val.validateAnnotationAttributeName(":"));
    }

    /**
     * Test of validateAnnotationName method, of class ClassModelValidation.
     */
    @Test
    public void testValidateAnnotationName() {
        ClassModelValidation val = ClassModelValidation.getInstance();

        assertTrue(val.validateAnnotationName("atr1"));
        assertTrue(val.validateAnnotationName("Atr1"));
        assertFalse(val.validateAnnotationName(" Atr1"));
        assertFalse(val.validateAnnotationName(" "));
        assertFalse(val.validateAnnotationName(""));
        assertFalse(val.validateAnnotationName("1atr"));
        assertFalse(val.validateAnnotationName("12"));
        assertFalse(val.validateAnnotationName("1"));
        assertFalse(val.validateAnnotationName("*"));
        assertFalse(val.validateAnnotationName("."));
        assertFalse(val.validateAnnotationName("/"));
        assertFalse(val.validateAnnotationName(":"));
    }

}