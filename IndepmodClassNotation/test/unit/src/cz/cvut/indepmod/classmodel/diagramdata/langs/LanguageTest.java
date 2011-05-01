
package cz.cvut.indepmod.classmodel.diagramdata.langs;

import java.util.HashSet;
import cz.cvut.indepmod.classmodel.api.model.IType;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucky
 */
public class LanguageTest {


    /**
     * Test of getLangName method, of class Language.
     */
    @Test
    public void testGetLangName() {
        Language l = new Language("Java", new HashSet<IType>());
        assertEquals("Java", l.getLangName());
    }

    /**
     * Test of setLangName method, of class Language.
     */
    @Test
    public void testSetLangName() {
        Language l = new Language("Java", new HashSet<IType>());
        assertEquals("Java", l.getLangName());
        l.setLangName("xxx");
        assertEquals("xxx", l.getLangName());
    }

    /**
     * Test of getStaticDataTypes method, of class Language.
     */
    @Test
    public void testGetStaticDataTypes() {
        
    }

    /**
     * Test of setStaticDataTypes method, of class Language.
     */
    @Test
    public void testSetStaticDataTypes() {
        
    }

    /**
     * Test of equals method, of class Language.
     */
    @Test
    public void testEquals() {
        
    }

    /**
     * Test of hashCode method, of class Language.
     */
    @Test
    public void testHashCode() {
        
    }

}