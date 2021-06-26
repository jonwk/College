// -------------------------------------------------------------------------

/**
 * This class contains static methods that implementing sorting of an array of
 * numbers using different sort algorithms.
 *
 * @author John Wesley Kommala
 * @version HT 2020
 */

class SortComparison {

    /**
     * Sorts an array of doubles using InsertionSort. This method is static, thus it
     * can be called as SortComparison.sort(a)
     * 
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order.
     *
     */

    static double[] insertionSort(double a[]) {

        for (int i = 0; i < a.length; i++) {
            double temp = a[i];
            int j = i - 1;
            while (j >= 0 && temp <= a[j]) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = temp;
        }
        return a;
    }
    // end insertionsort

    /**
     * Sorts an array of doubles using Selection Sort. This method is static, thus
     * it can be called as SortComparison.sort(a)
     * 
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double[] selectionSort(double a[]) {

        int len = a.length;

        for (int i = 0; i < len - 1; i++) {

            int min_index = i;
            double min_elm = a[i];

            for (int j = i; j < len; j++) {

                double curr_elm = a[j];

                if (curr_elm < min_elm) {
                    min_elm = a[j];
                    min_index = j;
                }
            }

            double temp = a[min_index];
            a[min_index] = a[i];
            a[i] = temp;
        }

        return a;
    }

    /**
     * Sorts an array of doubles using Quick Sort. This method is static, thus it
     * can be called as SortComparison.sort(a)
     * 
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double[] quickSort(double a[]) {
        // assert a != null;
        a = quickSort_recursive(a, 0, a.length - 1);
        return a;
    }

    private static double[] quickSort_recursive(double a[], int lo, int hi) {
        if (lo < hi) {
            int pivot = partition(a, lo, hi);
            a = quickSort_recursive(a, lo, pivot - 1);
            a = quickSort_recursive(a, pivot + 1, hi);
        }
        return a;
    }

    private static int partition(double a[], int lo, int hi) {

        double pivot = a[hi];
        int i = (lo - 1);
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                i++;

                double temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        double temp = a[i + 1];
        a[i + 1] = a[hi];
        a[hi] = temp;

        return i + 1;
    }
    // end quicksort

    /**
     * Sorts an array of doubles using Merge Sort. This method is static, thus it
     * can be called as SortComparison.sort(a)
     * 
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    /**
     * Sorts an array of doubles using iterative implementation of Merge Sort. This
     * method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted
     *         order.
     */

    public static double[] mergeSort_Iterative(double a[]) {
        int len = a.length;
        double[] temp = new double[len];
        for (int i = 1; i < len; i *= 2) {
            for (int low = 0; low < len - i; low += 2 * i) {
                int mid = low + i - 1;
                // int hi = Math.min(lo + 2 * i - 1, len - 1);
                int high = Math.min(mid + i, len - 1);
                merge(a, temp, low, mid, high);
            }
        }
        return a;
    }

    public static double[] mergeSort_Recursive(double a[]) {
        int len = a.length;
        double[] aux = new double[len];
        m_sort_recursive(a, aux, 0, len - 1);
        return a;
    }

    private static void m_sort_recursive(double a[], double aux[], int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        m_sort_recursive(a, aux, lo, mid);
        m_sort_recursive(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(double a[], double aux[], int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)       a[k] = aux[j++];
            else if (j > hi)        a[k] = aux[i++];
            else if (a[j] < aux[i]) a[k] = aux[j++];
            else                    a[k] = aux[i++];
        }
    }

    // end mergesort

    // public static void main(String[] args) throws IOException {
    // // todo: do experiments as per assignment instructions
    // }

}// end class

