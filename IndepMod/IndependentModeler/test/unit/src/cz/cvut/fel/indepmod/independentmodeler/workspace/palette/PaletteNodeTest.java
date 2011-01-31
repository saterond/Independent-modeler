/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.indepmod.independentmodeler.workspace.palette;

import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCellFactory;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.NoteCell;
import cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells.Cell;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Studio
 */
public class PaletteNodeTest {

    private static PaletteNode<NoteCell> paletteNode;

    public PaletteNodeTest() {
    }

    @Before
    public void setUp() {
        paletteNode = new PaletteNode<NoteCell>(IndependentModelerPaletteNodeModel.Note.
                name(),
                IndependentModelerPaletteNodeModel.Note,
                new NoteCellFactory());
    }

    /**
     * Test of getCell method, of class PaletteNode.
     */
    @Test
    public void testGetCell() throws Exception {
        System.out.println("getCell");
        Cell result = paletteNode.getCell();
        assert (result instanceof NoteCell);
        fail("The test case is a prototype.");
    }
}
