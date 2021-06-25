
// -------------------------------------------------------------------------
/**
 * This class contains only two static methods that search for points on the
 * same line in three arrays of integers.
 *
 * @author
 * @version 18/09/18 12:21:09
 */

class Collinear {

	// ----------------------------------------------------------
	/**
	 * Counts for the number of non-hoizontal lines that go through 3 points in
	 * arrays a1, a2, a3. This method is static, thus it can be called as
	 * Collinear.countCollinear(a1,a2,a3)
	 * 
	 * @param a1: An UNSORTED array of integers. Each integer a1[i] represents the
	 *            point (a1[i], 1) on the plain.
	 * @param a2: An UNSORTED array of integers. Each integer a2[i] represents the
	 *            point (a2[i], 2) on the plain.
	 * @param a3: An UNSORTED array of integers. Each integer a3[i] represents the
	 *            point (a3[i], 3) on the plain.
	 * @return the number of points which are collinear and do not lie on a
	 *         horizontal line.
	 *
	 *         Array a1, a2 and a3 contain points on the horizontal line y=1, y=2
	 *         and y=3, respectively. A non-horizontal line will have to cross all
	 *         three of these lines. Thus we are looking for 3 points, each in a1,
	 *         a2, a3 which lie on the same line.
	 *
	 *         Three points (x1, y1), (x2, y2), (x3, y3) are collinear (i.e., they
	 *         are on the same line) if
	 *
	 *         x1(y2âˆ’y3)+x2(y3âˆ’y1)+x3(y1âˆ’y2)=0
	 *
	 *         In our case y1=1, y2=2, y3=3.
	 *
	 *         You should implement this using a BRUTE FORCE approach (check all
	 *         possible combinations of numbers from a1, a2, a3)
	 *
	 *         ----------------------------------------------------------
	 *
	 *
	 *         Order of Growth -------------------------
	 *
	 *         Caclulate and write down the order of growth of your algorithm. You
	 *         can use the asymptotic notation. You should adequately explain your
	 *         answer. Answers without adequate explanation will not be counted.
	 *
	 *         Order of growth: N^3
	 *
	 *         Explanation: three linear for loops
	 */
	static int countCollinear(int[] a1, int[] a2, int[] a3) {
		// TODO: implement this method
		int collinearCount = 0;
		for (int x1 : a1) {
			for (int x2 : a2) {
				for (int x3 : a3) {
					//	collinear_equation = ((x1 * (y2 - y3)) + (x2 * (y3 - y1)) + (x3 * (y1 - y2)))
					// 	collinear_equation = ((x1 * (2 - 3)) + (x2 * (3 - 1)) + (x3 * (1 - 2)))
					//	collinear_equation = ((x1 * (-1)) + (x2 * (2)) + (x3 * (-1)))
					//  collinear_equation = (-x1 + 2*x2 -x3 = 0)
					// 	collinear_equation = (2*x2)-(x1+x3)
					int collinear_equation = (2 * x2) - (x1 + x3);
					if (collinear_equation == 0)
						collinearCount++;
				}
			}
		}
		return collinearCount;
	}

	// ----------------------------------------------------------
	/**
	 * Counts for the number of non-hoizontal lines that go through 3 points in
	 * arrays a1, a2, a3. This method is static, thus it can be called as
	 * Collinear.countCollinearFast(a1,a2,a3)
	 * 
	 * @param a1: An UNSORTED array of integers. Each integer a1[i] represents the
	 *            point (a1[i], 1) on the plain.
	 * @param a2: An UNSORTED array of integers. Each integer a2[i] represents the
	 *            point (a2[i], 2) on the plain.
	 * @param a3: An UNSORTED array of integers. Each integer a3[i] represents the
	 *            point (a3[i], 3) on the plain.
	 * @return the number of points which are collinear and do not lie on a
	 *         horizontal line.
	 *
	 *         In this implementation you should make non-trivial use of
	 *         InsertionSort and Binary Search. The performance of this method
	 *         should be much better than that of the above method.
	 *
	 *
	 *         Order of Growth -------------------------
	 *
	 *         Caclulate and write down the order of growth of your algorithm. You
	 *         can use the asymptotic notation. You should adequately explain your
	 *         answer. Answers without adequate explanation will not be counted.
	 *
	 *         Order of Growth: N^2 log N
	 *
	 *         Explanation: Two linear for loops paired with an implementation of an binarySearch algorithms which has N^2 and log N as their order of growths respectively.
	 *
	 *
	 */
	static int countCollinearFast(int[] a1, int[] a2, int[] a3) {
		// TODO: implement this method
		int collinearCount = 0;
		sort(a3);
		for (int x1 : a1) {
			for (int x2 : a2) {
				// collinear_equation = ((x1 * (2 - 3)) + (x2 * (3 - 1)) + (x3 * (1 - 2)));
				// -x1 + 2*x2 -x3 =0
				// x3 = 2*x2 - x1
				int x3 = (2 * x2) - x1;
				if (binarySearch(a3, x3))
					collinearCount++;
			}
		}
		return collinearCount;
	}

	// ----------------------------------------------------------
	/**
	 * Sorts an array of integers according to InsertionSort. This method is static,
	 * thus it can be called as Collinear.sort(a)
	 * 
	 * @param a: An UNSORTED array of integers.
	 * @return after the method returns, the array must be in ascending sorted
	 *         order.
	 *
	 *         ----------------------------------------------------------
	 *
	 *         Order of Growth -------------------------
	 *
	 *         Caclulate and write down the order of growth of your algorithm. You
	 *         can use the asymptotic notation. You should adequately explain your
	 *         answer. Answers without adequate explanation will not be counted.
	 *
	 *         Order of Growth: N^2
	 *
	 *         Explanation: Two linear for-loops.
	 *
	 */
	static void sort(int[] a) {
		for (int j = 1; j < a.length; j++) {
			int i = j - 1;
			while (i >= 0 && a[i] > a[i + 1]) {
				int temp = a[i];
				a[i] = a[i + 1];
				a[i + 1] = temp;
				i--;
			}
		}
	}

	// ----------------------------------------------------------
	/**
	 * Searches for an integer inside an array of integers. This method is static,
	 * thus it can be called as Collinear.binarySearch(a,x)
	 * 
	 * @param a: A array of integers SORTED in ascending order.
	 * @param x: An integer.
	 * @return true if 'x' is contained in 'a'; false otherwise.
	 *
	 *         ----------------------------------------------------------
	 *
	 *         Order of Growth -------------------------
	 *
	 *         Caclulate and write down the order of growth of your algorithm. You
	 *         can use the asymptotic notation. You should adequately explain your
	 *         answer. Answers without adequate explanation will not be counted.
	 *
	 *         Order of Growth: log N
	 *
	 *         Explanation: divide log N (the binary search step) until we found our element.
	 *
	 */

	static boolean binarySearch(int[] a, int x) {
		// TODO: implement this method
		int left = 0;
		int right = a.length - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (x > a[mid]) left = mid + 1;
			else if(x < a[mid]) right = mid - 1;
			else
				return true;
		}
		return false;

	}
}