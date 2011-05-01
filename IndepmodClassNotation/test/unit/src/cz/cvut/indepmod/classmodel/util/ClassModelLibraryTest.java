
package cz.cvut.indepmod.classmodel.util;

import java.util.List;
import cz.cvut.indepmod.classmodel.Common;
import cz.cvut.indepmod.classmodel.api.model.IType;
import cz.cvut.indepmod.classmodel.workspace.cell.model.classModel.TypeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucky
 */
public class ClassModelLibraryTest {


    /**
     * Test of joinTypeCollections method, of class ClassModelLibrary.
     */
    @Test
    public void testJoinTypeCollections() {
        System.out.println("joinTypeCollections");
        List<TypeModel> types1 = Arrays.asList(new TypeModel(Common.ANOT1), new TypeModel(Common.ANOT2));
        List<TypeModel> types2 = Arrays.asList(new TypeModel(Common.ANOT3), new TypeModel(Common.CLASS_NAME));
        Collection<IType> result = ClassModelLibrary.joinTypeCollections(types1, types2);

        boolean anot1 = false;
        boolean anot2 = false;
        boolean anot3 = false;
        boolean classname = false;
        for (IType type : result) {
            if (type.getTypeName().equals(Common.ANOT1)) {
                anot1 = true;
            } else if (type.getTypeName().equals(Common.ANOT2)) {
                anot2 = true;
            } else if (type.getTypeName().equals(Common.ANOT3)) {
                anot3 = true;
            } else if (type.getTypeName().equals(Common.CLASS_NAME)) {
                classname = true;
            } else {
                fail("Any other Type model!");
            }
        }
        
        assertTrue(anot1);
        assertTrue(anot2);
        assertTrue(anot3);
        assertTrue(classname);
    }

}