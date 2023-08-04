package telran.util.test;

import org.junit.jupiter.api.BeforeEach;

import telran.util.Map.Entry;
import telran.util.TreeMap;

public class TreeMapTest extends MapTest {
	@BeforeEach
	@Override
	void setUp() {
		map = new TreeMap<>();
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
}