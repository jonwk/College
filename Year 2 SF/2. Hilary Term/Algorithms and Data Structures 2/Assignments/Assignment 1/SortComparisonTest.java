import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

//-------------------------------------------------------------------------
/**
 * Test class for SortComparison.java
 *
 * @author John Wesley Kommala
 * @version HT 2020
 */
// .---------------------.-----------------.-----------------.-------------.-----------------------.------------------------.
// |                     | insertionSort() | selectionSort() | quickSort() | mergeSort_Iterative() | mergerSort_Recursive() |
// |---------------------|-----------------|-----------------|-------------|-----------------------|------------------------|
// | 100 random          |     154539 ns   |     100426ns    |   184614ns  |        74052ns        |         67241ns        |
// | 1000 random         |    3036682 ns   |    3924002 ns   |  4523883 ns |       338909 ns       |        189064 ns       |
// | 1000 few unique     |    761900 ns    |    169500 ns    |  452822 ns  |        63313 ns       |        42268 ns        |
// | 1000 nearly ordered |     62376 ns    |    166732 ns    |  2747765 ns |       100448 ns       |        34677 ns        |
// | 1000 reverse order  |     67707 ns    |    184251 ns    |  627589 ns  |        65947 ns       |        41287 ns        |
// | 1000 sorted         |     3522 ns     |    178836 ns    |  467690 ns  |        72422 ns       |        35086 ns        |
// | 10000 random        |    3300973 ns   |   16323267 ns   | 47779827 ns |        672815 ns      |        308867 ns       |
// '---------------------'-----------------'-----------------'-------------'-----------------------'------------------------'


@RunWith(JUnit4.class)
public class SortComparisonTest {
    // ~ Constructor ........................................................
    @Test
    public void testConstructor() {
        new SortComparison();
    }

    // ~ Public Methods ........................................................

    // ----------------------------------------------------------

    public static final double[] array_1 = { 339.166 };

    public static final double[] array_1_sorted = { 339.166 };

    public static final double[] array_10 = { 339.166, 7885.86, 1709.59, 1463.05, 465.835, 4995.04, 6513.31, 6817.9,
            4131.23, 5396.18, 3070.9 };

    public static final double[] array_10_sorted = { 339.166, 465.835, 1463.05, 1709.59, 3070.9, 4131.23, 4995.04,
            5396.18, 6513.31, 6817.9, 7885.86 };

    public static final double[] array_100 = { 6119.8, 53.3623, 265.633, 4186.77, 5054.64, 9113.09, 3097.86, 9082.7,
            1843.84, 2401.59, 7922.73, 3127.65, 1348.56, 7707.1, 9873.41, 9629.27, 6605.68, 9621.29, 8039.55, 7998.5,
            1126.91, 9044.08, 2732.97, 6277.04, 5929.72, 9249.33, 5526.41, 6989.07, 8786.32, 7008.74, 3951.62, 6409.77,
            7188.78, 6756.82, 1286.65, 9858.67, 668.02, 3495.92, 8440.57, 2878.76, 2159.83, 3309.76, 8499.57, 3893.64,
            7513.14, 786.409, 5641.59, 8948.33, 7980.53, 7612.56, 7900.24, 5044.56, 9699.93, 1204.76, 9633.62, 3158.49,
            2383.82, 8435.06, 7048.27, 7503.92, 1336.95, 3477.14, 1549.71, 7669.5, 7722.87, 7988.5, 2175.27, 7229.91,
            6343, 9440.86, 8523.56, 367.403, 2768.99, 691.718, 3819.37, 5167.93, 2875.03, 2748.44, 2377.71, 8983.39,
            8604.68, 6644.23, 4642.73, 5769.64, 4813.72, 7546.69, 3823.73, 9753.46, 9002.79, 4529.2, 1518.28, 304.592,
            7313.33, 1264.95, 7674.72, 4863.5, 1620.32, 2906.97, 2765.64, 3433.66, 6929.58 };

    public static final double[] array_100_sorted = { 53.3623, 265.633, 304.592, 367.403, 668.02, 691.718, 786.409,
            1126.91, 1204.76, 1264.95, 1286.65, 1336.95, 1348.56, 1518.28, 1549.71, 1620.32, 1843.84, 2159.83, 2175.27,
            2377.71, 2383.82, 2401.59, 2732.97, 2748.44, 2765.64, 2768.99, 2875.03, 2878.76, 2906.97, 3097.86, 3127.65,
            3158.49, 3309.76, 3433.66, 3477.14, 3495.92, 3819.37, 3823.73, 3893.64, 3951.62, 4186.77, 4529.2, 4642.73,
            4813.72, 4863.5, 5044.56, 5054.64, 5167.93, 5526.41, 5641.59, 5769.64, 5929.72, 6119.8, 6277.04, 6343,
            6409.77, 6605.68, 6644.23, 6756.82, 6929.58, 6989.07, 7008.74, 7048.27, 7188.78, 7229.91, 7313.33, 7503.92,
            7513.14, 7546.69, 7612.56, 7669.5, 7674.72, 7707.1, 7722.87, 7900.24, 7922.73, 7980.53, 7988.5, 7998.5,
            8039.55, 8435.06, 8440.57, 8499.57, 8523.56, 8604.68, 8786.32, 8948.33, 8983.39, 9002.79, 9044.08, 9082.7,
            9113.09, 9249.33, 9440.86, 9621.29, 9629.27, 9633.62, 9699.93, 9753.46, 9858.67, 9873.41 };

