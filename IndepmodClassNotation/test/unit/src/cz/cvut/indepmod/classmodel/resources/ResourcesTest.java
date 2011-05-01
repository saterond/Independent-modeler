
package cz.cvut.indepmod.classmodel.resources;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucky
 */
public class ResourcesTest {


    /**
     * Test of getString method, of class Resources.
     */
    @Test
    public void testGetString() {
        String key = "asdfsdfasdf";
        String expResult = "{<"+ key +">}";
        String result = Resources.getString("asdfsdfasdf");
        assertEquals(expResult, result);

        assertFalse(Resources.getString("new_file_wizard_panel1_name_label").startsWith("{<"));
    }

}