package telran.util;

public interface Set<T> extends Collection<T> {
	T get(Object pattern);

	default boolean setEqualsTo(Object other) {
		// Checks other is Set containing the same elements (no checks of order)
		if (this == other) {
			return true;
		}
		if (!(other instanceof Set)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Set<T> otherSet = (Set<T>) other;
		return size() == otherSet.size() && containsAll(otherSet);
	}
}