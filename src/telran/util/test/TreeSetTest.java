package telran.util.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Collection;
import telran.util.TreeSet;

class TreeSetTest extends SetTest {

	@Override
	@BeforeEach
	void setUp() {
		collection = new TreeSet<Integer>();
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
		int exp = -20;
		assertEquals((Integer) exp, ((TreeSet<Integer>) collection).first());
		collection.clear();
		assertEquals(null, ((TreeSet<Integer>) collection).first());
	}

	@Test
	protected void getLastTest() {
		int exp = 100;
		assertEquals((Integer) exp, ((TreeSet<Integer>) collection).last());
		collection.clear();
		assertEquals(null, ((TreeSet<Integer>) collection).last());
	}

	@Test
	protected void addRemoveTest() {
		assertTrue(collection.add(7));
		assertTrue(collection.remove(8));
	}
}
