package telran.util;

import java.util.function.Predicate;

public interface Collection<T> extends Iterable<T> {
	boolean add(T obj);

	boolean remove(Object pattern);

	T[] toArray(T[] array);

	boolean removeIf(Predicate<T> predicate);

	int size();

	boolean addAll(Collection<T> collection);

	boolean removeAll(Collection<T> collection);
}
