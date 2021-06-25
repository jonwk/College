import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 * Test class for Doubly Linked List
 *
 * @version 3.1 09/11/15 11:32:15
 *
 * @author TODO
 */

@RunWith(JUnit4.class)
public class BSTTest {

        // TODO write more tests here.
        @Test
        public void testHeight() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                assertEquals("Checking height of empty tree", -1, bst.height());

                bst.put(7, 7);
                assertEquals("Checking height of a tree with 1 element", 0, bst.height());

             // bst.put(7, 7); //   _7_
                bst.put(8, 8); // /   \
                bst.put(3, 3); // _3_ 8
                bst.put(1, 1); // / \
                bst.put(2, 2); // 1 6
                bst.put(6, 6); // \ /
                bst.put(4, 4); // 2 4
                bst.put(5, 5); // \
                               // 5
                assertEquals("Checking height of constructed tree", 4, bst.height());
        }

        @Test
        public void testRank() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                bst.put(8, 8);
                bst.put(3, 3);
                bst.put(1, 1);
                bst.put(2, 2);
                bst.put(4, null);

                assertEquals("Checking rank of 8", 3, bst.rank(8));
                assertEquals("Checking rank of 3", 2, bst.rank(3));
                assertEquals("Checking rank of 2", 1, bst.rank(2));
                assertEquals("Checking rank of 1", 0, bst.rank(1));
                // assertEquals("Checking rank of 4 an element out of the list", 0, bst.rank(4));
        }

        @Test
        public void testMedian(){
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                assertEquals("Checking median for an empty tree", null, bst.median());
                
                bst.put(8, 8);
                assertEquals("Checking median for a tree with 1 element",(Object)8, (Object)bst.median());

                bst.put(5, 5);
                bst.put(3, 3);
                assertEquals("Checking median for a tree with 3 element",(Object)5, (Object)bst.median());

                bst.put(1,1);
                assertEquals("Checking median for a tree with 4 element",(Object)3, (Object)bst.median());
                
                bst.put(2,2);
                assertEquals("Checking median for a tree with 5 element",(Object)3, (Object)bst.median());

                BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
                bst1.put(7, 7); // _7_
                bst1.put(8, 8); // / \
                bst1.put(3, 3); // _3_ 8
                bst1.put(1, 1); // / \
                bst1.put(2, 2); // 1 6
                bst1.put(6, 6); // \ /
                bst1.put(4, 4); // 2 4
                bst1.put(5, 5); // \
                                // 5
                assertEquals("Checking median for a tree with 8 element",(Object)4, (Object)bst1.median());
            
                BST<String, String> bst_new = new BST<>();

                bst_new.put("T", "T");
                bst_new.put("F", "F");
                bst_new.put("B", "B");
                bst_new.put("S", "S");
                bst_new.put("D", "D");
                bst_new.put("I", "I");
                bst_new.put("N", "N");

                assertEquals("Getting median of tree", "I", bst_new.median());
        }

        @Test
        public void printKeysInOrder() {
                // Example 1: Empty tree -- output: "()" Example 2: Tree containing only "A" --
                // * output: "(()A())" Example 3: Tree: B / \ A C \ D
                // *
                // * output: "((()A())B(()C(()D())))"
                // *
                // * output of example in the assignment:
                // * (((()A(()C()))E((()H(()M()))R()))S(()X()))
                BST<String, String> bst = new BST<String, String>();
                assertEquals("Checking printing in order of empty tree", "()", bst.printKeysInOrder());

                bst.put("A", "A");
                assertEquals("Checking order of constructed tree", "(()A())", bst.printKeysInOrder());

                BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
                assertEquals("Checking printing in order of empty tree", "()", bst1.printKeysInOrder());
                bst1.put(7, 7);
                assertEquals("Checking order of constructed tree", "(()7())", bst1.printKeysInOrder());

             // bst1.put(7, 7); // _7_
                bst1.put(8, 8); // / \
                bst1.put(3, 3); // _3_ 8
                bst1.put(1, 1); // / \
                bst1.put(2, 2); // 1 6
                bst1.put(6, 6); // \ /
                bst1.put(4, 4); // 2 4
                bst1.put(5, 5); // \
                                // 5

                assertEquals("Checking order of constructed tree", "(((()1(()2()))3((()4(()5()))6()))7(()8()))",
                                bst1.printKeysInOrder());
        }

        /**
         * <p>
         * Test {@link BST#prettyPrintKeys()}.
         * </p>
         */

        @Test
        public void testPrettyPrint() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                assertEquals("Checking pretty printing of empty tree", "-null\n", bst.prettyPrintKeys());

                               // -7
                               // |-3
                               // | |-1
                               // | | |-null
                bst.put(7, 7); // | | -2
                bst.put(8, 8); // | | |-null
                bst.put(3, 3); // | | -null
                bst.put(1, 1); // | -6
                bst.put(2, 2); // | |-4
                bst.put(6, 6); // | | |-null
                bst.put(4, 4); // | | -5
                bst.put(5, 5); // | | |-null
                               // | | -null
                               // | -null
                               // -8
                               // |-null
                               // -null

                String result = "-7\n" + 
                                " |-3\n" + 
                                " | |-1\n" + 
                                " | | |-null\n" + 
                                " | |  -2\n" + 
                                " | |   |-null\n"+ 
                                " | |    -null\n" +
                                " |  -6\n" + 
                                " |   |-4\n" + 
                                " |   | |-null\n" + 
                                " |   |  -5\n" + 
                                " |   |   |-null\n" + 
                                " |   |    -null\n" + 
                                " |    -null\n" + 
                                "  -8\n" + 
                                "   |-null\n" + 
                                "    -null\n";
                assertEquals("Checking pretty printing of non-empty tree", result, bst.prettyPrintKeys());
        }

        /**
         * <p>
         * Test {@link BST#delete(Comparable)}.
         * </p>
         */
        @Test
        public void testDelete() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                bst.delete(1);
                bst.delete(null);
                assertEquals("Deleting from empty tree", (Object) null, (Object) bst.get(null));
                assertEquals("Deleting from empty tree", "()", bst.printKeysInOrder());
                // assertNull()

                bst.put(7, 7); // _7_
                bst.put(8, 8); // / \
                bst.put(3, 3); // _3_ 8
                bst.put(1, 1); // / \
                bst.put(2, 2); // 1 6
                bst.put(6, 6); // \ /
                bst.put(4, 4); // 2 4
                bst.put(5, 5); // \
                               // 5

                assertEquals("Checking order of constructed tree", "(((()1(()2()))3((()4(()5()))6()))7(()8()))",
                                bst.printKeysInOrder());

                bst.delete(9);
                assertEquals("Deleting non-existent key", "(((()1(()2()))3((()4(()5()))6()))7(()8()))",
                                bst.printKeysInOrder());

                bst.delete(8);
                assertEquals("Deleting leaf", "(((()1(()2()))3((()4(()5()))6()))7())", bst.printKeysInOrder());

                bst.delete(6);
                assertEquals("Deleting node with single child", "(((()1(()2()))3(()4(()5())))7())",
                                bst.printKeysInOrder());

                bst.delete(3);
                assertEquals("Deleting node with two children", "(((()1())2(()4(()5())))7())", bst.printKeysInOrder());
                
                bst.delete(7);
                bst.delete(5);
                assertEquals("Deleting node with two children", "((()1())2(()4()))", bst.printKeysInOrder());
                
                // bst.delete(4);
                // bst.delete(2);
                // assertEquals("Deleting all elements, so that only one element is left", "(()1())", bst.printKeysInOrder());
                
                bst.delete(1);
                assertEquals("Deleting the only left element root", "(()2(()4()))", bst.printKeysInOrder());
                
                BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
                bst1.put(7, 7);  
                bst1.put(8, 8);  
                bst1.put(3, 3);
                bst1.put(6, 6);
                bst1.delete(6);
                assertEquals("Deleting the a node that has left to be null", "((()3())7(()8()))", bst1.printKeysInOrder());



        }

        @Test
        public void testDeleteMax(){
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                

                bst.put(7, 7); // _7_
                bst.put(8, 8); // / \
                bst.put(3, 3); // _3_ 8
                bst.put(1, 1); // / \
                bst.put(2, 2); // 1 6
                bst.put(6, 6); // \ /
                bst.put(4, 4); // 2 4
                bst.put(5, 5); // \
                               // 5
                bst.deleteMax();
                assertFalse("Checking deleteMax", bst.contains(8));
        }

        @Test
	public void testPut()
	{
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		bst.put(1,3);
		bst.put(2,null);
		bst.put(3, 12);
		assertEquals("Testing put using get","3", bst.get(1).toString());
		bst.put(2,2);
		//bst.put(3,3);
		bst.put(4,4);
		bst.put(5,5);
		assertEquals("(()1((()2())3(()4(()5()))))", bst.printKeysInOrder().toString());
                
                BST<Integer, Integer> bst1 = new BST<>();

                bst1.put(7, 7);
                bst1.put(8, 8);
                bst1.put(3, 3);
                bst1.put(1, 1);
                bst1.put(2, 2);
                bst1.put(6, 6);
                bst1.put(4, 4);
                bst1.put(5, 5);
                assertEquals( "Testing put", "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst1.printKeysInOrder());
                
                bst1.put(7, 7);
                assertEquals("Testing put with same key and value","(((()1(()2()))3((()4(()5()))6()))7(()8()))",bst1.printKeysInOrder());
                
                bst1.put(7, 10);
                assertEquals("Changing value with put", (Object) 10, bst1.get(7));
        }
}
