package telran.util;

public class TreeMap<K, V> extends AbstractMap<K, V> {
	public TreeMap() {
		set = new TreeSet<>();
	}

	@Override
	protected Set<K> getEmptyKeySet() {

		return new TreeSet<>();
	}

}