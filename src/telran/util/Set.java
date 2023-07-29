package telran.util;

public interface Set<T> extends Collection<T> {
	T get(Object pattern);

	@SuppressWarnings("unchecked")
	default boolean setEqualsTo(Object other) {		
		if (this == other) {
			return true;
		}
		if (!(other instanceof Set)) {
			return false;
		}
		Set<T> otherSet = (Set<T>) other;
		return size() == otherSet.size() && containsAll(otherSet);
	}
}