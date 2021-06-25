import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

//-------------------------------------------------------------------------
/**
 *  Test class for Collinear.java
 *
 *  @author  
 *  @version 18/09/18 12:21:26
 */
@RunWith(JUnit4.class)
public class CollinearTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new Collinear();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check that the two methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
        int expectedResult = 0;

        assertEquals("countCollinear failed with 3 empty arrays",       expectedResult, Collinear.countCollinear(new int[0], new int[0], new int[0]));
        assertEquals("countCollinearFast failed with 3 empty arrays", expectedResult, Collinear.countCollinearFast(new int[0], new int[0], new int[0]));
    }

    // ----------------------------------------------------------
    /**
     * Check for no false positives in a single-element array
     */
    @Test
    public void testSingleFalse()
    {
        int[] a3 = { 15 };
        int[] a2 = { 5 };
        int[] a1 = { 10 };

        int expectedResult = 0;

        assertEquals("countCollinear({10}, {5}, {15})",       expectedResult, Collinear.countCollinear(a1, a2, a3) );
        assertEquals("countCollinearFast({10}, {5}, {15})", expectedResult, Collinear.countCollinearFast(a1, a2, a3) );
    }

    // ----------------------------------------------------------
    /**
     * Check for no false positives in a single-element array
     */
    @Test
    public void testSingleTrue()
    {
        int[] a3 = { 15, 5 };       int[] a2 = { 5 };       int[] a1 = { 10, 15, 5 };

        int expectedResult = 1;

        assertEquals("countCollinear(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")",     expectedResult, Collinear.countCollinear(a1, a2, a3));
        assertEquals("countCollinearFast(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")", expectedResult, Collinear.countCollinearFast(a1, a2, a3));
    }
    
    /* Check if sort() is working */
    @Test
    public void testSort()
    {
 
    int[] array  =  {4,1,2,3};
    int[] expectedResult = {1,2,3,4};
    Collinear.sort(array);
    
    assertTrue(Arrays.equals(array,expectedResult));
   
    }
  
    /* Check if Binary Search is working */
    @Test
    public void testBinary()
    {
 
    int[] array  =  {10,2,3,5,9,1,4,22,33,12,100,1};
   
    Collinear.sort(array);
    assertTrue(Collinear.binarySearch(array, 22));
    assertFalse(Collinear.binarySearch(array, 200));
   
   
    }



    // TODO: add more tests here. Each line of code and ech decision in Collinear.java should
    // be executed at least once from at least one test.
   
    
//    public static void main(String[]args) {
//    	
//    	System.out.println("Working");
//    	
//    	 In FileThousand1 = new In("r01000-1.txt");
//    	 In FileThousand2 = new In("r01000-2.txt");
//    	 In FileThousand3 = new In("r01000-3.txt");
//    	 
//    	 
//    	 int[] a10001 = FileThousand1.readAllInts();
//    	 int[] a10002 = FileThousand2.readAllInts();
//    	 int[] a10003 = FileThousand3.readAllInts();
//    	 
//    	 Stopwatch stopwatch = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinear(a10001,a10002,a10003));
//    	 double time = stopwatch.elapsedTime();
//    	 StdOut.println("1000 countCollinear elapsed time " + time);
//    	 
//    	 Stopwatch stopwatch2 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinearFast(a10001,a10002,a10003));
//    	 double time2 = stopwatch2.elapsedTime();
//    	 StdOut.println("1000 CountCollinearFast elapsed time " + time2);
//    	 
//    	 In File2Thousand1 = new In("r02000-1.txt");
//    	 In File2Thousand2 = new In("r02000-2.txt");
//    	 In File2Thousand3 = new In("r02000-3.txt");
//    	 
//    	 
//    	 int[] a20001 = File2Thousand1.readAllInts();
//    	 int[] a20002 = File2Thousand2.readAllInts();
//    	 int[] a20003 = File2Thousand3.readAllInts();
//    	 
//    	 Stopwatch stopwatch3 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinear(a20001,a20002,a20003));
////    	 StdOut.println(a);
//    	 double time3 = stopwatch.elapsedTime();
//    	 StdOut.println("2000 countCollinear elapsed time " + time3);
//    	 
//    	 Stopwatch stopwatch4 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinearFast(a20001,a20002,a20003));
////    	 StdOut.println(a);
//    	 double time4 = stopwatch4.elapsedTime();
//    	 StdOut.println("2000 CountCollinearFast elapsed time " + time4);
//    	 
//    	 In File4Thousand1 = new In("r04000-1.txt");
//    	 In File4Thousand2 = new In("r04000-2.txt");
//    	 In File4Thousand3 = new In("r04000-3.txt");
//    	 
//    	 
//    	 int[] a40001 = File4Thousand1.readAllInts();
//    	 int[] a40002 = File4Thousand2.readAllInts();
//    	 int[] a40003 = File4Thousand3.readAllInts();
//    	 
//    	 Stopwatch stopwatch5 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinear(a40001,a40002,a40003));
////    	 StdOut.println(a);
//    	 double time5 = stopwatch.elapsedTime();
//    	 StdOut.println("4000 countCollinear elapsed time " + time5);
//    	 
//    	 Stopwatch stopwatch6 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinearFast(a40001,a40002,a40003));
//    	 double time6 = stopwatch6.elapsedTime();
//    	 StdOut.println("4000 CountCollinearFast elapsed time " + time6);
//    	 
//    	 In File5Thousand1 = new In("r05000-1.txt");
//    	 In File5Thousand2 = new In("r05000-2.txt");
//    	 In File5Thousand3 = new In("r05000-3.txt");
//    	 
//    	 
//    	 int[] a50001 = File5Thousand1.readAllInts();
//    	 int[] a50002 = File5Thousand2.readAllInts();
//    	 int[] a50003 = File5Thousand3.readAllInts();
//    	 
//    	 Stopwatch stopwatch7 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinear(a50001,a50002,a50003));
//    	 double time7 = stopwatch7.elapsedTime();
//    	 StdOut.println("5000 countCollinear elapsed time " + time7);
//    	 
//    	 Stopwatch stopwatch8 = new Stopwatch();
//    	 StdOut.println(Collinear.countCollinearFast(a50001,a50002,a50003));
//    	 double time8 = stopwatch8.elapsedTime();
//    	 StdOut.println("CountCollinearFast elapsed time " + time8);
//    	 
//    }
}
