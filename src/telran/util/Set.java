package telran.util;

public interface Set<T> extends Collection<T> {
	T get(Object pattern);

	default boolean setEqualsTo(Object other) {
		// TODO Checks other is Set containing the same elements (no checks of order)
		return false;
	}
}