
package cz.cvut.indepmod.classmodel.workspace.cell.model.classModel;

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
public class CardinalityTest {

    private Cardinality cardinality;

    @Before
    public void setUp() {
        this.cardinality = new Cardinality(0, -1);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFrom method, of class Cardinality.
     */
    @Test
    public void testGetFromTo() {
        assertEquals(0, this.cardinality.getFrom());
        assertEquals(-1, this.cardinality.getTo());
    }

    /**
     * Test of toString method, of class Cardinality.
     */
    @Test
    public void testToString() {
        assertNotNull(this.cardinality.toString());
        assertTrue(this.cardinality.toString().length() > 0);
    }

    /**
     * Test of equals method, of class Cardinality.
     */
    @Test
    public void testEqualsHashCode() {
        Cardinality card = new Cardinality(0, -1);
        Cardinality card2 = new Cardinality(0, 1);
        Cardinality card3 = new Cardinality(1, -1);
        Cardinality card4 = new Cardinality(1, 1);

        assertTrue(cardinality.equals(card));
        assertFalse(cardinality.equals(card2));
        assertFalse(cardinality.equals(card3));
        assertFalse(cardinality.equals(card4));

        assertEquals(cardinality.hashCode(), card.hashCode());
    }

}