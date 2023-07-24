package telran.recursion;

public class LinearRecursion {

	public static void function(int a) {
		if (a > 3) {
			function(a - 1);
		}

	}

	public static long pow(int a, int b) {
		// long res = 1;
		// if (b < 0) {
		// throw new IllegalArgumentException("degree cannot be a negative");
		// }
		// if (b > 0) {
		// res = a * pow(a, b - 1);
		// }
		// return res;

		// HW #17 definition
		// Write method pow with following limitations
		// No cycles
		// Arithmetic operations allowed +; - only
		// if Additional functions applied then only with the same limitations

		// long sq = sum(a, a);

		return power(a, a, b);
	}

	private static long power(long num, int a, int b) {
		long res = num;
		if (b < 0) {
			throw new IllegalArgumentException("degree cannot be a negative");
		}
		if (b > 1) {
			res = sum(num, a);
			if (a < 0 && num < 0 || a > 0 && num > 0) {
				res = Math.abs(res);
			} else {
				res = Math.negateExact(res);
			}
			res = power(res, a, b - 1);
		}
		return res;

	}

	public static long sum(long number, int counter) {
		counter = Math.abs(counter);
		long res = 0;
		if (counter > 0) {
			res = number + sum(number, counter - 1);
		}
		return res;
	}

	public static int square(int x) {
		// returns x ^ 2
		// With following limitations
		// No cycles
		// No any additional methods
		// No static fields
		// Only + ; - arithmetic operations
		x = Math.abs(x);
		int res = 1;
		if (x > 0) {
			res = x + square(x - 1);
		}
		return res + x - 1;
	}

	public static boolean isSubstring(String string, String substr) {

		// TODO
		// returns true if a given 'substr' is indeed the //substring of a given
		// `string`

		/*
		 * Challenges: 1. To apply only following methods of the class String:
		 * charAt(int ind);
		 * 
		 * String substring(int ind);
		 * 
		 * int length();
		 * 
		 * 2. No cycles;
		 */
		if (string.length() < substr.length() || string.length() == 0) {
			throw new IllegalArgumentException("substing is bigger or empty");
		}
		String main = isSubstring(0, string, substr);

		return main.length() == 0 ? true : false;

	}

	private static String isSubstring(int index, String string, String substr) {
		String main = string.substring(index);
		boolean res = checkMatch(main, substr);
		if (res) {
			main = main.substring(main.length());
		}
		if (main.length() >= substr.length() ) { //&& main.length() != 1
			main = isSubstring(1, main, substr);
		}

		return main;
	}

	private static boolean checkMatch(String main, String substr) {
		int counter = substr.length() - 1;
		boolean res = true;

		res = recMatch(counter, main, substr, res);

		return res;

	}

	private static boolean recMatch(int index, String main, String substr, boolean res) {
		if (index == -1) {
			res = true;
		} else if (res && main.charAt(index) == substr.charAt(index)) {
			res = recMatch(index - 1, main, substr, res);
		} else {
			res = false;
		}
		return res;
	}

	public static long factorial(int n) {
		long res = 1;
		if (n < 0) {
			throw new IllegalArgumentException("factorial exists only for positive numbers and 0");
		}
		if (n > 0) {
			res = n * factorial(n - 1);
		}
		return res;

	}

	public static void displayArray(int[] ar) {
		displayArray(0, ar, false);
	}

	private static void displayArray(int index, int[] ar, boolean isReverse) {
		if (index < ar.length) {
			if (isReverse) {
				displayArray(index + 1, ar, isReverse);
				System.out.print(ar[index] + " ");
			} else {
				System.out.print(ar[index] + " ");
				displayArray(index + 1, ar, isReverse);
			}
		}

	}

	public static void displayReversedArray(int[] ar) {
		displayArray(0, ar, true);
	}

	public static long sumArray(int[] array) {
		return sumArray(0, array);
	}

	private static long sumArray(int index, int[] array) {
		long res = 0;
		if (index < array.length) {
			res = array[index] + sumArray(index + 1, array);
		}
		return res;
	}

	public static void reverseArray(int[] array) {
		reverseArray(0, array, array.length - 1);
	}

	private static void reverseArray(int left, int[] array, int right) {
		if (left < right) {
			int tmp = array[left];
			array[left] = array[right];
			array[right] = tmp;
			reverseArray(left + 1, array, right - 1);
		}

	}

}
