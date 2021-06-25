import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @author  
 *  @version 13/10/16 18:15
 */
@RunWith(JUnit4.class)
public class DoublyLinkedListTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new DoublyLinkedList<Integer>();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check if the insertBefore works
     */
    @Test
    public void testInsertBefore()
    {
        // test non-empty list
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);

        testDLL.insertBefore(0,4);
        assertEquals( "Checking insertBefore to a list containing 3 elements at position 0", "4,1,2,3", testDLL.toString() );
        testDLL.insertBefore(1,5);
        assertEquals( "Checking insertBefore to a list containing 4 elements at position 1", "4,5,1,2,3", testDLL.toString() );
        testDLL.insertBefore(2,6);       
        assertEquals( "Checking insertBefore to a list containing 5 elements at position 2", "4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(-1,7);        
        assertEquals( "Checking insertBefore to a list containing 6 elements at position -1 - expected the element at the head of the list", "7,4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(7,8);        
        assertEquals( "Checking insertBefore to a list containing 7 elemenets at position 8 - expected the element at the tail of the list", "7,4,5,6,1,2,3,8", testDLL.toString() );
        testDLL.insertBefore(700,9);        
        assertEquals( "Checking insertBefore to a list containing 8 elements at position 700 - expected the element at the tail of the list", "7,4,5,6,1,2,3,8,9", testDLL.toString() );

        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();
        testDLL1.insertBefore(0,1);
        testDLL1.insertBefore(1,2);
        
        testDLL1.insertBefore(1,3);
        assertEquals( "Checking insertBefore to a list containing 8 elements at position 700 - expected the element at the tail of the list", "1,3,2", testDLL1.toString() );

        
        
        // test empty list
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);        
        assertEquals( "Checking insertBefore to an empty list at position 0 - expected the element at the head of the list", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(10,1);        
        assertEquals( "Checking insertBefore to an empty list at position 10 - expected the element at the head of the list", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(-10,1);        
        assertEquals( "Checking insertBefore to an empty list at position -10 - expected the element at the head of the list", "1", testDLL.toString() );
     }

    // TODO: add more tests here. Each line of code in DoublyLinkedList.java should
    // be executed at least once from at least one test.
   
    @Test
    public void testGet()
    {
        // test non-empty list
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);

   
        assertEquals( "Checking insertBefore to a list containing 1,2,3 at position 0", "1", testDLL.get(0).toString() );
        assertEquals( "Checking insertBefore to a list containing 1,2,3 elements at position 1", "2", testDLL.get(1).toString() );
        assertEquals( "Checking insertBefore to a list containing 1,2,3 elements at position 2", "3", testDLL.get(2).toString() );
        assertEquals("Checking for a negative index", null, testDLL.get(-2));
       
        
        // test empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();      
        assertEquals( "Checking insertBefore to an empty list at position 0 - expected the element at the head of the list", null, testDLL1.get(0));
        
        DoublyLinkedList<Integer> testDLL3 = new DoublyLinkedList<Integer>();
        testDLL3.insertBefore(0,1);
        assertEquals("Checking for null", null, testDLL3.get(1));
     }
    
    @Test
    public void testDeleteAt()
    {
        // test non-empty list
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);

        testDLL.deleteAt(0);
        assertEquals( "Checking deleteAt to a list containing 1,2,3 at position 0", "2,3", testDLL.toString() );
        testDLL.deleteAt(0);
        assertEquals( "Checking deleteAt to a list containing 2,3 elements at position 0", "3", testDLL.toString() );
        
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();
        testDLL1.insertBefore(0,1);
        testDLL1.insertBefore(1,2);
        testDLL1.insertBefore(2,3);
        testDLL1.insertBefore(3,4);
        testDLL1.insertBefore(4,5);
        testDLL1.insertBefore(5,6);
        
        DoublyLinkedList<Integer> testDLL2 = new DoublyLinkedList<Integer>();
        testDLL2.insertBefore(0,1);
        testDLL2.insertBefore(1,2);
        testDLL2.deleteAt(0);
        assertEquals( "Checking deleteAt to a list containing 1,2 elements at position 0", "2", testDLL2.toString() );

        
        
        

        testDLL1.deleteAt(5);
        assertEquals( "Checking deleteAt to a list containing 1,2,3,4,5,6 at position 5", "1,2,3,4,5", testDLL1.toString() );
        testDLL1.deleteAt(2);
        assertEquals( "Checking deleteAt to a list containing 1,2,3,4,5 at position 2", "1,2,4,5", testDLL1.toString() );
        testDLL1.deleteAt(1);
        assertEquals( "Checking deleteAt to a list containing 1,2,4,5 at position 1", "1,4,5", testDLL1.toString() );
        
        // checking for index out of bounds
        assertEquals( "Checking deleteAt to a list containing 1,2,4,5 at position -2", false, testDLL1.deleteAt(-2) );
        assertEquals( "Checking deleteAt to a list containing 1,2,4,5 at position 10", false, testDLL1.deleteAt(10) );
        
        testDLL1.deleteAt(1);
        assertEquals( "Checking deleteAt to a list containing 1,2,4,5 at position 1", "1,5", testDLL1.toString() );
//        testDLL1.deleteAt(1);
//        assertEquals( "Checking deleteAt to a list containing 1,4,5 at position 2", "1,4", testDLL1.toString() );
   
        // test empty list
        DoublyLinkedList<Integer> testDLL3 = new DoublyLinkedList<Integer>();      
        assertEquals( "Checking deleteAt to an empty list at position 0 - expected false", false, testDLL3.deleteAt(0) );
        assertEquals( "Checking deleteAt to an empty list at position 0", "", testDLL3.toString() );

     }
    
    @Test
    public void testReverse()
    {
        // test non-empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();
        testDLL1.insertBefore(0,1);
        testDLL1.insertBefore(1,2);
        testDLL1.insertBefore(2,3);
        testDLL1.insertBefore(3,4);
        testDLL1.insertBefore(4,5);
        testDLL1.insertBefore(5,6);

        testDLL1.reverse();
        assertEquals( "Checking  reverse to a list containing 1,2,3,4,5,6 ", "6,5,4,3,2,1", testDLL1.toString() );
        
        DoublyLinkedList<String> testDLL2 = new DoublyLinkedList<String>();
        testDLL2.insertBefore(0,"a");
        testDLL2.insertBefore(1,"b");
        testDLL2.insertBefore(2,"c");
        testDLL2.insertBefore(3,"d");
        testDLL2.insertBefore(4,"e");
        testDLL2.insertBefore(5,"f");
        testDLL2.insertBefore(6,"g");
        testDLL2.insertBefore(7,"h");
        testDLL2.insertBefore(8,"i");

        testDLL2.reverse();
        assertEquals( "Checking  reverse to a list containing a,b,c,d,e,f,g,h,i ", "i,h,g,f,e,d,c,b,a", testDLL2.toString() );
        
        // test empty list
        DoublyLinkedList<Integer> testDLL3 = new DoublyLinkedList<Integer>();
        testDLL3.reverse();
        assertEquals( "Checking reverse to an empty list", "" , testDLL3.toString());
     }
    
    @Test
    public void testMakeUnique(){
        // test non-empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();
        testDLL1.insertBefore(0,1);
        testDLL1.insertBefore(1,2);
        testDLL1.insertBefore(2,3);
        testDLL1.insertBefore(3,4);
        testDLL1.insertBefore(4,5);
        testDLL1.insertBefore(5,5);

        testDLL1.makeUnique();
        assertEquals( "Checking  makeUnique to a list containing 1,2,3,4,5,5 ", "1,2,3,4,5", testDLL1.toString() );
        
        DoublyLinkedList<Integer> testDLL2 = new DoublyLinkedList<Integer>();
        testDLL2.insertBefore(0,1);
        testDLL2.insertBefore(1,1);
        testDLL2.insertBefore(2,1);
        testDLL2.insertBefore(3,1);
        testDLL2.insertBefore(4,1);
        testDLL2.insertBefore(5,1);
        testDLL2.insertBefore(6,1);
        testDLL2.insertBefore(7,1);
        testDLL2.insertBefore(8,1);
        testDLL2.insertBefore(9,1);
        testDLL2.insertBefore(10,1);

        testDLL2.makeUnique();
        assertEquals( "Checking  makeUnique to a list containing 1,1,1,1,1,1,1,1,1,1", "1", testDLL2.toString() );
        
        DoublyLinkedList<String> testDLL4 = new DoublyLinkedList<String>();
        testDLL4.insertBefore(0,"a");
        testDLL4.insertBefore(1,"b");
        testDLL4.insertBefore(2,"c");
        testDLL4.insertBefore(3,"d");
        testDLL4.insertBefore(4,"e");
        testDLL4.insertBefore(5,"f");
        testDLL4.insertBefore(6,"g");
        testDLL4.insertBefore(7,"h");
        testDLL4.insertBefore(8,"i");
        testDLL4.makeUnique();
        assertEquals( "Checking  makeUnique to a list containing a,b,c,d,e,f,g,h,i", "a,b,c,d,e,f,g,h,i", testDLL4.toString() );
        
        DoublyLinkedList<Integer> testDLL5 = new DoublyLinkedList<Integer>();
        testDLL5.insertBefore(0,2);
        testDLL5.insertBefore(1,2);
        testDLL5.insertBefore(2,2);
        testDLL5.insertBefore(3,1);
        testDLL5.insertBefore(4,1);
        testDLL5.insertBefore(5,1);
        testDLL5.insertBefore(6,1);
        


        testDLL5.makeUnique();
        assertEquals( "Checking  makeUnique to a list containing 2,2,2,1,1,1,1 ", "2,1", testDLL5.toString() );

        
//        testDLL5.makeUnique();
//        testDLL5.insertBefore(1,3);
//        testDLL5.insertBefore(2,4);
        testDLL5.deleteAt(1);
//
        assertEquals( "Checking  makeUnique to a list containing 2,2,2,1,1,1,1   testDLL5.deleteAt(1); ", "2", testDLL5.toString() );
//        
        
        DoublyLinkedList<Integer> testDLL6 = new DoublyLinkedList<Integer>();
        
        testDLL6.insertBefore(0,1);
        testDLL6.insertBefore(1,2);
        testDLL6.insertBefore(2,2);
        testDLL6.insertBefore(3,3);
        testDLL6.insertBefore(4,4);

        testDLL6.makeUnique();
        assertEquals( "Checking  makeUnique to a list containing 1,2,2,3,4", "1,2,3,4", testDLL6.toString() );
//        calling makeUnique of list [testA,testB,test,test,testA,test] should result in list [testA,testB,test] but list was [testA,testB,test,testA,test]
 
        // test empty list
        DoublyLinkedList<Integer> testDLL3 = new DoublyLinkedList<Integer>();  
        testDLL3.makeUnique();
        assertEquals( "Checking makeUnique to an empty list", "" , testDLL3.toString());
     }
    
    @Test
    public void testPush() {
    	 DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
         testDLL.push(1);
         assertEquals( "Checking push", "1", testDLL.toString());
         testDLL.push(2);
         assertEquals( "Checking push", "1,2", testDLL.toString()); 
         testDLL.push(3);
         assertEquals( "Checking push", "1,2,3", testDLL.toString()); 
         
    }
    
    @Test
    public void testPop() {
    	DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(1);
        testDLL.push(2);
        testDLL.push(3);
        testDLL.pop();
        assertEquals( "Checking pop", "1,2", testDLL.toString() );
        testDLL.pop();
        assertEquals( "Checking pop", "1", testDLL.toString() );
        testDLL.pop();
        assertEquals( "Checking pop", "", testDLL.toString() );
        
        // Empty List
       
        assertEquals( "Checking pop for an empty lsit",null,  testDLL.pop() );
    }
    
    @Test
    public void testEnqueue()
    {
    	DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
    	testDLL.enqueue(1);
    	assertEquals("Checking enqueue","1",testDLL.toString());
    	testDLL.enqueue(2);
    	assertEquals("Checking enqueue","1,2",testDLL.toString());
    	testDLL.enqueue(2);
    	assertEquals("Checking enqueue","1,2,2",testDLL.toString());   	
    }
    
    @Test
    public void testDequeue()
    {
    	DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
    	testDLL.enqueue(4);
    	testDLL.enqueue(3);
    	testDLL.enqueue(2);
    	testDLL.enqueue(1);

    	assertEquals("Checking dequeue","4", testDLL.dequeue().toString());
    	assertEquals("Checking dequeue","3,2,1",testDLL.toString());
    	
    	// test empty list
    	DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();
    	assertEquals(null,testDLL1.dequeue());
    	testDLL1.enqueue(5);
    	assertEquals("Checking dequeue","5",testDLL1.dequeue().toString());
    	
    }
    
    @Test
    public void testisEmpty()
    {
    	DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
    	assertTrue(testDLL.isEmpty());
    	testDLL.addHead(1);
    	assertFalse(testDLL.isEmpty());
    }
    @Test
    public void testaddHead()
    {
    	DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
    	testDLL.addHead(1);
    	assertEquals( "Checking add head","1", testDLL.toString());
    	testDLL.addHead(2);
    	assertEquals( "Checking add head","2,1", testDLL.toString());
    	
    	 // test empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();      
        assertEquals( "Checking add head for an empty list", "", testDLL1.toString());
    }
    @Test
    public void testaddTail()
    {
    	DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
    	testDLL.addTail(1);
    	assertEquals( "Checking add tail","1", testDLL.toString());
    	testDLL.addTail(2);
    	assertEquals( "Checking add tail","1,2", testDLL.toString());
    	
    	 // test empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();      
        assertEquals( "Checking add tail for an empty list", "", testDLL1.toString());
    }
    
    @Test
    public void testgetTail()
    {
    	
    	 // test empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();      
        assertEquals( "Checking get tail for an empty list", null, testDLL1.getTail());
    }
    
    @Test
    public void testdeleteTail()
    {
    	
    	 // test empty list
        DoublyLinkedList<Integer> testDLL1 = new DoublyLinkedList<Integer>();      
        assertFalse(testDLL1.deleteTail());
    }
    
}
