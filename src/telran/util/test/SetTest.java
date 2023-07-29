package telran.util.test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import telran.util.Set;

public abstract class SetTest extends CollectionTest {

	@Override
	@Test
	void addTest() {
		assertFalse(collection.add(numbers[0]));
		runArrayTest(numbers, collection.toArray(new Integer[0]));
		assertTrue(collection.add(1000000));

	}

	@Test
	void getPatternTest() {
		assertEquals(numbers[1], ((Set<Integer>) collection).get(numbers[1]));
		assertNull(((Set<Integer>) collection).get(100000));
	}

	@Test
	void equalsTest() {
		// Compare equality of Sets, order of elements must not be compared
		var collection2 = getCollection(numbers);
		assertEquals(collection, collection2);

		// swap elements
		int temp = numbers[0];
		numbers[0] = numbers[1];
		numbers[1] = temp;
		collection2 = getCollection(numbers);
		assertEquals(collection, collection2);

		collection2.add(888);
		assertNotEquals(collection, collection2);
	}
}