    public static final double[] array_20_dupes = { 339.166, 1463.05, 465.835, 6817.9, 7885.86, 1709.59, 1463.05,
            465.835, 4995.04, 6513.31, 6817.9, 4131.23, 5396.18, 3070.9, 339.166, 7885.86, 1709.59, 4995.04, 6513.31,
            4131.23, 5396.18, 3070.9 };

    public static final double[] array_20_dupes_sorted = { 339.166, 339.166, 465.835, 465.835, 1463.05, 1463.05,
            1709.59, 1709.59, 3070.9, 3070.9, 4131.23, 4131.23, 4995.04, 4995.04, 5396.18, 5396.18, 6513.31, 6513.31,
            6817.9, 6817.9, 7885.86, 7885.86 };

    @Test
    public void testEmpty() {
        double[] toSort = {};

        double[] sorted = new double[toSort.length];
        System.arraycopy(toSort, 0, sorted, 0, sorted.length);
        Arrays.sort(sorted);
        assertArrayEquals("Checking empty for Arrays.sort", sorted, toSort, 0);

        double[] sorted_ins = new double[toSort.length];
        System.arraycopy(toSort, 0, sorted_ins, 0, sorted_ins.length);
        sorted_ins = SortComparison.insertionSort(sorted_ins);
        assertArrayEquals("Checking empty for Insertion Sort", sorted_ins, toSort, 0);

        double[] sorted_sel = new double[toSort.length];
        System.arraycopy(toSort, 0, sorted_sel, 0, sorted_sel.length);
        sorted_sel = SortComparison.selectionSort(sorted_sel);
        assertArrayEquals("Checking empty for Selection Sort", sorted_sel, toSort, 0);

        double[] sorted_qck = new double[toSort.length];
        System.arraycopy(toSort, 0, sorted_qck, 0, sorted_qck.length);
        sorted_qck = SortComparison.quickSort(sorted_qck);
        assertArrayEquals("Checking empty for Quick Sort", sorted_qck, toSort, 0);

        double[] sorted_mrg_itr = new double[toSort.length];
        System.arraycopy(toSort, 0, sorted_mrg_itr, 0, sorted_mrg_itr.length);
        sorted_mrg_itr = SortComparison.mergeSort_Iterative(sorted_mrg_itr);
        assertArrayEquals("Checking empty for Merge Sort Iterative", sorted_mrg_itr, toSort, 0);

        double[] sorted_mrg_rec = new double[toSort.length];
        System.arraycopy(toSort, 0, sorted_mrg_rec, 0, sorted_mrg_rec.length);
        sorted_mrg_rec = SortComparison.mergeSort_Recursive(sorted_mrg_rec);
        assertArrayEquals("Checking empty for Merge Sort Recursive", sorted_mrg_rec, toSort, 0);

    }

    @Test
    public void testInsertionSort() {
        double[] temp_1 = new double[array_1.length];
        System.arraycopy(array_1, 0, temp_1, 0, temp_1.length);
        temp_1 = SortComparison.insertionSort(temp_1);
        assertArrayEquals("Insertion Sort : 1 Test", array_1_sorted, temp_1, 0);

        double[] temp_10 = new double[array_10.length];
        System.arraycopy(array_10, 0, temp_10, 0, temp_10.length);
        temp_10 = SortComparison.insertionSort(temp_10);
        assertArrayEquals("Insertion Sort : 10 Test", array_10_sorted, temp_10, 0);

        double[] temp_100 = new double[array_100.length];
        System.arraycopy(array_100, 0, temp_100, 0, temp_100.length);
        temp_100 = SortComparison.insertionSort(temp_100);
        assertArrayEquals("Insertion Sort : 100 Test", array_100_sorted, temp_100, 0);

        double[] temp_20_dupes = new double[array_20_dupes.length];
        System.arraycopy(array_20_dupes, 0, temp_20_dupes, 0, temp_20_dupes.length);
        temp_20_dupes = SortComparison.insertionSort(temp_20_dupes);
        assertArrayEquals("Insertion Sort : 20 Duplicates Test", array_20_dupes_sorted, temp_20_dupes, 0);

    }

