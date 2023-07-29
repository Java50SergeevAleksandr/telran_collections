package telran.util;

import java.util.Iterator;
import java.util.function.Predicate;

public interface List<T> extends Collection<T> {
	void add(int index, T obj);

	T get(int index);

	T set(int index, T obj);

	T remove(int index);

	int indexOf(Predicate<T> predicate);

	int lastIndexOf(Predicate<T> predicate);

	@Override
	default boolean remove(Object pattern) {
		int index = indexOf(pattern);
		boolean res = false;
		if (index >= 0) {
			res = true;
			remove(index);
		}
		return res;
	}

	@Override
	default boolean contains(Object pattern) {
		return indexOf(pattern) >= 0;
	}

	default void indexValidation(int index, boolean sizeInclusive) {
		int size = size();
		int bounder = sizeInclusive ? size : size - 1;
		if (index < 0 || index > bounder) {
			throw new IndexOutOfBoundsException(index);
		}
	}

	default int indexOf(Object pattern) {
		return indexOf(Predicate.isEqual(pattern));
	}

	default int lastIndexOf(Object pattern) {
		return lastIndexOf(Predicate.isEqual(pattern));
	}

	@SuppressWarnings("unchecked")
	default boolean listEqualsTo(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof List)) {
			return false;
		}
		List<T> otherList = (List<T>) other;

		boolean res = false;
		Iterator<T> it = this.iterator();
		if (otherList.size() == this.size()) {
			for (T obj : otherList) {
				if (!obj.equals(it.next())) {
					return false;
				}
				res = true;
			}
		}

		return res;
	}

}