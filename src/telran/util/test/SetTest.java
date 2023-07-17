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

}