    @Test
    public void testSelectionSort() {
        double[] temp_1 = new double[array_1.length];
        System.arraycopy(array_1, 0, temp_1, 0, temp_1.length);
        temp_1 = SortComparison.selectionSort(temp_1);
        assertArrayEquals("Selection Sort : 1 Test", array_1_sorted, temp_1, 0);

        double[] temp_10 = new double[array_10.length];
        System.arraycopy(array_10, 0, temp_10, 0, temp_10.length);
        temp_10 = SortComparison.selectionSort(temp_10);
        assertArrayEquals("Selection Sort : 10 Test", array_10_sorted, temp_10, 0);

        double[] temp_100 = new double[array_100.length];
        System.arraycopy(array_100, 0, temp_100, 0, temp_100.length);
        temp_100 = SortComparison.selectionSort(temp_100);
        assertArrayEquals("Selection Sort : 100 Test", array_100_sorted, temp_100, 0);

        double[] temp_20_dupes = new double[array_20_dupes.length];
        System.arraycopy(array_20_dupes, 0, temp_20_dupes, 0, temp_20_dupes.length);
        temp_20_dupes = SortComparison.selectionSort(temp_20_dupes);
        assertArrayEquals("Selection Sort : 20 Duplicates Test", array_20_dupes_sorted, temp_20_dupes, 0);

    }

    @Test
    public void testQuickSort() {
        double[] temp_1 = new double[array_1.length];
        System.arraycopy(array_1, 0, temp_1, 0, temp_1.length);
        temp_1 = SortComparison.quickSort(temp_1);
        assertArrayEquals("Quick Sort : 1 Test", array_1_sorted, temp_1, 0);

        double[] temp_10 = new double[array_10.length];
        System.arraycopy(array_10, 0, temp_10, 0, temp_10.length);
        temp_10 = SortComparison.quickSort(temp_10);
        assertArrayEquals("Quick Sort : 10 Test", array_10_sorted, temp_10, 0);

        double[] temp_100 = new double[array_100.length];
        System.arraycopy(array_100, 0, temp_100, 0, temp_100.length);
        temp_100 = SortComparison.quickSort(temp_100);
        assertArrayEquals("Quick Sort : 100 Test", array_100_sorted, temp_100, 0);

        double[] temp_20_dupes = new double[array_20_dupes.length];
        System.arraycopy(array_20_dupes, 0, temp_20_dupes, 0, temp_20_dupes.length);
        temp_20_dupes = SortComparison.quickSort(temp_20_dupes);
        assertArrayEquals("Quick Sort : 20 Duplicates Test", array_20_dupes_sorted, temp_20_dupes, 0);

    }

    @Test
    public void testMergeSort_Iterative() {
        double[] temp_1 = new double[array_1.length];
        System.arraycopy(array_1, 0, temp_1, 0, temp_1.length);
        temp_1 = SortComparison.mergeSort_Iterative(temp_1);
        assertArrayEquals("Merge Sort : 1 Test", array_1_sorted, temp_1, 0);

        double[] temp_10 = new double[array_10.length];
        System.arraycopy(array_10, 0, temp_10, 0, temp_10.length);
        temp_10 = SortComparison.mergeSort_Iterative(temp_10);
        assertArrayEquals("Merge Sort : 10 Test", array_10_sorted, temp_10, 0);

        double[] temp_100 = new double[array_100.length];
        System.arraycopy(array_100, 0, temp_100, 0, temp_100.length);
        temp_100 = SortComparison.mergeSort_Iterative(temp_100);
        assertArrayEquals("Merge Sort : 100 Test", array_100_sorted, temp_100, 0);

        double[] temp_20_dupes = new double[array_20_dupes.length];
        System.arraycopy(array_20_dupes, 0, temp_20_dupes, 0, temp_20_dupes.length);
        temp_20_dupes = SortComparison.mergeSort_Iterative(temp_20_dupes);
        assertArrayEquals("Merge Sort : 20 Duplicates Test", array_20_dupes_sorted, temp_20_dupes, 0);

    }

    @Test
    public void testMergeSort_Recursive() {
        double[] temp_1 = new double[array_1.length];
        System.arraycopy(array_1, 0, temp_1, 0, temp_1.length);
        temp_1 = SortComparison.mergeSort_Recursive(temp_1);
        assertArrayEquals("Merge Sort : 1 Test", array_1_sorted, temp_1, 0);

        double[] temp_10 = new double[array_10.length];
        System.arraycopy(array_10, 0, temp_10, 0, temp_10.length);
        temp_10 = SortComparison.mergeSort_Recursive(temp_10);
        assertArrayEquals("Merge Sort : 10 Test", array_10_sorted, temp_10, 0);

        double[] temp_100 = new double[array_100.length];
        System.arraycopy(array_100, 0, temp_100, 0, temp_100.length);
        temp_100 = SortComparison.mergeSort_Recursive(temp_100);
        assertArrayEquals("Merge Sort : 100 Test", array_100_sorted, temp_100, 0);

        double[] temp_20_dupes = new double[array_20_dupes.length];
        System.arraycopy(array_20_dupes, 0, temp_20_dupes, 0, temp_20_dupes.length);
        temp_20_dupes = SortComparison.mergeSort_Recursive(temp_20_dupes);
        assertArrayEquals("Merge Sort : 20 Duplicates Test", array_20_dupes_sorted, temp_20_dupes, 0);

    }
    // be executed at least once from at least one test.

