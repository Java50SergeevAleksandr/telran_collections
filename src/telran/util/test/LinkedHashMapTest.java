package telran.util.test;

import org.junit.jupiter.api.BeforeEach;

import telran.util.LinkedHashMap;
import telran.util.Map.Entry;

public class LinkedHashMapTest extends MapTest {
	@BeforeEach
	@Override
	void setUp() {
		map = new LinkedHashMap<>();
		super.setUp();
	}

	@Override
	protected String[] getKeysActual(String[] keys) {

		return keys;
	}

	@Override
	protected Entry<String, Integer>[] getEntriesActual(Entry<String, Integer>[] entries) {

		return entries;
	}

	protected Entry<String, Integer>[] getEntriesExpected(Entry<String, Integer>[] entries) {

		return entries;
	}

	protected String[] getKeysExpected(String[] keys) {

		return keys;
	}

	protected Integer[] getValuesExpected(Integer[] values) {

		return values;
	};

	protected Integer[] getValuesActual(Integer[] values) {

		return values;
	}
}
