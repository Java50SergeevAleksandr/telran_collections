package telran.recursion;

import static org.junit.jupiter.api.Assertions.*;
import static telran.recursion.LinearRecursion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinearRecursionTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void functionTest() {
		function(3);
		function(30);
	}

	@Test
	void factorialTest() {
		assertThrowsExactly(IllegalArgumentException.class, () -> factorial(-6));
		assertEquals(6, factorial(3));
		assertEquals(24, factorial(4));
	}

	@Test
	void powTest() {
		assertThrowsExactly(IllegalArgumentException.class, () -> pow(10, -3));
		assertEquals(-10, pow(-10, 1));
		assertEquals(100, pow(-10, 2));
		assertEquals(-1000, pow(-10, 3));
		assertEquals(100, pow(-10, 2));
		assertEquals(1000, pow(10, 3));
	}

	@Test
	void sumTest() {
		assertEquals(100, sum(10, 10));
		assertEquals(-100, sum(-10, -10));
		assertEquals(-1000, sum(-100, 10));
		assertEquals(9, sum(3, 3));
		assertEquals(-25, sum(-5, -5));
	}

	@Test
	void displayArrayTest() {
		System.out.println("\nDiaplaying range [1-5]");
		int ar[] = { 1, 2, 3, 4, 5 };
		displayArray(ar);
	}

	@Test
	void displayArrayReverseTest() {
		System.out.println("\nDiaplaying reversed range [1-5]");
		int ar[] = { 1, 2, 3, 4, 5 };
		displayReversedArray(ar);
	}

	@Test
	void sumArrayTest() {
		int ar[] = { 1, 2, 3, 4, 5 };
		assertEquals(15, sumArray(ar));
	}

	@Test
	void reverseArrayTest() {
		int ar[] = { 1, 2, 3, 4, 5 };
		int expected[] = { 5, 4, 3, 2, 1 };
		reverseArray(ar);
		assertArrayEquals(expected, ar);
	}

	@Test
	void squareTest() {
		assertEquals(4, square(2));
		assertEquals(9, square(3));
		assertEquals(100, square(10));
		assertEquals(100, square(-10));
	}

	@Test
	void isSubstringTest() {
		String str = "blobloblobloblokbloblo.";		
		assertTrue(isSubstring(str, "blok"));
		assertTrue(isSubstring(str, "kblob"));
		assertTrue(isSubstring(str, "oblo."));
		assertFalse(isSubstring(str, "blod"));
		assertFalse(isSubstring(str, "bok"));
		assertFalse(isSubstring(str, "loko"));
	}

}