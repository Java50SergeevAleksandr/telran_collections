package telran.util.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.util.Collection;

import telran.util.LinkedTreeMap;

class LinkedTreeMapTest extends SetTest {
	@BeforeEach
	@Override
	void setUp() {
		collection = new LinkedTreeMap<Integer>();
		super.setUp();
	}

	@Override
	protected Collection<Integer> getCollection(Integer[] ar1) {
		Collection<Integer> res = new LinkedTreeMap<Integer>();
		for (Integer num : ar1) {
			res.add(num);
		}
		return res;
	}

	@Override
	protected void runArrayTest(Integer[] expected, Integer[] actual) {
		Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
		Arrays.sort(expectedSorted);
		assertArrayEquals(expectedSorted, actual);
	}

}
