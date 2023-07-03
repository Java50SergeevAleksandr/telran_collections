package telran.util;

import java.util.function.Predicate;

public interface List<T> extends Collection<T> {
	void add(int index, T obj);

	T get(int index);

	T set(int index, T obj);

	T remove(int index);

	int indexOf(T pattern);

	int lastIndexOf(T pattern);

	int indexOf(Predicate<T> predicate);

	int lastIndexOf(Predicate<T> predicate);

}