    // ----------------------------------------------------------
    public static String get_time(long startTime, long finishTime) {
        long execution_time = finishTime - startTime;
        return (execution_time) + "ns  ";
    }

    public static void print_time(String filename, int times) throws IOException {
        double[] testInput;
        long startTime;
        long endTime;

        System.out.println();
        testInput = Files.lines(Paths.get(filename)).mapToDouble(Double::parseDouble).toArray();

        System.out.println("*** " + filename + " ***");
        String exe_time_3 = "";
        long sum_times, avg_time;

        sum_times = 0;
        avg_time = 0;
        for (int i = 0; i < times; i++) {
            startTime = System.nanoTime();
            SortComparison.insertionSort(testInput);
            endTime = System.nanoTime();
            sum_times += (endTime - startTime);
            exe_time_3 += get_time(startTime, endTime);
        }
        avg_time = sum_times / times;
        System.out.println("- insertionSort() \t\t- " + exe_time_3 + "Avg - "+avg_time+"ns");

        exe_time_3 = "";
        sum_times = 0;
        avg_time = 0;
        for (int i = 0; i < times; i++) {
            startTime = System.nanoTime();
            SortComparison.selectionSort(testInput);
            endTime = System.nanoTime();
            sum_times += (endTime - startTime);
            exe_time_3 += get_time(startTime, endTime);
        }
        avg_time = sum_times / times;
        System.out.println("- selectionSort() \t\t- " + exe_time_3+ "Avg - "+avg_time+"ns");

        exe_time_3 = "";
        sum_times = 0;
        avg_time = 0;
        for (int i = 0; i < times; i++) {
            startTime = System.nanoTime();
            SortComparison.quickSort(testInput);
            endTime = System.nanoTime();
            sum_times += (endTime - startTime);
            exe_time_3 += get_time(startTime, endTime);
        }
        avg_time = sum_times / times;
        System.out.println("- quickSort() \t\t\t- " + exe_time_3+ "Avg - "+avg_time+"ns");

        // startTime = System.nanoTime();
        // SortComparison.mergeSort(testInput);
        // endTime = System.nanoTime();
        // System.out.println("- mergeSort() \t\t- " + (endTime - startTime) + "ns");
        
        exe_time_3 = "";
        sum_times = 0;
        avg_time = 0;
        for (int i = 0; i < times; i++) {
            startTime = System.nanoTime();
            SortComparison.mergeSort_Iterative(testInput);
            endTime = System.nanoTime();
            sum_times += (endTime - startTime);
            exe_time_3 += get_time(startTime, endTime);
        }
        avg_time = sum_times / times;
        System.out.println("- mergeSort_Iterative() \t- " + exe_time_3+ "Avg - "+avg_time+"ns");

        exe_time_3 = "";
        sum_times = 0;
        avg_time = 0;
        for (int i = 0; i < times; i++) {
            startTime = System.nanoTime();
            SortComparison.mergeSort_Recursive(testInput);
            endTime = System.nanoTime();
            sum_times += (endTime - startTime);
            exe_time_3 += get_time(startTime, endTime);
        }
        avg_time = sum_times / times;
        System.out.println("- mergeSort_Recursive() \t- " + exe_time_3+ "Avg - "+avg_time+"ns");


        System.out.println();

    }

    /**
     * Main Method. Use this main method to create the experiments needed to answer
     * the experimental performance questions of this assignment.
     * 
     * @throws IOException
     *
     */
    public static void main(String[] args) throws IOException {

        print_time("numbers100.txt", 3);

        // tests numbers1000.txt
        print_time("numbers1000.txt", 3);

        // tests numbers1000Duplicates.txt
        print_time("numbers1000Duplicates.txt", 3);

        // tests numbersNearlyOrdered1000.txt
        print_time("numbersNearlyOrdered1000.txt", 3);

        // tests numbersReverse1000.txt
        print_time("numbersReverse1000.txt", 3);

        // tests numbersSorted1000.txt
        print_time("numbersSorted1000.txt", 3);

        // tests numbers10000.txt
        print_time("numbers10000.txt", 3);

    }

}
