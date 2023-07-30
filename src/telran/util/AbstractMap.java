package telran.util;

import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {

	protected Set<Entry<K, V>> set;

	@Override
	public V remove(Object key) {
		Entry<K, V> pattern = new Entry<>((K) key, null);
		Entry<K, V> entry = set.get(pattern);
		V res = null;
		if (entry != null) {
			res = entry.getValue();
			set.remove(pattern);
		}

		return res;
	}

	@Override
	public V get(Object key) {
		Entry<K, V> pattern = new Entry<>((K) key, null);

		Entry<K, V> entry = set.get(pattern);

		return entry == null ? null : entry.getValue();
	}

	@Override
	public V put(K key, V value) {
		Entry<K, V> newEntry = new Entry<>(key, value);
		Entry<K, V> entry = set.get(newEntry);
		V res = null;
		if (entry != null) {
			res = entry.getValue();
			entry.setValue(value);
		} else {
			set.add(newEntry);
		}
		return res;
	}

	@Override
	public boolean containsKey(Object key) {
		Entry<K, V> pattern = new Entry<>((K) key, null);

		return set.contains(pattern);
	}

	@Override
	public int size() {

		return set.size();
	}

	@Override
	public boolean containsValue(Object value) {
		Iterator<Entry<K, V>> it = set.iterator();
		boolean res = false;
		while (it.hasNext() && !res) {
			Entry<K, V> entry = it.next();
			if (entry.getValue().equals(value)) {
				res = true;
			}
		}
		return res;
	}

	@Override
	public Set<K> keySet() {
		Set<K> res = getEmptyKeySet();
		for (Entry<K, V> entry : set) {
			res.add(entry.getKey());
		}
		return res;
	}

	protected abstract Set<K> getEmptyKeySet();

	@Override
	public Set<Entry<K, V>> entrySet() {

		return set;
	}

	@Override
	public Collection<V> values() {
		ArrayList<V> res = new ArrayList<>();
		for (Entry<K, V> entry : set) {
			res.add(entry.getValue());
		}
		return res;
	}

}
