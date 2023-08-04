package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

import telran.util.Map;
import telran.util.Map.Entry;

import org.junit.jupiter.api.Test;

abstract class MapTest {
	String[] keys = { "lmn", "abc", "ab", "a" };
	Integer[] values = { 3, 2, 2, 1 };
	protected Map<String, Integer> map;

	@BeforeEach
	void setUp() {
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
	}

	@Test
	void getTest() {
		for (int i = 0; i < keys.length; i++) {
			assertEquals(values[i], map.get(keys[i]));
		}
	}

	@Test
	void containsKeyTest() {
		Arrays.stream(keys).forEach(k -> assertTrue(map.containsKey(k)));
		assertFalse(map.containsKey("12345"));
	}

	@Test
	void containsValueTest() {
		Arrays.stream(values).forEach(v -> assertTrue(map.containsValue(v)));
		assertFalse(map.containsValue(12345));
	}

	@SuppressWarnings("unchecked")
	@Test
	void entrySetTest() {

		Entry<String, Integer>[] entriesExpected = new Entry[keys.length];
		for (int i = 0; i < keys.length; i++) {
			entriesExpected[i] = new Entry<>(keys[i], values[i]);
		}
		;
		entriesExpected = getEntriesExpected(entriesExpected);
		Entry<String, Integer>[] entriesActual = getEntriesActual(map.entrySet().toArray(new Entry[0]));
		assertArrayEquals(entriesExpected, entriesActual);
	}

	@Test
	void keySetTest() {
		String[] expected = getKeysExpected(keys);
		String[] actual = getKeysActual(map.keySet().toArray(new String[0]));
		assertArrayEquals(expected, actual);
	}

	@Test
	void valuesTest() {
		Integer[] expected = getValuesExpected(values);
		Integer[] actual = getValuesActual(map.values().toArray(new Integer[0]));
		assertArrayEquals(expected, actual);
	}

	@Test
	void removeTest() {
		Integer[] removedValues = Arrays.stream(keys).map(map::remove).toArray(size -> new Integer[size]);
		assertArrayEquals(values, removedValues);
		assertNull(map.remove(keys[0]));
		assertEquals(0, map.entrySet().size());
	}

	protected Integer[] getValuesActual(Integer[] values) {
		Arrays.sort(values);
		return values;
	}

	protected Integer[] getValuesExpected(Integer[] values) {
		Integer[] res = Arrays.copyOf(values, values.length);
		Arrays.sort(res);
		return res;
	};

	protected abstract String[] getKeysActual(String[] keys);

	protected String[] getKeysExpected(String[] keys) {
		String[] res = Arrays.copyOf(keys, keys.length);
		Arrays.sort(res);
		return res;
	}

	protected abstract Entry<String, Integer>[] getEntriesActual(Entry<String, Integer>[] entries);

	protected Entry<String, Integer>[] getEntriesExpected(Entry<String, Integer>[] entries) {
		Arrays.sort(entries);
		return entries;
	}
}
