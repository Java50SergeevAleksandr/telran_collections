package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.ArrayList;

class ArrayListTest {
	ArrayList<Integer> testIntList;

	@BeforeEach
	void setUp() {
		testIntList = new ArrayList<Integer>();
		testIntList.add(10);
		testIntList.add(100);
	}

	@Test
	void addObjGetTest() {
		assertEquals(10, testIntList.get(0));
	}

	@Test
	void sizeTest() {
		assertEquals(2, testIntList.size());
	}

	@Test
	void reallocateTest() {
		for (int i = 0; i < 16; i++) {
			testIntList.add(10);
		}
		assertEquals(18, testIntList.size());
	}

	@Test
	void removeObjTest() {
		Integer v = 10;
		assertTrue(testIntList.remove(v));
		assertEquals(100, testIntList.get(0));
		assertFalse(testIntList.remove(null));

	}

	@Test
	void toArrayTest() {
		Integer[] arr = new Integer[0];
		Integer[] expected = { 10, 100 };
		arr = testIntList.toArray(arr);
		assertArrayEquals(expected, arr);

		Integer[] arr2 = { 2, 2, 2, 2 };
		Integer[] expected2 = { 10, 100, null, 2 };
		arr2 = testIntList.toArray(arr2);
		assertArrayEquals(expected2, arr2);
	}

	@Test
	void removeIfTest() {
		assertTrue(testIntList.removeIf(v -> v == 10));
		assertEquals(100, testIntList.get(0));
		assertEquals(1, testIntList.size());
		assertFalse(testIntList.removeIf(v -> v < 10));
	}

	@Test
	void addAllTest() {
		ArrayList<Integer> cl = new ArrayList<Integer>();
		cl.add(15);
		cl.add(20);
		Integer[] expected = { 10, 100, 15, 20 };
		assertTrue(testIntList.addAll(cl));
		assertEquals(expected, testIntList.toArray(expected));
	}

	@Test
	void removeAllTest() {
		ArrayList<Integer> cl = new ArrayList<Integer>();
		cl.add(15);
		cl.add(20);
		Integer[] expected = { 10, 100 };
		assertFalse(testIntList.removeAll(cl));
		assertTrue(testIntList.addAll(cl));
		assertTrue(testIntList.removeAll(cl));
		assertEquals(expected, testIntList.toArray(expected));
	}

	@Test
	void addByIndexTest() {
		Integer[] expected = { 15, 10, 100 };
		testIntList.add(0, 15);
		assertEquals(expected, testIntList.toArray(expected));
	}

	@Test
	void setTest() {
		Integer[] expected = { 15, 100 };
		assertEquals(10, testIntList.set(0, 15));
		assertEquals(expected, testIntList.toArray(expected));
	}

	@Test
	void removeByIndexTest() {
		Integer[] expected = { null, 100 };
		assertEquals(10, testIntList.remove(0));
		assertEquals(expected, testIntList.toArray(expected));
	}

	@Test
	void indexOfTest() {
		testIntList.add(10);
		assertEquals(0, testIntList.indexOf(10));
		assertEquals(-1, testIntList.indexOf(2));
	}

	@Test
	void lastindexOfTest() {
		testIntList.add(10);
		assertEquals(2, testIntList.lastIndexOf(10));
		assertEquals(-1, testIntList.lastIndexOf(2));
	}

	@Test
	void indexOfPredicateTest() {
		testIntList.add(10);
		assertEquals(0, testIntList.indexOf(x -> x < 100));
		assertEquals(-1, testIntList.indexOf(x -> x > 100));
	}

	@Test
	void lastIndexOfPredicateTest() {
		testIntList.add(10);
		assertEquals(2, testIntList.lastIndexOf(x -> x < 100));
		assertEquals(-1, testIntList.lastIndexOf(x -> x > 100));
	}

	@Test
	void iteratorTest() {
		Iterator<Integer> it = testIntList.iterator();
		while (it.hasNext()) {
			it.next();
		}
		assertThrowsExactly(NoSuchElementException.class, () -> it.next());

	}
}
