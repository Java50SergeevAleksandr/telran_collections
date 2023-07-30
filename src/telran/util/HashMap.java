package telran.util;

public class HashMap<K, V> extends AbstractMap<K, V> {
	public HashMap() {
		set = new HashSet<>();
	}

	@Override
	protected Set<K> getEmptyKeySet() {
		Set<K> res = new HashSet<>();
		return res;
	}

}