package telran.util.test;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;

import telran.util.Collection;
import telran.util.LinkedHashSet;

public class LinkedHashSetTest extends SetTest {
	@BeforeEach
	@Override
	void setUp() {
		collection = new LinkedHashSet<Integer>();
		super.setUp();
	}

	@Override
	protected Collection<Integer> getCollection(Integer[] ar1) {
		Collection<Integer> res = new LinkedHashSet<Integer>();
		for (Integer num : ar1) {
			res.add(num);
		}
		return res;
	}

	@Override
	protected void runArrayTest(Integer[] expected, Integer[] actual) {
		assertArrayEquals(expected, actual);

	}

}
