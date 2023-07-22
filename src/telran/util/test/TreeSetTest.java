package telran.util.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Collection;
import telran.util.TreeSet;

class TreeSetTest extends SetTest {
	TreeSet<Integer> treeSet;

	@Override
	@BeforeEach
	void setUp() {
		treeSet = new TreeSet<Integer>();
		collection = treeSet;
		super.setUp();

	}

	@Override
	protected Collection<Integer> getCollection(Integer[] ar) {
		TreeSet<Integer> res = new TreeSet<>();
		for (Integer num : ar) {
			res.add(num);
		}
		return res;
	}

	@Override
	protected void runArrayTest(Integer[] expected, Integer[] actual) {
		Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
		Integer[] actualSorted = Arrays.copyOf(actual, actual.length);
		Arrays.sort(expectedSorted);
		assertArrayEquals(expectedSorted, actualSorted);

	}

	@Test
	protected void getNodeTest() {
		assertTrue(collection.contains(numbers[0]));
		assertTrue(collection.contains(10));
		assertFalse(collection.contains(500));
		collection.clear();
		assertFalse(collection.contains(10));
	}

	@Test
	protected void getFirstTest() {
		assertEquals(Integer.valueOf(-20), treeSet.first());
		collection.clear();
		assertNull(treeSet.first());
	}

	@Test
	protected void getLastTest() {
		assertEquals(Integer.valueOf(100), treeSet.last());
		collection.clear();
		assertNull(treeSet.last());
	}

	@Test
	void ceilingTest() {
		assertEquals(Integer.valueOf(-20), treeSet.ceiling(-30));
		assertEquals(Integer.valueOf(-20), treeSet.ceiling(-20));
		assertEquals(Integer.valueOf(100), treeSet.ceiling(80));
		assertEquals(Integer.valueOf(100), treeSet.ceiling(100));
		assertNull(treeSet.ceiling(200));
		collection.clear();
		assertNull(treeSet.ceiling(1));
	}

	@Test
	void floorTest() {
		assertEquals(Integer.valueOf(100), treeSet.floor(200));
		assertEquals(Integer.valueOf(100), treeSet.floor(100));
		assertEquals(Integer.valueOf(30), treeSet.floor(80));
		assertEquals(Integer.valueOf(-20), treeSet.floor(-20));
		assertNull(treeSet.floor(-30));
		collection.clear();
		assertNull(treeSet.ceiling(1));
	}
}
