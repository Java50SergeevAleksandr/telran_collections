package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ArrayList<T> implements List<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private T[] array;
	private int size = 0;

	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	@Override
	public boolean add(T obj) {
		if (size == array.length) {
			reallocate();
		}
		array[size++] = obj;
		return true;
	}

	private void reallocate() {
		array = Arrays.copyOf(array, array.length * 2);

	}

	@Override
	public boolean remove(Object pattern) {
		if (pattern != null) {
			for (int i = 0; i < size; i++) {
				if (pattern.equals(array[i])) {
					remove(i);
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public T[] toArray(T[] ar) {
		T[] res = ar.length < size ? Arrays.copyOf(ar, size) : ar;
		int index = 0;
		for (T obj : this) {
			res[index++] = obj;
		}
		if (res.length > size) {
			res[size] = null;
		}
		return res;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		boolean isRemoved = false;
		for (T obj : this) {
			if (predicate.test(obj)) {
				remove(obj);
				isRemoved = true;
			}
		}

		return isRemoved;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean addAll(Collection<T> collection) {
		boolean res = false;
		for (T obj : collection) {
			res = add(obj);
		}
		return res;
	}

	@Override
	public boolean removeAll(Collection<T> collection) {
		boolean res = false;
		for (T obj : collection) {
			res = remove(obj);

		}
		return res;

	}

	@Override
	public void add(int index, T obj) {
		if (size == array.length) {
			reallocate();
		}
		System.arraycopy(array, index, array, index + 1, size - index);
		array[index] = obj;
	}

	@Override
	public T get(int index) {
		indexValidation(index, false);
		return array[index];
	}

	@Override
	public T set(int index, T obj) {
		indexValidation(index, false);
		T old = array[index];
		array[index] = obj;
		return old;
	}

	@Override
	public T remove(int index) {
		indexValidation(index, false);
		T res = array[index];
		size--;
		System.arraycopy(array, 0, array, 0, index);
		System.arraycopy(array, index + 1, array, index, size - index);
		array[size] = null;
		return res;
	}

	private void indexValidation(int index, boolean sizeInclusive) {
		int bounder = sizeInclusive ? size : size - 1;
		if (index < 0 || index > bounder) {
			throw new IndexOutOfBoundsException(index);
		}

	}

	@Override
	public int indexOf(T pattern) {
		if (pattern != null) {
			for (int i = 0; i < size; i++) {
				if (pattern.equals(array[i])) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(T pattern) {
		if (pattern != null) {
			for (int i = size - 1; i >= 0; i--) {
				if (pattern.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		for (int i = 0; i < size; i++) {
			if (predicate.test(array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		for (int i = size - 1; i >= 0; i--) {
			if (predicate.test(array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<T> {
		int current = 0;

		@Override
		public boolean hasNext() {
			return current < size;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return array[current++];
		}

	}
}
