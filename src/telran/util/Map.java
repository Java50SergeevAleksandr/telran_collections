package telran.util;

import java.util.Objects;

public interface Map<K, V> {
	public static class Entry<K, V> implements Comparable<Entry<K, V>> {

		@Override
		public int hashCode() {
			return Objects.hash(key);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry<?, ?> other = (Entry<?, ?>) obj;
			return Objects.equals(key, other.key);
		}

		private final K key;
		private V value;

		public Entry(K key, V value) {

			this.key = key;
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		@Override
		public int compareTo(Entry<K, V> o) {

			return ((Comparable<K>) key).compareTo(o.key);
		}
	}

	V remove(Object key);
	
	V get(Object key);

	default V getOrDefault(Object key, V defaultValue) {
		V res = defaultValue;
		V value = get(key);
		if (value != null) {
			res = value;
		}
		return res;
	}

	V put(K key, V value);

	boolean containsKey(Object key);

	default V putIfAbsent(K key, V value) {
		V res = get(key);
		if (res == null) {
			put(key, value);
		}
		return res;
	}

	int size();

	default boolean isEmpty() {
		return size() == 0;
	}

	boolean containsValue(Object value);

	Set<K> keySet();

	Set<Entry<K, V>> entrySet();

	Collection<V> values();
}
